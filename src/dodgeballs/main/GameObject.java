package dodgeballs.main;

import java.awt.Graphics;

/**
 *
 * @author Paul Holsters
 */
public abstract class GameObject {

    //kenmerken van een object (bal, speler, punt)
    protected int posX, posY;
    protected ID id;   
    protected double snelheidX;
    protected double snelheidY;
    protected int straal;

    //afgeleide kenmerken
    protected int diameter;
    protected int middelpuntX;
    protected int middelpuntY;

    //constructor voor 'speler'object
    public GameObject(int straal, double snelheid, int posX, int posY, ID id) {
        //kenmerken object
        this.straal = straal;
        this.snelheidX = snelheid;
        this.snelheidY = snelheid;
        this.posX = posX;
        this.posY = posY;
        this.id = id;

        //afgeleide kenmerken
        diameter = this.straal * 2;
        middelpuntX = this.posX + this.straal;
        middelpuntY = this.posY + this.straal;
    }

    //eigenschappen specifiek voor vijanden en punten
    //beide types hebben een eigen constructor
    protected int startX;
    protected int startY;
    protected int stap;
    protected double hoogteExact;
    protected double lengteExact;
    protected double hoek;//in graden (van 0°tot 360 °)
    protected double snelheid;
    protected int score;

    //afgeleide kenmerken
    protected int kwadrant;
    protected double hoekKwadrant;
    protected double hoekKwadrantInRad;
    protected double hoekInRad;
    

    //constructor voor 'vijanden'object
    public GameObject(int straal, double snelheid, int posX, int posY, ID id, double hoek) {
        //kenmerken object
        this.straal = straal;
        this.snelheid = snelheid;
        this.posX = posX;
        this.posY = posY;
        this.id = id;

        //afgeleide kenmerken
        diameter = this.straal * 2;
        middelpuntX = this.posX + this.straal;
        middelpuntY = this.posY + this.straal;

        //kenmerken
        this.hoek = hoek;
        startX = posX;
        startY = posY;
        stap = 0;
        lengteExact = posX;
        hoogteExact = posY;

        //afgeleide kenmerken
        if (this.hoek >= 0 && this.hoek < 90) {
            kwadrant = 1;
            hoekKwadrant = this.hoek;
        } else if (this.hoek >= 90 && this.hoek < 180) {
            kwadrant = 2;
            hoekKwadrant = this.hoek - 90;
        } else if (this.hoek >= 180 && this.hoek < 270) {
            kwadrant = 3;
            hoekKwadrant = this.hoek - 180;
        } else if (this.hoek >= 270 && this.hoek < 360) {
            kwadrant = 4;
            hoekKwadrant = this.hoek - 270;
        }
        hoekKwadrantInRad = Math.toRadians(hoekKwadrant);
        hoekInRad = Math.toRadians(this.hoek);
        snelheidX = Math.cos(hoekInRad) * snelheid;
        snelheidY = Math.sin(hoekInRad) * snelheid;
    }
    
    //constructor voor 'punten'object
    public GameObject(int straal, double snelheid, int posX, int posY, ID id, double hoek, int score) {
        //kenmerken object
        this.score = score;
        this.straal = straal;
        this.snelheid = snelheid;
        this.posX = posX;
        this.posY = posY;
        this.id = id;

        //afgeleide kenmerken
        diameter = this.straal * 2;
        middelpuntX = this.posX + this.straal;
        middelpuntY = this.posY + this.straal;

        //kenmerken
        this.hoek = hoek;
        startX = posX;
        startY = posY;
        stap = 0;
        lengteExact = posX;
        hoogteExact = posY;

        //afgeleide kenmerken
        if (this.hoek >= 0 && this.hoek < 90) {
            kwadrant = 1;
            hoekKwadrant = this.hoek;
        } else if (this.hoek >= 90 && this.hoek < 180) {
            kwadrant = 2;
            hoekKwadrant = this.hoek - 90;
        } else if (this.hoek >= 180 && this.hoek < 270) {
            kwadrant = 3;
            hoekKwadrant = this.hoek - 180;
        } else if (this.hoek >= 270 && this.hoek < 360) {
            kwadrant = 4;
            hoekKwadrant = this.hoek - 270;
        }
        hoekKwadrantInRad = Math.toRadians(hoekKwadrant);
        hoekInRad = Math.toRadians(this.hoek);
        snelheidX = Math.cos(hoekInRad) * snelheid;
        snelheidY = Math.sin(hoekInRad) * snelheid;
    }

    public abstract void tick();

    public abstract void render(Graphics g);
    
    //methodes te gebruiken voor VIJAND en PUNT
    //update reële hoogte en lengte
    public void update() {
        if (kwadrant == 1) {
            lengteExact = startX + snelheidX * stap;
            hoogteExact = startY - snelheidY * stap;
        } else if (kwadrant == 2) {
            lengteExact = startX + snelheidX * stap;
            hoogteExact = startY - snelheidY * stap;
        } else if (kwadrant == 3) {
            lengteExact = startX + snelheidX * stap;
            hoogteExact = startY - snelheidY * stap;
        } else {
            lengteExact = startX + snelheidX * stap;
            hoogteExact = startY - snelheidY * stap;
        }
    }
    
    

    //voor deze functie moet hoek een getal van 0 tot 180 zijn
    //de uitgangshoek wordt berekend op basis van de werkelijk hoek
    //nadat de uitgangshoek is bepaald wordt alles van de 
    //bal gereset
    public void bots(double hoek) {
        this.hoek = 2 * hoek - this.hoek;
        if (this.hoek < 0) {
            this.hoek = 360 + this.hoek;
        }
        reset();
    }
    
    public void resetSpeler() {
        middelpuntX = this.posX + this.straal;
        middelpuntY = this.posY + this.straal;
    }

    public void reset() {
        startX = posX;
        startY = posY;
        stap = 0;
        lengteExact = posX;
        hoogteExact = posY;
        middelpuntX = this.posX + this.straal;
        middelpuntY = this.posY + this.straal;
        if (this.hoek >= 0 && this.hoek < 90) {
            kwadrant = 1;
            hoekKwadrant = this.hoek;
        } else if (this.hoek >= 90 && this.hoek < 180) {
            kwadrant = 2;
            hoekKwadrant = this.hoek - 90;
        } else if (this.hoek >= 180 && this.hoek < 270) {
            kwadrant = 3;
            hoekKwadrant = this.hoek - 180;
        } else if (this.hoek >= 270 && this.hoek < 360) {
            kwadrant = 4;
            hoekKwadrant = this.hoek - 270;
        }
        hoekKwadrantInRad = Math.toRadians(hoekKwadrant);
        hoekInRad = Math.toRadians(this.hoek);
        snelheidX = Math.cos(hoekInRad) * this.snelheid;
        snelheidY = Math.sin(hoekInRad) * this.snelheid;
    }

    //veranderen van richting na botsing met de rand 
    //deze functie maakt gebruik van de functie bots()
    public void botsingWand() {
        //botsen tegen linker of rechterwand is OK
        if ((posX - 1 <= 0 && (kwadrant == 2 || kwadrant == 3) && hoek != 90) || (posX + 1 >= 780 && (kwadrant == 1 || kwadrant == 4) && hoek != 270)) {
            if (posX < 0) {
                posX = 0;
                middelpuntX = this.posX + this.straal;
            }
            if (posX > 780) {
                posX = 780;
                middelpuntX = this.posX + this.straal;
            }
            bots(90);
        }

        if ((posY - 1 <= 0 && (kwadrant == 1 || kwadrant == 2) && hoek != 0) || (posY + 1 >= 780 && (kwadrant == 3 || kwadrant == 4) && hoek != 180)) {
            if (posY < 0) {
                posY = 0;
                middelpuntY = this.posY + this.straal;
            }
            if (posY > 780) {
                posY = 780;
                middelpuntY = this.posY + this.straal;
            }
            bots(0);
        }
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    public double getSnelheid() {
        return snelheid;
    }

    public void setSnelheid(double snelheid) {
        this.snelheid = snelheid;
    }

    public int getStraal() {
        return straal;
    }

    public void setStraal(int straal) {
        this.straal = straal;
    }

    public int getDiameter() {
        return diameter;
    }

    public void setDiameter(int diameter) {
        this.diameter = diameter;
    }

    public int getMiddelpuntX() {
        return middelpuntX;
    }

    public void setMiddelpuntX(int middelpuntX) {
        this.middelpuntX = middelpuntX;
    }

    public int getMiddelpuntY() {
        return middelpuntY;
    }

    public void setMiddelpuntY(int middelpuntY) {
        this.middelpuntY = middelpuntY;
    }

    public int getStartX() {
        return startX;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public int getStartY() {
        return startY;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    public int getStap() {
        return stap;
    }

    public void setStap(int stap) {
        this.stap = stap;
    }

    public double getHoogteExact() {
        return hoogteExact;
    }

    public void setHoogteExact(double hoogteExact) {
        this.hoogteExact = hoogteExact;
    }

    public double getLengteExact() {
        return lengteExact;
    }

    public void setLengteExact(double lengteExact) {
        this.lengteExact = lengteExact;
    }

    public double getHoek() {
        return hoek;
    }

    public void setHoek(double hoek) {
        this.hoek = hoek;
    }

    public int getKwadrant() {
        return kwadrant;
    }

    public void setKwadrant(int kwadrant) {
        this.kwadrant = kwadrant;
    }

    public double getHoekKwadrant() {
        return hoekKwadrant;
    }

    public void setHoekKwadrant(double hoekKwadrant) {
        this.hoekKwadrant = hoekKwadrant;
    }

    public double getHoekKwadrantInRad() {
        return hoekKwadrantInRad;
    }

    public void setHoekKwadrantInRad(double hoekKwadrantInRad) {
        this.hoekKwadrantInRad = hoekKwadrantInRad;
    }

    public double getHoekInRad() {
        return hoekInRad;
    }

    public void setHoekInRad(double hoekInRad) {
        this.hoekInRad = hoekInRad;
    }

    public double getSnelheidX() {
        return snelheidX;
    }

    public void setSnelheidX(double snelheidX) {
        this.snelheidX = snelheidX;
    }

    public double getSnelheidY() {
        return snelheidY;
    }

    public void setSnelheidY(double snelheidY) {
        this.snelheidY = snelheidY;
    }
    
    

}
