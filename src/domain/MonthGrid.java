package domain;

import java.time.DayOfWeek;
import java.time.Month;
import java.util.List;
import java.util.Objects;

/**
 * Один календарный месяц: недели по {@link #CELLS_PER_WEEK} ячеек. Колонки начинаются с {@link
 * #FIRST_DAY_OF_WEEK}, незанятая ячейка содержит {@link #EMPTY}, занятая — номер дня месяца от 1 до
 * {@link #MAX_DAY_OF_MONTH}
 */
public record MonthGrid(Month month, List<List<Integer>> rows) {

    public static final int CELLS_PER_WEEK = DayOfWeek.values().length;
    public static final int EMPTY = 0;
    public static final DayOfWeek FIRST_DAY_OF_WEEK = DayOfWeek.MONDAY;

    private static final int MAX_DAY_OF_MONTH = 31;

    public MonthGrid {
        Objects.requireNonNull(month, "month");
        Objects.requireNonNull(rows, "rows");

        for (List<Integer> row : rows) {
            Objects.requireNonNull(row, "row");
            if (row.size() != CELLS_PER_WEEK) {
                throw new IllegalArgumentException(
                        "Строка сетки должна содержать ровно "
                                + CELLS_PER_WEEK
                                + " ячеек, получено: "
                                + row.size());
            }
            for (Integer cell : row) {
                Objects.requireNonNull(cell, "cell");
                if (cell < EMPTY || cell > MAX_DAY_OF_MONTH) {
                    throw new IllegalArgumentException(
                            "Номер дня должен быть в диапазоне 1.."
                                    + MAX_DAY_OF_MONTH
                                    + ", получено: "
                                    + cell);
                }
            }
        }

        rows = rows.stream().map(List::copyOf).toList();
    }
}
