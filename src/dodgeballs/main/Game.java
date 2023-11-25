package dodgeballs.main;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

/**
 *
 * @author Paul Holsters
 */
public class Game extends Canvas implements Runnable {

    private static final long serialVersionUID = 8406;

    public static final int BREEDTE = 840, HOOGTE = BREEDTE * 30 / 29;

    private Thread thread;
    private boolean running = false;
    private Handler handler;
    private HUD hud;
    private int teller = 0;
    private boolean doorlopen = false;
    private int tellerLevel = 0;

    public void setRunning(boolean running) {
        this.running = running;
    }

    public Game() {
        handler = new Handler();
        this.addKeyListener(new KeyInput(handler));

        new Window(BREEDTE, HOOGTE, "Dodgeballs!", this);

        //hier kan je alles per level specifiëren door specifieke parameter getallen
        //te gebruiken
        hud = new HUD();
        handler.addObject(new Vijand(26, 2, 20, 100, ID.Vijand, 45));
        handler.addObject(new Vijand(46, 2, 20, 140, ID.Vijand, 80));
        handler.addObject(new Vijand(16, 2, 660, 100, ID.Vijand, 135));
        handler.addObject(new Vijand(36, 2, 660, 140, ID.Vijand, 170));
        handler.addObject(new Punt(26, 2, 20, 20, ID.Punt, 225, 1));
        handler.addObject(new Punt(26, 2, 60, 20, ID.Punt, 260, 2));
        handler.addObject(new Punt(26, 2, 100, 20, ID.Punt, 315, 3));
        handler.addObject(new Punt(26, 2, 140, 20, ID.Punt, 350, 4));
        handler.addObject(new Speler(20, 0, 500, 500, ID.Speler));
        handler.resetSpelSpeciaal();

    }

    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public synchronized void stop() {
        try {
            thread.join();
            running = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {               
                tick();
                delta--;
            }
            if (running) {
                render();
                frames++;
            }
            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                frames = 0;
            }
        }
        stop();
    }

    private void tick() {
        //verplaatsen van alle GAME objecten
        if (!handler.gameOver()) {
            handler.tick();
            hud.tick();
            if (hud.isGameOver()) {
                hud.setGameOver(false);
                hud.setLevel(0);
                hud.setScore(0);
                hud.setTijd(1000);
                hud.setEkin(handler.energie());
            }
        }
    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.black);
        g.fillRect(0, 0, BREEDTE, HOOGTE);
        if (handler.gameOver()) {
            hud.setGameOver(true);
        } else {
            int[] lijst = handler.pakPunt();
            if (lijst[0] != 0) {
                hud.setScoreVoorlopig(hud.getScoreVoorlopig() + lijst[0]);
            }
            if (lijst[1] == -1) {
                hud.setLevel(hud.getLevel() + 1);
                hud.setScore(hud.getScore() + hud.getScoreVoorlopig() + hud.getTijd());
                hud.setScoreVoorlopig(0);
                //=>zet de tijd terug op maximum
                hud.setTijd(1000);
                //=>zet opnieuw alle munten in het spel
                //=>verander de snelheid van de vijanden/punten            
                //=>plaats de speler terug op "zijn" nulpunt
                //=>voorzie een break van 2 seconden
                handler.resetSpel(hud.getLevel());
            }
        }
        hud.setEkin(handler.energie());
        handler.render(g);
        hud.render(g);
        g.dispose();
        bs.show();
    }

    public static void main(String[] args) {
        new Game();
    }
}
