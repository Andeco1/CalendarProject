package render;

import static domain.MonthGrid.*;
import static java.lang.String.format;
import static java.lang.System.lineSeparator;
import static java.time.format.TextStyle.FULL_STANDALONE;
import static java.time.format.TextStyle.SHORT;
import static java.util.stream.Collectors.joining;

import domain.CalendarTemplate;
import domain.MonthGrid;
import domain.YearCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.IntStream;

public class ConsoleCalendarRenderer implements CalendarRenderer {

    private static final int CELL_WIDTH = 2;

    private final Locale locale;

    public ConsoleCalendarRenderer() {
        this(Locale.forLanguageTag("ru"));
    }

    public ConsoleCalendarRenderer(Locale locale) {
        this.locale = Objects.requireNonNull(locale, "locale");
    }

    @Override
    public String render(YearCalendar calendar) {
        Objects.requireNonNull(calendar, "calendar");

        CalendarTemplate template = calendar.template();
        StringBuilder sb = new StringBuilder();

        sb.append("Календарь на ").append(calendar.year().getValue()).append(" год");
        if (calendar.year().isLeap()) {
            sb.append(" (високосный)");
        }
        sb.append(lineSeparator());

        for (MonthGrid month : template.months()) {
            appendMonth(sb, calendar.year().getValue(), month);
        }
        return sb.toString();
    }

    private void appendMonth(StringBuilder sb, int year, MonthGrid month) {
        sb.append(lineSeparator())
                .append(capitalize(month.month().getDisplayName(FULL_STANDALONE, locale)))
                .append(' ')
                .append(year)
                .append(lineSeparator())
                .append(weekdayHeader())
                .append(lineSeparator());

        for (List<Integer> row : month.rows()) {
            appendRow(sb, row);
        }
    }

    private void appendRow(StringBuilder sb, List<Integer> row) {
        sb.append(row.stream().map(this::cellText).collect(joining(" "))).append(lineSeparator());
    }

    private String cellText(int cell) {
        return cell == EMPTY
                ? " ".repeat(CELL_WIDTH)
                : format(Locale.ROOT, "%" + CELL_WIDTH + "d", cell);
    }

    private String weekdayHeader() {
        return IntStream.range(0, CELLS_PER_WEEK)
                .mapToObj(FIRST_DAY_OF_WEEK::plus)
                .map(day -> capitalize(day.getDisplayName(SHORT, locale)))
                .collect(joining(" "));
    }

    private String capitalize(String s) {
        if (s == null || s.isEmpty()) {
            return s;
        }
        return s.substring(0, 1).toUpperCase(locale) + s.substring(1);
    }
}
