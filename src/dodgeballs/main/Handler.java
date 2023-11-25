package dodgeballs.main;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * @author Paul Holsters
 */
public class Handler {

    LinkedList<GameObject> object = new LinkedList<>();
    LinkedList<GameObject> tempObject = new LinkedList<>();
    private HashMap<GameObject, ArrayList<double[]>> botsingen = new HashMap<>();
    private GameObject[] vijanden = new GameObject[4];
    private int[][] collisionMatrix;
    private int[][] rechtstreekseBotsingen;
    private int aantalVijanden;
    private int teller = 3;
    private boolean doorlopen = true;
    private double Ekin=0;

    public int getTeller() {
        return teller;
    }

    public void setTeller(int teller) {
        this.teller = teller;
    }
    
    public Handler() {

    }

    public void aanmakenBotsingen() {
        if (botsingen.size() != 0) {
            botsingen.clear();
        }
        int aantal = 0;
        for (int i = 0; i < object.size(); i++) {
            if (object.get(i).getId() == ID.Vijand || object.get(i).getId() == ID.Punt) {
                ArrayList<double[]> nieuweSnelheden = new ArrayList<>();
                GameObject tempObject = object.get(i);
                botsingen.put(tempObject, nieuweSnelheden);
                if (object.get(i).getId() == ID.Vijand) {
                    vijanden[aantal] = tempObject;
                    aantal++;
                }
            }
        }
        aantalVijanden = aantal;

        //aanmaken en invullen van de collisionmatrix/kopie       
        if (tempObject.size() != 0) {
            tempObject.clear();
        }
        for (int i = 0; i < object.size(); i++) {
            if (object.get(i).getId() != ID.Speler) {
                tempObject.add(object.get(i));
            }
        }
        int[][] tempMatrix = new int[tempObject.size()][tempObject.size()];
        for (int i = 0; i < tempMatrix.length; i++) {
            for (int j = 0; j < tempMatrix.length; j++) {
                if (i == j) {
                    tempMatrix[i][j] = 1;
                } else {
                    tempMatrix[i][j] = 0;
                }
            }
        }
        collisionMatrix = tempMatrix;
        rechtstreekseBotsingen = tempMatrix;
    }
    
    public double energie(){
        double E=0;
        for (int i = 0; i < tempObject.size(); i++) {
            double energie=Math.pow(tempObject.get(i).getSnelheid(),2);
            E+=energie;
        }
        return Math.sqrt(E);
    }

    public void resetSpel(int level) {
        //System.out.println("resetspel="+doorlopen);
        int teller = 0;
        for (int i = 0; i < object.size(); i++) {
            if (object.get(i).getId() == ID.Vijand) {
                object.get(i).setSnelheid(level + 2);
                object.get(i).setHoek(45 * teller + 12);
                object.get(i).setPosX(20 * teller + 100);
                object.get(i).setPosY(700);
                object.get(i).reset();
                teller++;
                //System.out.println("vijanden gereset="+doorlopen);
            } else if (object.get(i).getId() == ID.Speler) {
                object.get(i).setPosX(400);
                object.get(i).setPosY(400);
                object.get(i).resetSpeler();
            }
        }
        verwijderPunten();
        addObject(new Punt(26, 2, 20, 30, ID.Punt, 225, 1));
        addObject(new Punt(26, 2, 120, 30, ID.Punt, 260, 2));
        addObject(new Punt(26, 2, 240, 30, ID.Punt, 315, 3));
        addObject(new Punt(26, 2, 400, 30, ID.Punt, 350, 4));
        aanmakenBotsingen();
    }
    
    public void resetSpelSpeciaal() {
        //System.out.println("resetspel="+doorlopen);
        int teller = 0;
        for (int i = 0; i < object.size(); i++) {
            if (object.get(i).getId() == ID.Vijand) {
                object.get(i).setSnelheid(2);
                object.get(i).setHoek(45 * teller + 12);
                object.get(i).setPosX(20 * teller + 100);
                object.get(i).setPosY(700);
                object.get(i).reset();
                teller++;
                //System.out.println("vijanden gereset="+doorlopen);
            } else if (object.get(i).getId() == ID.Speler) {
                object.get(i).setPosX(400);
                object.get(i).setPosY(400);
                object.get(i).resetSpeler();
                //System.out.println("speler gereset="+doorlopen);
            }
        }
        verwijderPunten();
        addObject(new Punt(26, 2, 20, 30, ID.Punt, 225, 1));
        addObject(new Punt(26, 2, 120, 30, ID.Punt, 260, 2));
        addObject(new Punt(26, 2, 240, 30, ID.Punt, 315, 3));
        addObject(new Punt(26, 2, 400, 30, ID.Punt, 350, 4));
        aanmakenBotsingen();
    }

    public void verwijderPunten() {
        for (int i = 0; i < object.size(); i++) {
            if (object.get(i).getId() == ID.Punt) {
                object.remove(object.get(i));
                i--;
            }
        }
    }

    public void tick() {
        //bereken botsingen tegen de wand
        for (int i = 0; i < object.size(); i++) {
            GameObject tempObject = object.get(i);
            //de tick methode verandert de coördinaten van het object
            //rekening houdende met collisions (indien van toepassing)
            //de tick methode is specifiek per type object
            //zo is er de tick methode voor vijanden, punten en de speler
            //de tick methode komt overeen met de methode 'verplaats' uit de vroegere klasse 'Bal'
            if (tempObject.getId() == ID.Vijand || tempObject.getId() == ID.Punt) {
                tempObject.botsingWand();
            }
        }
        //bepaal rechtsreekse botsingen
        //en nieuw hoek en snelheid voor elke vijanden
        //ten gevolge van deze botsingen
        botsingen();
        herbereken();
        //verplaats alle objecten
        for (int i = 0; i < object.size(); i++) {
            GameObject tempObject = object.get(i);
            //de tick methode verandert de coördinaten van het object
            //rekening houdende met collisions (indien van toepassing)
            //de tick methode is specifiek per type object
            //zo is er de tick methode voor vijanden, punten en de speler
            //de tick methode komt overeen met de methode 'verplaats' uit de vroegere klasse 'Bal'
            tempObject.tick();
        }

    }

    public void render(Graphics g) {
        for (int i = 0; i < object.size(); i++) {
            GameObject tempObject = object.get(i);
            tempObject.render(g);
        }
    }

    public void addObject(GameObject object) {
        this.object.add(object);
    }

    public void removeObject(GameObject object) {
        this.object.remove(object);
    }

    //hier staan de methodes voor collision-handling
    public void reset() {
        for (int i = 0; i < collisionMatrix.length; i++) {
            for (int j = 0; j < collisionMatrix.length; j++) {
                if (i == j) {
                    collisionMatrix[i][j] = 1;
                    rechtstreekseBotsingen[i][j] = 1;
                } else {
                    collisionMatrix[i][j] = 0;
                    rechtstreekseBotsingen[i][j] = 0;
                }
            }
        }
    }

    //deze functie vult de rechtstreekse botsingen in per vijand/punt
    //probleem is dat de punten steeds minder zullen worden
    //de collisionmatrix moet dus door iets anders vervangen worden: voor later!
    public void botsingen() {
        for (int i = 0; i < tempObject.size(); i++) {
            for (int j = 0; j < tempObject.size(); j++) {
                if (hit(tempObject.get(i), tempObject.get(j)) && i != j) {
                    collisionMatrix[i][j] = 1;
                    rechtstreekseBotsingen[i][j] = 1;
                }
            }
        }
    }

    public boolean hit(GameObject bal_A, GameObject bal_B) {
        boolean raak = false;
        int afstandX = Math.abs((bal_A.getMiddelpuntX() - bal_B.getMiddelpuntX()));
        int afstandY = Math.abs((bal_A.getMiddelpuntY() - bal_B.getMiddelpuntY()));
        double afstand = Math.sqrt(Math.pow(afstandX, 2) + Math.pow(afstandY, 2));
        if (afstand <= bal_A.getStraal() + bal_B.getStraal()) {
            raak = true;
        }
        return raak;
    }

    public boolean gameOver() {
        boolean gameOver = false;
        GameObject tempObject = object.get(0);
        for (int i = 0; i < object.size(); i++) {
            if (object.get(i).getId() == ID.Speler) {
                tempObject = object.get(i);
            }
        }
        for (int i = 0; i < aantalVijanden; i++) {
            if (hit(vijanden[i], tempObject)) {
                gameOver = true;
            }
        }
        return gameOver;
    }

    public int[] pakPunt() {
        int[] lijst = new int[2];
        int score = 0;
        //selecteer de speler uit de verschillende objecten      
        GameObject tempObject = object.get(0);
        for (int i = 0; i < object.size(); i++) {
            if (object.get(i).getId() == ID.Speler) {
                tempObject = object.get(i);
            }
        }
        //kijk of deze in aanraking komt met een 'punt'
        for (int i = 0; i < this.tempObject.size(); i++) {
            if (hit(this.tempObject.get(i), tempObject) && this.tempObject.get(i).getId() == ID.Punt) {
                //verander de score
                score = this.tempObject.get(i).score * (int) Math.pow(10, teller);
                teller--;
                //verwijder object 'punt'
                object.remove(this.tempObject.get(i));
                //maak nieuwe lijsten en matrixen aan
                aanmakenBotsingen();
                lijst[0] = score;
                lijst[1] = teller;
                if (teller == -1) {
                    teller = 3;
                }
            }
        }
        return lijst;
    }

    //bepalen van een matrix die laat zien welke ballen
    //rechtstreeks of onrechtstreeks met elkaar in
    //botsing zijn
    public void botsingsKlassen() {
        for (int i = 0; i < aantalVijanden; i++) {
            if (rijOK(i)) {
                //rijOK kijkt na of de bal in kwestie wel botst met een andere bal
                //zoniet wordt in deze rij alles op -1 gezet (zie else tak)
                for (int j = i + 1; j < aantalVijanden; j++) {
                    //j te mergen rij
                    if (rijOK(j)) {
                        if (mergeOK(i, j)) {
                            //mergeOK gaat na of in een te mergen rij j
                            //het getal -1 voorkomt, in dat geval
                            //is de rij al gemerged
                            //en tot slot of er op een plaats niet gelijk aan j of i
                            //een 1 voorkomt op een plaats waar dat ook in rij i het geval is                
                            merge(i, j);
                            //hier wordt j met i gemerged; dit betekent dat 
                            //bij j alles op -1 wordt gezet en de nodige 1'nen 
                            //bij i worden ingevuld
                            j = i + 1;
                            //tot slot wordt j gereset omdat steeds opnieuw moet worden begonnen
                            //na het mergen kunnen immers rijen j in aanmerking komen
                            //die voor het mergen niet in aanmerking kwamen
                        }
                    } else {
                        for (int k = 0; k < aantalVijanden; k++) {
                            collisionMatrix[j][k] = -1;
                        }
                    }
                }
            } else {
                for (int j = 0; j < aantalVijanden; j++) {
                    collisionMatrix[i][j] = -1;
                }
            }
        }
    }

    public boolean mergeOK(int i, int j) {
        boolean mergeOK = false;
        for (int k = 0; k < aantalVijanden; k++) {
            if (collisionMatrix[j][k] == -1) {
                return mergeOK;
            }
        }
        for (int k = 0; k < aantalVijanden; k++) {
            if (collisionMatrix[i][k] == collisionMatrix[j][k]) {
                mergeOK = true;
                return mergeOK;
            }
        }
        return mergeOK;
    }

    public void merge(int i, int j) {
        for (int k = 0; k < aantalVijanden; k++) {
            if (collisionMatrix[j][k] == 1) {
                collisionMatrix[i][k] = 1;
            }
        }
        for (int k = 0; k < aantalVijanden; k++) {
            collisionMatrix[j][k] = -1;
        }
    }

    public boolean rijOK(int rij) {
        boolean OK = false;
        for (int j = 0; j < aantalVijanden; j++) {
            if (rij != j && collisionMatrix[rij][j] == 1) {
                OK = true;
            }
        }
        return OK;
    }

    /*In wat volgt wordt de nieuwe snelheid en hoek van een bal berekend
    na botsing met een andere bal.
     */
    //onderstaande methode is nu wellicht juist!
    public double as(GameObject balA, GameObject balB) {
        double as;
        if (balB.getMiddelpuntX() - balA.getMiddelpuntX() == 0) {
            as = 90;
        } else if (balB.getMiddelpuntY() - balA.getMiddelpuntY() == 0) {
            as = 0;
        } else {
            double tan = -((double) (balB.getMiddelpuntY() - balA.getMiddelpuntY())
                    / (double) (balB.getMiddelpuntX() - balA.getMiddelpuntX()));
            double atan = Math.atan(tan);
            if (atan < 0) {
                atan = Math.PI + atan;
            }
            as = Math.toDegrees(atan);
        }
        return as;
    }

    //deze methode levert een correcte kinetische energie
    //maar volledig verkeerde hoeken (en mogelijk ook groottes!)
    public double[] weerkaatsing(GameObject balA, GameObject balB) {
        double[] nieuweSnelheid = new double[2];
        double as = as(balA, balB);
        //onderstaande formules zijn kwadrantafhankelijk!
        double kwadrantA = balA.getKwadrant();
        double kwadrantB = balB.getKwadrant();
        double snelheidXA;
        double snelheidYA;
        if (kwadrantB == 1 || kwadrantB == 2) {
            snelheidXA = Math.cos(Math.toRadians(balB.getHoek()) - Math.toRadians(as)) * balB.getSnelheid();
        } else {
            snelheidXA = -Math.cos(Math.toRadians(as) + Math.PI - Math.toRadians(balB.getHoek())) * balB.getSnelheid();
        }
        if (kwadrantA == 1 || kwadrantA == 2) {
            snelheidYA = Math.sin(Math.toRadians(balA.getHoek()) - Math.toRadians(as)) * balA.getSnelheid();
        } else {
            snelheidYA = Math.sin(Math.toRadians(as) + Math.PI - Math.toRadians(balA.getHoek())) * balA.getSnelheid();
        }
        double grootte = Math.sqrt(Math.pow(snelheidXA, 2) + Math.pow(snelheidYA, 2));
        double nieuweHoek;
        if (snelheidYA == 0) {
            if (snelheidXA > 0) {
                nieuweHoek = as;
            } else {
                nieuweHoek = as + 180;
            }
        } else if (snelheidXA == 0) {
            nieuweHoek = balA.getHoek() + 180;
        } else if (snelheidXA > 0) {
            if (snelheidYA > 0) {
                nieuweHoek = as + 90 - Math.toDegrees(Math.atan(Math.abs(snelheidXA) / Math.abs(snelheidYA)));
            } else {
                nieuweHoek = as + 270 + Math.toDegrees(Math.atan(Math.abs(snelheidXA) / Math.abs(snelheidYA)));
            }
        } else {
            if (snelheidYA > 0) {
                nieuweHoek = as + 90 + Math.toDegrees(Math.atan(Math.abs(snelheidXA) / Math.abs(snelheidYA)));
            } else {
                nieuweHoek = as + 270 - Math.toDegrees(Math.atan(Math.abs(snelheidXA) / Math.abs(snelheidYA)));
            }
        }
        if (nieuweHoek >= 360) {
            nieuweHoek = nieuweHoek - 360;
        }
        nieuweSnelheid[0] = nieuweHoek;
        nieuweSnelheid[1] = grootte;
        return nieuweSnelheid;
    }

    public void herbereken() {
        for (int i = 0; i < tempObject.size(); i++) {
            for (int j = 0; j < tempObject.size(); j++) {
                if (i != j && rechtstreekseBotsingen[i][j] == 1) {
                    double[] nieuweSnelheid = weerkaatsing(tempObject.get(i), tempObject.get(j));
                    botsingen.get(tempObject.get(i)).add(nieuweSnelheid);
                }
            }
        }
        for (int i = 0; i < tempObject.size(); i++) {
            berekenSnelheid(tempObject.get(i));
        }
        positioneer();
        reset();
    }

    public void berekenSnelheid(GameObject a) {
        if (botsingen.get(a).size() != 0) {
            a.setHoek(botsingen.get(a).get(0)[0]);
            a.setSnelheid(botsingen.get(a).get(0)[1]);
            a.reset();
            botsingen.get(a).clear();
            /*
            deze lus is maar belangrijk wanneer meer dan 
            2 vijanden tegelijk op elkaar botsen
            for (int i = 1; i < botsingen.get(a).size(); i++) {
                //bereken vectoriële snelheid
            }
             */
        }
    }

    public void positioneer() {
        for (int i = 0; i < tempObject.size(); i++) {
            for (int j = 0; j < tempObject.size(); j++) {
                if (i != j && rechtstreekseBotsingen[i][j] == 1) {
                    int afstandX = tempObject.get(i).getPosX() - tempObject.get(j).getPosX();
                    int afstandY = tempObject.get(i).getPosY() - tempObject.get(j).getPosY();

                    while (hit(tempObject.get(i), tempObject.get(j))) {
                        if (afstandX < 0) {
                            tempObject.get(i).setPosX(tempObject.get(i).getPosX() - 1);
                            tempObject.get(j).setPosX(tempObject.get(j).getPosX() + 1);
                        }
                        if (afstandX > 0) {
                            tempObject.get(i).setPosX(tempObject.get(i).getPosX() + 1);
                            tempObject.get(j).setPosX(tempObject.get(j).getPosX() - 1);
                        }
                        if (afstandY < 0) {
                            tempObject.get(i).setPosY(tempObject.get(i).getPosY() - 1);
                            tempObject.get(j).setPosY(tempObject.get(j).getPosY() + 1);
                        }
                        if (afstandY > 0) {
                            tempObject.get(i).setPosY(tempObject.get(i).getPosY() + 1);
                            tempObject.get(j).setPosY(tempObject.get(j).getPosY() - 1);
                        }
                        tempObject.get(i).reset();
                        tempObject.get(j).reset();
                    }
                }
            }
        }
    }
}
