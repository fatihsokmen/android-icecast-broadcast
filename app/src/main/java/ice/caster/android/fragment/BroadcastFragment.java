package ice.caster.android.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import ice.caster.android.R;
import ice.caster.android.shout.Config;
import ice.caster.android.shout.Encoder;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class BroadcastFragment extends Fragment {

    /**
     * Icecast host
     */
    static final String ICE_HOST    = "aaa.bbb.ccc.ddd";

    /**
     * Broadcast port that server listens incoming streams
     */
    static final int    ICE_PORT    = 8002;

    /**
     * Mount point of incoming source
     */
    static final String ICE_MOUNT   = "/test";

    /**
     * Credentials
     */
    static final String ICE_USER    = "user";
    static final String ICE_PASS    = "pass";

    @InjectView(R.id.start)
    Button start;
    @InjectView(R.id.stop)
    Button stop;
    @InjectView(R.id.status)
    TextView status;

    private Encoder encoder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        encoder = new Encoder(
                new Config().host(ICE_HOST).port(ICE_PORT).mount(ICE_MOUNT)
                        .username(ICE_USER).password(ICE_PASS).sampleRate(8000));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_broadcast, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.inject(this, view);

        encoder.setHandle(new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case Encoder.MSG_REC_STARTED:
                        status.setText("Streaming");
                        break;
                    case Encoder.MSG_REC_STOPPED:
                        status.setText("");
                        break;
                    case Encoder.MSG_ERROR_GET_MIN_BUFFERSIZE:
                        status.setText("");
                        Toast.makeText(getActivity(),
                                "MSG_ERROR_GET_MIN_BUFFERSIZE",
                                Toast.LENGTH_LONG).show();
                        break;
                    case Encoder.MSG_ERROR_REC_START:
                        status.setText("");
                        Toast.makeText(getActivity(), "Can not start recording",
                                Toast.LENGTH_LONG).show();
                        break;
                    case Encoder.MSG_ERROR_AUDIO_RECORD:
                        status.setText("");
                        Toast.makeText(getActivity(), "Error audio record",
                                Toast.LENGTH_LONG).show();
                        break;
                    case Encoder.MSG_ERROR_AUDIO_ENCODE:
                        status.setText("");
                        Toast.makeText(getActivity(), "Error audio encode",
                                Toast.LENGTH_LONG).show();
                        break;
                    case Encoder.MSG_ERROR_STREAM_INIT:
                        status.setText("");
                        Toast.makeText(getActivity(), "Can not init stream",
                                Toast.LENGTH_LONG).show();
                        break;
                    default:
                        break;
                }
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                encoder.start();
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                encoder.stop();
            }
        });
    }
}