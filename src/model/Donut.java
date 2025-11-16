package model;

import enums.DonutFlavor;
import enums.DonutType;


public class Donut extends MenuItem {
    private final DonutType type;
    private final DonutFlavor flavor;

    public Donut(DonutType type, DonutFlavor flavor, int qty) {
        super(qty);
        this.type = type;
        this.flavor = flavor;
    }

    @Override
    public double price() {
        double unit;
        switch(type) {
            case YEAST: unit = 1.99;
                break;
            case CAKE: unit = 2.19;
                break;
            case HOLE: unit = 0.39;
                break;
            case SEASONAL: unit = 2.49;
                break;
            default: unit = 0;
        }
        return unit * quantity;
    }

    @Override
    public String displayLine() {
        return String.format("Donut (%s, %s) x%d - %s", type, flavor, quantity, Util.fmt(price()));
    }

    public DonutType getType() {
        return type;
    }
    
    public DonutFlavor getFlavor() {
        return flavor;
    }
}
