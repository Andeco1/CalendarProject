package console;

import domain.UnsupportedYearException;
import java.io.PrintStream;
import java.time.Year;
import java.time.format.DateTimeParseException;
import java.util.Objects;
import java.util.Scanner;
import render.CalendarRenderer;
import service.CalendarService;

public class ConsoleController {

    private final CalendarService calendarService;
    private final CalendarRenderer renderer;
    private final Scanner scanner;
    private final PrintStream output;
    private final PrintStream error;

    public ConsoleController(
            CalendarService calendarService, CalendarRenderer renderer, Scanner scanner) {
        this(calendarService, renderer, scanner, System.out, System.err);
    }

    public ConsoleController(
            CalendarService calendarService,
            CalendarRenderer renderer,
            Scanner scanner,
            PrintStream output,
            PrintStream error) {
        this.calendarService = Objects.requireNonNull(calendarService, "calendarService");
        this.renderer = Objects.requireNonNull(renderer, "renderer");
        this.scanner = Objects.requireNonNull(scanner, "scanner");
        this.output = Objects.requireNonNull(output, "output");
        this.error = Objects.requireNonNull(error, "error");
    }

    /**
     * Печатает календарь по году
     *
     * <p>Ошибки ввода печатаются в {@code stderr}
     *
     * @return {@code 0} при успехе, {@code 2} при некорректном вводе
     */
    public int run(String[] args) {
        Objects.requireNonNull(args, "args");
        if (args.length > 1) {
            return fail("Ожидается один аргумент - номер года");
        }
        if (args.length == 1) {
            return process(args[0].trim());
        }

        output.print("Введите номер года: ");
        output.flush();
        if (!scanner.hasNextLine()) {
            return fail("Ввод не получен");
        }
        return process(scanner.nextLine().trim());
    }

    private int process(String yearStr) {
        Year year;
        try {
            year = Year.parse(yearStr);
        } catch (DateTimeParseException e) {
            return fail("Ошибка ввода, год должен быть целым числом");
        }

        try {
            output.println(renderer.render(calendarService.calendarFor(year)));
            return 0;
        } catch (UnsupportedYearException e) {
            return fail(e.getMessage());
        }
    }

    private int fail(String message) {
        error.println(message);
        return 2;
    }
}
