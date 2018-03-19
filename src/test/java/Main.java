import com.stanjg.ptero4j.PteroAdminAPI;
import util.Config;

public class Main {

    private static PteroAdminAPI api;

    public static void main(String[] args) {
        new Config();

        api = new PteroAdminAPI(Config.getBaseURL(), Config.getKey());

        System.out.println(api.getServersController().getServersForUser(22).get(1).getName());
    }

}
