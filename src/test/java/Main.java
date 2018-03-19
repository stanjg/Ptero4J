import com.stanjg.ptero4j.PteroAdminAPI;
import com.stanjg.ptero4j.PteroUserAPI;
import com.stanjg.ptero4j.actions.admin.users.UserCreateAction;
import util.Config;

public class Main {

    private static PteroUserAPI api;

    public static void main(String[] args) {
        new Config();

        api = new PteroUserAPI(Config.getBaseURL(), Config.getKey());

        System.out.println("Sending 'help' to server");
        System.out.println(api.getServersController().getServer("8045c0d0").sendCommand("help"));
    }

}
