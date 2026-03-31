public class SearchUtility {
    // Patient Search (Linear)
    public static Node<Patient> linearSearchPatient(Node<Patient> head, String targetId) {
        Node<Patient> current = head;
        while (current != null) {
            // Case-insensitive ID match
            if (current.data.getId().equalsIgnoreCase(targetId)) {
                return current;
            }
            current = current.next;
        }
        return null; // Not found
    }

    // Doctor Search (Binary - expects sorted array by ID)
    public static Doctor binarySearchDoctorById(Doctor[] arr, String targetId) {
        int left = 0, right = arr.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            int comparison = arr[mid].getId().compareToIgnoreCase(targetId);

            if (comparison == 0) {
                return arr[mid];
            } else if (comparison < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return null; // Not found
    }

    // Medicine Search (Linear)
    public static Node<Medicine> linearSearchMedicine(Node<Medicine> head, String targetId) {
        Node<Medicine> current = head;
        while (current != null) {
            if (current.data.getId().equalsIgnoreCase(targetId)) {
                return current;
            }
            current = current.next;
        }
        return null; // Not found
    }

    // Bill Search (Binary - expects sorted array by ID)
    public static Bill binarySearchBillById(Bill[] arr, String targetId) {
        int left = 0, right = arr.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            int comparison = arr[mid].getId().compareToIgnoreCase(targetId);

            if (comparison == 0) {
                return arr[mid];
            } else if (comparison < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return null; // Not found
    }
}
