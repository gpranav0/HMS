import java.util.Scanner;

public class HospitalManagementSystem {

    static Scanner scanner = new Scanner(System.in);

    // Generic List Managers
    static LinkedList<Patient> patientList = new LinkedList<>();
    static LinkedList<Doctor> doctorList = new LinkedList<>();
    static LinkedList<Appointment> appointmentList = new LinkedList<>();
    static LinkedList<Medicine> medicineList = new LinkedList<>();
    static LinkedList<Bill> billList = new LinkedList<>();

    // Generic Stacks / Queues
    static Queue<Patient> normalQueue = new Queue<>();
    static Queue<Patient> emergencyQueue = new Queue<>();
    static Stack<Patient> patientUndoStack = new Stack<>();
    static Stack<Appointment> appointmentBacktrackStack = new Stack<>();

    // Additional States
    static boolean[] dailySlots = new boolean[24];

    // --- Safe Input Helpers ---
    public static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
    }

    public static double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    public static String getStringInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("Input cannot be empty. Please enter a valid value.");
        }
    }

    public static void main(String[] args) {
        System.out.println("Loading data from files...");
        FileUtility.loadPatients(patientList);
        FileUtility.loadDoctors(doctorList);
        FileUtility.loadAppointments(appointmentList, dailySlots);
        FileUtility.loadMedicines(medicineList);
        FileUtility.loadBills(billList);
        System.out.println("Data loaded successfully.");

        boolean running = true;
        while (running) {
            System.out.println("\n===== Hospital Management System =====");
            System.out.println("1. Patient Management"); // Jaswant
            System.out.println("2. Doctor Management"); // Jaswant
            System.out.println("3. Appointment System");
            System.out.println("4. Waiting Queue");
            System.out.println("5. Pharmacy");
            System.out.println("6. Billing");
            System.out.println("7. Exit");

            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    patientMenu();
                    break;
                case 2:
                    doctorMenu();
                    break;
                case 3:
                    appointmentMenu();
                    break;
                case 4:
                    queueMenu();
                    break;
                case 5:
                    pharmacyMenu();
                    break;
                case 6:
                    billingMenu();
                    break;
                case 7:
                    running = false;
                    System.out.println("Exiting Hospital Management System...");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void patientMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Patient Management ---");
            System.out.println(
                    "1. Add Patient\n2. Delete Patient\n3. Update Patient\n4. Search Patient\n5. Display All Patients\n6. Undo Last Update\n7. Back to Main Menu");
            int c = getIntInput("Choice: ");
            switch (c) {
                case 1:
                    String id = getStringInput("ID: ");
                    String name = getStringInput("Name: ");
                    int age = getIntInput("Age: ");
                    String ailment = getStringInput("Ailment: ");
                    Patient newPatient = new Patient(id, name, age, ailment);
                    patientList.add(newPatient);
                    FileUtility.savePatient(newPatient);
                    System.out.println("Patient " + name + " added successfully.");
                    break;
                case 2:
                    String delId = getStringInput("Enter ID to Delete: ");
                    Node<Patient> delNode = SearchUtility.linearSearchPatient(patientList.getHead(), delId);
                    if (delNode != null) {
                        patientList.delete(delNode);
                        FileUtility.rewritePatients(patientList);
                        System.out.println("Patient " + delNode.data.getName() + " deleted successfully.");
                    } else {
                        System.out.println("Patient with ID " + delId + " not found.");
                    }
                    break;
                case 3:
                    String uId = getStringInput("ID to Update: ");
                    String uName = getStringInput("New Name: ");
                    int uAge = getIntInput("New Age: ");
                    String uAilment = getStringInput("New Ailment: ");
                    Node<Patient> updateNode = SearchUtility.linearSearchPatient(patientList.getHead(), uId);
                    if (updateNode != null) {
                        // Save state
                        patientUndoStack.push(updateNode.data.copy());

                        // Modify state
                        updateNode.data.setName(uName);
                        updateNode.data.setAge(uAge);
                        updateNode.data.setAilment(uAilment);
                        FileUtility.rewritePatients(patientList);
                        System.out.println("Patient successfully updated.");
                    } else {
                        System.out.println("Patient with ID " + uId + " not found.");
                    }
                    break;
                case 4:
                    String searchId = getStringInput("Enter ID to Search: ");
                    Node<Patient> searchNode = SearchUtility.linearSearchPatient(patientList.getHead(), searchId);
                    if (searchNode != null) {
                        System.out.println("Patient Found: " + searchNode.data.toString());
                    } else {
                        System.out.println("Patient not found.");
                    }
                    break;
                case 5:
                    System.out.println("--- Patient List ---");
                    patientList.display();
                    System.out.println("--------------------");
                    break;
                case 6:
                    Patient oldState = patientUndoStack.pop();
                    if (oldState == null) {
                        System.out.println("No update to undo.");
                    } else {
                        Node<Patient> targetNode = SearchUtility.linearSearchPatient(patientList.getHead(),
                                oldState.getId());
                        if (targetNode != null) {
                            targetNode.data.setName(oldState.getName());
                            targetNode.data.setAge(oldState.getAge());
                            targetNode.data.setAilment(oldState.getAilment());
                            FileUtility.rewritePatients(patientList);
                            System.out.println("Last update reversed. Restored patient: " + targetNode.data.getName());
                        } else {
                            System.out.println("Could not undo: original patient record was deleted or lost.");
                        }
                    }
                    break;
                case 7:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void doctorMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Doctor Management ---");
            System.out.println(
                    "1. Add Doctor\n2. Delete Doctor\n3. Search Doctor\n4. Sort Doctors by Experience\n5. Display Doctors\n6. Back");
            int c = getIntInput("Choice: ");
            switch (c) {
                case 1:
                    String id = getStringInput("ID: ");
                    String name = getStringInput("Name: ");
                    String spec = getStringInput("Specialty: ");
                    int exp = getIntInput("Years of Experience: ");
                    Doctor newDoctor = new Doctor(id, name, spec, exp);
                    doctorList.add(newDoctor);
                    FileUtility.saveDoctor(newDoctor);
                    System.out.println("Doctor " + name + " added successfully.");
                    break;
                case 2:
                    String dDelId = getStringInput("Enter ID to Delete: ");

                    // Iterate to delete
                    Node<Doctor> currentDoc = doctorList.getHead();
                    while (currentDoc != null) {
                        if (currentDoc.data.getId().equalsIgnoreCase(dDelId)) {
                            doctorList.delete(currentDoc);
                            FileUtility.rewriteDoctors(doctorList);
                            System.out.println("Doctor " + currentDoc.data.getName() + " deleted successfully.");
                            break;
                        }
                        currentDoc = currentDoc.next;
                    }
                    if (currentDoc == null) {
                        System.out.println("Doctor with ID " + dDelId + " not found.");
                    }
                    break;
                case 3:
                    String docSearchId = getStringInput("Enter ID to Search (Uses Binary Search): ");

                    int pCount = 0;
                    Node<Doctor> pTemp = doctorList.getHead();
                    while (pTemp != null) {
                        pCount++;
                        pTemp = pTemp.next;
                    }

                    if (pCount == 0) {
                        System.out.println("Doctor list is empty.");
                        break;
                    }

                    Doctor[] docArr = new Doctor[pCount];
                    pTemp = doctorList.getHead();
                    int d_i = 0;
                    while (pTemp != null) {
                        docArr[d_i++] = pTemp.data;
                        pTemp = pTemp.next;
                    }

                    SortUtility.mergeSortDoctorsById(docArr, 0, pCount - 1);
                    Doctor docResult = SearchUtility.binarySearchDoctorById(docArr, docSearchId);

                    if (docResult != null) {
                        System.out.println("Doctor Found: " + docResult);
                    } else {
                        System.out.println("Doctor with ID " + docSearchId + " not found.");
                    }
                    break;
                case 4:
                    doctorList.setHead(SortUtility.mergeSortDoctorsByExperience(doctorList.getHead()));
                    System.out.println("Doctors sorted by experience successfully.");
                    break;
                case 5:
                    System.out.println("--- Doctor List ---");
                    doctorList.display();
                    System.out.println("-------------------");
                    break;
                case 6:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void appointmentMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Appointment System ---");
            System.out.println(
                    "1. Book Appointment\n2. Cancel Appointment\n3. View Appointments\n4. Sort Appointments by Time\n5. Backtrack Last Booking\n6. Back");
            int c = getIntInput("Choice: ");
            switch (c) {
                case 1:
                    String id = getStringInput("Appointment ID: ");
                    String pName = getStringInput("Patient Name: ");
                    String dName = getStringInput("Doctor Name: ");
                    int t = getIntInput("Time (24-hour format HHMM): ");
                    Appointment apt = new Appointment(id, pName, dName, t);
                    int hr = apt.getTimeCode() / 100;
                    if (hr < 0 || hr >= 24) {
                        System.out.println("Invalid time code. Use 24-hour format HHMM (e.g., 1430).");
                        break;
                    }
                    if (dailySlots[hr]) {
                        System.out.println("Slot at hour " + hr + " is already booked. Please choose another time.");
                        break;
                    }

                    appointmentList.add(apt);
                    dailySlots[hr] = true;
                    appointmentBacktrackStack.push(apt);
                    FileUtility.saveAppointment(apt);
                    System.out.println("Appointment " + apt.getId() + " booked successfully.");
                    break;
                case 2:
                    String aptDelId = getStringInput("Enter Appointment ID to Cancel: ");

                    Node<Appointment> aptCurrent = appointmentList.getHead();
                    while (aptCurrent != null) {
                        if (aptCurrent.data.getId().equalsIgnoreCase(aptDelId)) {
                            int cancelHr = aptCurrent.data.getTimeCode() / 100;
                            dailySlots[cancelHr] = false;
                            appointmentList.delete(aptCurrent);
                            FileUtility.rewriteAppointments(appointmentList);
                            System.out.println("Appointment " + aptCurrent.data.getId() + " cancelled successfully.");
                            break;
                        }
                        aptCurrent = aptCurrent.next;
                    }
                    if (aptCurrent == null) {
                        System.out.println("Appointment with ID " + aptDelId + " not found.");
                    }
                    break;
                case 3:
                    System.out.println("--- Appointments ---");
                    appointmentList.display();
                    System.out.println("--------------------");
                    break;
                case 4:
                    SortUtility.quickSortAppointmentsByTime(appointmentList.getHead());
                    System.out.println("Appointments sorted by time using Quick Sort.");
                    break;
                case 5:
                    Appointment lastBooking = appointmentBacktrackStack.pop();
                    if (lastBooking == null) {
                        System.out.println("No bookings to backtrack.");
                    } else {
                        System.out.println("Backtracking last booking...");
                        // Reuse cancellation logic
                        Node<Appointment> bCurrent = appointmentList.getHead();
                        while (bCurrent != null) {
                            if (bCurrent.data.getId().equalsIgnoreCase(lastBooking.getId())) {
                                int bHr = bCurrent.data.getTimeCode() / 100;
                                dailySlots[bHr] = false;
                                appointmentList.delete(bCurrent);
                                FileUtility.rewriteAppointments(appointmentList);
                                System.out.println("Appointment " + bCurrent.data.getId() + " cancelled successfully.");
                                break;
                            }
                            bCurrent = bCurrent.next;
                        }
                    }
                    break;
                case 6:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void queueMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Waiting Queue ---");
            System.out.println(
                    "1. Add Normal Patient\n2. Add Emergency Patient\n3. Serve Next Patient\n4. Display Queues\n5. Back");
            int c = getIntInput("Choice: ");
            switch (c) {
                case 1:
                    String nid = getStringInput("Patient ID: ");
                    String nname = getStringInput("Patient Name: ");
                    int nage = getIntInput("Age: ");
                    normalQueue.enqueue(new Patient(nid, nname, nage, "Normal"));
                    break;
                case 2:
                    String eid = getStringInput("Patient ID: ");
                    String ename = getStringInput("Patient Name: ");
                    int eage = getIntInput("Age: ");
                    String eailment = getStringInput("Emergency Type: ");
                    emergencyQueue.enqueue(new Patient(eid, ename, eage, eailment));
                    break;
                case 3:
                    if (!emergencyQueue.isEmpty()) {
                        System.out.println("Serving Emergency Patient: " + emergencyQueue.dequeue());
                    } else if (!normalQueue.isEmpty()) {
                        System.out.println("Serving Normal Patient: " + normalQueue.dequeue());
                    } else {
                        System.out.println("No patients in the queue to serve.");
                    }
                    break;
                case 4:
                    System.out.println("--- Emergency Queue ---");
                    emergencyQueue.display("Emergency Patient");
                    System.out.println("--- Normal Queue ---");
                    normalQueue.display("Normal Patient");
                    break;
                case 5:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void pharmacyMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Pharmacy Management ---");
            System.out.println(
                    "1. Add Medicine\n2. Issue Medicine\n3. Search Medicine\n4. Sort Medicines by Expiry\n5. Display Medicines\n6. Back");
            int c = getIntInput("Choice: ");
            switch (c) {
                case 1:
                    String id = getStringInput("ID: ");
                    String name = getStringInput("Name: ");
                    int stock = getIntInput("Stock: ");
                    int exp = getIntInput("Expiry Date (YYYYMMDD): ");
                    Medicine newMedicine = new Medicine(id, name, stock, exp);
                    medicineList.add(newMedicine);
                    FileUtility.saveMedicine(newMedicine);
                    System.out.println("Medicine " + name + " added successfully.");
                    break;
                case 2:
                    String mid = getStringInput("Medicine ID to Issue: ");
                    int num = getIntInput("Quantity: ");
                    Node<Medicine> issueNode = SearchUtility.linearSearchMedicine(medicineList.getHead(), mid);
                    if (issueNode != null) {
                        if (issueNode.data.getStock() >= num) {
                            issueNode.data.setStock(issueNode.data.getStock() - num);
                            FileUtility.rewriteMedicines(medicineList);
                            System.out.println(num + " units of " + issueNode.data.getName() + " issued successfully.");
                            if (issueNode.data.getStock() == 0) {
                                System.out.println("Warning: Stock for " + issueNode.data.getName() + " is now empty!");
                            }
                        } else {
                            System.out.println("Insufficient stock! Available: " + issueNode.data.getStock());
                        }
                    } else {
                        System.out.println("Medicine with ID " + mid + " not found.");
                    }
                    break;
                case 3:
                    String searchMedId = getStringInput("Medicine ID to Search: ");
                    Node<Medicine> rMed = SearchUtility.linearSearchMedicine(medicineList.getHead(), searchMedId);
                    if (rMed != null) {
                        System.out.println("Medicine Found: " + rMed.data);
                    } else {
                        System.out.println("Medicine not found.");
                    }
                    break;
                case 4:
                    medicineList.setHead(SortUtility.mergeSortMedicinesByExpiry(medicineList.getHead()));
                    System.out.println("Medicines sorted by expiry date successfully.");
                    break;
                case 5:
                    System.out.println("--- Medicine Inventory ---");
                    medicineList.display();
                    System.out.println("--------------------------");
                    break;
                case 6:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void billingMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Billing System ---");
            System.out.println("1. Generate Bill\n2. Search Bill\n3. Sort Bills by Amount\n4. Display Bills\n5. Back");
            int c = getIntInput("Choice: ");
            switch (c) {
                case 1:
                    String id = getStringInput("Bill ID: ");
                    String pid = getStringInput("Patient ID: ");
                    double amt = getDoubleInput("Amount: ");
                    Bill newBill = new Bill(id, pid, amt);
                    billList.add(newBill);
                    FileUtility.saveBill(newBill);
                    System.out.println("Bill " + id + " generated successfully.");
                    break;
                case 2:
                    String sBillId = getStringInput("Enter Bill ID to Search (Uses Binary Search): ");

                    int bCount = 0;
                    Node<Bill> bTemp = billList.getHead();
                    while (bTemp != null) {
                        bCount++;
                        bTemp = bTemp.next;
                    }
                    if (bCount == 0) {
                        System.out.println("Bill list is empty.");
                        break;
                    }

                    Bill[] billArr = new Bill[bCount];
                    bTemp = billList.getHead();
                    int b_i = 0;
                    while (bTemp != null) {
                        billArr[b_i++] = bTemp.data;
                        bTemp = bTemp.next;
                    }
                    SortUtility.mergeSortBillsById(billArr, 0, bCount - 1);
                    Bill billResult = SearchUtility.binarySearchBillById(billArr, sBillId);
                    if (billResult != null) {
                        System.out.println("Bill Found: " + billResult);
                    } else {
                        System.out.println("Bill with ID " + sBillId + " not found.");
                    }
                    break;
                case 3:
                    SortUtility.quickSortBillsByAmount(billList.getHead());
                    System.out.println("Bills sorted by amount successfully.");
                    break;
                case 4:
                    System.out.println("--- Billing Records ---");
                    billList.display();
                    System.out.println("-----------------------");
                    break;
                case 5:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
