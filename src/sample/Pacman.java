package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class Pacman extends ImageView {
    private Image[]sprites;
    private Image killed, pacL;
    double x, y, maxWidth, maxHeight, rychlost;
    int actImage = 0;
    int stav = 0;

    public Pacman(String nazovSpritu, int pocetSpritov, double w, double h, double maxw, double maxh){
        super();
        maxWidth= maxw;
        maxHeight=maxh;
        sprites = new Image[pocetSpritov];
        for(int i = 0; i<pocetSpritov; i++){
            sprites[i] = new Image(nazovSpritu+".png",w,h,false,false);
        }
        killed = new Image("pacdead.png", w,h,false,false);
        pacL = new Image("pacman1.png", w,h, false, false);


        do{
            rychlost = (int)(-5+Math.random()*11);
        } while(rychlost==0);

        if(rychlost<0){
            setLayoutX(maxWidth);
        }
        else
            setLayoutX(1);
            setImage(pacL);
            setLayoutY(100+(int)Math.random()*600);


        Timeline t = new Timeline(new KeyFrame(Duration.seconds(1),e->vykresli()));
        setOnMousePressed(evt->onClick());
        t.setCycleCount(120);
        t.play();
    }

    public double getStav(){ return stav; }

    public void nextImage(){actImage= 0;}

    private void vykresli(){
        nextImage();

        if(stav==0)setImage(sprites[actImage]);
        if(stav==1) {
            setImage(killed);
        }
        if(stav == 2) setImage(null);
        if (stav == 3) setImage(null);
        if (stav == 4) setImage(null);
    }

    private void onClick(){
        stav = 2;
        stav = 4;
    }

    public void zmena(double deltaTime){
        setLayoutX(getLayoutX()+rychlost+deltaTime);
        setLayoutY(getLayoutY()-5+(int)(Math.random()*11));
        if((getLayoutX()<0)||(getLayoutX()>maxWidth)) stav=2;
        vykresli();
    }


}
