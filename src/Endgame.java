import java.io.File;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Endgame{
    public Endgame(){
    }

    @FXML
    Button button;
    @FXML
    ImageView imageView;
    @FXML
    Label result,scores;
    

    @FXML
    private void initialize(){

        Menu.mediaPlayer.pause();

        result.setText(Controller.resulttext);
        String tempt=Menu.Name+"'s last 5 scores : "; 

        for (int i = Main.scores.size()-1; i>Main.scores.size()-6 && i>=0; i--) {
            tempt+="\n"+Main.scores.get(i);
        }
        scores.setText(tempt);
        if(Controller.resulttext.equals("You Won !")) imageView.setVisible(false);

        Media media;
        if(Controller.resulttext.equals("You Won !")) media=new Media(new File("win.mp3").toURI().toString());
        else media=new Media(new File("lose.wav").toURI().toString());
        MediaPlayer mediaPlayer=new MediaPlayer(media);
        mediaPlayer.play();
    }

    public void playagain() throws Exception{
        Controller.score=0;
        Controller.xsball=700;
        Controller.ysball=550;
        AnchorPane pane = FXMLLoader.load(getClass().getResource("pooltable.fxml"));
        Main.stage.getScene().setRoot(pane);
        if(Menu.musicplaying==true)
            Menu.mediaPlayer.play();
    }
}
