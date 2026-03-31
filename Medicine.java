public class Medicine {
    private String id;
    private String name;
    private int stock;
    private int expiryDate; // Format YYYYMMDD e.g. 20251231

    public Medicine(String id, String name, int stock, int expiryDate) {
        this.id = id;
        this.name = name;
        this.stock = stock;
        this.expiryDate = expiryDate;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
    public int getExpiryDate() { return expiryDate; }

    @Override
    public String toString() {
        return String.format("Medicine[ID: %s, Name: %s, Stock: %d, Expiry: %d]", id, name, stock, expiryDate);
    }
}
