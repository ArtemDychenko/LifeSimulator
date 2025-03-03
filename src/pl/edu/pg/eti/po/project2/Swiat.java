package pl.edu.pg.eti.po.project2;

import pl.edu.pg.eti.po.project2.zwierzeta.Czlowiek;

import java.io.IOException;
import java.util.*;
import java.io.File;
import java.io.PrintWriter;

public class Swiat {
    protected int widthB, heightB;
    protected Organizm[][] plansza;
    protected int tura;
    protected ArrayList<Organizm> organizmy;
    protected boolean ifKoniecGry;

    protected boolean iSHumanAlive;
    protected Czlowiek human;
    protected boolean isPauza;

    protected Log log;
    protected SwiatGrafika swiatGrafika;

    public Swiat(SwiatGrafika swiatGrafika) {
        this.widthB = 0;
        this.heightB = 0;
        this.ifKoniecGry = false;
        this.iSHumanAlive = true;
        this.tura = 0;
        this.isPauza = true;
        this.log = new Log();
        organizmy = new ArrayList<>();
        this.swiatGrafika = swiatGrafika;
    }

    public Swiat(int widthB, int heightB, SwiatGrafika swiatGrafika) {
        this.widthB = widthB;
        this.heightB = heightB;
        this.iSHumanAlive = true;
        this.ifKoniecGry = false;
        this.isPauza = true;
        this.tura = 0;
        this.log = new Log();
        plansza = new Organizm[heightB][widthB];
        for (int i = 0; i < heightB; i++) {
            for (int j = 0; j < widthB; j++) {
                plansza[i][j] = null;
            }
        }
        organizmy = new ArrayList<>();
        this.swiatGrafika = swiatGrafika;
    }

    public void wykonajTure() {
        if (getIfKoniecGry()) return;
        this.tura++;

        sortOrganisms();
        if (iSHumanAlive) {
            getHuman().getSkill().iteratorFunction();
        }
        for (int i = 0; i < organizmy.size(); i++) {
            if (!organizmy.get(i).getCzyZywy()) {
                continue;
            } else if (organizmy.get(i).getTura() != tura) {
                organizmy.get(i).Akcja();

            }
        }

        for (int i = 0; i < organizmy.size(); i++) {
            if (!organizmy.get(i).getCzyZywy()) {
                organizmy.remove(i);
                i--;
            }
        }

        for (int i = 0; i < organizmy.size(); i++) {
            organizmy.get(i).setCzyRozmnozylSie(false);
        }


    }


    public void ZapiszSwiat(String nameOfFile) {
        try {
            nameOfFile += ".txt";
            File file = new File(nameOfFile);
            file.createNewFile();

            PrintWriter pw = new PrintWriter(file);
            pw.print(widthB + " ");
            pw.print(heightB + " ");
            pw.print(tura + " ");
            pw.print(iSHumanAlive + " ");
            pw.print(ifKoniecGry + "\n");
            for (int i = 0; i < organizmy.size(); i++) {
                pw.print(organizmy.get(i).getTypOrganizmu() + " ");
                pw.print(organizmy.get(i).getPolozenie().getX() + " ");
                pw.print(organizmy.get(i).getPolozenie().getY() + " ");
                pw.print(organizmy.get(i).getSila() + " ");
                pw.print(organizmy.get(i).getTura() + " ");
                pw.print(organizmy.get(i).getCzyZywy());
                if (organizmy.get(i).getTypOrganizmu() == Organizm.TypOrganizmu.CZLOWIEK) {
                    pw.print(" " + human.getSkill().getTime() + " ");
                    pw.print(human.getSkill().getCooldown() + " ");
                    pw.print(human.getSkill().ifActive() + " ");
                    pw.print(human.getSkill().ifIsAvailable());
                }
                pw.println();
            }
            pw.close();
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }

    public static Swiat OdtworzSwiat(String nameOfFile) {
        try {
            nameOfFile += ".txt";
            File file = new File(nameOfFile);

            Scanner scanner = new Scanner(file);
            String line = scanner.nextLine();
            String[] properties = line.split(" ");
            int sizeX = Integer.parseInt(properties[0]);
            int sizeY = Integer.parseInt(properties[1]);
            Swiat tmpSwiat = new Swiat(sizeX, sizeY, null);
            int numerTury = Integer.parseInt(properties[2]);
            tmpSwiat.tura = numerTury;
            boolean iSHumanAlive = Boolean.parseBoolean(properties[3]);
            tmpSwiat.iSHumanAlive = iSHumanAlive;
            boolean ifKoniecGry = Boolean.parseBoolean(properties[4]);
            tmpSwiat.ifKoniecGry = ifKoniecGry;
            tmpSwiat.human = null;

            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                properties = line.split(" ");
                Organizm.TypOrganizmu typOrganizmu = Organizm.TypOrganizmu.valueOf(properties[0]);
                int x = Integer.parseInt(properties[1]);
                int y = Integer.parseInt(properties[2]);

                Organizm tmpOrganizm = SpawnerOrganisms.spawnNewCreation
                        (typOrganizmu, tmpSwiat, new point(x, y));
                int sila = Integer.parseInt(properties[3]);
                tmpOrganizm.setSila(sila);
                int turaUrodzenia = Integer.parseInt(properties[4]);
                tmpOrganizm.setTura(turaUrodzenia);
                boolean CzyZywy = Boolean.parseBoolean(properties[5]);
                tmpOrganizm.setCzyZywy(CzyZywy);

                if (typOrganizmu == Organizm.TypOrganizmu.CZLOWIEK) {
                    tmpSwiat.human = (Czlowiek) tmpOrganizm;
                    int czasTrwania = Integer.parseInt(properties[6]);
                    tmpSwiat.human.getSkill().setTime(czasTrwania);
                    int cooldown = Integer.parseInt(properties[7]);
                    tmpSwiat.human.getSkill().setCooldown(cooldown);
                    boolean czyJestAktywna = Boolean.parseBoolean(properties[8]);
                    tmpSwiat.human.getSkill().setActive(czyJestAktywna);
                    boolean czyMoznaAktywowac = Boolean.parseBoolean(properties[9]);
                    tmpSwiat.human.getSkill().setAvailable(czyMoznaAktywowac);
                }
                tmpSwiat.addOrganism(tmpOrganizm);
            }
            scanner.close();
            return tmpSwiat;
        } catch (
                IOException e) {
            System.out.println("Error: " + e);
        }
        return null;
    }

    public void createOrganisms(double LOADEDWORLD) {
        int liczbaOrganizmow = (int) Math.floor(widthB * heightB * LOADEDWORLD);
        //DODANIE CZLOWIEKA
        point place = findFreePlace();
        Organizm tmpOrganizm = SpawnerOrganisms.spawnNewCreation(Organizm.TypOrganizmu.CZLOWIEK, this, place);
        addOrganism(tmpOrganizm);
        human = (Czlowiek) tmpOrganizm;

        //DODANIE INNYCH ORGANIZMOW
        for (int i = 0; i < liczbaOrganizmow - 1; i++) {
            place = findFreePlace();
            if (!(place.equals(new point(-192, -441)))) {
                addOrganism(SpawnerOrganisms.spawnNewCreation(Organizm.losujGatunekOrganizmu(), this, place));
            } else return;
        }
    }

    public void addOrganism(Organizm organizm) {
        organizmy.add(organizm);
        plansza[organizm.getPolozenie().getY()][organizm.getPolozenie().getX()] = organizm;
    }

    private void sortOrganisms() {
        Collections.sort(organizmy, new Comparator<Organizm>() {
            @Override
            public int compare(Organizm o1, Organizm o2) {
                if (o1.getInicjatywa() != o2.getInicjatywa())
                    return Integer.valueOf(o2.getInicjatywa()).compareTo(o1.getInicjatywa());
                else
                    return Integer.valueOf(o1.getTura()).compareTo(o2.getTura());
            }
        });
    }

    public point findFreePlace() {
        Random rand = new Random();
        for (int i = 0; i < heightB; i++) {
            for (int j = 0; j < widthB; j++) {
                if (plansza[i][j] == null) {
                    while (true) {
                        int x = rand.nextInt(widthB);
                        int y = rand.nextInt(heightB);
                        if (plansza[y][x] == null) return new point(x, y);
                    }
                }
            }
        }
        return new point(-192, -441);
    }

    public boolean placeIsFree(point place) {
        return plansza[place.getY()][place.getX()] == null;
    }

    public Czlowiek getHuman() {
        return human;
    }

    public void deleteOrganizm(Organizm organizm) {
        this.plansza[organizm.getPolozenie().getY()][organizm.getPolozenie().getX()] = null;
        organizm.setCzyZywy(false);

        if (organizm.getTypOrganizmu() == Organizm.TypOrganizmu.CZLOWIEK) {
            iSHumanAlive = false;
            human = null;
        }
    }

    public boolean isExistBarszcz() {
        for (int i = 0; i < heightB; i++) {
            for (int j = 0; j < widthB; j++) {
                if (plansza[i][j] != null &&
                        plansza[i][j].getTypOrganizmu() == Organizm.TypOrganizmu.BARSZCZ_SOSNOWSKIEGO) {
                    return true;
                }
            }
        }
        return false;
    }

    public void setPauza(boolean value) {
        this.isPauza = value;
    }

    public boolean IsHumanAlive() {
        return iSHumanAlive;
    }

    public boolean getIsPauza() {
        return isPauza;
    }

    public Organizm contentOfPlace(point place) {
        return plansza[place.getY()][place.getX()];
    }

    public Organizm[][] getPlansza() {
        return plansza;
    }

    public boolean getIfKoniecGry() {
        return ifKoniecGry;
    }

    public int getNumerTury() {
        return tura;
    }

    public void setSwiatGrafika(SwiatGrafika swiatGrafika) {
        this.swiatGrafika = swiatGrafika;
    }

    public int getWidthB() {
        return widthB;
    }

    public int getHeightB() {
        return heightB;
    }
}
