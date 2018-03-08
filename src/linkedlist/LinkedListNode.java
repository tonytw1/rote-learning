package linkedlist;

public class LinkedListNode<T> {

    private T value;
    private LinkedListNode next;

    public LinkedListNode(T value) {
        this.value = value;
        this.next = null;
    }

    public T getValue() {
        return value;
    }

    public LinkedListNode getNext() {
        return next;
    }

    public void setNext(LinkedListNode next) {
        this.next = next;
    }

    public void insert(T value) {
        if (this.value == null) {
            this.value = value;

        } else {
            LinkedListNode insertHere = this;
            while (insertHere.getNext() != null) {
                insertHere = insertHere.getNext();
            }

            LinkedListNode newNode = new LinkedListNode(value);
            insertHere.setNext(newNode);
        }
    }

}
