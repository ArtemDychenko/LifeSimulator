package pl.edu.pg.eti.po.project2.zwierzeta;

import pl.edu.pg.eti.po.project2.Zwierze;
import pl.edu.pg.eti.po.project2.Organizm;
import pl.edu.pg.eti.po.project2.Log;
import pl.edu.pg.eti.po.project2.Swiat;
import pl.edu.pg.eti.po.project2.point;
import pl.edu.pg.eti.po.project2.Skill;

import java.awt.*;

public class Czlowiek extends Zwierze {
    private static final int ZASIEG_RUCHU_CZLOWIEKA = 1;
    private static final int SZANSA_WYKONYWANIA_RUCHU_CZLOWIEKA = 1;
    private static final int SILA_CZLOWIEKA = 5;
    private static final int INICJATYWA_CZLOWIEKA = 4;
    private Direction direction;
    private final Skill skill;

    public Czlowiek(Swiat swiat, point polozenie, int turaUrodzenia) {
        super(swiat, polozenie, SILA_CZLOWIEKA, INICJATYWA_CZLOWIEKA, turaUrodzenia, TypOrganizmu.CZLOWIEK);
        this.setZasiegRuchu(ZASIEG_RUCHU_CZLOWIEKA);
        this.setSzansaWykonywaniaRuchu(SZANSA_WYKONYWANIA_RUCHU_CZLOWIEKA);
        direction = Direction.STAY;
        setKolor(new Color(25, 12, 189));
        skill = new Skill();
    }


    @Override
    protected point nextMove() {
        int x = polozenie.getX();
        int y = polozenie.getY();
        boolean Up = true;
        boolean Down = true;
        boolean Left = true;
        boolean Right = true;

        if (polozenie.getY() == 0) {
            Up = false;
        }
        if (polozenie.getY() == (swiat.getHeightB() - 1)) {
            Down = false;
        }
        if (polozenie.getX() == 0) {
            Left = false;
        }
        if (polozenie.getX() == (swiat.getWidthB() - 1)) {
            Right = false;
        }

        point przyszlaPozycja;
        if (direction == Direction.DOWN && Down) return new point(x, y + 1);
        if (direction == Direction.UP && Up) return new point(x, y - 1);
        if (direction == Direction.LEFT && Left) return new point(x - 1, y);
        if (direction == Direction.RIGHT && Right) return new point(x + 1, y);
        else return new point(x, y);
    }

    public void MagicElixir() {
        if (this.getSkill().getIterator() > 0) {
            this.setSila(this.getSkill().getEndPoint() + skill.getTime());
        } else {
            this.setSila(this.getSkill().getEndPoint() - skill.getTime());
        }
    }

    @Override
    public void Akcja() {
        if (skill.ifActive()) {
            Log.addToLog(infoOrganism() + " 'Magiczny eliksir' jest aktywny(Pozostaly czas "
                    + skill.getTime() + " tur)");
            MagicElixir();
        }
        for (int i = 0; i < getZasiegRuchu(); i++) {
            point przyszlaPozycja = nextMove();

            if (!getSwiat().placeIsFree(przyszlaPozycja)
                    && getSwiat().contentOfPlace(przyszlaPozycja) != this) {
                Kolizja(getSwiat().contentOfPlace(przyszlaPozycja));
                break;
            } else if (getSwiat().contentOfPlace(przyszlaPozycja) != this) goToThePlace(przyszlaPozycja);
            if (skill.ifActive()) MagicElixir();
        }
        direction = Direction.STAY;

    }

    @Override
    public String nameTypOrganizmu() {
        return "Czlowiek";
    }

    public Skill getSkill() {
        return skill;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
