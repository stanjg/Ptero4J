import com.stanjg.ptero4j.PteroAPI;
import util.Config;

public class Main {

    public static void main(String[] args) {
        new Config();

        new PteroAPI(Config.getBaseURL(), Config.getKey());
    }

}
