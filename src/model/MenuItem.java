package src.model;

public abstract class MenuItem {
    protected int quantity;

    public MenuItem(int quantity) {
        if (quantity <= 0) throw new IllegalArgumentException("quantity must be > 0");
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity (int q) {
        if (q <= 0) throw new IllegalArgumentException("quantity must be > 0");
        this.quantity = q;
    }

    public abstract double price();

    public abstract String displayLine();
}
