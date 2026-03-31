public class Appointment {
    private String id;
    private String patientName;
    private String doctorName;
    private int timeCode; // HHMM format e.g. 1430 for 2:30 PM

    public Appointment(String id, String patientName, String doctorName, int timeCode) {
        this.id = id;
        this.patientName = patientName;
        this.doctorName = doctorName;
        this.timeCode = timeCode;
    }

    public String getId() { return id; }
    public String getPatientName() { return patientName; }
    public String getDoctorName() { return doctorName; }
    public int getTimeCode() { return timeCode; }

    @Override
    public String toString() {
        return String.format("Appointment[ID: %s, Patient: %s, Doctor: %s, Time: %04d]", id, patientName, doctorName, timeCode);
    }
}
