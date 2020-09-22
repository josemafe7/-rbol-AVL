
import java.util.*;

public interface IAVLTree <E extends Comparable<E>> {
    
    public boolean addElement(E element);
    
    public boolean removeElement(E element);
    
    public boolean removeMin();
    
    public boolean removeMax();
    
    public E findMin();
    
    public E findMax();
    
    public void removeLeftSubtree();
    
    public void removeRightSubtree();
    
    public void removeAllElements();
    
    public boolean isEmpty();
    
    public int size();
    
    public boolean contains(E element);
    
    public E find(E element);
    
    public String toString();
    
    public Iterator iteratorInOrder();
    
    public Iterator iteratorPreOrder();
    
    public Iterator iteratorPostOrder();
    
    public Iterator iteratorLevelOrder();
      
    
}
