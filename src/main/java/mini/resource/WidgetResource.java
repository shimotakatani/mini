package mini.resource;

import mini.dto.ResponseDto;
import mini.dto.WidgetDto;
import mini.object.CommonFilter;
import mini.object.PageFilter;
import mini.object.RectangleFilter;
import mini.object.Widget;
import mini.service.WidgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/widget")
public class WidgetResource {

    @Autowired
    private WidgetService widgetService;

    /**
     * Добавить виджет
     * @param widgetDto - объект с данными о виджете, обязательны поля: x, y, width ,height
     * @return Добавленный виджет или сообщение об ошибке
     */
    @PostMapping(value = "add")
    public ResponseEntity<Object> add(@RequestBody WidgetDto widgetDto) {
        List<String> errors = new ArrayList<>();
        if (widgetDto == null) {
            errors.add("Не был передан добавляемый виджет.");

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto(errors));
        }
        if (widgetDto.getX() == Integer.MIN_VALUE) errors.add("Не указаны координаты виджета(x).");
        if (widgetDto.getY() == Integer.MIN_VALUE) errors.add("Не указаны координаты виджета(y).");
        if (widgetDto.getWidth() == Integer.MIN_VALUE) errors.add("Не указана ширина виджета.");
        if (widgetDto.getHeight() == Integer.MIN_VALUE) errors.add("Не указана высота виджета.");

        if ( ! errors.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto(errors));
        }

        Widget newWidget = widgetService.add(widgetDto);

        return ResponseEntity.status(HttpStatus.OK).body(newWidget);
    }

    /**
     * Получить виджет по id
     * @param id - идентификатор виджета
     * @return виджет или сообщение об ошибке
     */
    @GetMapping(value = "{id}")
    public ResponseEntity<Object> get(@PathVariable("id") Long id) {
        List<String> errors = new ArrayList<>();
        if (id == null) {
            errors.add("Не был передан id виджета.");

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto(errors));
        }

        Widget widget = widgetService.get(id);

        if (widget == null) {
            errors.add("По заданному id виджет не был найден.");

            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(errors));
        }

        return ResponseEntity.status(HttpStatus.OK).body(widget);
    }

    /**
     * Изменить виджет
     * @param widgetDto - данные, которые нужно поменять виджет. Изменяемый виджет опрежделяется по widgetDto.id,
     *                  остальные поля необязательны
     * @return измененныё виджет или сообщение об ошибке
     */
    @PostMapping(value = "edit")
    public ResponseEntity<Object> edit(@RequestBody WidgetDto widgetDto) {
        List<String> errors = new ArrayList<>();
        if (widgetDto == null) {
            errors.add("Не был передан обновляемый виджет.");

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto(errors));
        }
        if (widgetDto.getId() == Integer.MIN_VALUE) errors.add("Не указан id виджета.");

        if ( ! errors.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto(errors));
        }

        Widget newWidget = widgetService.edit(widgetDto);

        return ResponseEntity.status(HttpStatus.OK).body(newWidget);
    }

    /**
     * Удалить виджет по id
     * @param id - идентификатор виджета
     * @return статус удаления(успешно/нет) или сообщение об ошибке
     */
    @DeleteMapping(value = "{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
        List<String> errors = new ArrayList<>();
        if (id == null) {
            errors.add("Не был передан id виджета.");

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto(errors));
        }

        boolean isDeleted = widgetService.delete(id);

        return ResponseEntity.status(HttpStatus.OK).body(isDeleted ? "По заданному id виджет был удалён." : "По заданному id виджет не был найден.");
    }

    /**
     * Получить все виджеты
     * @return список всех виджетов или сообщение об отсутствии виджетов
     */
    @GetMapping(value = "all")
    public ResponseEntity<Object> listAll() {
        List<String> errors = new ArrayList<>();
        List<Widget> widgetList = widgetService.listAll();

        if (widgetList.isEmpty()) {
            errors.add("Список виджетов пуст.");

            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(errors));
        }
        return ResponseEntity.status(HttpStatus.OK).body(widgetList);
    }

    /**
     * Получить список виджетов постранично
     * Усложнение 1
     * @param filter - фильтр с полями, по которым фильтруем, обязательных полей нет
     * @return список виджетов, удовлетворяющих фильтру, или сообщение об отсутствии таких виджетов
     */
    @PostMapping(value = "list")
    public ResponseEntity<Object> list(@RequestBody CommonFilter filter) {
        List<String> errors = new ArrayList<>();
        if (filter == null) {
            errors.add("Не был передан фильтр.");

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto(errors));
        }

        PageFilter pageFilter = new PageFilter();

        if (filter.getOffset() != Integer.MIN_VALUE) pageFilter.setOffset(filter.getOffset());
        if (filter.getLimit() != Integer.MIN_VALUE) pageFilter.setLimit(filter.getLimit());

        List<Widget> widgetList = widgetService.list(pageFilter);

        if (widgetList.isEmpty()) {
            errors.add("Нет виджетов удовлетворяющих заданным параметрам.");

            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(errors));
        }

        return ResponseEntity.status(HttpStatus.OK).body(widgetList);
    }

    /**
     * Получить список виджетов в заданном прямоугольнике(постранично тоже входит)
     * Усложнение 2
     * @param filter - фильтр с полями, по которым фильтруем, обязательных полей нет
     * @return список виджетов, удовлетворяющих фильтру, или сообщение об отсутствии таких виджетов
     */
    @PostMapping(value = "search")
    public ResponseEntity<Object> search(@RequestBody CommonFilter filter) {
        List<String> errors = new ArrayList<>();
        if (filter == null) {
            errors.add("Не был передан фильтр.");

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto(errors));
        }

        PageFilter pageFilter = new PageFilter();

        if (filter.getOffset() != Integer.MIN_VALUE) pageFilter.setOffset(filter.getOffset());
        if (filter.getLimit() != Integer.MIN_VALUE) pageFilter.setLimit(filter.getLimit());

        RectangleFilter rectangleFilter = new RectangleFilter();

        if (filter.getX1() != Integer.MIN_VALUE) rectangleFilter.setX1(filter.getX1());
        if (filter.getY1() != Integer.MIN_VALUE) rectangleFilter.setY1(filter.getY1());
        if (filter.getX2() != Integer.MIN_VALUE) rectangleFilter.setX2(filter.getX2());
        if (filter.getY2() != Integer.MIN_VALUE) rectangleFilter.setY2(filter.getY2());

        List<Widget> widgetList = widgetService.search(rectangleFilter, pageFilter);

        if (widgetList.isEmpty()) {
            errors.add("Нет виджетов удовлетворяющих заданным параметрам.");

            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(errors));
        }

        return ResponseEntity.status(HttpStatus.OK).body(widgetList);
    }

}
