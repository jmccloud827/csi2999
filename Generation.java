package ai;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Generation {
    Fly[] population;
    Fly bestFly;
    int genNum;
    int stepNum;
    int popSize = 0;
    Generation(int popS){
        population = new Fly[popS];
        bestFly = new Fly(stepNum);
        popSize = 0;
    }
    public ImageView[] show(){
        return null;
    }
    public void calcBestFly(){
        for(int i=0; i<popSize; i++){
         if(population.length!=0)
             bestFly.fitness = 1;
         else
             bestFly.fitness = -1;
    }
    public boolean allDead(){
        return false;
    }
    public void nextGen(){
        
    }
}
