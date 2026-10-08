
public class ReceiptRow {

    private Produkt product;
    private int quantity;

    public ReceiptRow(Produkt product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Produkt getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotalPrice() {
        return product.getPrice() * quantity;
    }

    public double getVat() {
        double vatRate = product.getVatRate();
        return getTotalPrice() * vatRate / (100 + vatRate);
    }
}
