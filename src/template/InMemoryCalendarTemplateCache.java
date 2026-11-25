package template;

import domain.CalendarTemplate;
import domain.YearPatternKey;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class InMemoryCalendarTemplateCache implements CalendarTemplateCache {

    private final Map<YearPatternKey, CalendarTemplate> templates = new HashMap<>();

    @Override
    public Optional<CalendarTemplate> get(YearPatternKey key) {
        Objects.requireNonNull(key, "key");
        return Optional.ofNullable(templates.get(key));
    }

    @Override
    public void put(YearPatternKey key, CalendarTemplate template) {
        templates.put(
                Objects.requireNonNull(key, "key"), Objects.requireNonNull(template, "template"));
    }
}
