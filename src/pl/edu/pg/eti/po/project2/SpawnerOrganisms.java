package pl.edu.pg.eti.po.project2;

import pl.edu.pg.eti.po.project2.rosliny.*;
import pl.edu.pg.eti.po.project2.zwierzeta.*;

import static pl.edu.pg.eti.po.project2.Organizm.*;

public class SpawnerOrganisms {
    public static Organizm spawnNewCreation(TypOrganizmu typOrganizmu, Swiat swiat, point polozenie) {


        if (typOrganizmu == TypOrganizmu.OWCA) {
            return new Owca(swiat, polozenie, swiat.getNumerTury());
        }
        if (typOrganizmu == TypOrganizmu.WILK) {
            return new Wilk(swiat, polozenie, swiat.getNumerTury());
        }
        if (typOrganizmu == TypOrganizmu.ZOLW) {
            return new Zolw(swiat, polozenie, swiat.getNumerTury());
        }
        if (typOrganizmu == TypOrganizmu.LIS) {
            return new Lis(swiat, polozenie, swiat.getNumerTury());
        }
        if (typOrganizmu == TypOrganizmu.ANTYLOPA) {
            return new Antylopa(swiat, polozenie, swiat.getNumerTury());
        }
        if (typOrganizmu == TypOrganizmu.TRAWA) {
            return new Trawa(swiat, polozenie, swiat.getNumerTury());
        }
        if (typOrganizmu == TypOrganizmu.MLECZ) {
            return new Mlecz(swiat, polozenie, swiat.getNumerTury());
        }
        if (typOrganizmu == TypOrganizmu.GUARANA) {
            return new Guarana(swiat, polozenie, swiat.getNumerTury());
        }
        if (typOrganizmu == TypOrganizmu.CYBER_OWCA) {
            return new CyberOwca(swiat, polozenie, swiat.getNumerTury());
        }


        if (typOrganizmu == TypOrganizmu.WILCZE_JAGODY) {
            return new WilczeJagody(swiat, polozenie, swiat.getNumerTury());
        }
        if (typOrganizmu == TypOrganizmu.BARSZCZ_SOSNOWSKIEGO) {
            return new BarszczSosnowskiego(swiat, polozenie, swiat.getNumerTury());
        }
        if (typOrganizmu == TypOrganizmu.CZLOWIEK) {
            return new Czlowiek(swiat, polozenie, swiat.getNumerTury());
        }
        return null;  //UNDEFINED TYP
    }
}
