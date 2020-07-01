package ctf;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // instructs the javafx system not to exit implicitly when the last application window is shut.
        Platform.setImplicitExit(true);

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/main.fxml"));
        primaryStage.setTitle("Jiayu工具箱 by 五矿FLA9");
        primaryStage.setScene(new Scene(root, 640, 480));
        primaryStage.show();
    }
}
