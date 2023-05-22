package pl.edu.pg.eti.po.project2;

import java.util.Random;

public abstract class Roslina extends Organizm {
    protected Roslina( Swiat swiat,
                      point polozenie, int sila, int inicjatywa, int turaUrodzenia,TypOrganizmu typOrganizmu) {
        super( swiat, polozenie,  sila, inicjatywa, turaUrodzenia, typOrganizmu);
        setSzansaRozmnazania(0.3);
    }


    @Override
    public void Akcja() {
        Random rand = new Random();
        int upperbound = 100;
        int tmpLosowanie = rand.nextInt(upperbound);
        if (tmpLosowanie < getSzansaRozmnazania() * 100) Rozprzestrzenianie();
    }

    @Override
    public boolean isAnimal() {
        return false;
    }


    protected void Rozprzestrzenianie() {
        point place = chooseFreePlace();
        if (place.equals(new point(-192, -441))) return;
        else {
            Organizm tmpOrganizm = SpawnerOrganisms.spawnNewCreation(getTypOrganizmu(), this.getSwiat(), place);
            Log.addToLog("Wzrasta nowa roslina " + tmpOrganizm.infoOrganism());
            getSwiat().addOrganism(tmpOrganizm);
        }
    }

    @Override
    public void Kolizja(Organizm other) {
    }

}
