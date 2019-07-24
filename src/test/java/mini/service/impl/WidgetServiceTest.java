package mini.service.impl;

import junit.framework.TestCase;
import mini.object.Widget;

import java.util.Date;

import static org.junit.Assert.*;

public class WidgetServiceTest extends TestCase {

    @org.junit.Before
    public void setUp() throws Exception {
        Widget widget1 = new Widget();
        widget1.setId(0L);
        widget1.setX(0);
        widget1.setY(0);
        widget1.setZ(0);
        widget1.setWidth(1);
        widget1.setHeight(1);
        widget1.setUpdateTime(new Date(100000));

        Widget widget2 = new Widget();
        widget2.setId(1L);
        widget2.setX(4);
        widget2.setY(4);
        widget2.setZ(5);
        widget2.setWidth(1);
        widget2.setHeight(1);
        widget2.setUpdateTime(new Date(100000));

        Widget widget3 = new Widget();
        widget3.setId(2L);
        widget3.setX(-2);
        widget3.setY(-2);
        widget3.setZ(10);
        widget3.setWidth(11);
        widget3.setHeight(11);
        widget3.setUpdateTime(new Date(100000));

        Widget widget4 = new Widget();
        widget4.setId(3L);
        widget4.setX(-2);
        widget4.setY(-2);
        widget4.setZ(10);
        widget4.setWidth(11);
        widget4.setHeight(11);
        widget4.setUpdateTime(new Date(100000));
    }

    @org.junit.Test
    public void get() {

    }

    @org.junit.Test
    public void add() {
    }

    @org.junit.Test
    public void edit() {
    }

    @org.junit.Test
    public void delete() {
    }

    @org.junit.Test
    public void listAll() {
    }

    @org.junit.Test
    public void list() {
    }

    @org.junit.Test
    public void search() {
    }
}