
package dodgeballs.main;

import java.awt.Color;
import java.awt.Graphics;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Paul Holsters
 */
public class HUD {
    private int tijd=1000;
    private int score=0;
    private int scoreVoorlopig=0;
    private int level=0;
    private boolean gameOver=false;
    int teller=0;
    private int topscore=0;
    private int toplevel=0;
    private double Ekin;

    public double getEkin() {
        return Ekin;
    }

    public void setEkin(double Ekin) {
        this.Ekin = Ekin;
    }
    
    

    public int getTopscore() {
        return topscore;
    }

    public void setTopscore(int topscore) {
        this.topscore = topscore;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getTijd() {
        return tijd;
    }

    public void setTijd(int tijd) {
        this.tijd = tijd;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public int getScoreVoorlopig() {
        return scoreVoorlopig;
    }

    public void setScoreVoorlopig(int scoreVoorlopig) {
        this.scoreVoorlopig = scoreVoorlopig;
    }
    
    public void tick(){
        if(teller%60==0){
            tijd--;
        }
        teller++;
    }
    
    public void render(Graphics g){
        g.setColor(Color.white);
        if(!gameOver){
            g.drawString("Resterende tijd: "+tijd, 5, 50);
            g.drawString("SCORE VOORLOPIG:  "+scoreVoorlopig, 5, 80);
            g.drawString("SCORE:  "+score, 5, 100);
            g.drawString("De kinetische energie bedraagt:     "+Ekin, 5, 120);
        }
        else{
            g.drawString("GAME OVER!!!", 400, 350);
            g.drawString("SCORE:         "+score, 380, 370);
            g.drawString("LEVEL:         "+level, 380, 390);
            g.drawString("Topscore:      "+topscore, 380, 410);
            g.drawString("Toplevel:      "+toplevel, 380, 430);
            g.drawString("Druk ENTER om opnieuw te beginnen", 380, 480);
            if(score>topscore){
                topscore=score;
            }
            if(level>toplevel){
                toplevel=level;
            }
        }        
    }   
}
