package pl.edu.pg.eti.po.project2;

public class point {
    private int x, y;

    public point() {
        this.x=0;
        this.y=0;
    }
    public point(int a, int b) {
        this.x=a;
        this.y=b;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void setX(int x) {
        this.x=x;
    }

    public void setY(int y) {
        this.y=y;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof point)) {
            return false;
        }

        point objPoint = (point) obj;
        return Integer.compare(x, objPoint.x) == 0
                && Integer.compare(y, objPoint.y) == 0;
    }

}
