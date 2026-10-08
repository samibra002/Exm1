
public class Produkt {

    private String name;
    private double price;
    private double vatRate;

    public Produkt(String name, double price, double vatRate) {
        this.name = name;
        this.price = price;
        this.vatRate = vatRate;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public double getVatRate() {
        return vatRate;
    }
}
