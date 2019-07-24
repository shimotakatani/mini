package mini.object;

import java.util.Date;

import java.util.Objects;
import java.util.UUID;

/**
 * Собственно виджет
 */
public class Widget {

    /**
     * id виджета
     */
    private Long id;

    /**
     * х координата левого верхнего края
     */
    private int x;

    /**
     * у координата левого верхнего края
     */
    private int y;

    /**
     * z-index, отвечающий за порядок в наслоении виджетов
     * может менять в зависимости от значений z-index'а других виджетов
     */
    private int z;

    /**
     * Ширина виджета
     */
    private int width;

    /**
     * Высота виджета
     */
    private int height;

    /**
     * Время последнего изменения виджета
     */
    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Widget widget = (Widget) o;
        return x == widget.x &&
                y == widget.y &&
                z == widget.z &&
                width == widget.width &&
                height == widget.height &&
                Objects.equals(id, widget.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, x, y, z, width, height);
    }
}
