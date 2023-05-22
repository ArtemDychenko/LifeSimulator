package pl.edu.pg.eti.po.project2.rosliny;

import pl.edu.pg.eti.po.project2.Roslina;
import pl.edu.pg.eti.po.project2.Organizm;
import pl.edu.pg.eti.po.project2.Log;
import pl.edu.pg.eti.po.project2.Swiat;
import pl.edu.pg.eti.po.project2.point;

import java.awt.*;

public class Guarana extends Roslina {
    private static final int BONUS_POWER = 3;
    private static final int SILA_GUARANY = 0;
    private static final int INICJATYWA_GUARANY = 0;

    public Guarana(Swiat swiat, point polozenie, int turaUrodzenia) {
        super( swiat, polozenie,
                SILA_GUARANY, INICJATYWA_GUARANY, turaUrodzenia, TypOrganizmu.GUARANA);
        setKolor(new Color(168, 32, 201));
    }

    @Override
    public String nameTypOrganizmu() {
        return "Guarana";
    }

    @Override
    public boolean specialAction(Organizm attacker, Organizm victim) {
        point place = this.getPolozenie();
        getSwiat().deleteOrganizm(this);
        attacker.goToThePlace(place);
        Log.addToLog(attacker.infoOrganism() + " zjada " + this.infoOrganism()
                + "  i zwieksza swoja sile na " + Integer.toString(BONUS_POWER));
        attacker.setSila(attacker.getSila() + BONUS_POWER);
        return true;
    }
}