package template;

import domain.CalendarTemplate;
import domain.YearPatternKey;
import java.util.Objects;
import java.util.Optional;

public class CalendarTemplateService {

    private final CalendarTemplateCache cache;
    private final CalendarTemplateFactory factory;

    public CalendarTemplateService(CalendarTemplateCache cache, CalendarTemplateFactory factory) {
        this.cache = Objects.requireNonNull(cache, "cache");
        this.factory = Objects.requireNonNull(factory, "factory");
    }

    public CalendarTemplate templateFor(YearPatternKey key) {
        Objects.requireNonNull(key, "key");

        Optional<CalendarTemplate> cached = cache.get(key);
        if (cached.isPresent()) {
            return cached.get();
        }

        CalendarTemplate created = factory.create(key);
        cache.put(key, created);
        return created;
    }
}
