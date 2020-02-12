package ai;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class AI extends Application {
    Rectangle top = new Rectangle(0,-1,400,5);
    Rectangle bottom = new Rectangle(0,796,400,5);
    Rectangle left = new Rectangle(-1,-1,5,802);
    Rectangle right = new Rectangle(396,-1,5,802);
    Circle start = new Circle(200,700,8);
    ImageView fruit = new ImageView(new Image(getClass().getResourceAsStream("fruit.png")));
    
    Button btnGo = new Button("Go!");
    Button btnStop = new Button("Stop!");
    Button btnReset = new Button("Reset!");
    
    Group stage = new Group(start,top,bottom,left,right,btnGo,fruit,btnStop,btnReset);
    Scene scene = new Scene(stage, 400, 835);
    
    Group flies;    
    
    Generation next;
    
    boolean stop = false;
    
    @Override
    public void start(Stage primaryStage) {
        top.setFill(Color.YELLOW);top.setStroke(Color.BLACK);
        bottom.setFill(Color.YELLOW);bottom.setStroke(Color.BLACK);
        left.setFill(Color.YELLOW);left.setStroke(Color.BLACK);
        right.setFill(Color.YELLOW);right.setStroke(Color.BLACK);
        start.setFill(Color.GRAY);
        fruit.setFitHeight(40);fruit.setFitWidth(45);fruit.setX(178);fruit.setY(45);
        
        btnGo.setLayoutY(805);btnGo.setLayoutX(4);
        btnStop.setLayoutY(805);btnStop.setLayoutX(45);btnStop.setDisable(true);
        btnReset.setLayoutY(805);btnReset.setLayoutX(96);btnReset.setDisable(true);
        
        btnGo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage.getChildren().removeAll(flies);
                btnReset.setDisable(true);
                btnGo.setDisable(true);
                if(!stop){
                    next = new Generation(1000,15,1000);
                    btnStop.setDisable(false);
                    doMove();
                } else {
                    stop = false;
                    btnStop.setDisable(false);
                    doMove();
                }
            }
        });
        btnStop.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stop = true;
                btnStop.setDisable(true);
            }
        });
        btnReset.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage.getChildren().removeAll(flies);
                stop = false;
                btnStop.setDisable(true);
                btnReset.setDisable(true);
            }
        });
        
        primaryStage.setTitle("Fly!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public void doMove(){
        Timeline time = new Timeline();
        time.setCycleCount(Timeline.INDEFINITE);
        KeyFrame frame = new KeyFrame(Duration.millis(20), new EventHandler<ActionEvent>(){
        @Override
        public void handle(ActionEvent event){
                if(next.allDead()){
                    time.stop();
                    next.calcBestFly();
                    System.out.println("Generation Number: " + next.genNum + "\nFitness: " + next.bestFly.fitness);
                    next.nextGen();
                    if(!stop){
                        doMove();
                    } else {
                        btnReset.setDisable(false);
                        btnGo.setDisable(false);
                    }
                } else {
                    flies = new Group();
                    flies = next.show();
                    stage.getChildren().add(flies);
                }
            }
        });
        time.getKeyFrames().add(frame);
        time.play(); 
    }
    public static void main(String[] args) {
        launch(args);
    }
}
