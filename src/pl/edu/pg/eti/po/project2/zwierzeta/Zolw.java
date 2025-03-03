package pl.edu.pg.eti.po.project2.zwierzeta;

import pl.edu.pg.eti.po.project2.Zwierze;
import pl.edu.pg.eti.po.project2.Organizm;
import pl.edu.pg.eti.po.project2.Log;
import pl.edu.pg.eti.po.project2.Swiat;
import pl.edu.pg.eti.po.project2.point;

import java.awt.*;

public class Zolw extends Zwierze {
    private static final int ZASIEG_RUCHU_ZOLWA = 1;
    private static final double SZANSA_WYKONYWANIA_RUCHU_ZOLWA = 0.25;
    private static final int SILA_ZOLWA = 2;
    private static final int INICJATYWA_ZOLWA = 1;

    public Zolw(Swiat swiat, point polozenie, int turaUrodzenia) {
        super(swiat, polozenie, SILA_ZOLWA, INICJATYWA_ZOLWA, turaUrodzenia, TypOrganizmu.ZOLW);
        this.setZasiegRuchu(ZASIEG_RUCHU_ZOLWA);
        this.setSzansaWykonywaniaRuchu(SZANSA_WYKONYWANIA_RUCHU_ZOLWA);
        setKolor(new Color(2, 86, 2));
    }

    @Override
    public String nameTypOrganizmu() {
        return "Zolw";
    }

    @Override
    public boolean specialAction(Organizm attacker, Organizm victim) {
        if (this == victim) {
            if (attacker.getSila() < 5 && attacker.isAnimal()) {
                Log.addToLog(infoOrganism() + " odpiera atak " + attacker.infoOrganism());
                return true;
            } else return false;
        } else {
            if (attacker.getSila() >= victim.getSila()) return false;
            else {
                if (victim.getSila() < 5 && victim.isAnimal()) {
                    Log.addToLog(infoOrganism() + " odpiera atak " + victim.infoOrganism());
                    return true;
                } else return false;
            }
        }
    }
}
