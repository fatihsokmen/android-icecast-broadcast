/*
 * Original Copyright is below: Added shoutcast broadcasting rather than file output
 * Updated by Fatih Sokmen 01.11.2015
 *
 * Copyright (c) 2011-2012 Yuichi Hirano
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package ice.caster.android.shout;

import java.security.InvalidParameterException;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Handler;

/**
 */
public class Encoder {

	static {
		System.loadLibrary("lamemp3");
	}


	/**
	 */
	private boolean mIsRecording = false;

	/**
	 *
	 */
	private Handler mHandler;


	/**
	 * Upstream source
	 */
	private ShoutOutputStream shout;

    /**
     * Config
     */
    private Config config;

	/**
	 *
	 */
	public static final int MSG_REC_STARTED = 0;

	/**
	 */
	public static final int MSG_REC_STOPPED = 1;

	/**
	 */
	public static final int MSG_ERROR_GET_MIN_BUFFERSIZE = 2;


	/**
	 */
	public static final int MSG_ERROR_REC_START = 4;
	
	/**
	 */
	public static final int MSG_ERROR_AUDIO_RECORD = 5;

	/**
	 */
	public static final int MSG_ERROR_AUDIO_ENCODE = 6;

	/**
	 */
	public static final int MSG_ERROR_STREAM_INIT = 7;

	/**
	 */
	public static final int MSG_ERROR_CLOSE_STREAM = 8;


	/**
	 */
	public Encoder(Config config) {
		if (config.sampleRate <= 0) {
			throw new InvalidParameterException(
					"Invalid sample rate specified.");
		}
		this.config = config;
	}

	/**
	 */
	public void start() {
		if (mIsRecording) {
			return;
		}

		new Thread() {
			@Override
			public void run() {
				android.os.Process
						.setThreadPriority(android.os.Process.THREAD_PRIORITY_URGENT_AUDIO);

				final int minBufferSize = AudioRecord.getMinBufferSize(
						config.sampleRate, AudioFormat.CHANNEL_IN_MONO,
						AudioFormat.ENCODING_PCM_16BIT);
				if (minBufferSize < 0) {
					if (mHandler != null) {
						mHandler.sendEmptyMessage(MSG_ERROR_GET_MIN_BUFFERSIZE);
					}
					return;
				}

				AudioRecord audioRecord = new AudioRecord(
						MediaRecorder.AudioSource.MIC, config.sampleRate,
						AudioFormat.CHANNEL_IN_MONO,
						AudioFormat.ENCODING_PCM_16BIT, minBufferSize * 2);

				// PCM buffer size (5sec)
				short[] buffer = new short[config.sampleRate * (16 / 8) * 1 * 1]; // SampleRate[Hz] * 16bit * Mono * 5sec
				byte[] mp3buffer = new byte[(int) (7200 + buffer.length * 2 * 1.25)];

				shout = null;
				try {
					shout = new ShoutOutputStream();
					shout.init("188.226.213.202", 8002, "/test", "test", "testing");
				} catch (Exception e) { //FileNotFoundException
					if (mHandler != null) {
						mHandler.sendEmptyMessage(MSG_ERROR_STREAM_INIT);
					}
					return;
				}

				// Lame init
				Lame.init(config.sampleRate, 1, config.sampleRate, 32);

				mIsRecording = true;
				try {
					try {
						audioRecord.startRecording();
					} catch (IllegalStateException e) {
						if (mHandler != null) {
							mHandler.sendEmptyMessage(MSG_ERROR_REC_START);
						}
						return;
					}

					try {
						if (mHandler != null) {
							mHandler.sendEmptyMessage(MSG_REC_STARTED);
						}

						int readSize = 0;
						while (mIsRecording) {
							readSize = audioRecord.read(buffer, 0, minBufferSize);
							if (readSize < 0) {
								if (mHandler != null) {
									mHandler.sendEmptyMessage(MSG_ERROR_AUDIO_RECORD);
								}
								break;
							}
							else if (readSize == 0) {
								;
							}
							else {
								int encResult = Lame.encode(buffer,
										buffer, readSize, mp3buffer);
								if (encResult < 0) {
									if (mHandler != null) {
										mHandler.sendEmptyMessage(MSG_ERROR_AUDIO_ENCODE);
									}
									break;
								}
								if (encResult != 0) {
									try {
										shout.write(mp3buffer, encResult);
									} catch (Exception e) { //IOException
										if (mHandler != null) {
											mHandler.sendEmptyMessage(MSG_ERROR_AUDIO_ENCODE);
										}
										break;
									}
								}
							}
						}

						int flushResult = Lame.flush(mp3buffer);
						if (flushResult < 0) {
							if (mHandler != null) {
								mHandler.sendEmptyMessage(MSG_ERROR_AUDIO_ENCODE);
							}
						}
						if (flushResult != 0) {
							try {
								shout.write(mp3buffer, flushResult);
							} catch (Exception e) { //IOException
								if (mHandler != null) {
									mHandler.sendEmptyMessage(MSG_ERROR_AUDIO_ENCODE);
                                }
							}
						}

						try {
							shout.close();
						} catch (Exception e) { //IOException
							if (mHandler != null) {
								mHandler.sendEmptyMessage(MSG_ERROR_CLOSE_STREAM);
							}
						}
					} finally {
						audioRecord.stop();
						audioRecord.release();
					}
				} finally {
					Lame.close();
					mIsRecording = false;
				}

				if (mHandler != null) {
					mHandler.sendEmptyMessage(MSG_REC_STOPPED);
				}
			}
		}.start();
	}

	public void stop() {
		mIsRecording = false;

	}

	public boolean isRecording() {
		return mIsRecording;
	}

	/**
	 * 
	 * @param handler
	 *
	 */
	public void setHandle(Handler handler) {
		this.mHandler = handler;
	}
}