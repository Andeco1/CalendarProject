package template;

import static domain.CalendarTemplate.MONTHS_PER_YEAR;
import static domain.MonthGrid.*;
import static java.lang.Math.floorMod;

import domain.CalendarTemplate;
import domain.MonthGrid;
import domain.YearPatternKey;
import java.time.DayOfWeek;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CalendarTemplateFactory {

    private int firstDayOffset(DayOfWeek dayOfWeek) {
        return (dayOfWeek.getValue() - FIRST_DAY_OF_WEEK.getValue() + CELLS_PER_WEEK)
                % CELLS_PER_WEEK;
    }

    public CalendarTemplate create(YearPatternKey key) {
        Objects.requireNonNull(key, "key");

        List<MonthGrid> months = new ArrayList<>(MONTHS_PER_YEAR);
        int offset = firstDayOffset(key.firstDayOfYear());

        for (Month month : Month.values()) {
            months.add(buildMonthGrid(month, offset, key.leapYear()));
            offset = floorMod(offset + month.length(key.leapYear()), CELLS_PER_WEEK);
        }

        return new CalendarTemplate(months);
    }

    private MonthGrid buildMonthGrid(Month month, int offset, boolean leapYear) {
        int daysInMonth = month.length(leapYear);
        List<List<Integer>> monthGrid = new ArrayList<>();
        List<Integer> week = new ArrayList<>(CELLS_PER_WEEK);
        for (int i = 0; i < offset; i++) {
            week.add(EMPTY);
        }
        for (int day = 1; day <= daysInMonth; day++) {
            week.add(day);
            if (week.size() == CELLS_PER_WEEK) {
                monthGrid.add(week);
                week = new ArrayList<>(CELLS_PER_WEEK);
            }
        }
        if (!week.isEmpty()) {
            while (week.size() < CELLS_PER_WEEK) {
                week.add(EMPTY);
            }
            monthGrid.add(week);
        }
        return new MonthGrid(month, monthGrid);
    }
}
