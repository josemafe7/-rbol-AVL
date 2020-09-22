
public class BinaryNode <E extends Comparable<E>> {

    private E element;
    private int balance;
    private BinaryNode left;
    private BinaryNode right;
    

    public BinaryNode(E ele) {
        this.element = ele;
        this.left = null;
        this.right = null;
        this.balance = 0;
    }

    public BinaryNode(E ele, BinaryNode l, BinaryNode r, int balance) {
        this.element = ele;
        this.left = l;
        this.right = r;
        this.balance = 0;
    }
    

    public E getElement() {
        return element;
    }

    public void setElement(E element) {
        this.element = element;
    }

    public BinaryNode getLeft() {
        return left;
    }

    public void setLeft(BinaryNode left) {
        this.left = left;
    }

    public BinaryNode getRight() {
        return right;
    }

    public void setRight(BinaryNode right) {
        this.right = right;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
    
    


    @Override
    public String toString() {
        
        E r, l;
        
        if(this.getLeft() == null){
            l = null;
        } else {
            l = (E) getLeft().getElement();
        }
        
        if(this.getRight() == null){
            r = null;
        } else {
            r = (E) getRight().getElement();
        }
        
        return ("Hijo Izquierdo:" + l +
                "\nHijo Derecho: " + r +
                "\nFactor Equilibrio: " + this.getBalance() + "\n");
    }
  
    
}
