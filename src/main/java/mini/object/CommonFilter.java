package mini.object;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class CommonFilter {

    private int x1 = Integer.MIN_VALUE;

    private int y1 = Integer.MIN_VALUE;

    private int x2 = Integer.MIN_VALUE;

    private int y2 = Integer.MIN_VALUE;

    private int offset = Integer.MIN_VALUE;

    private int limit = Integer.MIN_VALUE;

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

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    void setFilters(PageFilter pageFilter, RectangleFilter rectangleFilter){
        if (pageFilter != null) {
            if (this.getOffset() != Integer.MIN_VALUE) pageFilter.setOffset(this.getOffset());
            if (this.getLimit() != Integer.MIN_VALUE) pageFilter.setLimit(this.getLimit());
        }

        if (rectangleFilter != null) {
            if (this.getX1() != Integer.MIN_VALUE) rectangleFilter.setX1(this.getX1());
            if (this.getY1() != Integer.MIN_VALUE) rectangleFilter.setY1(this.getY1());
            if (this.getX2() != Integer.MIN_VALUE) rectangleFilter.setX2(this.getX2());
            if (this.getY2() != Integer.MIN_VALUE) rectangleFilter.setY2(this.getY2());
        }
    }
}
