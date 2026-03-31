public class Doctor {
    private String id;
    private String name;
    private String specialty;
    private int experienceYears;

    public Doctor(String id, String name, String specialty, int experienceYears) {
        this.id = id;
        this.name = name;
        this.specialty = specialty;
        this.experienceYears = experienceYears;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getSpecialty() { return specialty; }
    public int getExperienceYears() { return experienceYears; }

    @Override
    public String toString() {
        return String.format("Doctor[ID: %s, Name: %s, Specialty: %s, Experience: %d years]", id, name, specialty, experienceYears);
    }
}
