
package dodgeballs.main;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Paul Holsters
 */
public class Punt extends GameObject {
    
     public Punt(int straal, double snelheid, int posX, int posY, ID id, double hoek, int score) {
        super(straal, snelheid, posX, posY, id, hoek, score);
    }
    
    public void tick() {
        stap++;
        //indien hoekKwadrant gelijk is aan k*90°
        //deze VERPLAATSINGEN gaan nog niet goed! NOK!
        if (hoek == 0) {
            posX++;
            middelpuntX = this.posX + this.straal;
        } else if (hoek == 90) {
            posY--;
            middelpuntY = this.posY + this.straal;
        } else if (hoek == 180) {
            posX--;
            middelpuntX = this.posX + this.straal;
        } else if (hoek == 270) {
            posY++;
            middelpuntY = this.posY + this.straal;
        } //de overige hoeken:OK!
        else {
            //de werkelijke positie bepalen ten opzichte van de startpositie
            update();
            //verplaatsing uitvoeren: sommige hoeken resulteren in een merkbaar tragere snelheid dan ander
            //ik moet nog onderzoeken waarom
            //alle hoeken echter werken perfect
            int verplaatsingX;
            int verplaatsingY;
            if (kwadrant == 1) {
                verplaatsingX = (int) (lengteExact - posX);
                posX = posX + verplaatsingX;
                verplaatsingY = (int) (posY - hoogteExact);
                posY = posY - verplaatsingY;
                middelpuntX = this.posX + this.straal;
                middelpuntY = this.posY + this.straal;
            } else if (kwadrant == 2) {
                verplaatsingX = (int) (posX - lengteExact);
                posX = posX - verplaatsingX;
                verplaatsingY = (int) (posY - hoogteExact);
                posY = posY - verplaatsingY;
                middelpuntX = this.posX + this.straal;
                middelpuntY = this.posY + this.straal;
            } else if (kwadrant == 3) {
                verplaatsingX = (int) (posX - lengteExact);
                posX = posX - verplaatsingX;
                verplaatsingY = (int) (hoogteExact - posY);
                posY = posY + verplaatsingY;
                middelpuntX = this.posX + this.straal;
                middelpuntY = this.posY + this.straal;
            } else {
                verplaatsingX = (int) (lengteExact - posX);
                posX = posX + verplaatsingX;
                verplaatsingY = (int) (hoogteExact - posY);
                posY = posY + verplaatsingY;
                middelpuntX = this.posX + this.straal;
                middelpuntY = this.posY + this.straal;
            }
        }
    }

    public void render(Graphics g) {
        g.setColor(Color.yellow);        
        g.fillOval(posX, posY, diameter, diameter);
        g.setColor(Color.black);
        g.drawString(""+score, middelpuntX-4, middelpuntY+4);
    }
    
}
