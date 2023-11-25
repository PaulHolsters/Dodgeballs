
package dodgeballs.main;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 *
 * @author Paul Holsters
 */
public class KeyInput extends KeyAdapter {
    
    private Handler handler;
    
    public KeyInput(Handler handler){
        this.handler = handler;
    }
    
    //klasse speler nog af te werken!
    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();
        System.out.println(key);
        
        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);
            
            if(tempObject.getId()== ID.Speler){
                if(key == 101){
                    tempObject.setSnelheidY(-8);
                }
                else if(key == 99){
                    tempObject.setSnelheidX(8);
                }
                else if(key == 97){
                    tempObject.setSnelheidX(-8);
                }
                else if(key == 98 ){
                    tempObject.setSnelheidY(8);
                }
            }
        }
        if (key == KeyEvent.VK_ESCAPE){
            System.exit(1);
        }
        if(key == 10 && handler.gameOver()){
            handler.resetSpelSpeciaal();
            handler.setTeller(3);
        }
    }
    
  
    public void keyReleased(KeyEvent e){
        int key = e.getKeyCode();  
        
        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);
            
            if(tempObject.getId()== ID.Speler){
                if(key == 101){
                    tempObject.setSnelheidY(0);
                }
                else if(key == 99){
                    tempObject.setSnelheidX(0);
                }
                else if(key == 97){
                    tempObject.setSnelheidX(0);
                }
                else if(key == 98){
                    tempObject.setSnelheidY(0);
                }
            }
        }
    }
}
