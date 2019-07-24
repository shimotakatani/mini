package mini.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import mini.object.Widget;

import java.util.Date;

@JsonIgnoreProperties
public class WidgetDto {

    /**
     * id виджета
     */
    private Long id = Long.MIN_VALUE;

    /**
     * х координата левого верхнего края
     */
    private int x = Integer.MIN_VALUE;

    /**
     * у координата левого верхнего края
     */
    private int y = Integer.MIN_VALUE;

    /**
     * z-index, отвечающий за порядок в наслоении виджетов
     * может менять в зависимости от значений z-index'а других виджетов
     */
    private int z = Integer.MIN_VALUE;

    /**
     * Ширина виджета
     */
    private int width = Integer.MIN_VALUE;

    /**
     * Высота виджета
     */
    private int height = Integer.MIN_VALUE;

    /**
     * Время последнего изменения виджета
     */
    private Date updateTime = null;

    public void fromObject(Widget widget) {
        if (widget != null) {
            this.setId(widget.getId());
            this.setX(widget.getX());
            this.setY(widget.getY());
            this.setZ(widget.getZ());
            this.setWidth(widget.getWidth());
            this.setHeight(widget.getHeight());
            this.setUpdateTime(widget.getUpdateTime());
        }
    }

    public void toObject(Widget widget) {
        if (widget != null) {
            if (this.getId() != Long.MIN_VALUE) widget.setId(this.getId());
            if (this.getX() != Integer.MIN_VALUE) widget.setX(this.getX());
            if (this.getY() != Integer.MIN_VALUE) widget.setY(this.getY());
            if (this.getZ() != Integer.MIN_VALUE) widget.setZ(this.getZ());
            if (this.getWidth() >= 0) widget.setWidth(this.getWidth());
            if (this.getHeight() >= 0) widget.setHeight(this.getHeight());
            if (this.getUpdateTime() != null) widget.setUpdateTime(this.getUpdateTime());
        }
    }

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
}
