package mini.service;

import mini.dto.WidgetDto;
import mini.object.PageFilter;
import mini.object.RectangleFilter;
import mini.object.Widget;

import java.util.List;

public interface WidgetService {

    Widget get(Long widgetId);

    Widget add(WidgetDto newWidget);

    Widget edit(WidgetDto newWidget);

    boolean delete(Long widgetId);

    List<Widget> listAll();

    List<Widget> list(PageFilter pageFilter);

    List<Widget> search(RectangleFilter rectangleFilter);

    List<Widget> search(RectangleFilter rectangleFilter, PageFilter pageFilter);
}
