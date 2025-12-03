package cs2.adt;

import java.util.Iterator;

public class BinarySearchTree<T extends Comparable<T>> implements Iterable<T> {
    private class Node {
        public T data; public Node left; public Node right;
        public Node(T d, Node l, Node r) {
            data = d; left = l; right = r;
        }
        public boolean contains(T elem) {
            if(data.compareTo(elem) == 0) return true;
            else if(data.compareTo(elem) < 0) {
                if(right == null) return false;
                else return right.contains(elem);
            }
            else {
                if(left == null) return false;
                else return left.contains(elem);
            }
        }
        public void insert(T elem) {
            if(data.compareTo(elem) < 0) {
                //Go right
                if(right == null) {
                    right = new Node(elem, null, null);
                } else {
                    right.insert(elem);
                }
            } else {
                //Go left
                if(left == null) {
                    left = new Node(elem, null, null);
                } else {
                    left.insert(elem);
                }
            }
        }

        private MaxResult passUpMax() {
            if(right == null) {
                return new MaxResult(data, left);
            } else {
                MaxResult mr = right.passUpMax();
                right = mr.kid;
                return new MaxResult(mr.data, this);
            }
        }

        public Node remove(T elem) {
            if(data.compareTo(elem) == 0) {
                if(left == null) { 
                    return right;
                }
                else if(right == null) {
                    return left;
                } else { //2 kids
                    MaxResult mr = left.passUpMax();
                    data = mr.data;
                    left = mr.kid;
                    return this;
                }
            } else {
                if(data.compareTo(elem) < 0 && right != null) {
                    right = right.remove(elem);
                } else if (left != null) {
                    left = left.remove(elem);
                }
                return this;
            }
        }

    }

    private class MaxResult {
        public T data;
        public Node kid;
        public MaxResult(T d, Node n) {
            data = d; kid = n;
        }
    }

    private Node root;

    public BinarySearchTree() {
        root = null;
    }

    public boolean isEmpty() { return root == null; }
    public void remove(T elem) {
        root = root.remove(elem);
    }

    public void insert(T elem) {
        if(root == null) root = new Node(elem, null, null);
        else root.insert(elem);
    }
    public boolean contains(T elem) {
        if(root == null) return false;
        return root.contains(elem);
        
        /*data
        Node current = root;
        while(current != null) {
            if(current.data.compareTo(elem) == 0) {
                return true;
            } else {
                if(current.data.compareTo(elem) < 0) {
                    current = current.right;
                } else {
                    current = current.left;
                }
            }
        }
        return false;
        */
    }

    public void helperPreOrder(Node current) {
        System.out.print(current.data + ",");
        if(current.left != null) helperPreOrder(current.left);
        if(current.right != null) helperPreOrder(current.right);
    }
    public void printPreOrder() {
        if(root != null) helperPreOrder(root);
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            Stack<Node> s = new LinkedStack<>();
            { if(root != null) s.push(root); }
            public boolean hasNext() {
                return !s.isEmpty();
            }
            public T next() {
                Node nextNode = s.pop();
                if(nextNode.right != null) s.push(nextNode.right);
                if(nextNode.left != null) s.push(nextNode.left);
                return nextNode.data;
            }
        };
    }

    public static void main(String[] args) {
        BinarySearchTree<String> tree = new BinarySearchTree<>();
        tree.insert("Hello");
        tree.insert("Goodbye");
        tree.insert("What");
        tree.printPreOrder();

        Iterator<String> it = tree.iterator();
        while(it.hasNext()) {
            System.out.println(it.next());
        }

        for(String s : tree) {
            System.out.println(s);
        }
        
    }


    
}





