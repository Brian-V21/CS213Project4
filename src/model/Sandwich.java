package src.model;

import java.util.EnumSet;

import src.enums.Bread;
import src.enums.Protein;
import src.enums.SandwichAddOn;

public class Sandwich extends MenuItem {
    private Bread bread;
    private Protein protein;
    private final EnumSet<SandwichAddOn> addOns;

    public Sandwich(Bread bread, Protein protein, EnumSet<SandwichAddOn> addOns, int qty) {
        super(qty);
        this.bread = bread;
        this.protein = protein;
        this.addOns = (addOns == null) ? EnumSet.noneOf(SandwichAddOn.class) : EnumSet.copyOf(addOns);
    }

    @Override
    public double price() {
        double base;
        switch (protein) {
            case BEEF: base = 12.99;
                break;
            case CHICKEN: base = 10.99;
                break;
            case SALMON: base = 14.99;
                break;
            default: base = 0;
        }
        double extras = 0.0;
        for (SandwichAddOn ao : addOns) {
            extras += (ao == SandwichAddOn.CHEESE) ? 1.00 : 0.30;
        }
        return (base + extras) * quantity;
    }

    @Override
    public String displayLine() {
        String ao = addOns.isEmpty() ? "no add-ons" : addOns.toString();
        return String.format("Sandwich (%s, %s, %s) x%d - %s", bread, protein, ao , quantity, Util.fmt(price()));
    }

    public Bread getBread() {
        return bread;
    }

    public Protein getProtein() {
        return protein;
    }

    public EnumSet<SandwichAddOn> getAddOns() {
        return EnumSet.copyOf(addOns);
    }
}
