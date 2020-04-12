package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.LinkedList;
import java.util.ListIterator;


public class Game extends Group {
    final int SPRITESIZE = 32;
    final int MAXPACMAN = 10;
    private ImageView background;
    private LinkedList<Pacman> zoznam;
    private double maxWidth, maxHeight;
    public boolean gameOver = false;
    private HBox text = new HBox(30);
    private Label score = new Label();
    private Label tajm = new Label();
    private Label label2;
    int points = 0;
    int time = 120;
    int naboje = 5;
    boolean prazdnyZasobnik = false;

    public Game(int w, int h, String pozadie){
        maxWidth = w;
        maxHeight = h;
        Image bg = new Image(pozadie, w, h, false, false);
        background = new ImageView(bg);
        text.getChildren().addAll(score);
        score.setText("Score: ");
        text.getStylesheets().add("sample/stylesheet.css");
        text.getChildren().add(tajm);
        tajm.setText("Seconds left: ");
        score.getStyleClass().add("score-text");
        tajm.getStyleClass().add("time-text");
        getChildren().addAll(background, text);
        zoznam = new LinkedList<>();
        Timeline t = new Timeline(new KeyFrame(Duration.seconds(120),actionEvent -> {
            gameOver();
        }));
        Timeline x = new Timeline(new KeyFrame(Duration.seconds(1), e-> odratajSekundu()));
        x.setCycleCount(Animation.INDEFINITE);
        t.setCycleCount(Animation.INDEFINITE);
        t.play();
        x.play();
        if(time<0){
            x.stop();
        }
        setOnMousePressed(evt->clickedOnScreen());
    }

    public int getNaboje(){
        return naboje;
    }

    public int getPoints(){
        return points;
    }

    private void clickedOnScreen(){
        //Metoda na zniženie počtu nabojov
        naboje--;
        if(getNaboje()==0){
            prazdnyZasobnik = true;
        }
    }


    public void update(double deltaTime){
        if(!gameOver) {
            vykresli();
            pohyb(deltaTime / 1000000000);
            vymaz();
            aktualizuj();
        }
    }

    private void vykresli(){
        if(zoznam.size()<MAXPACMAN)
            if(Math.random()<0.05){
                Pacman pac = new Pacman("pacman", 1, SPRITESIZE, SPRITESIZE, maxWidth, maxHeight);
                zoznam.add(pac);
                getChildren().add(pac);
            }
    }

    private void pohyb(double delta){
        for(int i = 0; i<zoznam.size(); i++){
            Pacman sample = (zoznam.get(i));
            sample.zmena(delta);
        }
    }

    public int getTime(){
        return time;
    }

    private void vymaz(){
        ListIterator<Pacman> it = zoznam.listIterator();
        while(it.hasNext()){
            Pacman cislo = it.next();
            if(cislo.getStav()==2){
                it.remove();
            }
            if(cislo.getStav()==4){
                it.remove();
                points+=10;
                score.setText("SCORE: " + points);
            }
        }
    }

    public void aktualizuj(){

    }

    private void gameOver() {
        gameOver=true;
        Pane xvb = new Pane();
        xvb.getStylesheets().add("sample/stylesheet.css");
        label2 = new Label("GAME OVER");
        label2.getStyleClass().add("gameover-text");
        label2.setLayoutX(180);
        label2.setLayoutY(220);
        xvb.getChildren().add(label2);
        getChildren().add(xvb);

    }

    public boolean getGameOver(){
        return gameOver;
    }

    private void odratajSekundu(){
        time--;
        tajm.setText(" ");
        if(time<=0){
            tajm.setText("Seconds left: 0" );
        }
        else
            tajm.setText("Seconds left: " + time);
    }

}
