import java.io.File;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Menu {

    @FXML
    Button button;
    @FXML
    TextField name;
    @FXML
    private Label wrongLogIn;
    @FXML
    private CheckBox music;
    @FXML
    private void initialize() {
        musicplaying=true;
        mediaPlayer.play();
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
    }

    static String Name;
    static Media media=new Media(new File("music.mp3").toURI().toString());
    static MediaPlayer mediaPlayer=new MediaPlayer(media);
    static boolean musicplaying;

    public void play() throws IOException{
        if(name.getText().isEmpty()) 
        wrongLogIn.setText("Please enter your name...");
        else{
            Name=name.getText();
            AnchorPane pane = FXMLLoader.load(getClass().getResource("pooltable.fxml"));
            mediaPlayer.setVolume(5);
            Main.stage.getScene().setRoot(pane);
        }
    }
    public void musicaction() {
        if(!music.isSelected())
        {
            mediaPlayer.pause();
            musicplaying=false;
        }  
        else
        {
            mediaPlayer.play();
            musicplaying=true;
        }    
    }
}
