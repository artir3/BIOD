package source;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("view_controller/Front.fxml")
        );
        Parent root = (Parent) loader.load();

        primaryStage.setTitle("Szyfrograf");
        primaryStage.setScene(new Scene(root, 500, 300));
        primaryStage.show();
    }



    public static void main(String[] args) {
        launch(args);
    }
}
