package cs2.adt;

public class BinarySearchTree<T extends Comparable<T>> {
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



    
}
