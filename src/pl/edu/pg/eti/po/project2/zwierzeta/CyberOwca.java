package pl.edu.pg.eti.po.project2.zwierzeta;

import pl.edu.pg.eti.po.project2.Organizm;
import pl.edu.pg.eti.po.project2.point;
import pl.edu.pg.eti.po.project2.Swiat;
import pl.edu.pg.eti.po.project2.rosliny.BarszczSosnowskiego;

import java.awt.*;

public class CyberOwca extends Owca {
    private static final int ZASIEG_RUCHU_CYBER_OWCY = 1;
    private static final int SZANSA_WYKONYWANIA_RUCHU_CYBER_OWCY = 1;
    private static final int SILA_CYBER_OWCY = 11;
    private static final int INICJATYWA_CYBER_OWCY = 4;

    public CyberOwca(Swiat swiat, point polozenie, int turaUrodzenia) {
        super(swiat, polozenie, turaUrodzenia);
        setTypOrganizmu(TypOrganizmu.CYBER_OWCA);
        setSila(SILA_CYBER_OWCY);
        setInicjatywa(INICJATYWA_CYBER_OWCY);
        setSzansaRozmnazania(0.1);

        this.setZasiegRuchu(ZASIEG_RUCHU_CYBER_OWCY);
        this.setSzansaWykonywaniaRuchu(SZANSA_WYKONYWANIA_RUCHU_CYBER_OWCY);
        setKolor(new Color(0, 234, 255));
    }

    @Override
    public point chooseAnyPlace() {
        if (getSwiat().isExistBarszcz()) {

            point cel = findBarszcz().getPolozenie();
            int dx = Math.abs(polozenie.getX() - cel.getX());
            int dy = Math.abs(polozenie.getY() - cel.getY());
            if (dx >= dy) {
                if (polozenie.getX() > cel.getX()) {
                    return new point(polozenie.getX() - 1, polozenie.getY());
                } else {
                    return new point(polozenie.getX() + 1, polozenie.getY());
                }
            } else {
                if (polozenie.getY() > cel.getY()) {
                    return new point(polozenie.getX(), polozenie.getY() - 1);
                } else {
                    return new point(polozenie.getX(), polozenie.getY() + 1);
                }
            }
        } else return super.chooseAnyPlace();
    }

    private BarszczSosnowskiego findBarszcz() {
        BarszczSosnowskiego tmpBarszcz = null;
        int najmniejszaOdleglosc = getSwiat().getWidthB() + getSwiat().getHeightB() + 1;
        for (int i = 0; i < getSwiat().getHeightB(); i++) {
            for (int j = 0; j < getSwiat().getWidthB(); j++) {
                Organizm tmpOrganizm = getSwiat().getPlansza()[i][j];
                if (tmpOrganizm != null &&
                        tmpOrganizm.getTypOrganizmu() == TypOrganizmu.BARSZCZ_SOSNOWSKIEGO) {
                    int tmpOdleglosc = findDistance(tmpOrganizm.getPolozenie());
                    if (najmniejszaOdleglosc > tmpOdleglosc) {
                        najmniejszaOdleglosc = tmpOdleglosc;
                        tmpBarszcz = (BarszczSosnowskiego) tmpOrganizm;
                    }
                }
            }
        }
        return tmpBarszcz;
    }

    private int findDistance(point otherPlace) {
        int dx = Math.abs(getPolozenie().getX() - otherPlace.getX());
        int dy = Math.abs(getPolozenie().getY() - otherPlace.getY());
        return dx + dy;
    }

    @Override
    public String nameTypOrganizmu() {
        return "Cyber owca";
    }
}