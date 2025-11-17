package src.model;

import java.util.EnumSet;

import src.enums.CoffeeAddIn;
import src.enums.CupSize;

public class Coffee extends MenuItem {
    private CupSize size;
    private final EnumSet<CoffeeAddIn> addIns;

    public Coffee(CupSize size, EnumSet<CoffeeAddIn> addIns, int qty) {
        super(qty);
        this.size = size;
        this.addIns = (addIns == null) ? EnumSet.noneOf(CoffeeAddIn.class) : EnumSet.copyOf(addIns);
    }

    @Override
    public double price() {
        double baseShort = 2.39;
        double sizeUp = 0.60;
        double unit = baseShort;
        switch (size) {
            case TALL: unit = baseShort + sizeUp;
                break;
            case GRANDE: unit = baseShort + 2 * sizeUp;
                break;
            case VENTI: unit = baseShort + 3 * sizeUp;
                break;
            default:
                break;
        }
        unit += addIns.size() * 0.25;
        return unit * quantity;
    }

    @Override
    public String displayLine() {
        String addInsString = addIns.isEmpty() ? "no add-ins" : addIns.toString();
        return String.format("Coffee (%s, %s) x%d - %s", size, addInsString, quantity, Util.fmt(price()));
    }

    public CupSize getSize() {
        return size;
    }

    public EnumSet<CoffeeAddIn> getAddIns() {
        return EnumSet.copyOf(addIns);
    }

}
