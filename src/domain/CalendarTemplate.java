package domain;

import java.time.Month;
import java.util.List;
import java.util.Objects;

public record CalendarTemplate(List<MonthGrid> months) {

    public static final int MONTHS_PER_YEAR = Month.values().length;

    public CalendarTemplate {
        Objects.requireNonNull(months, "months");
        if (months.size() != MONTHS_PER_YEAR) {
            throw new IllegalArgumentException(
                    "Календарь должен содержать ровно "
                            + MONTHS_PER_YEAR
                            + " месяцев, получено: "
                            + months.size());
        }
        months = List.copyOf(months);
    }
}
