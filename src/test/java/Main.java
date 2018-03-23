import com.stanjg.ptero4j.PteroAdminAPI;
import com.stanjg.ptero4j.PteroUserAPI;
import com.stanjg.ptero4j.actions.admin.users.UserCreateAction;
import util.Config;

public class Main {

    private static PteroAdminAPI api;

    public static void main(String[] args) {
        new Config();

        api = new PteroAdminAPI(Config.getBaseURL(), Config.getKey());

        System.out.println(api.getNodesController().getAllNodes().size());
    }

}
