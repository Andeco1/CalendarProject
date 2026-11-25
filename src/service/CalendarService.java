package service;

import domain.UnsupportedYearException;
import domain.YearCalendar;
import domain.YearPatternKey;
import java.time.Year;
import java.util.Objects;
import template.CalendarTemplateService;

public class CalendarService {

    private final CalendarTemplateService templates;

    public CalendarService(CalendarTemplateService templates) {
        this.templates = Objects.requireNonNull(templates, "templates");
    }

    /**
     * @throws UnsupportedYearException если год раньше {@link YearCalendar#MIN_YEAR}
     */
    public YearCalendar calendarFor(Year year) {
        Objects.requireNonNull(year, "year");
        return new YearCalendar(year, templates.templateFor(YearPatternKey.from(year)));
    }
}
