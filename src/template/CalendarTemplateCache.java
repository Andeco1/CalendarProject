package template;

import domain.CalendarTemplate;
import domain.YearPatternKey;
import java.util.Optional;

public interface CalendarTemplateCache {

    Optional<CalendarTemplate> get(YearPatternKey key);

    void put(YearPatternKey key, CalendarTemplate template);
}
