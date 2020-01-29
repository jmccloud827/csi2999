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
    Button btnGo = new Button("Go!");
    Button btnStop = new Button("Stop!");
    ImageView fruit = new ImageView(new Image(getClass().getResourceAsStream("fruit.png")));
    Group stage = new Group(start,top,bottom,left,right,btnGo,fruit,btnStop);
    Scene scene = new Scene(stage, 400, 835);
    boolean stop = false;
    Group flies;
    ImageView[] temp;
    Generation next = new Generation(1000);
    @Override
    public void start(Stage primaryStage) {
        top.setFill(Color.YELLOW);top.setStroke(Color.BLACK);
        bottom.setFill(Color.YELLOW);bottom.setStroke(Color.BLACK);
        left.setFill(Color.YELLOW);left.setStroke(Color.BLACK);
        right.setFill(Color.YELLOW);right.setStroke(Color.BLACK);
        start.setFill(Color.GRAY);
        btnGo.setLayoutY(805);btnGo.setLayoutX(4);
        btnStop.setLayoutY(805);btnStop.setLayoutX(45);
        fruit.setFitHeight(40);fruit.setFitWidth(45);fruit.setX(178);fruit.setY(45);
        
        btnGo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                doMove();
            }
        });
        btnStop.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stop = true;
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
                
            }
        });
        time.getKeyFrames().add(frame);
        time.play();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
