package mini.service.impl;

import mini.dto.WidgetDto;
import mini.object.PageFilter;
import mini.object.RectangleFilter;
import mini.object.Widget;
import mini.service.WidgetService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

/**
 * Сервис для работы с хранилищем виджетов
 */
@Service
public class WidgetServiceImpl implements WidgetService {

    /**
     * Хранилище виджетов
     */
    private List<Widget> store = new ArrayList<>();

    /**
     * Переменная для инкрементального индекса, используется для идентификаторов виджетов
     */
    private Long lastIndex = 0L;

    /**
     * Блокировщик
     */
    private ReadWriteLock lock = new ReentrantReadWriteLock();
    private Calendar calendar = new GregorianCalendar();

    /**
     * Обновление z-index'а, как в изменяемом элементе, так и в хранилище виджетов
     * @param newZ - новое значение индекса, если Integer.MIN_VALUE, значит не передавали
     * @param updateWidget - изменяемый(или новый) виджет
     */
    private void updateZ(int newZ, Widget updateWidget){
        if (newZ != Integer.MIN_VALUE) {
            List<Widget> sameZ = store.stream()
                    .filter(widget -> widget.getZ() == newZ)
                    .collect(Collectors.toList());
            if ( ! sameZ.isEmpty()) {
                store.forEach(widget -> {
                    if (widget.getZ() >= newZ) widget.setZ(widget.getZ() + 1);
                });
            }
            updateWidget.setZ(newZ);
        } else {
            updateWidget.setZ(store.stream()
                    .mapToInt(Widget::getZ)
                    .max()
                    .orElse(0) + 1);
        }
    }

    /**
     * Пересекается ли виджет и фильтрующий прямоугольник
     * @param widget - проверяемый виджет
     * @param rectangleFilter - фильтр с параметрами прямоугольника(х1,у1 - верхняя левая точка, x2,y2 - нижняя правая)
     * @return пересекаются ли виджет с прямоугольником
     */
    private boolean intersects(Widget widget, RectangleFilter rectangleFilter){

        //По y не пересекаются
        if (widget.getY() >= rectangleFilter.getY2() || widget.getY() + widget.getHeight() <= rectangleFilter.getY1()) {
            return false;
        }

        //По х не пересекаются
        if (widget.getX() + widget.getWidth() <= rectangleFilter.getX1() || widget.getX() >= rectangleFilter.getX2()) {
            return false;
        }
        return true;
    }

    /**
     * Получить виджет по идентификатору
     * @param widgetId - идентификатор
     * @return найденный виджет или null если по данному id нет виджета
     */
    @Override
    public Widget get(Long widgetId) {
        Widget finded = null;
        lock.readLock().lock();
        try {
             finded = store
                     .stream()
                     .filter(widget -> widget.getId().equals(widgetId))
                     .findAny()
                     .orElse(null);
        } finally {
            lock.readLock().unlock();
        }
        return finded;
    }

    /**
     * Добавление виджета
     * @param newWidget - объект с данными по новому виджету(могут быть не все поля)
     * @return созданный виджет
     */
    @Override
    public Widget add(WidgetDto newWidget) {
        Widget addWidget = new Widget();
        lock.writeLock().lock();
        try {
            newWidget.toObject(addWidget);
            addWidget.setId(lastIndex);
            lastIndex++;
            updateZ(newWidget.getZ(), addWidget);
            addWidget.setUpdateTime(calendar.getTime());
            store.add(addWidget);
        } finally {
            lock.writeLock().unlock();
        }
        return addWidget;
    }

    /**
     * Обновление виджета
     * @param newWidget - объект с данными по обновлённому виджету(могут быть не все поля)
     * @return обновлённый виджет или null если по пришедшему в объекте newWidget id не нашли изменяемого виджета
     */
    @Override
    public Widget edit(WidgetDto newWidget) {
        Widget finded = null;
        lock.writeLock().lock();
        try {
            finded = store.stream()
                    .filter(widget -> widget.getId().equals( newWidget.getId()))
                    .findAny()
                    .orElse(null);
            if (finded != null) {
                finded.setUpdateTime(calendar.getTime());
                newWidget.setId(Long.MIN_VALUE);
                if (newWidget.getZ() != Integer.MIN_VALUE) {
                    updateZ(newWidget.getZ(), finded);
                }
                newWidget.toObject(finded);
            }
        } finally {
            lock.writeLock().unlock();
        }
        return finded;
    }

    /**
     * Удаление виджета
     * @param widgetId - идентификатор
     * @return true - если успешно удалили виджет, false - если виджета с таким id нет
     */
    @Override
    public boolean delete(Long widgetId) {
        Widget finded = null;
        lock.writeLock().lock();
        try {
            finded = store.stream()
                    .filter(widget -> widget.getId().equals(widgetId))
                    .findAny()
                    .orElse(null);
            if (finded != null) {
                store.remove(finded);
            }
        } finally {
            lock.writeLock().unlock();
        }
        return finded != null;
    }

    /**
     * Выборка всех виджетов
     * @return список всех виджетов
     */
    @Override
    public List<Widget> listAll() {
        List<Widget> finded = null;
        lock.readLock().lock();
        try {
            finded = store;
        } finally {
            lock.readLock().unlock();
        }
        return finded;
    }

    /**
     * Выборка виджетов с пагинацией
     * Усложнение 1
     * @param pageFilter - фильтр с параметрами для пагинации
     * @return список отфильтрованных виджетов
     */
    @Override
    public List<Widget> list(PageFilter pageFilter) {
        List<Widget> finded = null;
        lock.readLock().lock();
        try {
            finded = store
                    .stream()
                    .sorted(Comparator.comparingLong(Widget::getId))
                    .skip(pageFilter.getOffset())
                    .limit(pageFilter.getLimit())
                    .collect(Collectors.toList());
        } finally {
            lock.readLock().unlock();
        }
        return finded;
    }

    /**
     * Выборка виджетов по прямоугольнику
     * Усложнение 2.
     * @param rectangleFilter - фильтр с параметрами прямоугольника
     * @return список отфильтрованных виджетов
     */
    @Override
    public List<Widget> search(RectangleFilter rectangleFilter) {
        PageFilter pageFilter = new PageFilter();
        return search(rectangleFilter, pageFilter);
    }

    /**
     * Выборка виджетов по прямоугольнику
     * Так как это усложнение 2, то решил что пагинация нужна будет и здесь.
     * @param rectangleFilter - фильтр с параметрами прямоугольника
     * @param pageFilter - фильтр с параметрами для пагинации
     * @return список отфильтрованных виджетов
     */
    @Override
    public List<Widget> search(RectangleFilter rectangleFilter, PageFilter pageFilter) {
        List<Widget> finded = null;
        lock.readLock().lock();
        try {
            finded = store
                    .stream()
                    .filter(widget -> intersects(widget, rectangleFilter))
                    .sorted(Comparator.comparingLong(Widget::getId))
                    .skip(pageFilter.getOffset())
                    .limit(pageFilter.getLimit())
                    .collect(Collectors.toList());
        } finally {
            lock.readLock().unlock();
        }
        return finded;
    }
}
