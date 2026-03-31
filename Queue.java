public class Queue<T> {
    private Node<T> front;
    private Node<T> rear;

    public Queue() {
        this.front = null;
        this.rear = null;
    }

    public void enqueue(T data) {
        Node<T> newNode = new Node<>(data);
        if (this.rear == null) {
            this.front = this.rear = newNode;
            return;
        }
        this.rear.next = newNode;
        this.rear = newNode;
    }

    public T dequeue() {
        if (this.front == null) {
            System.out.println("Queue Underflow: The queue is empty.");
            return null;
        }
        Node<T> temp = this.front;
        this.front = this.front.next;
        
        if (this.front == null) {
            this.rear = null;
        }
        
        return temp.data;
    }

    public boolean isEmpty() {
        return this.front == null;
    }

    public void display(String typeName) {
        if (this.front == null) {
            System.out.println("Queue is empty.");
            return;
        }
        Node<T> current = this.front;
        int count = 1;
        while (current != null) {
            // using toString() of T
            System.out.println(count + ". " + current.data.toString());
            current = current.next;
            count++;
        }
    }
}
