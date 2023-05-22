package pl.edu.pg.eti.po.project2.rosliny;

import pl.edu.pg.eti.po.project2.Roslina;
import pl.edu.pg.eti.po.project2.Organizm;
import pl.edu.pg.eti.po.project2.Log;
import pl.edu.pg.eti.po.project2.Swiat;
import pl.edu.pg.eti.po.project2.point;

import java.util.Random;
import java.awt.*;

public class WilczeJagody extends Roslina {
    private static final int SILA_WILCZE_JAGODY = 99;
    private static final int INICJATYWA_WILCZE_JAGODY = 0;

    public WilczeJagody(Swiat swiat, point polozenie, int turaUrodzenia) {
        super( swiat, polozenie, SILA_WILCZE_JAGODY, INICJATYWA_WILCZE_JAGODY, turaUrodzenia, TypOrganizmu.WILCZE_JAGODY);
        setKolor(new Color(0, 0, 0));
        setSzansaRozmnazania(0.05);
    }


    @Override
    public void Akcja() {
        Random rand = new Random();
        int upperbound = 100;
        int tmpLosowanie = rand.nextInt(upperbound);
        if (tmpLosowanie < getSzansaRozmnazania() * 100) Rozprzestrzenianie();
    }

    @Override
    public String nameTypOrganizmu() {
        return "Wilcze jagody";
    }

    @Override
    public boolean specialAction(Organizm attacker, Organizm victim) {
        Log.addToLog(attacker.infoOrganism() + " zjada " + this.infoOrganism());
        if (attacker.getSila() >= 99) {
            getSwiat().deleteOrganizm(this);
            Log.addToLog(attacker.infoOrganism() + " niszczy krzak wilczej jagody");
        }
        if (attacker.isAnimal()) {
            getSwiat().deleteOrganizm(attacker);
            Log.addToLog(attacker.infoOrganism() + " ginie od wilczej jagody");
        }
        return true;
    }
}