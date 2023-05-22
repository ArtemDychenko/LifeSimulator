package pl.edu.pg.eti.po.project2.zwierzeta;

import pl.edu.pg.eti.po.project2.Zwierze;
import pl.edu.pg.eti.po.project2.Swiat;
import pl.edu.pg.eti.po.project2.point;

import java.awt.*;

public class Wilk extends Zwierze {
    private static final int ZASIEG_RUCHU_WILKA = 1;
    private static final int SZANSA_WYKONYWANIA_RUCHU_WILKA = 1;
    private static final int SILA_WILKA = 9;
    private static final int INICJATYWA_WILKA = 5;

    public Wilk(Swiat swiat, point polozenie, int turaUrodzenia) {
        super( swiat, polozenie, SILA_WILKA, INICJATYWA_WILKA,turaUrodzenia, TypOrganizmu.WILK);
        this.setZasiegRuchu(ZASIEG_RUCHU_WILKA);
        this.setSzansaWykonywaniaRuchu(SZANSA_WYKONYWANIA_RUCHU_WILKA);
        setKolor(new Color(64, 64, 64));
    }

    @Override
    public String nameTypOrganizmu() {
        return "Wilk";
    }
}
