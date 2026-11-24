package domain;

import java.time.Year;
import java.util.Objects;

/**
 * Календарь на год
 *
 * <p>год не может быть раньше {@link #MIN_YEAR}, иначе выбрасывается {@link
 * UnsupportedYearException}.
 */
public record YearCalendar(Year year, CalendarTemplate template) {

    public static final int MIN_YEAR = 1600;

    public YearCalendar {
        Objects.requireNonNull(year, "year");
        Objects.requireNonNull(template, "template");
        if (year.getValue() < MIN_YEAR) {
            throw new UnsupportedYearException(year.getValue(), MIN_YEAR);
        }
    }
}
