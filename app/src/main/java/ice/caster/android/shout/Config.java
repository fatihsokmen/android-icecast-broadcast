package ice.caster.android.shout;

/**
 * Created by fatih on 01/11/15.
 */
public class Config {

    String host;
    String mount;
    String username;
    String password;
    int port;
    int sampleRate;

    /**
     * Shout host
     * @param host
     * @return
     */
    public Config host(String host) {
        this.host = host;
        return this;
    }

    /**
     * Input source port of mp3 data
     * @param port
     * @return
     */
    public Config port(int port) {
        this.port = port;
        return this;
    }

    /**
     * Server mount point of broadcast
     * @param mount
     * @return
     */
    public Config mount(String mount) {
        this.mount = mount;
        return this;
    }

    /**
     * Username
     * @param username
     * @return
     */
    public Config username(String username) {
        this.username = username;
        return this;
    }

    /**
     * Source password
     * @param password
     * @return
     */
    public Config password(String password) {
        this.password = password;
        return this;
    }

    /**
     * Audio sample rate
     * @param sampleRate
     * @return
     */
    public Config sampleRate(int sampleRate) {
        this.sampleRate = sampleRate;
        return this;
    }
}
