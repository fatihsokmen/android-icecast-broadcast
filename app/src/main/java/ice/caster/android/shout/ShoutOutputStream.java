package ice.caster.android.shout;

import java.io.IOException;

/**
 * Created by fatih on 19/10/15.
 */
public class ShoutOutputStream {

    static {
        System.loadLibrary("shout");
        System.loadLibrary("shout-jni");
    }

    public ShoutOutputStream() {
    }

    /**
     * Init shout client environment and connect
     * @param url
     * @param port
     * @param mount
     * @param user
     * @param password
     * @throws IOException
     */
    public void init(String url, int port, String mount, String user, String password) throws IOException {
        boolean ready = jniInit(url, port, mount, user, password) > 0;
        if (!ready) {
            throw new IOException("Stream is not initialized");
        }
    }

    /**
     * Pass mp3 data bytes to shout send buffer
     * @param buffer
     * @param count
     * @throws IOException
     */
    public void write(byte[] buffer, int count) throws IOException {
        jniSend(buffer, count);
    }

    /**
     * Close shout connection and reset environment
     * @throws IOException
     */
    public void close() throws IOException {
        jniClose();
    }

    public native int jniInit(String url, int port, String mount, String user, String password);
    public native int jniSend(byte[] buffer, int length);
    public native int jniClose();
}
