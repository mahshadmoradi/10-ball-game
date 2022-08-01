import java.io.File;

import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Ball{

    ImageView bi;
    double x;
    double y;
    double vx;
    double vy;
    double r=20;
    double mass=1;
    int ballnumber;
    int xmin=45,xmax=960,ymin=45,ymax=510,k=1;
    double w;

    public Ball(ImageView bi,double x,double y,int ballnumber) {
        this.bi=bi;
        this.x=x;
        this.y=y;
        this.ballnumber=ballnumber;
        vx=0;
        vy=0;
        w=0;
    }

    public void move(){
        bi.setX(bi.getX()+vx); bi.setY(bi.getY()+vy);
        x=bi.getX(); y=bi.getY();
        bi.setRotate(w);

        boolean rightBorder = bi.getX() >= (xmax - r);
        boolean leftBorder = bi.getX() <= (xmin + r);
        boolean bottomBorder = bi.getY() >= (ymax - r);
        boolean topBorder = bi.getY() <= (ymin + r);

        //leftuphole 
        if(x<xmin+r+5 && y<ymin+r+5) {x=Controller.xsball; y=Controller.ysball(); vx=0; vy=0; if(ballnumber==8) Controller.addtoscore(2*k*ballnumber); else Controller.addtoscore(k*ballnumber); Controller.xsball();}
        //middleuphole 
        if(485<x && x<520 && y<ymin+r+5) {x=Controller.xsball; y=Controller.ysball(); vx=0; vy=0; if(ballnumber==8) Controller.addtoscore(2*k*ballnumber); else Controller.addtoscore(k*ballnumber); Controller.xsball();}
        //rightuphole 
        if(x>xmax-r-5 && y<ymin+r+5) {x=Controller.xsball; y=Controller.ysball(); vx=0; vy=0; if(ballnumber==8) Controller.addtoscore(2*k*ballnumber); else Controller.addtoscore(k*ballnumber); Controller.xsball();}
        //leftdownhole 
        if(x<xmin+r+5 && y>ymax-r-5) {x=Controller.xsball; y=Controller.ysball(); vx=0; vy=0; if(ballnumber==8) Controller.addtoscore(2*k*ballnumber); else Controller.addtoscore(k*ballnumber); Controller.xsball();}
        //middledownhole 
        if(485<x && x<520 && y>ymax-r-5) {x=Controller.xsball; y=Controller.ysball(); vx=0; vy=0; if(ballnumber==8) Controller.addtoscore(2*k*ballnumber); else Controller.addtoscore(k*ballnumber); Controller.xsball();}
        //rightdownhole 
        if(x>xmax-r-5 && y>ymax-r-5) {x=Controller.xsball; y=Controller.ysball(); vx=0; vy=0; if(ballnumber==8) Controller.addtoscore(2*k*ballnumber); else Controller.addtoscore(k*ballnumber); Controller.xsball();}

        bi.setX(x); bi.setY(y);
        

        if (rightBorder || leftBorder) { vx*= -1; }
        if (bottomBorder || topBorder) { vy*= -1; }

        if(getv()<0.05){ vx=0; vy=0; } 
        else { vx*=0.995; vy*=0.995; w-=5;}
    }

    public double getv(){
        double v=Math.sqrt(vx*vx+vy*vy);
        return v;
    }

    public static void collision(Ball b1, Ball b2){
      
        double dx = b2.x - b1.x;
        double dy = b2.y - b1.y;
        double dist = Math.sqrt(dx*dx+dy*dy);
        
        if(dist < b1.r+b2.r){
        
        collisionsound();

        double angle = Math.atan2(dy,dx);
        double sin = Math.sin(angle), cos = Math.cos(angle);
          
        double x1 = 0, y1 = 0;
        double x2 = dx*cos+dy*sin;
        double y2 = dy*cos-dx*sin;
          
          // rotate velocity
          double vx1 = b1.vx*cos+b1.vy*sin;
          double vy1 = b1.vy*cos-b1.vx*sin;
          double vx2 = b2.vx*cos+b2.vy*sin;
          double vy2 = b2.vy*cos-b2.vx*sin;
          
          // resolve the 1D case
          double vx1final = ((b1.mass-b2.mass)*vx1+2*b2.mass*vx2)/(b1.mass+b2.mass);
          double vx2final = ((b2.mass-b1.mass)*vx2+2*b1.mass*vx1)/(b1.mass+b2.mass);
          
          vx1 = vx1final;
          vx2 = vx2final;
      
          double absV = Math.abs(vx1)+Math.abs(vx2);
          double overlap = (b1.r+b2.r)-Math.abs(x1-x2);
          x1 += vx1/absV*overlap;
          x2 += vx2/absV*overlap;
      
          // rotate the relative positions back
          double x1final = x1*cos-y1*sin;
          double y1final = y1*cos+x1*sin;
          double x2final = x2*cos-y2*sin;
          double y2final = y2*cos+x2*sin;
          
          
           // finally compute the new absolute positions
          b2.x = b1.x + x2final;
          b2.y = b1.y + y2final;
          
          b1.x = b1.x + x1final;
          b1.y = b1.y + y1final;
          
          //rotate vel back
          b1.vx = vx1*cos-vy1*sin;
          b1.vy = vy1*cos+vx1*sin;
          b2.vx = vx2*cos-vy2*sin;
          b2.vy = vy2*cos+vx2*sin;
          b1.bi.setX(b1.x);  b1.bi.setY(b1.y);
          b2.bi.setX(b2.x);  b2.bi.setY(b2.y);

          if(vy1 > vy2) {b2.w+=1.5*Math.abs(vy1-vy2); b1.w+=1.5*Math.abs(vy1-vy2);}
          if(vy1 < vy2) {b2.w+=-1.5*Math.abs(vy1-vy2); b1.w+=-1.5*Math.abs(vy1-vy2);}
        }  
    }

    public ImageView getball(){
        bi.setX(x);
        bi.setY(y);
        return bi;
    }

    public void setv(double angle,double v){
        vx=v*Math.cos(angle);
        vy=v*Math.sin(angle);
    }
    public static void collisionsound() {
        Media buzzer = new Media(new File("collision.wav").toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(buzzer);
        mediaPlayer.play();
    }
}
