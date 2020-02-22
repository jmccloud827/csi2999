package ai;

import java.util.Random;
import javafx.scene.Group;
import javafx.scene.image.Image;

public class Generation {
    Fly[] population;
    Fly bestFly;
    Builder build;
    Path bestPath;
    int[] solution;
    int genNum;
    int stepNum;
    int popSize;
    int mutationRate;
    boolean allDead;
    Random rnd = new Random();
    Generation(int popS, int mRate, int sNum, Builder b){
        bestFly = new Fly(sNum,b);
        build = b;
        bestPath = new Path(build);
        solution = bestPath.solution();
        genNum = 1;
        stepNum = sNum;
        popSize = popS;
        mutationRate = mRate;
        population = new Fly[popSize];
        allDead = false;
        for(int i = 0; i < popSize; i++){
            population[i] = new Fly(stepNum,build);
            population[i].randomize();
        }
    }
    public Group show(){
        Group temp = new Group();
        for(int i = 0; i < popSize; i++){
            temp.getChildren().add(population[i].move());
            allDead = true;
            if(!population[i].dead){
                allDead = false;
            }
        }
        return temp;
    }
    public void calcBestFly(){
        for(int i = 0; i < popSize; i++){
            if(population[i].goal){
                population[i].fitness = stepNum - population[i].moveNum;
            } else {
                double distencePath = 0;
                try {
                    distencePath = Math.abs(solution[population[i].yPos] - population[i].xPos);
                }catch(ArrayIndexOutOfBoundsException e){
                    
                }
                double distenceGoal = Math.sqrt(Math.pow(population[i].xPos - 195, 2) + Math.pow(population[i].yPos - 60, 2));
                population[i].fitness = 100/(distenceGoal*2 + distencePath);
            }
            if(population[i].fitness > bestFly.fitness){
                bestFly = population[i];
            }
        }
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
                if((rnd.nextInt(100) + 1) <= mutationRate){
                    population[i].moves[j][0] = (rnd.nextInt(9)-4);
                    population[i].moves[j][1] = (rnd.nextInt(9)-5);
                } else {
                    population[i].moves[j][0] = bestFly.moves[j][0];
                    population[i].moves[j][1] = bestFly.moves[j][1];
                }
            }
            population[i].reset();
        }
        genNum++;
    }
    public Group showPath(){
        return bestPath.show();
    }
}
