package ensemble;

import ensemble.generated.InterpolatorApp;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.stage.Stage;
//用来控制启动当前Stage
public class EmbeddedApplication {
    private static Stage TEMP_STAGE = new Stage() {
    };

    public static Node createApplication(String className) {
            System.out.println("EmbeddedApplication.createApplication()");
        Node node = null;
        try {
        	Class appClass = InterpolatorApp.class;
            System.out.println("appClass = " + appClass);
            Application app = (Application)appClass.newInstance();
            System.out.println("app = " + app);
            app.init();
            app.start(TEMP_STAGE);
            node = TEMP_STAGE.getScene().getRoot();
            System.out.println("node = " + node);
            TEMP_STAGE.setScene(null);
        } catch (Exception ex) {
            Logger.getLogger(EmbeddedApplication.class.getName()).log(Level.SEVERE, "Error loading application class", ex);
        }
        return node;
    }
}
