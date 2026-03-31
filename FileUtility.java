import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtility {
    static final String PATIENTS_FILE = "patients.txt";
    static final String DOCTORS_FILE = "doctors.txt";
    static final String APPOINTMENTS_FILE = "appointments.txt";
    static final String MEDICINES_FILE = "medicines.txt";
    static final String BILLS_FILE = "bills.txt";

    // --- Loading Methods ---

    public static void loadPatients(LinkedList<Patient> list) {
        try {
            File f = new File(PATIENTS_FILE);
            if (!f.exists())
                return;
            BufferedReader br = new BufferedReader(new FileReader(f));
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    list.add(new Patient(parts[0], parts[1], Integer.parseInt(parts[2]), parts[3]));
                }
            }
            br.close();
        } catch (Exception e) {
            System.out.println("Error loading patients: " + e.getMessage());
        }
    }

    public static void loadDoctors(LinkedList<Doctor> list) {
        try {
            File f = new File(DOCTORS_FILE);
            if (!f.exists())
                return;
            BufferedReader br = new BufferedReader(new FileReader(f));
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    list.add(new Doctor(parts[0], parts[1], parts[2], Integer.parseInt(parts[3])));
                }
            }
            br.close();
        } catch (Exception e) {
            System.out.println("Error loading doctors: " + e.getMessage());
        }
    }

    public static void loadAppointments(LinkedList<Appointment> list, boolean[] dailySlots) {
        try {
            File f = new File(APPOINTMENTS_FILE);
            if (!f.exists())
                return;
            BufferedReader br = new BufferedReader(new FileReader(f));
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    int timeCode = Integer.parseInt(parts[3]);
                    list.add(new Appointment(parts[0], parts[1], parts[2], timeCode));
                    int hr = timeCode / 100;
                    if (hr >= 0 && hr < 24) {
                        dailySlots[hr] = true;
                    }
                }
            }
            br.close();
        } catch (Exception e) {
            System.out.println("Error loading appointments: " + e.getMessage());
        }
    }

    public static void loadMedicines(LinkedList<Medicine> list) {
        try {
            File f = new File(MEDICINES_FILE);
            if (!f.exists())
                return;
            BufferedReader br = new BufferedReader(new FileReader(f));
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    list.add(new Medicine(parts[0], parts[1], Integer.parseInt(parts[2]), Integer.parseInt(parts[3])));
                }
            }
            br.close();
        } catch (Exception e) {
            System.out.println("Error loading medicines: " + e.getMessage());
        }
    }

    public static void loadBills(LinkedList<Bill> list) {
        try {
            File f = new File(BILLS_FILE);
            if (!f.exists())
                return;
            BufferedReader br = new BufferedReader(new FileReader(f));
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    list.add(new Bill(parts[0], parts[1], Double.parseDouble(parts[2])));
                }
            }
            br.close();
        } catch (Exception e) {
            System.out.println("Error loading bills: " + e.getMessage());
        }
    }

    // --- Appending (Saving New) Methods ---

    public static void savePatient(Patient p) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(PATIENTS_FILE, true));
            bw.write(p.getId() + "," + p.getName() + "," + p.getAge() + "," + p.getAilment() + "\n");
            bw.close();
        } catch (IOException e) {
            System.out.println("Error saving patient: " + e.getMessage());
        }
    }

    public static void saveDoctor(Doctor d) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(DOCTORS_FILE, true));
            bw.write(d.getId() + "," + d.getName() + "," + d.getSpecialty() + "," + d.getExperienceYears() + "\n");
            bw.close();
        } catch (IOException e) {
            System.out.println("Error saving doctor: " + e.getMessage());
        }
    }

    public static void saveAppointment(Appointment a) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(APPOINTMENTS_FILE, true));
            bw.write(a.getId() + "," + a.getPatientName() + "," + a.getDoctorName() + "," + a.getTimeCode() + "\n");
            bw.close();
        } catch (IOException e) {
            System.out.println("Error saving appointment: " + e.getMessage());
        }
    }

    public static void saveMedicine(Medicine m) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(MEDICINES_FILE, true));
            bw.write(m.getId() + "," + m.getName() + "," + m.getStock() + "," + m.getExpiryDate() + "\n");
            bw.close();
        } catch (IOException e) {
            System.out.println("Error saving medicine: " + e.getMessage());
        }
    }

    public static void saveBill(Bill b) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(BILLS_FILE, true));
            bw.write(b.getId() + "," + b.getPatientId() + "," + b.getAmount() + "\n");
            bw.close();
        } catch (IOException e) {
            System.out.println("Error saving bill: " + e.getMessage());
        }
    }

    // --- Rewriting Methods ---

    public static void rewritePatients(LinkedList<Patient> list) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(PATIENTS_FILE, false));
            Node<Patient> temp = list.getHead();
            while (temp != null) {
                Patient p = temp.data;
                bw.write(p.getId() + "," + p.getName() + "," + p.getAge() + "," + p.getAilment() + "\n");
                temp = temp.next;
            }
            bw.close();
        } catch (IOException e) {
            System.out.println("Error rewriting patients: " + e.getMessage());
        }
    }

    public static void rewriteDoctors(LinkedList<Doctor> list) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(DOCTORS_FILE, false));
            Node<Doctor> temp = list.getHead();
            while (temp != null) {
                Doctor d = temp.data;
                bw.write(d.getId() + "," + d.getName() + "," + d.getSpecialty() + "," + d.getExperienceYears() + "\n");
                temp = temp.next;
            }
            bw.close();
        } catch (IOException e) {
            System.out.println("Error rewriting doctors: " + e.getMessage());
        }
    }

    public static void rewriteAppointments(LinkedList<Appointment> list) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(APPOINTMENTS_FILE, false));
            Node<Appointment> temp = list.getHead();
            while (temp != null) {
                Appointment a = temp.data;
                bw.write(a.getId() + "," + a.getPatientName() + "," + a.getDoctorName() + "," + a.getTimeCode() + "\n");
                temp = temp.next;
            }
            bw.close();
        } catch (IOException e) {
            System.out.println("Error rewriting appointments: " + e.getMessage());
        }
    }

    public static void rewriteMedicines(LinkedList<Medicine> list) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(MEDICINES_FILE, false));
            Node<Medicine> temp = list.getHead();
            while (temp != null) {
                Medicine m = temp.data;
                bw.write(m.getId() + "," + m.getName() + "," + m.getStock() + "," + m.getExpiryDate() + "\n");
                temp = temp.next;
            }
            bw.close();
        } catch (IOException e) {
            System.out.println("Error rewriting medicines: " + e.getMessage());
        }
    }

    public static void rewriteBills(LinkedList<Bill> list) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(BILLS_FILE, false));
            Node<Bill> temp = list.getHead();
            while (temp != null) {
                Bill b = temp.data;
                bw.write(b.getId() + "," + b.getPatientId() + "," + b.getAmount() + "\n");
                temp = temp.next;
            }
            bw.close();
        } catch (IOException e) {
            System.out.println("Error rewriting bills: " + e.getMessage());
        }
    }
}
