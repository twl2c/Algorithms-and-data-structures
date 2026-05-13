public class BTree {

    static class BTreeNode {

        int[] values;
        BTreeNode[] children;
        int count;
        boolean isLeaf;

        BTreeNode(int degree, boolean isLeaf) {
            this.isLeaf = isLeaf;
            values = new int[2 * degree - 1];
            children = new BTreeNode[2 * degree];
            count = 0;
        }
    }

    private BTreeNode root;
    private final int degree;
    public long addOperations = 0;
    public long searchOperations = 0;
    public long removeOperations = 0;

    public BTree(int degree) {
        this.degree = degree;
        root = new BTreeNode(degree, true);
    }


    public boolean contains(int value) {
        return contains(root, value);
    }

    private boolean contains(BTreeNode node, int value) {
        int position = 0;
        while (position < node.count && value > node.values[position]) {
            searchOperations++;
            position++;
        }
        if (position < node.count && value == node.values[position]) {
            searchOperations++;
            return true;
        }
        if (node.isLeaf) {
            return false;
        }
        return contains(node.children[position], value);
    }

    public void add(int value) {
        BTreeNode currentRoot = root;
        if (currentRoot.count == 2 * degree - 1) {
            addOperations++;
            BTreeNode newRoot = new BTreeNode(degree, false);
            root = newRoot;
            newRoot.children[0] = currentRoot;
            splitChild(newRoot, 0, currentRoot);
            addToNonFullNode(newRoot, value);
        } else {
            addToNonFullNode(currentRoot, value);
        }
    }

    private void addToNonFullNode(BTreeNode node, int value) {
        int index = node.count - 1;
        if (node.isLeaf) {
            while (index >= 0 && value < node.values[index]) {
                addOperations++;
                node.values[index + 1] = node.values[index];
                index--;
            }
            node.values[index + 1] = value;
            node.count++;
        } else {
            while (index >= 0 && value < node.values[index]) {
                addOperations++;
                index--;
            }
            index++;
            if (node.children[index].count == 2 * degree - 1) {
                splitChild(node, index, node.children[index]);
                if (value > node.values[index]) {
                    index++;
                }
            }
            addToNonFullNode(node.children[index], value);
        }
    }

    private void splitChild(BTreeNode parentNode, int childIndex, BTreeNode fullChildNode) {
        BTreeNode rightNode = new BTreeNode(degree, fullChildNode.isLeaf);
        rightNode.count = degree - 1;
        for (int i = 0; i < degree - 1; i++) {
            addOperations++;
            rightNode.values[i] = fullChildNode.values[i + degree];
        }
        if (!fullChildNode.isLeaf) {
            for (int i = 0; i < degree; i++) {
                addOperations++;
                rightNode.children[i] = fullChildNode.children[i + degree];
            }
        }
        fullChildNode.count = degree - 1;
        for (int i = parentNode.count; i >= childIndex + 1; i--) {
            parentNode.children[i + 1] = parentNode.children[i];
        }
        parentNode.children[childIndex + 1] = rightNode;
        for (int i = parentNode.count - 1; i >= childIndex; i--) {
            parentNode.values[i + 1] = parentNode.values[i];
        }
        parentNode.values[childIndex] = fullChildNode.values[degree - 1];
        parentNode.count++;
    }

    public void remove(int value) {
        remove(root, value);
    }

    private void remove(BTreeNode node, int value) {
        int position = 0;
        while (position < node.count && value > node.values[position]) {
            removeOperations++;
            position++;
        }
        if (position < node.count && node.values[position] == value && node.isLeaf) {
            for (int i = position; i < node.count - 1; i++) {
                removeOperations++;
                node.values[i] = node.values[i + 1];
            }
            node.count--;
            return;
        }
        if (!node.isLeaf) {
            remove(node.children[position], value);
        }
    }
}