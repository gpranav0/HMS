public class Patient {
    private String id;
    private String name;
    private int age;
    private String ailment;

    public Patient(String id, String name, int age, String ailment) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.ailment = ailment;
    }

    public Patient copy() {
        return new Patient(this.id, this.name, this.age, this.ailment);
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    public String getAilment() { return ailment; }
    public void setAilment(String ailment) { this.ailment = ailment; }

    @Override
    public String toString() {
        return String.format("Patient[ID: %s, Name: %s, Age: %d, Ailment: %s]", id, name, age, ailment);
    }
}
