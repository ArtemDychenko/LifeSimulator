package pl.edu.pg.eti.po.project2.zwierzeta;

import pl.edu.pg.eti.po.project2.Zwierze;
import pl.edu.pg.eti.po.project2.Organizm;
import pl.edu.pg.eti.po.project2.Log;
import pl.edu.pg.eti.po.project2.Swiat;
import pl.edu.pg.eti.po.project2.point;

import java.util.Random;
import java.awt.*;

public class Antylopa extends Zwierze {
    private static final int ZASIEG_RUCHU_ANTYLOPY = 2;
    private static final int SZANSA_WYKONYWANIA_RUCHU_ANTYLOPY = 1;
    private static final int SILA_ANTYLOPY = 4;
    private static final int INICJATYWA_ANTYLOPY = 4;


    public Antylopa(Swiat swiat, point polozenie, int turaUrodzenia) {
        super(swiat, polozenie, SILA_ANTYLOPY, INICJATYWA_ANTYLOPY, turaUrodzenia, TypOrganizmu.ANTYLOPA);
        this.setZasiegRuchu(ZASIEG_RUCHU_ANTYLOPY);
        this.setSzansaWykonywaniaRuchu(SZANSA_WYKONYWANIA_RUCHU_ANTYLOPY);
        setKolor(new Color(153, 76, 0));
    }

    @Override
    public String nameTypOrganizmu() {
        return "Antylopa";
    }

    @Override
    public boolean specialAction(Organizm attacker, Organizm victim) {
        Random rand = new Random();
        int tmpLosowanie = rand.nextInt(100);
        if (tmpLosowanie < 50) {
            if (this == attacker) {
                Log.addToLog(infoOrganism() + " ucieka od " + victim.infoOrganism());
                point nextMove = victim.chooseFreePlace();
                if (!nextMove.equals(new point(-192, -441)))
                    goToThePlace(nextMove);
            } else if (this == victim) {
                Log.addToLog(infoOrganism() + " ucieka od " + attacker.infoOrganism());
                point place = this.getPolozenie();
                point nextMove = this.chooseFreePlace();
                if (!nextMove.equals(new point(-192, -441))) {
                    goToThePlace(nextMove);
                    if (getPolozenie().equals(place)) {
                        getSwiat().deleteOrganizm(this);
                        Log.addToLog(attacker.infoOrganism() + " zabija " + infoOrganism());
                    }
                }
                attacker.goToThePlace(place);
            }
            return true;
        } else return false;
    }
}
