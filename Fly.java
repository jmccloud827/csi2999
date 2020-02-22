package ai;

import java.util.Random;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Fly {
    int[][] moves;
    ImageView body;
    boolean dead;
    boolean goal;
    int stepNum;
    int moveNum;
    double fitness;
    int xPos;
    int yPos;
    Builder build;
    Random rnd = new Random();
    Fly(int sNum, Builder b){
        build = b;
        stepNum = sNum;
        moves = new int[stepNum][2];
        body = new ImageView(new Image(getClass().getResourceAsStream("fly.png")));
        body.setFitHeight(10);
        body.setFitWidth(10);
        dead = false;
        goal = false;
        moveNum = 0;
        fitness = 0;
        xPos = 195;
        yPos = 695;
    }
    public void randomize(){
        for(int i = 0; i < stepNum; i++){
            moves[i][0] = (rnd.nextInt(9)-4);
            moves[i][1] = (rnd.nextInt(9)-4);
        }
    }
    public ImageView move(){
        isDead();
        if(!dead){
            int rotate = 0;
            if(moves[moveNum][0] == 0 && moves[moveNum][1] > 0){
                rotate = 90;
            } else if(moves[moveNum][0] == 0 && moves[moveNum][1] < 0){
                rotate = 270;
            } else if(moves[moveNum][0] > 0){
                rotate = (int)(Math.atan(moves[moveNum][1]/moves[moveNum][0])*180/Math.PI);
            } else if(moves[moveNum][0] < 0){
                rotate = (int)(Math.atan(moves[moveNum][1]/moves[moveNum][0])*180/Math.PI)+180;
            }
            xPos += moves[moveNum][0];
            yPos += moves[moveNum][1];
            moveNum++;
            body.setX(xPos);
            body.setY(yPos);
            body.setRotate(rotate);
        }
        return body;
    }
    public void isDead(){
        double dist = Math.sqrt(Math.pow(xPos - 195, 2) + Math.pow(yPos - 60, 2));
        if(moveNum >= stepNum || build.hit(xPos, yPos)){
            dead = true;
        } else if(dist <= 20){
            dead = true;
            goal = true;
        }
    }
    public void reset(){
        dead = false;
        goal = false;
        moveNum = 0;
        xPos = 195;
        yPos = 695;
    }
}
