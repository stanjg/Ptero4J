import com.stanjg.ptero4j.PteroAPI;
import com.stanjg.ptero4j.entities.panel.User;
import com.stanjg.ptero4j.util.PteroUtils;
import util.Config;

public class Main {

    private static PteroAPI api;

    public static void main(String[] args) {
        new Config();

        api = new PteroAPI(Config.getBaseURL(), Config.getKey());
    }

}
