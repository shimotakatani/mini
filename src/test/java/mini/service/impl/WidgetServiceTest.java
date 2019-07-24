package mini.service.impl;

import mini.dto.WidgetDto;
import mini.object.PageFilter;
import mini.object.RectangleFilter;
import mini.object.Widget;
import mini.service.WidgetService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class WidgetServiceTest{

    private Widget widget1;
    private Widget widget2;
    private Widget widget3;
    private Widget widget4;

    private WidgetDto widgetDto1;
    private WidgetDto widgetDto2;
    private WidgetDto widgetDto3;
    private WidgetDto widgetDto4;

    private WidgetService widgetService;

    @Before
    public void setUp() throws Exception {

        widgetService = new WidgetServiceImpl();

        widget1 = new Widget();
        widget1.setId(0L);
        widget1.setX(0);
        widget1.setY(0);
        widget1.setZ(0);
        widget1.setWidth(1);
        widget1.setHeight(1);
        widget1.setUpdateTime(new Date(100000));

        widget2 = new Widget();
        widget2.setId(1L);
        widget2.setX(4);
        widget2.setY(4);
        widget2.setZ(5);
        widget2.setWidth(1);
        widget2.setHeight(1);
        widget2.setUpdateTime(new Date(100000));

        widget3 = new Widget();
        widget3.setId(2L);
        widget3.setX(-2);
        widget3.setY(-2);
        widget3.setZ(9);
        widget3.setWidth(11);
        widget3.setHeight(11);
        widget3.setUpdateTime(new Date(100000));

        widget4 = new Widget();
        widget4.setId(3L);
        widget4.setX(-2);
        widget4.setY(-2);
        widget4.setZ(10);
        widget4.setWidth(11);
        widget4.setHeight(11);
        widget4.setUpdateTime(new Date(100000));

        widgetDto1 = new WidgetDto();
        widgetDto1.setId(0L);
        widgetDto1.setX(0);
        widgetDto1.setY(0);
        widgetDto1.setZ(0);
        widgetDto1.setWidth(1);
        widgetDto1.setHeight(1);
        widgetDto1.setUpdateTime(new Date(100000));

        widgetDto2 = new WidgetDto();
        widgetDto2.setId(1L);
        widgetDto2.setX(4);
        widgetDto2.setY(4);
        widgetDto2.setZ(5);
        widgetDto2.setWidth(1);
        widgetDto2.setHeight(1);
        widgetDto2.setUpdateTime(new Date(100000));

        widgetDto3 = new WidgetDto();
        widgetDto3.setId(2L);
        widgetDto3.setX(-2);
        widgetDto3.setY(-2);
        widgetDto3.setZ(9);
        widgetDto3.setWidth(11);
        widgetDto3.setHeight(11);
        widgetDto3.setUpdateTime(new Date(100000));

        widgetDto4 = new WidgetDto();
        widgetDto4.setId(3L);
        widgetDto4.setX(-2);
        widgetDto4.setY(-2);
        widgetDto4.setZ(10);
        widgetDto4.setWidth(11);
        widgetDto4.setHeight(11);
        widgetDto4.setUpdateTime(new Date(100000));
    }

    @Test
    public void get() {

        Widget addedWidget = widgetService.add(widgetDto1);
        Widget gettedWidget = widgetService.get(addedWidget.getId());

        Assert.assertEquals(addedWidget, gettedWidget);
        Assert.assertEquals(widget1, gettedWidget);//при условии, что у нас останется инкрементальный генератор идентификаторов
    }

    @Test
    public void add() {
        Widget addedWidget = widgetService.add(widgetDto1);
        Assert.assertEquals(widget1, addedWidget);
    }

    @Test
    public void edit() {
        Widget addedWidget = widgetService.add(widgetDto1);
        widgetDto3.setId(addedWidget.getId());//при обновлении не меняется id, он нужен чтобы найти изменяемый элемент
        Widget editedWidget = widgetService.edit(widgetDto3);
        widget3.setId(addedWidget.getId());
        Assert.assertEquals(widget3, editedWidget);
    }

    @Test
    public void delete() {
        Widget addedWidget = widgetService.add(widgetDto1);
        Long deleteId = addedWidget.getId();
        boolean isDelete = widgetService.delete(deleteId);
        Assert.assertTrue(isDelete);
        isDelete = widgetService.delete(deleteId);
        Assert.assertFalse(isDelete);
    }

    @Test
    public void listAll() {
        widgetService.add(widgetDto1);
        widgetService.add(widgetDto2);
        widgetService.add(widgetDto3);
        widgetService.add(widgetDto4);

        List<Widget> widgetList = new ArrayList<>();
        widgetList.add(widget1);
        widgetList.add(widget2);
        widgetList.add(widget3);
        widgetList.add(widget4);

        Assert.assertEquals(widgetList, widgetService.listAll());
    }

    @Test
    public void list() {
        widgetService.add(widgetDto1);
        widgetService.add(widgetDto2);
        widgetService.add(widgetDto3);
        widgetService.add(widgetDto4);

        List<Widget> widgetList = new ArrayList<>();
        widgetList.add(widget1);
        widgetList.add(widget2);
        widgetList.add(widget3);
        widgetList.add(widget4);

        PageFilter pageFilter = new PageFilter();
        pageFilter.setOffset(0);
        pageFilter.setLimit(5);

        Assert.assertEquals(widgetList, widgetService.list(pageFilter));

        pageFilter.setOffset(2);
        widgetList.remove(widget1);
        widgetList.remove(widget2);

        Assert.assertEquals(widgetList, widgetService.list(pageFilter));

        pageFilter.setLimit(1);
        widgetList.remove(widget4);

        Assert.assertEquals(widgetList, widgetService.list(pageFilter));

        pageFilter.setOffset(5);
        Assert.assertEquals(new ArrayList<>(), widgetService.list(pageFilter));
    }

    @Test
    public void search() {

        widgetService.add(widgetDto1);
        widgetService.add(widgetDto2);
        widgetService.add(widgetDto3);
        widgetService.add(widgetDto4);

        List<Widget> widgetList = new ArrayList<>();
        widgetList.add(widget1);
        widgetList.add(widget2);
        widgetList.add(widget3);
        widgetList.add(widget4);

        RectangleFilter rectangleFilter = new RectangleFilter();
        rectangleFilter.setX1(-5);
        rectangleFilter.setY1(-5);
        rectangleFilter.setX2(5);
        rectangleFilter.setY2(5);

        Assert.assertEquals(widgetList, widgetService.search(rectangleFilter));

        widgetList.remove(widget1);
        rectangleFilter.setX1(2);
        rectangleFilter.setY1(2);

        Assert.assertEquals(widgetList, widgetService.search(rectangleFilter));

    }
}