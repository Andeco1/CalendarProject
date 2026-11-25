package render;

import domain.YearCalendar;

public interface CalendarRenderer {

    /**
     * Возвращает сформированный календарь: заголовок и двенадцать месяцев.
     *
     * <p>Строки разделены {@link System#lineSeparator()}
     */
    String render(YearCalendar calendar);
}
