package ai;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
    ImageView back = new ImageView(new Image(getClass().getResourceAsStream("background.png")));
    ImageView ground = new ImageView(new Image(getClass().getResourceAsStream("ground.png")));
    
    ImageView btnGo = new ImageView(new Image(getClass().getResourceAsStream("go.png")));
    ImageView btnStop = new ImageView(new Image(getClass().getResourceAsStream("stop.png")));
    ImageView btnReset = new ImageView(new Image(getClass().getResourceAsStream("reset.png")));
    ImageView addSquare = new ImageView(new Image(getClass().getResourceAsStream("addSquare.png")));
    ImageView addCircle = new ImageView(new Image(getClass().getResourceAsStream("addCircle.png")));
    ImageView btnUpdate = new ImageView(new Image(getClass().getResourceAsStream("update.png")));
    ImageView btnAdd = new ImageView(new Image(getClass().getResourceAsStream("add.png")));
    ImageView remove = new ImageView(new Image(getClass().getResourceAsStream("undo.png")));
    ImageView removeAll = new ImageView(new Image(getClass().getResourceAsStream("clear.png")));
    Button test1 = new Button("Test1!");
    Button test2 = new Button("Test2!");
    GaussianBlur blur = new GaussianBlur();
    
    TextField popNum = new TextField();
    TextField timeFrame = new TextField();
    TextField stepNum = new TextField();
    TextField mutationRate = new TextField();
    TextField x = new TextField();
    TextField y = new TextField();
    TextField r = new TextField();
    TextField l = new TextField();
    TextField h = new TextField();
    
    Alert alert = new Alert(AlertType.ERROR);
    Group stage = new Group(ground,back,start,top,bottom,left,right,btnGo,fruit,btnStop,btnReset,popNum,timeFrame,stepNum,mutationRate,addSquare,addCircle,test1,test2,remove,removeAll);
    Scene scene = new Scene(stage, 530, 835);
    
    Group flies;
    Group area = new Group();
    Rectangle rTemp;
    Circle cTemp;
    
    
    Generation next;
    Builder build = new Builder();
    
    boolean square;
    boolean stop = false;
    
    @Override
    public void start(Stage primaryStage) {
        top.setFill(Color.BROWN);top.setStroke(Color.BLACK);
        bottom.setFill(Color.BROWN);bottom.setStroke(Color.BLACK);
        left.setFill(Color.BROWN);left.setStroke(Color.BLACK);
        right.setFill(Color.BROWN);right.setStroke(Color.BLACK);
        start.setFill(Color.BISQUE);
        fruit.setFitHeight(40);fruit.setFitWidth(45);fruit.setX(178);fruit.setY(45);
        back.setFitHeight(800);back.setFitWidth(400);
        ground.setFitHeight(835);ground.setFitWidth(530);
        
        btnGo.setFitWidth(80);btnGo.setFitHeight(40);btnGo.setLayoutY(799);btnGo.setLayoutX(5);
        btnStop.setFitWidth(80);btnStop.setFitHeight(40);btnStop.setLayoutY(799);btnStop.setLayoutX(80);btnStop.setEffect(blur);btnStop.setDisable(true);
        btnReset.setFitWidth(80);btnReset.setFitHeight(40);btnReset.setLayoutY(799);btnReset.setLayoutX(156);btnReset.setEffect(blur);btnReset.setDisable(true);
        addSquare.setFitWidth(70);addSquare.setFitHeight(35);addSquare.setLayoutY(182);addSquare.setLayoutX(402);
        addCircle.setFitWidth(70);addCircle.setFitHeight(35);addCircle.setLayoutY(182);addCircle.setLayoutX(464);
        btnUpdate.setFitWidth(70);btnUpdate.setFitHeight(35);btnUpdate.setLayoutX(402);btnUpdate.setLayoutY(247);
        btnAdd.setFitWidth(70);btnAdd.setFitHeight(35);btnAdd.setLayoutX(464);btnAdd.setLayoutY(247);
        test1.setLayoutX(550);test1.setLayoutY(5);
        test2.setLayoutX(550);test2.setLayoutY(40);
        remove.setFitWidth(50);remove.setFitHeight(50);remove.setLayoutX(473);remove.setLayoutY(136);
        removeAll.setFitWidth(50);removeAll.setFitHeight(50);removeAll.setLayoutX(410);removeAll.setLayoutY(136);
        
        Tooltip popTip = new Tooltip();popTip.setText("Population Number");popNum.setLayoutX(405);popNum.setLayoutY(5);popNum.setMaxWidth(122);popNum.setTooltip(popTip);popNum.setText("1000");
        Tooltip timeTip = new Tooltip();timeTip.setText("Time Frame (ms)");timeFrame.setLayoutX(405);timeFrame.setLayoutY(40);timeFrame.setMaxWidth(122);timeFrame.setTooltip(timeTip);timeFrame.setText("20");
        Tooltip stepTip = new Tooltip();stepTip.setText("Number of Moves");stepNum.setLayoutX(405);stepNum.setLayoutY(75);stepNum.setMaxWidth(122);stepNum.setTooltip(stepTip);stepNum.setText("500");
        Tooltip mutTip = new Tooltip();mutTip.setText("Mutation Rate (int)");mutationRate.setLayoutX(405);mutationRate.setLayoutY(108);mutationRate.setMaxWidth(122);mutationRate.setTooltip(mutTip);mutationRate.setText("15");
        x.setLayoutX(410);x.setLayoutY(188);x.setMaxWidth(50);x.setPromptText("X");
        y.setLayoutX(473);y.setLayoutY(188);y.setMaxWidth(50);y.setPromptText("Y");
        r.setLayoutX(405);r.setLayoutY(220);r.setMaxWidth(122);r.setPromptText("Radius");
        h.setLayoutX(408);h.setLayoutY(220);h.setMaxWidth(55);h.setPromptText("Height");
        l.setLayoutX(470);l.setLayoutY(220);l.setMaxWidth(55);l.setPromptText("Length");
        
        alert.setTitle("Error!");
        alert.setHeaderText("Input Error!");
        alert.setContentText("One of the inputs are invalid");
        
        stage.getChildren().add(area);
        
        btnGo.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.getChildren().removeAll(flies);
                btnReset.setEffect(blur);
                btnReset.setDisable(true);
                btnGo.setEffect(blur);
                btnGo.setDisable(true);
                addSquare.setDisable(true);
                addSquare.setEffect(blur);
                addCircle.setDisable(true);
                addCircle.setEffect(blur);
                popNum.setDisable(true);
                timeFrame.setDisable(true);
                stepNum.setDisable(true);
                mutationRate.setDisable(true);
                remove.setDisable(true);
                remove.setEffect(blur);
                removeAll.setDisable(true);
                removeAll.setEffect(blur);
                if(!stop){
                    try {
                        next = new Generation(Integer.parseInt(popNum.getText()),Integer.parseInt(mutationRate.getText()),Integer.parseInt(stepNum.getText()),build);
                        stage.getChildren().add(next.showPath());
                        stage.getChildren().removeAll(x,y,h,l,r,btnUpdate,btnAdd);
                        btnStop.setEffect(null);
                        btnStop.setDisable(false);
                        doMove();
                    }catch(NumberFormatException e) {
                        alert.showAndWait(); 
                        popNum.setDisable(false);
                        timeFrame.setDisable(false);
                        stepNum.setDisable(false);
                        mutationRate.setDisable(false);
                        addSquare.setDisable(false);
                        addSquare.setEffect(null);
                        addCircle.setDisable(false);
                        addCircle.setEffect(null);
                        btnStop.setEffect(blur);
                        btnStop.setDisable(true);
                        mutationRate.setText("15");
                        stepNum.setText("500");
                        timeFrame.setText("20");
                        popNum.setText("1000");
                    }
                } else {
                    stop = false;
                    btnStop.setEffect(null);
                    btnStop.setDisable(false);
                    doMove();
                }
            }
        });
        btnStop.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stop = true;
                btnStop.setEffect(blur);
                btnStop.setDisable(true);
            }
        });
        btnReset.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.getChildren().removeAll(flies);
                stage.getChildren().remove(next.showPath());
                addSquare.setDisable(false);
                addSquare.setEffect(null);
                addCircle.setDisable(false);
                addCircle.setEffect(null);
                popNum.setDisable(false);
                timeFrame.setDisable(false);
                stepNum.setDisable(false);
                mutationRate.setDisable(false);
                remove.setDisable(false);
                remove.setEffect(null);
                removeAll.setDisable(false);
                removeAll.setEffect(null);
                mutationRate.setText("15");
                stepNum.setText("500");
                timeFrame.setText("20");
                popNum.setText("1000");
                build = new Builder();
                stage.getChildren().removeAll(area);
                area = new Group();
                stage.getChildren().add(area);
                stop = false;
                btnStop.setEffect(blur);
                btnStop.setDisable(true);
                btnReset.setDisable(true);
                btnReset.setEffect(blur);
            }
        });
        addSquare.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.getChildren().removeAll(addSquare,addCircle);
                stage.getChildren().addAll(x,y,h,l,btnUpdate,btnAdd);
                square = true;
            }
        });
        addCircle.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.getChildren().addAll(x,y,r,btnUpdate,btnAdd);
                stage.getChildren().removeAll(addSquare,addCircle);
                square = false;
            }
        });
        btnUpdate.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    if(square){
                        stage.getChildren().remove(rTemp);
                        rTemp = new Rectangle(Integer.parseInt(x.getText()),Integer.parseInt(y.getText()),Integer.parseInt(l.getText()),Integer.parseInt(h.getText()));
                        rTemp.setFill(Color.BROWN);rTemp.setStroke(Color.BLACK);
                        rTemp.setClip(new Rectangle(0,0,400,800));
                        stage.getChildren().add(rTemp);
                    }else{
                        stage.getChildren().remove(cTemp);
                        cTemp = new Circle(Integer.parseInt(x.getText()),Integer.parseInt(y.getText()),Integer.parseInt(r.getText()));
                        cTemp.setFill(Color.BROWN);cTemp.setStroke(Color.BLACK);
                        cTemp.setClip(new Rectangle(0,0,400,800));
                        stage.getChildren().add(cTemp);
                    }
                }catch(NumberFormatException e) {
                    alert.showAndWait(); 
                }
            }
        });
        btnAdd.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    if(square){
                        stage.getChildren().remove(rTemp);
                        rTemp = new Rectangle(Integer.parseInt(x.getText()),Integer.parseInt(y.getText()),Integer.parseInt(l.getText()),Integer.parseInt(h.getText()));
                        rTemp.setFill(Color.BROWN);rTemp.setStroke(Color.BLACK);
                        build.add(rTemp);
                        rTemp.setClip(new Rectangle(0,0,400,800));
                        area.getChildren().add(rTemp);
                    }else{
                        stage.getChildren().remove(cTemp);
                        cTemp = new Circle(Integer.parseInt(x.getText()),Integer.parseInt(y.getText()),Integer.parseInt(r.getText()));
                        cTemp.setFill(Color.BROWN);cTemp.setStroke(Color.BLACK);
                        build.add(cTemp);
                        cTemp.setClip(new Rectangle(0,0,400,800));
                        area.getChildren().add(cTemp);
                    }
                    stage.getChildren().removeAll(x,y,h,l,r,btnUpdate,btnAdd);
                    stage.getChildren().addAll(addSquare,addCircle);
                }catch(NumberFormatException e) {
                    alert.showAndWait(); 
                }
            }
        });
        remove.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(!area.getChildren().isEmpty()){
                    build.remove();
                    area.getChildren().remove(area.getChildren().size()-1);
                }
            }
        });
        removeAll.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                while(!area.getChildren().isEmpty()){
                    build.remove();
                    area.getChildren().remove(area.getChildren().size()-1);
                }
            }
        });
        test1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                cTemp = new Circle(200,400,75);
                build.add(cTemp);
                cTemp.setFill(Color.BROWN);cTemp.setStroke(Color.BLACK);
                stage.getChildren().removeAll(flies);
                area.getChildren().add(cTemp);
                btnReset.setDisable(true);
                btnReset.setEffect(blur);
                btnGo.setEffect(blur);
                btnGo.setDisable(true);
                addSquare.setDisable(true);
                addSquare.setEffect(blur);
                addCircle.setDisable(true);
                addCircle.setEffect(blur);
                popNum.setDisable(true);
                timeFrame.setDisable(true);
                stepNum.setDisable(true);
                mutationRate.setDisable(true);
                remove.setDisable(true);
                remove.setEffect(blur);
                removeAll.setDisable(true);
                removeAll.setEffect(blur);
                if(!stop){
                    try {
                        next = new Generation(Integer.parseInt(popNum.getText()),Integer.parseInt(mutationRate.getText()),Integer.parseInt(stepNum.getText()),build);
                        stage.getChildren().add(next.showPath());
                        stage.getChildren().removeAll(x,y,h,l,r,btnUpdate,btnAdd);
                        btnStop.setEffect(null);
                        btnStop.setDisable(false);
                        doMove();
                    }catch(NumberFormatException e) {
                        alert.showAndWait(); 
                        popNum.setDisable(false);
                        timeFrame.setDisable(false);
                        stepNum.setDisable(false);
                        mutationRate.setDisable(false);
                        addSquare.setDisable(false);
                        addSquare.setEffect(null);
                        addCircle.setDisable(false);
                        addCircle.setEffect(null);
                        btnStop.setEffect(blur);
                        btnStop.setDisable(true);
                        mutationRate.setText("15");
                        stepNum.setText("500");
                        timeFrame.setText("20");
                        popNum.setText("1000");
                    }
                } else {
                    stop = false;
                    btnStop.setEffect(null);
                    btnStop.setDisable(false);
                    doMove();
                }
            }
        });
        test2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                rTemp = new Rectangle(0,500,300,20);
                build.add(rTemp);
                rTemp.setFill(Color.BROWN);rTemp.setStroke(Color.BLACK);
                area.getChildren().add(rTemp);
                rTemp = new Rectangle(100,300,300,20);
                build.add(rTemp);
                rTemp.setFill(Color.BROWN);rTemp.setStroke(Color.BLACK);
                stage.getChildren().removeAll(flies);
                area.getChildren().add(rTemp);
                btnReset.setDisable(true);
                btnReset.setEffect(blur);
                btnGo.setEffect(blur);
                btnGo.setDisable(true);
                addSquare.setDisable(true);
                addSquare.setEffect(blur);
                addCircle.setDisable(true);
                addCircle.setEffect(blur);
                popNum.setDisable(true);
                timeFrame.setDisable(true);
                stepNum.setDisable(true);
                mutationRate.setDisable(true);
                remove.setDisable(true);
                remove.setEffect(blur);
                removeAll.setDisable(true);
                removeAll.setEffect(blur);
                if(!stop){
                    try {
                        next = new Generation(Integer.parseInt(popNum.getText()),Integer.parseInt(mutationRate.getText()),Integer.parseInt(stepNum.getText()),build);
                        stage.getChildren().add(next.showPath());
                        stage.getChildren().removeAll(x,y,h,l,r,btnUpdate,btnAdd);
                        btnStop.setEffect(null);
                        btnStop.setDisable(false);
                        doMove();
                    }catch(NumberFormatException e) {
                        alert.showAndWait(); 
                        popNum.setDisable(false);
                        timeFrame.setDisable(false);
                        stepNum.setDisable(false);
                        mutationRate.setDisable(false);
                        addSquare.setDisable(false);
                        addSquare.setEffect(null);
                        addCircle.setDisable(false);
                        addCircle.setEffect(null);
                        btnStop.setEffect(blur);
                        btnStop.setDisable(true);
                        mutationRate.setText("15");
                        stepNum.setText("500");
                        timeFrame.setText("20");
                        popNum.setText("1000");
                    }
                } else {
                    stop = false;
                    btnStop.setEffect(null);
                    btnStop.setDisable(false);
                    doMove();
                }
            }
        });
        
        primaryStage.setTitle("Fly!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public void doMove(){
        Timeline time = new Timeline();
        time.setCycleCount(Timeline.INDEFINITE);
        try {
            KeyFrame frame = new KeyFrame(Duration.millis(Integer.parseInt(timeFrame.getText())), new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                    if(next.allDead){
                        time.stop();
                        next.calcBestFly();
                        System.out.println("Generation Number: " + next.genNum + "\nFitness: " + next.bestFly.fitness);
                        next.nextGen();
                        next.allDead = false;
                        if(!stop){
                            doMove();
                        } else {
                            btnReset.setDisable(false);
                            btnReset.setEffect(null);
                            btnGo.setEffect(null);
                            btnGo.setDisable(false);
                            timeFrame.setDisable(false);
                        }
                    } else {
                        flies = next.show();
                        stage.getChildren().add(flies);
                    }
                }
            });
            time.getKeyFrames().add(frame);
            time.play(); 
        }catch(NumberFormatException e) {
            alert.showAndWait();
            popNum.setDisable(false);
            timeFrame.setDisable(false);
            stepNum.setDisable(false);
            mutationRate.setDisable(false);
            mutationRate.setText("15");
            stepNum.setText("500");
            timeFrame.setText("20");
            popNum.setText("1000");
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}
