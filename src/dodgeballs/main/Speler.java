
package dodgeballs.main;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Paul Holsters
 */
public class Speler extends GameObject {
    
     public Speler(int straal, double snelheid, int posX, int posY, ID id) {
        super(straal, snelheid, posX, posY, id);
    }
    
    public void tick() {
        if(posX<=5){
            posX=5;
        }
        if(posX>=785){
            posX=785;
        }
        if(posY<=5){
            posY=5;
        }
        if(posY>=785){
            posY=785;
        }
        update();
        posX+=snelheidX;
        posY+=snelheidY;
        
        middelpuntX = posX + straal;
        middelpuntY = posY + straal;
        
    }

    public void render(Graphics g) {
    g.setColor(Color.white);
    g.fillOval(posX, posY, diameter, diameter);
    
    }
    
}
