package pl.edu.pg.eti.po.project2;

public class Skill {

    private final int COOLDOWN_SKILL = 5;
    private final int VALUE_SKILL = 10;

    protected  boolean isAvailable;
    protected  boolean isActive;

    protected int startPoint;
    protected int momentPoint;
    protected int endPoint;

    protected int iterator;
    protected int time;
    protected int cooldown;

    public Skill() {
        cooldown = 0;
        startPoint = VALUE_SKILL;
        momentPoint = 0;
        this.time = -2;
        isActive = false;
        isAvailable = true;
    }

    public void iteratorFunction() {
        if (cooldown > 0) cooldown--;
        if (time >= 0) {
            time--;
        }
        if (time == -1) {
            Dezaktywuj();
            time = -2;
        }
        if (cooldown == 0) {
            isAvailable = true;
        }
    }
    public void Aktywuj() {
        if (cooldown == 0) {
            isActive = true;
            isAvailable = false;
            cooldown = COOLDOWN_SKILL;

            time = Math.abs(endPoint - VALUE_SKILL);
            int range = endPoint - VALUE_SKILL;

            if (range > 0) {
                this.setIterator(-1);
            }
            else if (range == 0) {
                this.setIterator(0);
            }
            else if (range < 0) {
                this.setIterator(1);
            }

        }
        else if (cooldown > 0) {
            Log.addToLog("Umiejetnosc 'Magiczny Eliksir' mozna aktywowac tylko po " + cooldown +" turach\n");
        }
    }
    public void Dezaktywuj() {
        this.time = 0;
        this.cooldown = COOLDOWN_SKILL;
        isActive = false;
        isAvailable = false;
        Log.addToLog("Skill is off");
    }
    public boolean ifActive() { return isActive; };
    public boolean ifIsAvailable() { return isAvailable; };
    public int getTime() { return time; };
    public void setActive(boolean value) {this.isActive = value;}
    public void setAvailable(boolean value) {this.isAvailable = value;}
    public void setTime(int value) { this.time = time; };
    public void setEndtPoint(int sila) { this.endPoint = sila; }
    public int getEndPoint() { return endPoint; };
    public int getCooldown() { return cooldown; };
    public void setCooldown(int value) { this.cooldown = value; };
    public int getIterator() { return iterator; };
    public void setIterator(int value) { this.iterator = value; };



};
