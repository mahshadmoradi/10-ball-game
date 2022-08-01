import java.util.ArrayList;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {

    public AnchorPane scene;
    public static Stage stage;
    public static ArrayList<Integer> scores=new ArrayList<>();

    @Override
    public void start(Stage primaryStage) throws Exception{
        stage=primaryStage;
        Image icon=new Image("/menu/icon.jpg");
        primaryStage.getIcons().add(icon);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Pool Game");
        AnchorPane root = FXMLLoader.load(getClass().getResource("menu.fxml"));
        Scene scene=new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
