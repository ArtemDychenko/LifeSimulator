package pl.edu.pg.eti.po.project2;

import java.util.Random;

public abstract class Zwierze extends Organizm {
    protected int zasiegRuchu;
    protected double szansaWykonaniaRuchu;

    protected Zwierze(Swiat swiat, point polozenie, int sila, int inicjatywa, int turaUrodzenia, TypOrganizmu typOrganizmu) {
        super(swiat, polozenie, sila, inicjatywa, turaUrodzenia, typOrganizmu);
        setCzyRozmnozylSie(false);
        setSzansaRozmnazania(0.5);
    }

    @Override
    public void Akcja() {
        for (int i = 0; i < zasiegRuchu; i++) {
            point nextPlace = nextMove();
            if (nextPlace.equals(new point(-192, -441))) return;
            if (swiat.placeIsFree(nextPlace)) {
                goToThePlace(nextPlace);
            } else {
                if (nextPlace.equals(new point(-192, -441))) return;
                Kolizja(swiat.contentOfPlace(nextPlace));
                break;
            }
        }
    }

    @Override
    public boolean isAnimal() {
        return true;
    }

    @Override
    public void Kolizja(Organizm other) {
        if (typOrganizmu == other.getTypOrganizmu()) {   //ROZMNAZANIE
            Random rand = new Random();
            if (rand.nextInt(100) < getSzansaRozmnazania() * 100) rozmnazanie(other);
        } else {
            if (other.specialAction(this, other)) return;
            if (specialAction(this, other)) return;

            if (sila >= other.getSila()) {
                this.getSwiat().deleteOrganizm(other);
                goToThePlace(other.getPolozenie());

                Log.addToLog(this.infoOrganism() + " zabija " + other.infoOrganism());
            } else {
                swiat.deleteOrganizm(this);
                Log.addToLog(other.infoOrganism() + " zabija " + infoOrganism());
            }
        }
    }


    public void rozmnazanie(Organizm other) {
        if (this.getCzyRozmnozylSie() || other.getCzyRozmnozylSie()) return;

        point placeForChild = this.chooseFreePlace();
        if (placeForChild.equals(new point(-192, -441))) {
            placeForChild = other.chooseFreePlace();
            if (placeForChild.equals(new point(-192, -441))) {
                return;
            }
        }


        Organizm child = SpawnerOrganisms.spawnNewCreation(getTypOrganizmu(), this.getSwiat(), placeForChild);
        Log.addToLog("Urodzil sie " + child.infoOrganism());

        getSwiat().addOrganism(child);
        setCzyRozmnozylSie(true);
        other.setCzyRozmnozylSie(true);
    }

    protected point nextMove() {
        Random rand = new Random();

        if (rand.nextInt(100) >= (int) (szansaWykonaniaRuchu * 100)) return new point(-192, -441);
        else return chooseAnyPlace();

    }


    public int getZasiegRuchu() {
        return zasiegRuchu;
    }

    public void setZasiegRuchu(int zasiegRuchu) {
        this.zasiegRuchu = zasiegRuchu;
    }

    public double getSzansaWykonaniaRuchu() {
        return szansaWykonaniaRuchu;
    }

    public void setSzansaWykonywaniaRuchu(double szansaWykonywaniaRuchu) {
        this.szansaWykonaniaRuchu = szansaWykonywaniaRuchu;
    }

}