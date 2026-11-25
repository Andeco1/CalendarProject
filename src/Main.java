import console.ConsoleController;
import java.util.Scanner;
import render.CalendarRenderer;
import render.ConsoleCalendarRenderer;
import service.CalendarService;
import template.CalendarTemplateCache;
import template.CalendarTemplateFactory;
import template.CalendarTemplateService;
import template.InMemoryCalendarTemplateCache;

public class Main {
    public static void main(String[] args) {
        CalendarTemplateFactory factory = new CalendarTemplateFactory();
        CalendarTemplateCache cache = new InMemoryCalendarTemplateCache();
        CalendarTemplateService templates = new CalendarTemplateService(cache, factory);
        CalendarService service = new CalendarService(templates);
        CalendarRenderer renderer = new ConsoleCalendarRenderer();
        ConsoleController controller =
                new ConsoleController(service, renderer, new Scanner(System.in));

        int exitCode = controller.run(args);
        if (exitCode != 0) {
            System.exit(exitCode);
        }
    }
}
