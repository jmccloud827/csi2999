package ai;

import java.util.ArrayList;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class Builder {
    ArrayList build;
    Builder(){
        build = new ArrayList();
    }
    public void add(Circle cir){
        build.add(cir);
    }
    public void add(Rectangle rec){
        build.add(rec);
    }
    public void remove(){
        build.remove(build.size()-1);
    }
    public void removeAll(){
        build = new ArrayList();
    }
    public boolean hit(int xPos, int yPos){
        for(int i = 0; i < build.size(); i++){
            try {
                Circle temp = (Circle)(build.get(i));
                double dist = Math.sqrt(Math.pow(xPos - temp.getCenterX() + 5, 2) + Math.pow(yPos - temp.getCenterY() + 5, 2));
                if(dist < temp.getRadius()){
                    return true;
                }
            }catch(ClassCastException e) {
                Rectangle temp = (Rectangle)(build.get(i));
                if(xPos < temp.getX() + temp.getWidth() - 5 && xPos > temp.getX() - 5 && yPos < temp.getY() + temp.getHeight() && yPos > temp.getY()){
                    return true;
                }
            }
        }
        if(xPos < 0 || yPos < 0 || xPos > 390 || yPos > 790){
            return true;
        }
        return false;
    }
    public boolean hitPath(int xPos, int yPos){
        for(int i = 0; i < build.size(); i++){
            try {
                Circle temp = (Circle)(build.get(i));
                double dist = Math.sqrt(Math.pow(xPos - temp.getCenterX(), 2) + Math.pow(yPos - temp.getCenterY(), 2));
                if(dist < temp.getRadius() + 5){
                    return true;
                }
            }catch(ClassCastException e) {
                Rectangle temp = (Rectangle)(build.get(i));
                if(xPos < temp.getX() + temp.getWidth() + 10 && xPos > temp.getX() - 10 && yPos < temp.getY() + 10 + temp.getHeight() && yPos > temp.getY() - 10){
                    return true;
                }
            }
        }
        if(xPos < 5 || yPos < 5 || xPos > 395 || yPos > 795){
            return true;
        }
        return false;
    }
}
