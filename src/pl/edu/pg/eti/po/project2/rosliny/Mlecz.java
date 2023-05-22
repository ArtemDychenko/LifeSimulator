package pl.edu.pg.eti.po.project2.rosliny;

import pl.edu.pg.eti.po.project2.Roslina;
import pl.edu.pg.eti.po.project2.Swiat;
import pl.edu.pg.eti.po.project2.point;

import java.util.Random;
import java.awt.*;

public class Mlecz extends Roslina {
    private static final int SILA_MLECZ = 0;
    private static final int INICJATYWA_MLECZ = 0;
    private static final int NumbChances = 3;

    public Mlecz(Swiat swiat, point polozenie, int turaUrodzenia) {
        super(swiat, polozenie,
                 SILA_MLECZ, INICJATYWA_MLECZ, turaUrodzenia, TypOrganizmu.MLECZ);
        setKolor(new Color(217, 194, 32));
        setSzansaRozmnazania(0.2);
    }

    @Override
    public void Akcja() {
        Random rand = new Random();
        for (int i = 0; i < NumbChances; i++) {
            int tmpLosowanie = rand.nextInt(100);
            if (tmpLosowanie < getSzansaRozmnazania() * 100) Rozprzestrzenianie();
        }
    }

    @Override
    public String nameTypOrganizmu() {
        return "Mlecz";
    }
}
