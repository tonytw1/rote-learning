package binarytree;

import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class BinaryTreeTest {

    @Test
    public void canInsertFirstNode() {
        BinaryTreeNode head = new BinaryTreeNode(10);

        assertEquals(new Integer(10), head.getValue());
    }

    @Test
    public void singleValueHasNoChildred() {
        BinaryTreeNode singleValue = new BinaryTreeNode(10);

        assertNull(singleValue.getLeft());
        assertNull(singleValue.getRight ());
    }

    @Test
    public void insertsShouldGoLeftOrRightBasedOnValue() {
        BinaryTreeNode head = new BinaryTreeNode(10);

        head.insert(9);
        head.insert(11);

        assertNotNull(head.getLeft());
        assertEquals(new Integer(9), head.getLeft().getValue());

        assertNotNull(head.getRight());
        assertEquals(new Integer(11), head.getRight().getValue());
    }

    @Test
    public void insertsIntoPopulatedNodeShouldAddNewLeaf() {
        BinaryTreeNode head = new BinaryTreeNode(10);

        head.insert(5);
        head.insert(4);
        head.insert(6);

        head.insert(15);
        head.insert(14);
        head.insert(16);

        assertEquals(new Integer(4), head.getLeft().getLeft().getValue());
        assertEquals(new Integer(6), head.getLeft().getRight().getValue());

        assertEquals(new Integer(14), head.getRight().getLeft().getValue());
        assertEquals(new Integer(16), head.getRight().getRight().getValue());
    }

    @Test
    public void canOutputTree() {
        BinaryTreeNode head = new BinaryTreeNode(10);

        head.insert(5);
        head.insert(4);
        head.insert(6);

        head.insert(15);
        head.insert(14);
        head.insert(16);

        StringBuffer out = new StringBuffer();
        head.output(out);
        System.out.println(out.toString());
    }

    @Test
    public void insertOrderDoesNotEffectOutput() {
        BinaryTreeNode one = new BinaryTreeNode(10);
        one.insert(5);
        one.insert(4);
        one.insert(6);
        one.insert(15);
        one.insert(14);
        one.insert(16);

        BinaryTreeNode two = new BinaryTreeNode(5);
        two.insert(10);
        two.insert(4);
        two.insert(6);
        two.insert(15);
        two.insert(14);
        two.insert(16);

        StringBuffer outOne = new StringBuffer();
        one.output(outOne);
        System.out.println(outOne.toString());

        StringBuffer outTwo = new StringBuffer();
        two.output(outTwo);
        System.out.println(outTwo.toString());

        assertTrue(outOne.toString().equals(outTwo.toString()));
    }

    @Test
    public void canDetectIdenticalTrees() {
        BinaryTreeNode one = new BinaryTreeNode(10);
        one.insert(5);
        one.insert(4);
        one.insert(6);
        one.insert(15);
        one.insert(14);
        one.insert(16);

        BinaryTreeNode two = new BinaryTreeNode(10);
        two.insert(5);
        two.insert(4);
        two.insert(6);
        two.insert(15);
        two.insert(14);
        two.insert(16);

        assertTrue(one.same(two));
    }

    @Test
    public void sameCheckNeedsToConsiderChildrenAsWell() {
        BinaryTreeNode one = new BinaryTreeNode(10);

        BinaryTreeNode two = new BinaryTreeNode(10);
        two.insert(5);

        assertFalse(one.same(two));
    }

    @Test
    public void sameDoesNotWorkForDifferentlyStructuredTreeWithTheSameContent() {
        BinaryTreeNode one = new BinaryTreeNode(10);
        one.insert(5);
        one.insert(4);
        one.insert(6);
        one.insert(15);
        one.insert(14);
        one.insert(16);

        BinaryTreeNode two = new BinaryTreeNode(5);
        two.insert(10);
        two.insert(4);
        two.insert(6);
        two.insert(15);
        two.insert(14);
        two.insert(16);

        assertFalse(one.same(two));
    }

}
