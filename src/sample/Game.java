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
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;
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
    private int points = 0;
    private int time = 120;
    private int naboje = 5;
    private boolean strela = true;
    private Image naboj;
    private Label n1 = new Label();
    private Label n2 = new Label();
    private Label n3 = new Label();
    private Label n4 = new Label();
    private Label n5 = new Label();
    private HBox NHbox;
    private VBox vbox;
    private Button reload;
    private Label hint = new Label();
    private Label hint2 = new Label();
    MediaPlayer mediaPlayer;

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
        naboj = new Image("ammo.png", 15,60,false,false);
        //Naboje
        NHbox = new HBox(15);
        vbox = new VBox();
        vbox.setLayoutX(240);
        vbox.setLayoutY(380);
        NHbox.setLayoutX(480);
        NHbox.setLayoutY(400);
        hint2.getStylesheets().add("sample/stylesheet.css");
        hint2.getStyleClass().add("hint2-label");
        hint2.setLayoutX(150);
        hint2.setLayoutY(300);
        n1.setGraphic(new ImageView(naboj));
        n2.setGraphic(new ImageView(naboj));
        n3.setGraphic(new ImageView(naboj));
        n4.setGraphic(new ImageView(naboj));
        n5.setGraphic(new ImageView(naboj));
        NHbox.getChildren().addAll(n1,n2,n3,n4,n5);
        reload = new Button("Reload");
        reload.setOnAction(e->{ //po stlačení tlačidla "reload"
            naboje = 5;
            points -=15;
            score.setText("SCORE: " + points);
            n1.setGraphic(new ImageView(naboj));
            n2.setGraphic(new ImageView(naboj));
            n3.setGraphic(new ImageView(naboj));
            n4.setGraphic(new ImageView(naboj));
            n5.setGraphic(new ImageView(naboj));
            File file=new File("src/reload.wav");
            Media media=new Media(file.toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setVolume(0.6);
            mediaPlayer.play();
        });
        vbox.getStylesheets().add("sample/stylesheet.css");
        reload.getStyleClass().add("reload-button");
        hint.getStyleClass().add("hint-label");
        hint.setText("          (Space)");
        vbox.getChildren().addAll(hint, reload);
        getChildren().addAll(vbox, NHbox, hint2);

        setOnMouseClicked(e-> {
            if(!strela)
                naboje = getNaboje() - 1;
                strela=true;
            naboje = getNaboje() - 1;
            if(naboje>-1) {
                File file=new File("src/shot.wav");
                Media media=new Media(file.toURI().toString());
                mediaPlayer = new MediaPlayer(media);
                mediaPlayer.setVolume(0.4);
                mediaPlayer.play();
            }
        });
    }


    public void start() {       //metoda na spustenie hry
        Timeline t = new Timeline(new KeyFrame(Duration.seconds(120),actionEvent -> {
            gameOver();
        }));
        Timeline x = new Timeline(new KeyFrame(Duration.seconds(1), e-> odratajSekundu()));
        x.setCycleCount(Animation.INDEFINITE);
        t.setCycleCount(Animation.INDEFINITE);
        t.play();
        x.play();
    }

    public int getNaboje(){ //getter na náboje
        return naboje;
    }

    public void update(double deltaTime){
        if(!gameOver) {
            vykresli();
            pohyb(deltaTime / 1000000000);
            vymaz();
            aktualizuj();
        }
    }


    public void aktualizuj(){ //metoda na obrázky nábojov
        if(getNaboje()==4) n1.setGraphic(null);
        if(getNaboje()==3) n2.setGraphic(null);
        if(getNaboje()==2) n3.setGraphic(null);
        if(getNaboje()==1) n4.setGraphic(null);
        if(getNaboje()==0) {
            n5.setGraphic(null);
            hint2.setText("You have no bullets, points are not added");
        }
        if(getNaboje() == 5) hint2.setText("");

    }

    private void vykresli(){ //metoda na vykreslovanie pacmanov
        if(zoznam.size()<MAXPACMAN)
            if(Math.random()<0.05){
                Pacman pac = new Pacman("p0", 2, SPRITESIZE, SPRITESIZE, maxWidth, maxHeight);
                if(pac.getRychlost()<1) pac = new Pacman("p1", 2, SPRITESIZE, SPRITESIZE, maxWidth, maxHeight);
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

    private void vymaz(){ //metoda na vymazávanie pacmanov ak prejdu za okraj alebo ich zastrelíme
        ListIterator<Pacman> it = zoznam.listIterator();
        while(it.hasNext()){
                Pacman cislo = it.next();
                if (cislo.getStav() == 2) {
                    it.remove();
                }
                if (cislo.getStav() == 4) {
                    it.remove();
                    if(naboje>-1) points += 10;
                    if (strela) {

                    }
                    score.setText("SCORE: " + points);
                }

        }
    }


    private void gameOver() { //metoda, vyvola sa ak vyprší časový limit
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

    public boolean getGameOver(){ //getter
        return gameOver;
    }

    private void odratajSekundu(){ //metoda ktorá odrátava sekundy z časomiery a vypisuje ich do labelu "tajm"
        time--;
        tajm.setText(" ");
        if(time<=0){
            tajm.setText("Seconds left: 0" );
        }
        else
            tajm.setText("Seconds left: " + time);
    }

}
