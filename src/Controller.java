import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable{

    @FXML
    private AnchorPane scene;
    @FXML
    private Slider vSlider;
    @FXML
    Line line;
    @FXML
    private Label scorebox;
    @FXML
    private Label slbox;

    int ballnum=11;
    double ang,velocity;
    static int score=0,xsball=700,ysball=550;
    int sl=20;
    public static String resulttext;

    ImageView ball0Image=new ImageView(new Image("/Balls/Ball0.png"));
    ImageView ball1Image=new ImageView(new Image("/Balls/Ball1.png"));
    ImageView ball2Image=new ImageView(new Image("/Balls/Ball2.png"));
    ImageView ball3Image=new ImageView(new Image("/Balls/Ball3.png"));
    ImageView ball4Image=new ImageView(new Image("/Balls/Ball4.png"));
    ImageView ball5Image=new ImageView(new Image("/Balls/Ball5.png"));
    ImageView ball6Image=new ImageView(new Image("/Balls/Ball6.png"));
    ImageView ball7Image=new ImageView(new Image("/Balls/Ball7.png"));
    ImageView ball8Image=new ImageView(new Image("/Balls/Ball8.png"));
    ImageView ball9Image=new ImageView(new Image("/Balls/Ball9.png"));
    ImageView ball10Image=new ImageView(new Image("/Balls/Ball10.png"));

    ImageView cuestick=new ImageView(new Image("/table/cuestick.png"));


    Ball[] balls={
    new Ball(ball0Image,200, 285,0),new Ball(ball1Image,650, 285,1),new Ball(ball2Image,700, 260,2),
    new Ball(ball3Image,700, 305,3),new Ball(ball4Image,750, 240,4),new Ball(ball5Image,750, 285,5),
    new Ball(ball6Image,750, 330,6),new Ball(ball7Image,800, 220,7),new Ball(ball8Image,800, 265,8),
    new Ball(ball9Image,800, 310,9),new Ball(ball10Image,800, 355,10)};

    boolean shot=false,moveline=false,game=true;

    Timeline timeline = new Timeline(new KeyFrame(Duration.millis(10), new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent actionEvent) {
            if(game){
                for(int i=0;i<ballnum;i++){
                    balls[i].move();
                }
                for(int i=0;i<ballnum;i++)
                    for(int j=i+1;j<ballnum;j++)
                        Ball.collision(balls[i], balls[j]);
                if(!shot) {moveline=play(); shot=play();}
                scorebox.setText("score:"+Integer.toString(score));
                slbox.setText("shots left:"+Integer.toString(sl));
                boolean win=true;
                for(int i=1;i<ballnum;i++){
                    if(balls[i].y<550){
                        win=false;
                        break;
                    }
                }
                if(win){
                        resulttext="You Won !";
                        try {
                            end();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        game=false;
                }
                if(balls[0].y>550 || score<0){
                    resulttext="You Lost !";
                    try {
                        end();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    game=false;
            }
                //double mousex=MouseInfo.getPointerInfo().getLocation().x;
                //double mousey=MouseInfo.getPointerInfo().getLocation().y;
            }
        }
    }));


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        line.setVisible(false);
        for(int i=0;i<ballnum;i++)
            scene.getChildren().add(balls[i].getball());
        scene.getChildren().add(cuestick);
        vSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
                // TODO Auto-generated method stub
                velocity=vSlider.getValue();
                
            }
        });
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void end() throws IOException{
        Main.scores.add(score);
        AnchorPane pane = FXMLLoader.load(getClass().getResource("endgame.fxml"));
        Main.stage.getScene().setRoot(pane);
    }

    public boolean play(){
        for(int i=0;i<ballnum;i++)
            if(balls[i].getv()!=0)
                return false;
        return true;
    }

    public void Moveline(MouseEvent event) {
        if(moveline) {
            line.setVisible (true);
            line.setStroke (Color.WHITE);
            double x2 = event.getSceneX(), y2 = event.getSceneY();
            double x1 = balls[0].getball().getX(), y1 = balls[0].getball().getY();
            line.setStartX (-220+x1);
            line.setStartY (-287+y1);
            line.setEndX (-240+x2);
            line.setEndY (-302+y2);
            cuestick.setVisible (true);
            cuestick.setLayoutX ( x1 -300);
            cuestick.setLayoutY (y1 );
            ang = Math.toDegrees (Math.atan ((y2 - y1-balls[0].r) / (x2 - x1-balls[0].r)));
            if (x2 <= x1) {
                ang = 180 - ang;
                ang = -ang;
            }
            cuestick.setRotate (ang);
            double mid_x = cuestick.getLayoutX () + cuestick.getFitWidth () / 2;
            double mid_y = cuestick.getLayoutY () + cuestick.getFitHeight () / 2;
            double dist = (x1 - mid_x);
            double now_y = Math.sin (Math.toRadians (-ang)) * dist + mid_y;
            double now_x = mid_x + (dist - dist * Math.cos (Math.toRadians (ang)));
            double pos_x = now_x - cuestick.getFitWidth () / 2;
            double pos_y = now_y - cuestick.getFitHeight () / 2 + 4;
            cuestick.setLayoutX (pos_x-260);
            cuestick.setLayoutY (pos_y-10);
        }
    }
    public void stopmove(MouseEvent event){
        moveline=false;
    }
    public void shot(){
        if(shot){
            line.setVisible (false);
            cuestick.setVisible(false);
            sl--;
            if(sl<0) score-=8;
            balls[0].setv(Math.toRadians(ang),velocity);
            shot=false;
        }
    }
    public static void addtoscore(int num){
        score+=num;
    }
    public static void xsball(){
        if(xsball!=900) xsball+=50;
        else xsball=700;
    }
    public static int ysball(){
        if(xsball==700) ysball+=50;
        return ysball;
    }
}