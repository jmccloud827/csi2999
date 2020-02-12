package ai;

import javafx.scene.Group;
import javafx.scene.image.Image;

public class Generation {
    Fly[] population;
    Fly bestFly;
    int genNum;
    int stepNum;
    int popSize;
    Generation(int popS, int mRate, int sNum){
        popSize = popS;
        population = new Fly[popSize];
        bestFly = new Fly(stepNum,mRate);
        stepNum = sNum;
        genNum = 1;
        for(int i = 0; i < popSize; i++){
            population[i] = new Fly(stepNum,mRate);
            population[i].randomize();
        }
    }
    public Group show(){
        Group temp = new Group();
        for(int i = 0; i < popSize; i++){
            temp.getChildren().add(population[i].move());
        }
        return temp;
    }
    public void calcBestFly(){
        for(int i = 0; i < popSize; i++){
            if(population[i].goal){
                population[i].fitness = stepNum - population[i].moveNum;
            } else {
                double distence = Math.sqrt(Math.pow(population[i].xPos - 195, 2) + Math.pow(population[i].yPos - 60, 2));
                population[i].fitness = 1/distence;
            }
            if(population[i].fitness > bestFly.fitness){
                bestFly = population[i];
            }
        }
    }
    public boolean allDead(){
        for(int i = 0; i < popSize; i++){
            if(!population[i].dead){
                return false;
            }
        }
        return true;
    }
    public void nextGen(){
        for(int j = 0; j < stepNum; j++){
            population[popSize-1].moves[j][0] = bestFly.moves[j][0];
            population[popSize-1].moves[j][1] = bestFly.moves[j][1];
        }
        population[popSize-1].body.setImage(new Image(getClass().getResourceAsStream("bestFly.png")));
        population[popSize-1].body.setFitHeight(20);
        population[popSize-1].body.setFitWidth(20);
        population[popSize-1].reset();
        for(int i = 0; i < popSize-1; i++){
            for(int j = 0; j < stepNum; j++){
                population[i].moves[j][0] = bestFly.moves[j][0];
                population[i].moves[j][1] = bestFly.moves[j][1];
            }
            population[i].reset();
            population[i].mutate();
        }
        genNum++;
    }
}
