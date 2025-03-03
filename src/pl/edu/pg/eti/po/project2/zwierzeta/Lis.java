package pl.edu.pg.eti.po.project2.zwierzeta;

import pl.edu.pg.eti.po.project2.Zwierze;
import pl.edu.pg.eti.po.project2.Organizm;
import pl.edu.pg.eti.po.project2.Swiat;
import pl.edu.pg.eti.po.project2.point;

import java.util.Random;
import java.awt.*;

public class Lis extends Zwierze {
    private static final int ZASIEG_RUCHU_LISA = 1;
    private static final int SZANSA_WYKONYWANIA_RUCHU_LISA = 1;
    private static final int SILA_LISA = 3;
    private static final int INICJATYWA_LISA = 7;

    public Lis(Swiat swiat, point polozenie, int turaUrodzenia) {
        super(swiat, polozenie, SILA_LISA, INICJATYWA_LISA, turaUrodzenia, TypOrganizmu.LIS);
        this.setZasiegRuchu(ZASIEG_RUCHU_LISA);
        this.setSzansaWykonywaniaRuchu(SZANSA_WYKONYWANIA_RUCHU_LISA);
        setKolor(new Color(255, 128, 0));
    }

    @Override
    public String nameTypOrganizmu() {
        return "Lis";
    }

    @Override
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
        } else if (getSwiat().getPlansza()[curY - 1][curX] != null && getSwiat().getPlansza()[curY - 1][curX].getSila() > this.getSila()) {
            Up = false;
            kierunki--;
        }

        if (curY == (swiat.getHeightB() - 1)) {
            Down = false;
            kierunki--;

        } else if (getSwiat().getPlansza()[curY + 1][curX] != null && getSwiat().getPlansza()[curY + 1][curX].getSila() > this.getSila()) {
            Down = false;
            kierunki--;
        }


        if (curX == 0) {
            Left = false;
            kierunki--;
        } else if (getSwiat().getPlansza()[curY][curX - 1] != null && getSwiat().getPlansza()[curY][curX - 1].getSila() > this.getSila()) {
            Left = false;
            kierunki--;
        }


        if (curX == (swiat.getWidthB() - 1)) {
            Right = false;
            kierunki--;
        } else if (getSwiat().getPlansza()[curY][curX + 1] != null && getSwiat().getPlansza()[curY][curX + 1].getSila() > this.getSila()) {
            Right = false;
            kierunki--;
        }

        if (kierunki == 0) return new point(-192, -441);
        while (true) {
            Random rand = new Random();
            int losowanyKierunek = rand.nextInt(4);
            if (losowanyKierunek == (Direction.UP.ordinal()) && (Up)) {
                return new point(curX, curY - 1);
            }
            if (losowanyKierunek == (Direction.DOWN.ordinal()) && (Down)) {
                return new point(curX, curY + 1);
            }

            if (losowanyKierunek == (Direction.LEFT.ordinal()) && (Left)) {
                return new point(curX - 1, curY);
            }

            if (losowanyKierunek == (Direction.RIGHT).ordinal() && (Right)) {
                return new point(curX + 1, curY);
            }
        }
    }
}
