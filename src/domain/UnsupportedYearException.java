package domain;

public class UnsupportedYearException extends IllegalArgumentException {

    public UnsupportedYearException(int year, int minYear) {
        super("Год должен быть не меньше " + minYear + ", получено: " + year);
    }
}
