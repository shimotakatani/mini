package mini.object;

/**
 * Прямоугольный фильтр
 */
public class RectangleFilter {

    /**
     * Левая(меньшая) x
     */
    private int x1;

    /**
     * Верхня(меньшая) y
     */
    private int y1;

    /**
     * Правая(большая) х
     */
    private int x2;

    /**
     * Нижняя(большая) y
     */
    private int y2;

    public RectangleFilter(){
        this.x1 = 0;
        this.y1 = 0;
        this.x2 = 0;
        this.y2 = 0;
    }

    public RectangleFilter(int x1, int y1, int x2, int y2){
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public int getX1() {
        return x1;
    }

    public void setX1(int x1) {
        this.x1 = x1;
    }

    public int getY1() {
        return y1;
    }

    public void setY1(int y1) {
        this.y1 = y1;
    }

    public int getX2() {
        return x2;
    }

    public void setX2(int x2) {
        this.x2 = x2;
    }

    public int getY2() {
        return y2;
    }

    public void setY2(int y2) {
        this.y2 = y2;
    }
}
