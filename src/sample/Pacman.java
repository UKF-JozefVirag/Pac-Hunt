package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Pacman extends ImageView {
    private Image[]sprites;
    private double maxWidth, maxHeight, rychlost;
    private int actImage = 0;
    private int stav = 0;

    public Pacman(String nazovSpritu, int pocetSpritov, double w, double h, double maxw, double maxh){
        super();
        maxWidth= maxw;
        maxHeight=maxh;
        sprites = new Image[pocetSpritov];
        for(int i = 0; i<pocetSpritov; i++){
            sprites[i] = new Image(nazovSpritu+".gif",w,h,false,false);
        }

        do{ //nahodna rychlost
            rychlost = (int)(-5+Math.random()*11);
        } while(rychlost==0);

        if(rychlost<0){
            setLayoutX(maxWidth);
        }
        else
            setLayoutX(1);
            setLayoutY(100+(int)Math.random()*600);

        Timeline t = new Timeline(new KeyFrame(Duration.seconds(1),e->vykresli()));
        setOnMousePressed(evt->onClick());
        t.setCycleCount(120);
        t.play();
    }

    public double getStav(){ return stav; } //getter

    public double getRychlost(){ //getter
        return rychlost;
    }

    public void nextImage(){ //metoda na nastavenie aktuálneho obrazku
        actImage=0;
    }

    private void vykresli(){ //metoda na vykreslenie
        nextImage();

        if(stav==0)setImage(sprites[actImage]);
        if(stav==1) {
            actImage = 1;
        }
        if(stav == 2 || stav == 3 || stav == 4) setImage(null);
    }


    private void onClick(){ //čo sa stane ak klikneme na pacmana
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
