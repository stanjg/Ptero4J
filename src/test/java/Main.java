import com.stanjg.ptero4j.PteroAdminAPI;
import util.Config;

public class Main {

    private static PteroAdminAPI api;

    public static void main(String[] args) {
        new Config();

        api = new PteroAdminAPI(Config.getBaseURL(), Config.getKey());

        System.out.println(api.getUsersController().getUser(90238408).delete());
    }

}
