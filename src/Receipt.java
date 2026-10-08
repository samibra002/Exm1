
import java.util.ArrayList;

public class Receipt {

    private ArrayList<ReceiptRow> rows = new ArrayList<>();
    private int receiptNumber;

    public Receipt(int receiptNumber) {
        this.receiptNumber = receiptNumber;
    }

    public void addProduct(Produkt product) {
        rows.add(new ReceiptRow(product, 1));
    }

    public double getTotal() {
        double total = 0;

        for (ReceiptRow row : rows) {
            total += row.getTotalPrice();
        }

        return total;
    }

    public double getVat() {
        double vat = 0;

        for (ReceiptRow row : rows) {
            vat += row.getVat();
        }

        return vat;
    }

    public double getNetTotal() {
        return getTotal() - getVat();
    }

    public boolean isEmpty() {
        return rows.isEmpty();
    }

    public int getReceiptNumber() {
        return receiptNumber;
    }

    public ArrayList<ReceiptRow> getRows() {
        return rows;
    }
}
