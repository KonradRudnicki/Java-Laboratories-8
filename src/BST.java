import java.util.Objects;

public class BST<T extends Comparable<? super T>> {
    private class Node {
        T value;
        Node left, right, parent;

        public Node(T v) {
            value = v;
        }

        public Node(T value, Node parent) {
            this.value = value;
            this.parent = parent;
        }

        public Node(T value, Node left, Node right, Node parent) {
            super();
            this.value = value;
            this.left = left;
            this.right = right;
            this.parent = parent;
        }
    }

    private Node root = null;

    public BST() {
    }

    public Node getNode(T toFind){
        if (toFind == null) return null;

        Node node = root;

        while (!node.value.equals(toFind)) {
            if (toFind.compareTo(node.value) < 0) {
                node = node.left;
            } else if (toFind.compareTo(node.value) > 0) {
                node = node.right;
            }

            if (node == null) return null;
        }

        return node;
    }

    public T getElement(T toFind) {
        if (toFind == null) return null;
        Node node = getNode(toFind);

        if (node==null) return null;

        return node.value;
    }

    public boolean isLeaf(Node node) {
        return node.left == null && node.right == null;
    }

    public T successor(T elem) {
        if (size() == 0) {
            return null;
        }

        Node current = getNode(elem);
        if (isLeaf(current)){
            return null;
        }else {
          current = current.right;
        }

        while (current.left != null) {
            current = current.left;
        }

        return current.value;
    }

    public String toStringInOrder() {
        return inOrder(root, "");
    }

    public String inOrder(Node node, String result) {
        if (node != null) {
            result = inOrder(node.left, result);
            if (Objects.equals(result, "")) {
                result += node.value;
            } else {
                result += ", " + node.value;
            }
            result = inOrder(node.right, result);
        }

        return result;
    }

    public String toStringPreOrder() {
        return preOrder(root, "");
    }

    public String preOrder(Node node, String result) {
        if (node != null) {
            if (Objects.equals(result, "")) {
                result += node.value;
            } else {
                result += ", " + node.value;
            }
            result = preOrder(node.left, result);
            result = preOrder(node.right, result);
        }

        return result;
    }

    public String toStringPostOrder() {
        return postOrder(root, "");
    }

    public String postOrder(Node node, String result) {
        if (node != null) {
            result = postOrder(node.left, result);
            result = postOrder(node.right, result);
            if (Objects.equals(result, "")) {
                result += node.value;
            } else {
                result += ", " + node.value;
            }
        }

        return result;
    }

    public boolean add(T elem) {
        root = add(elem, root, null);

        return true;
    }

    private Node add(T value, Node node, Node parent) {
        if (node == null) {
            return new Node(value, parent);
        }

        parent = node;
        if (value.compareTo(node.value) < 0) {
            node.left = add(value, node.left, parent);
        } else if (value.compareTo(node.value) > 0) {
            node.right = add(value, node.right, parent);
        }

        return node;
    }

    public T remove(T value) {
        if (getElement(value) == null) {
            return null;
        }

        T retValue = getElement(value);
        root = remove(value, root);

        return retValue;
    }

    private Node remove(T value, Node node) {
        if (node == null) {
            return null;
        }

        if (value.compareTo(node.value) < 0) {
            node.left = remove(value, node.left);
        } else if (value.compareTo(node.value) > 0) {
            node.right = remove(value, node.right);
        } else {

            if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            }

            node.value = getSuccesor(node).value;
            node.right.parent = node;
            node.left.parent = node;
            node.right = remove(node.value, node.right);
        }

        return node;
    }

    public void clear() {
        this.root = null;
    }

    public int size() {
        return size(root, 0);
    }

    public int size(Node node, int result) {
        if (node != null) {
            result = size(node.left, result);
            result++;
            result = size(node.right, result);
        }

        return result;
    }

    public Node getSuccesor(Node node) {
        if (size() == 0) {
            return null;
        }

        Node current = node.right;
        while (current.left != null) {
            current = current.left;
        }

        return current;
    }

    public boolean hasOnlyLeftChild(Node node){
        return node.left != null && node.right == null;
    }

    public int countOnlyLeftChild(Node node) {
        if (node == null)
            return 0;

        return countOnlyLeftChild(node.left)
             +((hasOnlyLeftChild(node))?1:0)
             + countOnlyLeftChild(node.right);
    }
}