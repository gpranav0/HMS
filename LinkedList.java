public class LinkedList<T> {
    private Node<T> head;

    public LinkedList() {
        head = null;
    }

    public Node<T> getHead() {
        return head;
    }

    public void setHead(Node<T> head) {
        this.head = head;
    }

    public void add(T data) {
        Node<T> newNode = new Node<>(data);
        if (head == null) {
            head = newNode;
        } else {
            Node<T> temp = head;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = newNode;
        }
    }

    public boolean delete(Node<T> targetNode) {
        if (head == null || targetNode == null) {
            return false;
        }

        if (head == targetNode) {
            head = head.next;
            return true;
        }

        Node<T> current = head;
        Node<T> prev = null;
        while (current != null && current != targetNode) {
            prev = current;
            current = current.next;
        }

        if (current == null) {
            return false;
        }

        prev.next = current.next;
        return true;
    }

    public void display() {
        if (head == null) {
            System.out.println("List is empty.");
            return;
        }

        Node<T> temp = head;
        while (temp != null) {
            System.out.println(temp.data.toString());
            temp = temp.next;
        }
    }
}
