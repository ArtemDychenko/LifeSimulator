package pl.edu.pg.eti.po.project2.rosliny;

import pl.edu.pg.eti.po.project2.*;
import pl.edu.pg.eti.po.project2.Log;

import java.awt.*;
import java.util.Random;

public class BarszczSosnowskiego extends Roslina {
    private static final int SILA_BARSZCZ_SOSNOWSKIEGO = 10;
    private static final int INICJATYWA_BARSZCZ_SOSNOWSKIEGO = 0;

    public BarszczSosnowskiego(Swiat swiat, point polozenie, int turaUrodzenia) {

        super(swiat, polozenie,
                SILA_BARSZCZ_SOSNOWSKIEGO, INICJATYWA_BARSZCZ_SOSNOWSKIEGO, turaUrodzenia, TypOrganizmu.BARSZCZ_SOSNOWSKIEGO);
        setKolor(new Color(82, 0, 0));
        setSzansaRozmnazania(0.05);
    }

    @Override
    public void Akcja() {
        boolean Up = true;
        boolean Down = true;
        Boolean Left = true;
        Boolean Right = true;
        int curX = polozenie.getX();
        int curY = polozenie.getY();
        Organizm tmpOrganizm = null;

        if (curY == 0) {
            Up = false;

        }

        if (curY == (swiat.getHeightB() - 1)) {
            Down = false;

        }


        if (curX == 0) {
            Left = false;

        }


        if (curX == (swiat.getWidthB() - 1)) {
            Right = false;

        }


        for (int i = 0; i < 4; i++) {
            if (i == 0 && (Up)) {
                tmpOrganizm = getSwiat().contentOfPlace(new point(curX, curY - 1));
            } else if (i == 1 && (Down)) {
                tmpOrganizm = getSwiat().contentOfPlace(new point(curX, curY + 1));
            } else if (i == 2 && (Left)) {
                tmpOrganizm = getSwiat().contentOfPlace(new point(curX - 1, curY));
            } else if (i == 3 && (Right)) {
                tmpOrganizm = getSwiat().contentOfPlace(new point(curX + 1, curY));
            }
            if (tmpOrganizm != null && tmpOrganizm.isAnimal()
                    && tmpOrganizm.getTypOrganizmu() != TypOrganizmu.CYBER_OWCA) {
                getSwiat().deleteOrganizm(tmpOrganizm);
                Log.addToLog(infoOrganism() + " zabija " + tmpOrganizm.infoOrganism());
            }
        }
        Random rand = new Random();
        int tmpLosowanie = rand.nextInt(100);
        if (tmpLosowanie < getSzansaRozmnazania() * 100) Rozprzestrzenianie();

    }

    @Override
    public String nameTypOrganizmu() {
        return "Barszcz Sosnowskiego";
    }

    @Override
    public boolean specialAction(Organizm attacker, Organizm victim) {
        if (attacker.getSila() >= 10) {
            getSwiat().deleteOrganizm(this);
            Log.addToLog(attacker.infoOrganism() + " zjada " + this.infoOrganism());
            attacker.goToThePlace(victim.getPolozenie());
        }
        if ((attacker.isAnimal() && attacker.getTypOrganizmu() != TypOrganizmu.CYBER_OWCA)
                || attacker.getSila() < 10) {
            getSwiat().deleteOrganizm(attacker);
            Log.addToLog(this.infoOrganism() + " zabija " + attacker.infoOrganism());
        }
        return true;
    }
}
