import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main extends JFrame {

    private JTextField productField;
    private JTextArea receiptArea;
    private JLabel totalLabel;

    private Produkt[] products;
    private Receipt receipt;
    private int receiptNumber = 1;

    public Main() {
        products = createProducts();
        receipt = new Receipt(receiptNumber);

        setTitle("Mitt Kassasystem");
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(new Color(245, 247, 250));

        JLabel titleLabel = new JLabel("Mitt Kassasystem", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBorder(new EmptyBorder(0, 0, 10, 0));

        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(createCenterPanel(), BorderLayout.CENTER);
        mainPanel.add(createBottomPanel(), BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    private Produkt[] createProducts() {
        return new Produkt[]{
                new Produkt("Kaffe", 30, 12),
                new Produkt("Smörgås", 55, 12),
                new Produkt("Sallad", 95, 12),
                new Produkt("T-shirt", 199, 25)
        };
    }

    private JPanel createCenterPanel() {
        JPanel centerPanel = new JPanel(new BorderLayout(15, 15));
        centerPanel.setBackground(new Color(245, 247, 250));

        centerPanel.add(createLeftPanel(), BorderLayout.NORTH);
        centerPanel.add(createReceiptPanel(), BorderLayout.CENTER);

        return centerPanel;
    }

    private JPanel createLeftPanel() {
        JPanel leftPanel = new JPanel(new BorderLayout(10, 10));
        leftPanel.setBackground(Color.WHITE);
        leftPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                new EmptyBorder(15, 15, 15, 15)
        ));

        JLabel chooseLabel = new JLabel("Välj produkt");
        chooseLabel.setFont(new Font("Arial", Font.BOLD, 16));
        leftPanel.add(chooseLabel, BorderLayout.NORTH);

        JPanel productButtonPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        productButtonPanel.setBackground(Color.WHITE);

        for (Produkt product : products) {
            JButton button = new JButton(product.getName());
            styleButton(button, new Color(70, 130, 180));
            button.addActionListener(e -> productField.setText(product.getName()));
            productButtonPanel.add(button);
        }

        leftPanel.add(productButtonPanel, BorderLayout.CENTER);
        leftPanel.add(createInputPanel(), BorderLayout.SOUTH);

        return leftPanel;
    }

    private JPanel createInputPanel() {
        JPanel inputPanel = new JPanel(new GridLayout(2, 1, 8, 8));
        inputPanel.setBackground(Color.WHITE);
        inputPanel.setBorder(new EmptyBorder(15, 0, 0, 0));

        JPanel fieldPanel = new JPanel(new BorderLayout(8, 8));
        fieldPanel.setBackground(Color.WHITE);

        JLabel productLabel = new JLabel("Produkt:");
        productLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        productField = new JTextField();
        productField.setFont(new Font("Arial", Font.PLAIN, 14));

        fieldPanel.add(productLabel, BorderLayout.WEST);
        fieldPanel.add(productField, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        buttonPanel.setBackground(Color.WHITE);

        JButton addButton = new JButton("Add");
        JButton payButton = new JButton("Pay");

        styleButton(addButton, new Color(46, 204, 113));
        styleButton(payButton, new Color(231, 76, 60));

        addButton.addActionListener(e -> addProduct());
        payButton.addActionListener(e -> pay());

        buttonPanel.add(addButton);
        buttonPanel.add(payButton);

        inputPanel.add(fieldPanel);
        inputPanel.add(buttonPanel);

        return inputPanel;
    }

    private JPanel createReceiptPanel() {
        JPanel receiptPanel = new JPanel(new BorderLayout(10, 10));
        receiptPanel.setBackground(Color.WHITE);
        receiptPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                new EmptyBorder(15, 15, 15, 15)
        ));

        JLabel receiptLabel = new JLabel("Kvitto");
        receiptLabel.setFont(new Font("Arial", Font.BOLD, 16));

        receiptArea = new JTextArea();
        receiptArea.setEditable(false);
        receiptArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        receiptArea.setBackground(new Color(250, 250, 250));
        receiptArea.setMargin(new Insets(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(receiptArea);

        receiptPanel.add(receiptLabel, BorderLayout.NORTH);
        receiptPanel.add(scrollPane, BorderLayout.CENTER);

        return receiptPanel;
    }

    private JPanel createBottomPanel() {
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(new Color(245, 247, 250));

        totalLabel = new JLabel("Totalt: 0.00 kr");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 18));
        totalLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        bottomPanel.add(totalLabel, BorderLayout.EAST);

        return bottomPanel;
    }

    private void styleButton(JButton button, Color backgroundColor) {
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
    }

    private void addProduct() {
        String name = productField.getText().trim();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Välj en produkt först!");
            return;
        }

        for (Produkt product : products) {
            if (product.getName().equalsIgnoreCase(name)) {
                receipt.addProduct(product);
                updateReceipt();
                productField.setText("");
                return;
            }
        }

        JOptionPane.showMessageDialog(this, "Produkten finns inte!");
    }

    private void updateReceipt() {
        StringBuilder text = new StringBuilder();

        text.append("KVITTO NR: ")
                .append(receipt.getReceiptNumber())
                .append("\n");
        text.append("--------------------------------\n");

        for (ReceiptRow row : receipt.getRows()) {
            text.append(row.getProduct().getName())
                    .append(" x ")
                    .append(row.getQuantity())
                    .append(" = ")
                    .append(String.format("%.2f", row.getTotalPrice()))
                    .append(" kr\n");
        }

        text.append("--------------------------------\n");
        text.append("TOTALT: ")
                .append(String.format("%.2f", receipt.getTotal()))
                .append(" kr");

        receiptArea.setText(text.toString());
        totalLabel.setText(String.format("Totalt: %.2f kr", receipt.getTotal()));
    }

    private void pay() {
        if (receipt.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Kvittot är tomt! Lägg till en produkt.");
            return;
        }

        String date = LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        );

        StringBuilder text = new StringBuilder();
        text.append(receiptArea.getText());
        text.append("\n\nDatum: ").append(date);

        text.append("\nNetto: ")
                .append(String.format("%.2f kr", receipt.getNetTotal()));

        text.append("\nMoms: ")
                .append(String.format("%.2f kr", receipt.getVat()));

        text.append("\nBrutto: ")
                .append(String.format("%.2f kr", receipt.getTotal()));

        text.append("\n\nMOMSREDOVISNING:");

        for (int rate : new int[]{12, 25}) {
            double gross = 0;
            double vat = 0;

            for (ReceiptRow row : receipt.getRows()) {
                if (row.getProduct().getVatRate() == rate) {
                    gross += row.getTotalPrice();
                    vat += row.getVat();
                }
            }

            if (gross > 0) {
                text.append(String.format(
                        "\nMoms %d%% | Netto: %.2f | Moms: %.2f | Brutto: %.2f",
                        rate, gross - vat, vat, gross
                ));
            }
        }

        text.append("\n\nTACK FÖR DITT KÖP!");

        JOptionPane.showMessageDialog(this, text.toString());

        receiptNumber++;
        receipt = new Receipt(receiptNumber);
        receiptArea.setText("");
        totalLabel.setText("Totalt: 0.00 kr");
        productField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}