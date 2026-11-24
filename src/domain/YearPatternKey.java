package domain;

import java.time.DayOfWeek;
import java.time.Year;
import java.util.Objects;

public record YearPatternKey(DayOfWeek firstDayOfYear, boolean leapYear) {
    public static YearPatternKey from(Year year) {
        Objects.requireNonNull(year, "year");
        return new YearPatternKey(year.atDay(1).getDayOfWeek(), year.isLeap());
    }
}
