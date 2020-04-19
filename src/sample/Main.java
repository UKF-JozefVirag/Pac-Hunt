```java
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

public class Main extends Application {
    Stage window;
    Scene scene1, scene2;
    Image c1, c2, c3, c4, c5;
    Label nadpis;
    boolean vyp;
    MediaPlayer mediaPlayer;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Media musicFile = new Media("file:///C:/Users/Dodo/Desktop/PACHUNT/src/bgmusic.mp3");
        //hudba
        mediaPlayer = new MediaPlayer(musicFile);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setVolume(0.35);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        window = primaryStage;

        Group root = new Group();
        Game g = new Game(640, 480, "bg.jpg");
        MyTimer t = new MyTimer(g);
        t.stop();
        root.getChildren().add(g);
        Scene scene2 = new Scene(root, 640, 480);

        if(g.getGameOver()){
            window.setScene(null);
        }

        Button btn1 = new Button("Start Game");
        Button btn2 = new Button("Quit Game");
        Button btnM = new Button(); // Button na mute
        Button btnU = new Button(); // Button na unmute
        Pane layoutx = new Pane();
        nadpis = new Label();
        nadpis.setText("PAC-HUNT");
        nadpis.getStyleClass().add("nadpis");
        nadpis.setLayoutX(210);

        layoutx.getStyleClass().add("layout-1");
        //Start Game button
        btn1.setLayoutX(150);
        btn1.setLayoutY(100);
        //Quit Game button
        btn2.setLayoutX(170);
        btn2.setLayoutY(300);
        //Button na mute
        btnM.setLayoutX(520);
        btnM.setLayoutY(400);
        btnM.setMaxWidth(32);
        btnM.setMaxHeight(32);
        btnM.setMinWidth(32);
        btnM.setMinHeight(32);
        //Button na unmute
        btnU.setLayoutX(580);
        btnU.setLayoutY(400);
        btnU.setMaxWidth(32);
        btnU.setMaxHeight(32);
        btnU.setMinWidth(32);
        btnU.setMinHeight(32);

        btn1.getStyleClass().add("button-1");
        btn2.getStyleClass().add("button-2");
        btnM.getStyleClass().add("button-3");
        btnU.getStyleClass().add("button-4");

        layoutx.getChildren().addAll(btn1,btn2, btnM, btnU, nadpis);
        scene1 = new Scene(layoutx, 640, 480);
        scene1.getStylesheets().add("sample/stylesheet.css");

        btn1.setOnAction(e -> {window.setScene(scene2); t.start(); g.start();}); //na stlačenie sa prehodí scéna a zapne sa hra
        btn2.setOnAction(e -> {window.close();}); //na stlačenie sa zavrie aplikácia
        btnM.setOnAction(e -> mediaPlayer.setVolume(0));   //na stlačenie sa nastaví hlasitosť hudby na 0
        btnU.setOnAction(e -> mediaPlayer.setVolume(0.35)); //na stlačenie sa nastaví hlasitosť hudby na 35%
            
        window.setScene(scene1); //začiatočná scéna je scéna 1
        window.setTitle("PAC-HUNT");
        window.show();
        window.setResizable(false);
        window.getIcons().add(new Image("pacico.png"));
        scene2.setCursor(Cursor.CROSSHAIR);
    }

    public static void main(String[] args) {
        launch(args);
    }
}```
