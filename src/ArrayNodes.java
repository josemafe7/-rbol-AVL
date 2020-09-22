
public class ArrayNodes <E extends Comparable<E>> {

    private E element;
    private int balance;

    public ArrayNodes(E element) {
        this.element = element;
        this.balance = 0;
    }

    
    
    public ArrayNodes(E element, int balance) {
        this.element = element;
        this.balance = balance;
    }

    
    
    public E getElement() {
        return element;
    }

    public void setElement(E element) {
        this.element = element;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "ArrayNodes{" + "element=" + element + ", balance=" + balance + '}';
    }

    
}
