package pl.edu.pg.eti.po.project2.rosliny;

import pl.edu.pg.eti.po.project2.Roslina;
import pl.edu.pg.eti.po.project2.Swiat;
import pl.edu.pg.eti.po.project2.point;

import java.awt.*;

public class Trawa extends Roslina {
    private static final int SILA_TRAWY = 0;
    private static final int INICJATYWA_TRAWY = 0;

    public Trawa(Swiat swiat, point polozenie, int turaUrodzenia) {
        super(swiat, polozenie, SILA_TRAWY, INICJATYWA_TRAWY, turaUrodzenia, TypOrganizmu.TRAWA);
        setKolor(Color.GREEN);
    }

    @Override
    public String nameTypOrganizmu() {
        return "Trawa";
    }
}
