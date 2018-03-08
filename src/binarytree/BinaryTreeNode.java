package binarytree;

public class BinaryTreeNode {

    private Integer value;
    private BinaryTreeNode left;
    private BinaryTreeNode right;

    public BinaryTreeNode(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return this.value;
    }

    public BinaryTreeNode getLeft() {
        return left;
    }

    public BinaryTreeNode getRight() {
        return right;
    }

    public void insert(Integer value) {
        BinaryTreeNode newNode = new BinaryTreeNode(value);
        if (value < this.getValue()) {
            if (left == null) left = newNode;
            else left.insert(value);
        } else {
            if (right == null) right = newNode;
            else right.insert(value);
        }
    }

    public void output(StringBuffer out) {
        if (left != null) left.output(out);
        out.append(value + " ");
        if (right != null) right.output(out);
    }

    public boolean same(BinaryTreeNode other) {
        boolean leftIsSame = left == null ? other.getLeft() == null : other.getLeft() != null && left.same(other.getLeft());
        boolean rightIsSame = right == null ? other.getRight() == null : other.getRight() != null && right.same(other.getRight());
        boolean childrenSame = leftIsSame && rightIsSame;
        return getValue() == other.getValue() && childrenSame;
    }

}
