public class Bill {
    private String id;
    private String patientId;
    private double amount;

    public Bill(String id, String patientId, double amount) {
        this.id = id;
        this.patientId = patientId;
        this.amount = amount;
    }

    public String getId() { return id; }
    public String getPatientId() { return patientId; }
    public double getAmount() { return amount; }

    @Override
    public String toString() {
        return String.format("Bill[ID: %s, Patient ID: %s, Amount: $%.2f]", id, patientId, amount);
    }
}
