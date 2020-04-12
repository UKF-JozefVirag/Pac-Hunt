package sample;

import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;


public class Main extends Application {
    Stage window;
    Scene scene1, scene2;
    Image c1, c2, c3, c4, c5;
    Label nadpis;
    boolean tr = true;

    @Override
    public void start(Stage primaryStage) throws Exception {
        c1 = new Image("crosshair1.png", 32,32,false,false);
        c2 = new Image("crosshair2.png", 32,32,false,false);
        c3 = new Image("crosshair3.png", 32,32,false,false);
        c4 = new Image("crosshair4.png", 32,32,false,false);

        window = primaryStage;
        Group root = new Group();
        Game g = new Game(640, 480, "bg.jpg");
        MyTimer t = new MyTimer(g);
        root.getChildren().add(g);
        Scene scene2 = new Scene(root, 640, 480);

        if(g.getGameOver()){
            window.setScene(null);
        }
        /*
        if(Math.random()<0.2){
            scene2.setCursor(new ImageCursor(c1));
        }
        else if(Math.random()<0.4 && Math.random() >0.2){
            scene2.setCursor(new ImageCursor(c2));
        }
        else if(Math.random()<0.6 && Math.random() > 0.4){
            scene2.setCursor(new ImageCursor(c3));
        }
        else if(Math.random()<0.8 && Math.random() >0.6){
            scene2.setCursor(new ImageCursor(c4));
        }
        else scene2.setCursor(new ImageCursor(c1));
         */
        Button btn1 = new Button("Start Game");
        Button btn2 = new Button("Quit Game");
        Pane layoutx = new Pane();
        nadpis = new Label();
        nadpis.setText("PAC-HUNT");
        nadpis.getStyleClass().add("nadpis");
        nadpis.setLayoutX(210);
        layoutx.getStyleClass().add("layout-1");
        btn1.setLayoutX(150);
        btn1.setLayoutY(100);
        btn2.setLayoutX(170);
        btn2.setLayoutY(300);
        btn1.getStyleClass().add("button-1");
        btn2.getStyleClass().add("button-2");
        layoutx.getChildren().addAll(btn1,btn2, nadpis);
        scene1 = new Scene(layoutx, 640, 480);
        scene1.getStylesheets().add("sample/stylesheet.css");


        btn1.setOnAction(e -> {window.setScene(scene2); t.start(); });
        btn2.setOnAction(e -> window.close());

        window.setScene(scene1);
        window.setTitle("PAC-HUNT");
        window.show();
        window.setResizable(false);
        window.getIcons().add(new Image("pacico.png"));

    }


    public Stage getWindow(){
        return window;
    }
    public void setWindow(Scene scena){
        window.setScene(scena);
    }

    public static void main(String[] args) {
        launch(args);
    }
}