
package dodgeballs.main;

import java.awt.Canvas;
import java.awt.Dimension;
import javax.swing.JFrame;
/**
 *
 * @author Paul Holsters
 */
public class Window extends Canvas {
    
    private static final long serialVersionUID = -2408406;
    
    public Window(int breedte, int hoogte, String titel, Game game){
        JFrame frame = new JFrame(titel);
        
        frame.setPreferredSize(new Dimension(breedte, hoogte));
        frame.setMaximumSize(new Dimension(breedte, hoogte));
        frame.setMinimumSize(new Dimension(breedte, hoogte));
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.add(game);
        frame.setVisible(true);
        game.start();
        
        
    }
    
}
