public class SortUtility {
    // Merge Sort for Doctor's experience (Descending)
    public static Node<Doctor> mergeSortDoctorsByExperience(Node<Doctor> head) {
        if (head == null || head.next == null) {
            return head;
        }

        Node<Doctor> middle = getMiddle(head);
        Node<Doctor> nextOfMiddle = middle.next;
        middle.next = null;

        Node<Doctor> left = mergeSortDoctorsByExperience(head);
        Node<Doctor> right = mergeSortDoctorsByExperience(nextOfMiddle);

        return sortedMergeExperience(left, right);
    }

    private static Node<Doctor> getMiddle(Node<Doctor> head) {
        if (head == null)
            return head;
        Node<Doctor> slow = head, fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    private static Node<Doctor> sortedMergeExperience(Node<Doctor> a, Node<Doctor> b) {
        Node<Doctor> result = null;
        if (a == null)
            return b;
        if (b == null)
            return a;

        // Descending order of experience
        if (a.data.getExperienceYears() >= b.data.getExperienceYears()) {
            result = a;
            result.next = sortedMergeExperience(a.next, b);
        } else {
            result = b;
            result.next = sortedMergeExperience(a, b.next);
        }
        return result;
    }

    // Merge Sort for Arrays (used for Binary Search prep e.g. Doctor by ID)
    public static void mergeSortDoctorsById(Doctor[] arr, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSortDoctorsById(arr, left, mid);
            mergeSortDoctorsById(arr, mid + 1, right);
            mergeById(arr, left, mid, right);
        }
    }

    private static void mergeById(Doctor[] arr, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        Doctor[] L = new Doctor[n1];
        Doctor[] R = new Doctor[n2];

        for (int i = 0; i < n1; ++i)
            L[i] = arr[left + i];
        for (int j = 0; j < n2; ++j)
            R[j] = arr[mid + 1 + j];

        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            // Ascending order of ID string
            if (L[i].getId().compareToIgnoreCase(R[j].getId()) <= 0) {
                arr[k] = L[i];
                i++;
            } else {
                arr[k] = R[j];
                j++;
            }
            k++;
        }
        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
        }
        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
        }
    }

    // Quick Sort for Appointment by Time (Ascending)
    public static void quickSortAppointmentsByTime(Node<Appointment> head) {
        if (head == null || head.next == null)
            return;
        Node<Appointment> tail = getTail(head);
        quickSortAppointmentsRecur(head, tail);
    }

    private static Node<Appointment> getTail(Node<Appointment> node) {
        while (node != null && node.next != null) {
            node = node.next;
        }
        return node;
    }

    private static void quickSortAppointmentsRecur(Node<Appointment> head, Node<Appointment> tail) {
        if (head == null || head == tail || head == tail.next)
            return;

        Node<Appointment> pivot_prev = partitionAppointments(head, tail);
        quickSortAppointmentsRecur(head, pivot_prev);

        if (pivot_prev != null && pivot_prev == head) {
            quickSortAppointmentsRecur(pivot_prev.next, tail);
        } else if (pivot_prev != null && pivot_prev.next != null) {
            quickSortAppointmentsRecur(pivot_prev.next.next, tail);
        }
    }

    private static Node<Appointment> partitionAppointments(Node<Appointment> head, Node<Appointment> tail) {
        if (head == tail || head == null || tail == null)
            return head;

        int pivotData = tail.data.getTimeCode();
        Node<Appointment> pivot_prev = head;
        Node<Appointment> curr = head;

        // Data swapping strategy for Linked List QuickSort
        while (curr != tail) {
            if (curr.data.getTimeCode() < pivotData) {
                // Swap data
                Appointment temp = curr.data;
                curr.data = head.data;
                head.data = temp;

                pivot_prev = head;
                head = head.next;
            }
            curr = curr.next;
        }

        // Swap tail with head
        Appointment temp = head.data;
        head.data = tail.data;
        tail.data = temp;

        return pivot_prev;
    }

    // Merge Sort for Medicine's Expiry Date (Ascending)
    public static Node<Medicine> mergeSortMedicinesByExpiry(Node<Medicine> head) {
        if (head == null || head.next == null) {
            return head;
        }

        Node<Medicine> middle = getMiddleMedicine(head);
        Node<Medicine> nextOfMiddle = middle.next;
        middle.next = null;

        Node<Medicine> left = mergeSortMedicinesByExpiry(head);
        Node<Medicine> right = mergeSortMedicinesByExpiry(nextOfMiddle);

        return sortedMergeExpiry(left, right);
    }

    private static Node<Medicine> getMiddleMedicine(Node<Medicine> head) {
        if (head == null)
            return head;
        Node<Medicine> slow = head, fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    private static Node<Medicine> sortedMergeExpiry(Node<Medicine> a, Node<Medicine> b) {
        Node<Medicine> result = null;
        if (a == null)
            return b;
        if (b == null)
            return a;

        if (a.data.getExpiryDate() <= b.data.getExpiryDate()) {
            result = a;
            result.next = sortedMergeExpiry(a.next, b);
        } else {
            result = b;
            result.next = sortedMergeExpiry(a, b.next);
        }
        return result;
    }

    // Quick Sort for Bills by Amount (Descending)
    public static void quickSortBillsByAmount(Node<Bill> head) {
        if (head == null || head.next == null)
            return;
        Node<Bill> tail = getTailBill(head);
        quickSortBillsRecur(head, tail);
    }

    private static Node<Bill> getTailBill(Node<Bill> node) {
        while (node != null && node.next != null) {
            node = node.next;
        }
        return node;
    }

    private static void quickSortBillsRecur(Node<Bill> head, Node<Bill> tail) {
        if (head == null || head == tail || head == tail.next)
            return;
        Node<Bill> pivot_prev = partitionBills(head, tail);
        quickSortBillsRecur(head, pivot_prev);
        if (pivot_prev != null && pivot_prev == head) {
            quickSortBillsRecur(pivot_prev.next, tail);
        } else if (pivot_prev != null && pivot_prev.next != null) {
            quickSortBillsRecur(pivot_prev.next.next, tail);
        }
    }

    private static Node<Bill> partitionBills(Node<Bill> head, Node<Bill> tail) {
        if (head == tail || head == null || tail == null)
            return head;
        double pivotData = tail.data.getAmount();
        Node<Bill> pivot_prev = head;
        Node<Bill> curr = head;

        while (curr != tail) {
            // Descending order (highest amounts first)
            if (curr.data.getAmount() > pivotData) {
                Bill temp = curr.data;
                curr.data = head.data;
                head.data = temp;

                pivot_prev = head;
                head = head.next;
            }
            curr = curr.next;
        }

        Bill temp = head.data;
        head.data = tail.data;
        tail.data = temp;

        return pivot_prev;
    }

    // Helper for Bill string sorting (For Binary Search prep)
    public static void mergeSortBillsById(Bill[] arr, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSortBillsById(arr, left, mid);
            mergeSortBillsById(arr, mid + 1, right);
            mergeBillsById(arr, left, mid, right);
        }
    }

    private static void mergeBillsById(Bill[] arr, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;
        Bill[] L = new Bill[n1];
        Bill[] R = new Bill[n2];
        for (int i = 0; i < n1; ++i)
            L[i] = arr[left + i];
        for (int j = 0; j < n2; ++j)
            R[j] = arr[mid + 1 + j];
        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            if (L[i].getId().compareToIgnoreCase(R[j].getId()) <= 0) {
                arr[k] = L[i];
                i++;
            } else {
                arr[k] = R[j];
                j++;
            }
            k++;
        }
        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
        }
        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
        }
    }
}
