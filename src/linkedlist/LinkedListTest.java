package linkedlist;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class LinkedListTest {

    @Test
    public void canInsertSingleElement() {
        LinkedListNode head = new LinkedListNode("A");

        assertEquals("A", head.getValue());
    }

    @Test
    public void canInsertSecondValue() {
        LinkedListNode head = new LinkedListNode("A");

        head.insert("B");

        assertEquals("A", head.getValue());
        assertEquals("B", head.getNext().getValue());
    }

    @Test
    public void insertsShouldBeOnTheEndOfThelist() {
        LinkedListNode head = new LinkedListNode("A");

        head.insert("B");
        head.insert("C");

        assertEquals("A", head.getValue());
        assertEquals("B", head.getNext().getValue());
        assertEquals("C", head.getNext().getNext().getValue());
    }

    @Test
    public void canTraverseList() {
        LinkedListNode head = new LinkedListNode("A");
        head.insert("B");
        head.insert("C");
        head.insert("D");

        StringBuilder out = new StringBuilder();
        LinkedListNode current = head;
        while(current != null && current.getValue() != null) {
            out.append(current.getValue());
            current = current.getNext();
        }

        System.out.println(out.toString());
        assertEquals("ABCD", out.toString());
    }

}