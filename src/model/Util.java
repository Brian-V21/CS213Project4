package src.model;

import java.text.NumberFormat;
import java.util.Locale;

public class Util {
    public static final double NJ_TAX = 0.06625;
    private static final NumberFormat CURRENCY = NumberFormat.getCurrencyInstance(Locale.US);
    private Util() {}
    public static String fmt(double v) {
        return CURRENCY.format(Math.round(v * 100.0/ 100.0));
    }
}
