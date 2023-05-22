package pl.edu.pg.eti.po.project2;

import java.awt.Color;
import java.util.Random;



public abstract class Organizm {
    public enum TypOrganizmu {
        CZLOWIEK,
        WILK,
        OWCA,
        LIS,
        ZOLW,
        ANTYLOPA,
        CYBER_OWCA,
        TRAWA,
        MLECZ,
        GUARANA,
        WILCZE_JAGODY,
        BARSZCZ_SOSNOWSKIEGO;
    }


    public enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT,
        STAY;
    }

    protected Swiat swiat;
    protected int sila;
    protected int inicjatywa;
    protected point polozenie;
    protected TypOrganizmu typOrganizmu;
    protected Color kolor;
    protected int turaUrodzenia;
    protected double szansaRozmnazania;

    private static final int LICZBA_ROZNYCH_GATUNKOW = 12;
    protected char symbol;
    protected point kierunek;
    protected boolean CzyZywy;
    protected boolean CzyRozmnozylSie;


    public abstract void Akcja();
    public abstract void Kolizja(Organizm other);

    public abstract boolean isAnimal();



    public Organizm(Swiat swiat, point polozenie, int sila, int inicjatywa, int turaUrodzenia, TypOrganizmu typOrganizmu) {
        this.typOrganizmu = typOrganizmu;
        this.swiat = swiat;
        this.polozenie = polozenie;
        this.turaUrodzenia = turaUrodzenia;
        this.sila = sila;
        this.inicjatywa = inicjatywa;
        this.CzyZywy = true;
    }

    public abstract String nameTypOrganizmu();

    public point chooseAnyPlace() {
        boolean Up = true;
        boolean Down = true;
        Boolean Left = true;
        Boolean Right = true;
        int curX = polozenie.getX();
        int curY = polozenie.getY();
        int kierunki = 4;

        if (curY == 0) {
            Up = false;
            kierunki--;
        }

        if (curY == (swiat.getHeightB() - 1)) {
            Down = false;
            kierunki--;
        }


        if (curX == 0) {
            Left = false;
            kierunki--;
        }


        if (curX == (swiat.getWidthB() - 1)) {
            Right = false;
            kierunki--;
        }

        if (kierunki == 0) return new point(-192, -441);
        while (true) {
            Random rand = new Random();
            int losowanyKierunek = rand.nextInt(4);
            if (losowanyKierunek == (Direction.UP.ordinal()) && (Up == true)) {
                return new point(curX, curY - 1);
            }
            if (losowanyKierunek == (Direction.DOWN.ordinal()) && (Down == true) ) {
                return new point(curX, curY + 1);
            }

            if (losowanyKierunek == (Direction.LEFT.ordinal()) && (Left == true)) {
                return new point(curX - 1, curY);
            }

            if (losowanyKierunek == (Direction.RIGHT).ordinal() && (Right == true) ) {
                return new point(curX + 1, curY);
            }
        }
        /*return new point(-192, -441);*/

    }

    public point chooseFreePlace() {
        boolean Up = true;
        boolean Down = true;
        Boolean Left = true;
        Boolean Right = true;
        int curX = polozenie.getX();
        int curY = polozenie.getY();
        int kierunki = 4;

        if (curY == 0) {
            Up = false;
            kierunki--;
        } else if (getSwiat().getPlansza()[curY - 1][curX] != null) {
            Up = false;
            kierunki--;
        }

        if (curY == (swiat.getHeightB() - 1)) {
            Down = false;
            kierunki--;
        } else if (getSwiat().getPlansza()[curY + 1][curX] != null) {
            Down = false;
            kierunki--;
        }


        if (curX == 0) {
            Left = false;
            kierunki--;
        } else if (getSwiat().getPlansza()[curY][curX - 1] != null) {
            Left = false;
            kierunki--;
        }


        if (curX == (swiat.getWidthB() - 1)) {
            Right = false;
            kierunki--;
        } else if (getSwiat().getPlansza()[curY][curX + 1] != null) {
            Right = false;
            kierunki--;
        }

        if (kierunki == 0) return new point(-192, -441);
        while (true) {
            Random rand = new Random();
            int losowanyKierunek = rand.nextInt(4);
            if (losowanyKierunek == (Direction.UP.ordinal()) && (Up == true)) {
                return new point(curX, curY - 1);
            }
            if (losowanyKierunek == (Direction.DOWN.ordinal()) && (Down == true) ) {
                return new point(curX, curY + 1);
            }

            if (losowanyKierunek == (Direction.LEFT.ordinal()) && (Left == true)) {
                return new point(curX - 1, curY);
            }

            if (losowanyKierunek == (Direction.RIGHT).ordinal() && (Right == true) ) {
                return new point(curX + 1, curY);
            }
        }
        /*return new point(-192, -441);*/

    }

    static TypOrganizmu losujGatunekOrganizmu() {
        Random rand = new Random();
        int tmp = rand.nextInt(LICZBA_ROZNYCH_GATUNKOW - 1);//BEZ CZLOWIEKA
        if (tmp == 0) return TypOrganizmu.ANTYLOPA;
        if (tmp == 1) return TypOrganizmu.BARSZCZ_SOSNOWSKIEGO;
        if (tmp == 2) return TypOrganizmu.GUARANA;
        if (tmp == 3) return TypOrganizmu.LIS;
        if (tmp == 4) return TypOrganizmu.MLECZ;
        if (tmp == 5) return TypOrganizmu.OWCA;
        if (tmp == 6) return TypOrganizmu.TRAWA;
        if (tmp == 7) return TypOrganizmu.WILCZE_JAGODY;
        if (tmp == 8) return TypOrganizmu.WILK;
        if (tmp == 9) return TypOrganizmu.CYBER_OWCA;
        else return TypOrganizmu.ZOLW;

    }


    public void goToThePlace(point place) {
        swiat.getPlansza()[this.polozenie.getY()][this.polozenie.getX()] = null;
        swiat.getPlansza()[place.getY()][place.getX()] = this;
        this.polozenie.setX(place.getX());
        this.polozenie.setY(place.getY());
    }

    public int getSila() {return sila;}
    public boolean specialAction(Organizm attacker, Organizm victim) {
        return false;
    }

    public void setTypOrganizmu(TypOrganizmu value) {
        this.typOrganizmu = value;
    }
    public void setInicjatywa(int value) {
        this.inicjatywa = value;
    }

    public point getPolozenie() {return polozenie;}
    public Swiat getSwiat() {return swiat;}
    public TypOrganizmu getTypOrganizmu() {return typOrganizmu;}
    public boolean getCzyZywy() {return CzyZywy;}
    public boolean getCzyRozmnozylSie() {return CzyRozmnozylSie;}
    public int getInicjatywa() {return inicjatywa;}
    public int getTura() {return turaUrodzenia;}
    public Color getKolor() {return kolor;}

    public String infoOrganism() {
        return (nameTypOrganizmu() + " x[" + polozenie.getX() + "] y["
                + polozenie.getY() + "] sila[" + sila + "]");
    }

    public void setKolor(Color kolor) {
        this.kolor = kolor;
    }
    public void setCzyZywy(boolean value) {
        this.CzyZywy = value;
    }
    public void setSila(int value) {
        this.sila = value;
    }

    public void setTura(int value) {this.turaUrodzenia = value;}
    public void setSzansaRozmnazania(double szansaRozmnazania) {
        this.szansaRozmnazania = szansaRozmnazania;
    }
    public double getSzansaRozmnazania() {return szansaRozmnazania;}
    public void setCzyRozmnozylSie(boolean value) {this.CzyRozmnozylSie = value;}
}
