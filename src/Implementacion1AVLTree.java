
import java.util.*;

public class Implementacion1AVLTree <E extends Comparable<E>>implements IAVLTree<E>{
    
    private BinaryNode root;
    
    
    public Implementacion1AVLTree(){
        this.root = null;
    }

    public Implementacion1AVLTree(BinaryNode root){
        this.root = root;
        this.calcularBalance();
    }

    public BinaryNode getRoot() {
        return (root);
    }

    private void setRoot(BinaryNode n) {
        this.root = n;
    }
    
    

    public boolean addElement(E element) {
        System.out.print("****Insertar "+element+": ");
        if (root == null){
            BinaryNode n = new BinaryNode(element);
            setRoot(n);
            System.out.println("\n");
            return true;
        } else {
            if (this.contains(element) || element == null){
                System.out.println("\n");
                return false;
            } else {
                addElement(root, element);
                this.balanced();
                System.out.println("\n");
                return true;
            } 
        }
    }

    private void addElement(BinaryNode n, E element){
        E nActual = (E) n.getElement();
        E nEle = (E) element;
        
        if(nActual.compareTo(nEle) > 0){
            if (n.getLeft() != null)
                addElement(n.getLeft(), element);
            else
                n.setLeft(new BinaryNode(element));
        } else if (nActual.compareTo(nEle) < 0){
             if (n.getRight() != null){
                addElement(n.getRight(), element);
            }else{
                n.setRight(new BinaryNode(element));
             }
        }
    }

    
    public boolean removeElement(E element) {
        System.out.print("****Eliminar "+element+": ");
        if(this.contains(element)){
            if(root.getElement().equals(element)){
                Iterator it = this.iteratorNodesInOrder();
                        boolean enc= false;
                            while(!enc){
                                BinaryNode newNode = (BinaryNode) it.next();
                                if(newNode.getElement().equals(element))
                                    enc = true;
                            }
                        BinaryNode newNode = (BinaryNode) it.next();
                        removeElement((E) newNode.getElement());
                        newNode.setLeft(root.getLeft());
                        newNode.setRight(root.getRight());
                        
                        setRoot(newNode);
            } else {
                removeElement(root, element);
            }
            this.balanced();
            System.out.println("\n");
            return true;
        } else {
            System.out.println("\n");
            return false; //No contiene elemento
        }
    }
    
    private void removeElement(BinaryNode n, E element){
        BinaryNode nodoBorrar = findNode(root, element);
        BinaryNode padre = parent(nodoBorrar);
        if (isExternal(nodoBorrar)){
            if(padre.getElement().compareTo(nodoBorrar.getElement()) > 0){
                padre.setLeft(null);
            } else {
                padre.setRight(null);
            }
        } else if (nodoBorrar.getLeft() == null){
            if(padre.getElement().compareTo(nodoBorrar.getElement()) > 0){
                padre.setLeft(nodoBorrar.getRight());
            } else {
                padre.setRight(nodoBorrar.getRight());
            }
        } else if (nodoBorrar.getRight() == null){
            if(padre.getElement().compareTo(nodoBorrar.getElement()) > 0){
                padre.setLeft(nodoBorrar.getLeft());
            } else {
                padre.setRight(nodoBorrar.getLeft());
            }
        } else {
            
            Iterator it = this.iteratorNodesInOrder();
                            boolean enc= false;
                                while(!enc){
                                    BinaryNode newNode = (BinaryNode) it.next();
                                    if(newNode.getElement().equals(element))
                                        enc = true;
                                }
                            BinaryNode newNode = (BinaryNode) it.next(); //Cogemos el siguiente
                            removeElement((E) newNode.getElement());
                            newNode.setLeft(nodoBorrar.getLeft());
                            newNode.setRight(nodoBorrar.getRight());

            
            if(padre.getElement().compareTo(nodoBorrar.getElement()) > 0){
                padre.setLeft(newNode);
            } else {
                padre.setRight(newNode);
            }
        }   
    }

    private boolean isExternal(BinaryNode n){
       return (n.getLeft() == null && n.getRight() == null);
    }
    
    public boolean removeMin() {
        E min = findMin();
        return removeElement(min);
    }

    public boolean removeMax() {
        E max = findMax();
        return removeElement(max);
    }

    public E findMin() {
        if(isEmpty())
            return null;
        else
            return findMin(root);
    }
    
    private E findMin(BinaryNode n){
        if(n.getLeft() == null){
            return (E) n.getElement();
        }else {
            return findMin(n.getLeft());
        }
    }

    public E findMax() {
        if(isEmpty())
            return null;
        else
            return findMax(root);
    }
    
    private E findMax(BinaryNode n){
        if(n.getRight() == null){
            return (E) n.getElement();
        }else {
            return findMax(n.getRight());
        }
    }

    @Override
    public void removeLeftSubtree() {
        root.setLeft(null);
        this.balanced();
        if(!isBalanced(root)){
            E rootEle = (E) root.getElement();
            setRoot(root.getRight());
            addElement(rootEle);
        }
    }
    

    @Override
    public void removeRightSubtree() {
        root.setRight(null);
        this.balanced();
        if(!isBalanced(root)){
            E rootEle = (E) root.getElement();
            setRoot(root.getLeft());
            addElement(rootEle);
        }
    }
    
    
    

    public void removeAllElements() {
       this.root = null;
    }

    
    public boolean isEmpty() {
        return (root==null);
    }
    
    
    private void balanced(){
        this.calcularBalance(); // Actualizar los elemento balance de cada nodo
        Iterator it = this.iteratorNodesPostOrder();
            while(it.hasNext()){
                BinaryNode n = (BinaryNode) it.next();
                if (!isBalanced(n)){
                    balanced(n);
                    this.calcularBalance();
                }
               
            }
        
        
    }
    
    private void balanced(BinaryNode n){
        int p = n.getBalance();
        int hizq = 0;
        int hder = 0;
        if(n.getLeft() != null)
            hizq = n.getLeft().getBalance();
        
        if (n.getRight() != null)
            hder = n.getRight().getBalance();

        
        if(p < -1 && hizq == -1){
            rotacionDerechaAVL(n);
            System.out.print("1 rotación simple");
        } else if (p > 1 && hder == 1){
            rotacionIzquierdaAVL(n);
            System.out.print("1 rotación simple");
        } else if (p > 1 && hder == -1){
            rotacionDerechaIzquierdaAVL(n);
            System.out.print("1 rotación doble");
        } else if (p < -1 && hizq == 1){
            rotacionIzquierdaDerechaAVL(n);
            System.out.print("1 rotación doble");
        }

    }
    
    public void rotacionDerechaAVL(BinaryNode n){
        
        BinaryNode hizq = n.getLeft();
        BinaryNode nder = n.getLeft().getRight();
        
        if (n.equals(root)) 
            setRoot(hizq);
        else{
            BinaryNode parentNode = parent(n);
            if(n.getElement().compareTo(parentNode.getElement()) > 0){
                parentNode.setRight(hizq);
            } else {
                parentNode.setLeft(hizq);
            }
        }
        hizq.setRight(n);
        n.setLeft(nder);
        

    }
    
    
    
    public void rotacionIzquierdaAVL(BinaryNode n){

        BinaryNode hder = n.getRight();
        BinaryNode nizq = n.getRight().getLeft();
        
        if (n.equals(root))
            setRoot(hder);
        else{
            BinaryNode parentNode = parent(n);
            if(n.getElement().compareTo(parentNode.getElement()) > 0){
                parentNode.setRight(hder);
            } else {
                parentNode.setLeft(hder);
            }
        }

        hder.setLeft(n);
        n.setRight(nizq);

        
    }
    
    
    
    private void rotacionDerechaIzquierdaAVL(BinaryNode n){
        BinaryNode hder = n.getRight();
        BinaryNode nizq = n.getRight().getLeft();
        
        n.setRight(nizq);
        nizq.setRight(hder);
        hder.setLeft(null);
        
        rotacionIzquierdaAVL(n);

    }
    
    private void rotacionIzquierdaDerechaAVL(BinaryNode n){
        
        BinaryNode hizq = n.getLeft();
        BinaryNode nder = n.getLeft().getRight();
        
        n.setLeft(nder);
        nder.setLeft(hizq);
        hizq.setRight(null);
        
        rotacionDerechaAVL(n);
    }
    
    
    private boolean isBalanced(BinaryNode n){
        int l = countHeight(n.getLeft());
        int r = countHeight(n.getRight());
        int bf = r-l;
        return ((-1 <= bf) && (bf <= 1));
    }
    
    private void calcularBalance(){
        Iterator it = this.iteratorNodesLevelOrder();
        while(it.hasNext()){
            BinaryNode n = (BinaryNode) it.next();
            calcularBalance(n);
                
        }
    }
    
    private void calcularBalance(BinaryNode n){
        
        int l = countHeight(n.getLeft());
        int r = countHeight(n.getRight());
        n.setBalance(r-l);
    }

    private int height(){
        return countHeight(this.root);
    }
    
    private int countHeight(BinaryNode n){
        if(n == null)
            return 0;
        else {

            return 1+(Math.max(countHeight(n.getLeft()), countHeight(n.getRight())));
        }
    }
    
    
    public int size() {
        return (count(this.root));
    }
    
    
    private int count(BinaryNode n){
        if(n == null)
            return (0);
        else
            return count(n.getLeft()) + count(n.getRight()) + 1;
    }

    
    public boolean contains(E element) {
        boolean enc = false;
        if(!isEmpty()){
        if(element!=null)
          enc = contains(root, element);
        }
        return enc;
    }
    
    private boolean contains(BinaryNode n, E element){

            if(n.getElement().compareTo(element) == 0) {
                return true;
            } else if (n.getElement().compareTo(element) > 0){
                if(n.getLeft() != null)
                    return contains(n.getLeft(), element);
                else
                     return false;
            } else{
                if(n.getRight() != null)
                    return contains(n.getRight(), element);
                else
                     return false;           
            }
    }
    
    public E find(E element) {
        E ele = null;
        if(element!=null && contains(element))
          ele = find(root, element);
        return (E) ele;
    }
    
    private E find(BinaryNode n, E element){

            if(n.getElement().compareTo(element) == 0) {
                return (E) n.getElement();
            } else if (n.getElement().compareTo(element) > 0){
                return find(n.getLeft(), element);
            } else{
                return find(n.getRight(), element);         
            }
    }
    
    private BinaryNode findNode(BinaryNode n, E element){

            if(n.getElement().compareTo(element) == 0) {
                return n;
            } else if (n.getElement().compareTo(element) > 0){
                return findNode(n.getLeft(), element); 
            } else{
                return findNode(n.getRight(), element);          
            }
    }

    public Iterator iteratorInOrder() {
        Collection c = new ArrayList();
        iteratorInOrder(root, c);
        return (c.iterator());
    }
    
    private void iteratorInOrder(BinaryNode n, Collection c){
        if (n != null){
         
       iteratorInOrder(n.getLeft(), c);
       c.add(n.getElement());
       iteratorInOrder(n.getRight(), c);
       
        }
    }

    public Iterator iteratorPreOrder(){
        Collection c = new ArrayList();
        iteratorPreOrder(root, c);
        return (c.iterator());
    }
    
    private void iteratorPreOrder(BinaryNode n, Collection c){
        if (n != null){
            c.add(n.getElement());
            iteratorPreOrder(n.getLeft(), c);       
            iteratorPreOrder(n.getRight(), c);
       
        }
    }

    public Iterator iteratorPostOrder() {
        Collection c = new ArrayList();
        iteratorPostOrder(root, c);
        return (c.iterator());
    }
    
    private void iteratorPostOrder(BinaryNode n, Collection c){
        if (n != null){
            
            iteratorPostOrder(n.getLeft(), c);       
            iteratorPostOrder(n.getRight(), c);
            c.add(n.getElement());
       
        }
    }
    
    private Iterator iteratorNodesPostOrder() {
        Collection c = new ArrayList();
        iteratorNodesPostOrder(root, c);
        return (c.iterator());
    }
    
    private void iteratorNodesPostOrder(BinaryNode n, Collection c){
        if (n != null){
            
            iteratorNodesPostOrder(n.getLeft(), c);       
            iteratorNodesPostOrder(n.getRight(), c);
            c.add(n);
       
        }
    }
    
    public Iterator iteratorLevelOrder() {
        Collection c = new ArrayList();
        iteratorLevelOrder(root, c);
        return c.iterator();
    }

    private void iteratorLevelOrder(BinaryNode n, Collection c){

        if (n != null){
        int i = 0;
        BinaryNode aux; 
        List col=new ArrayList(); 
        List colAux=new ArrayList(); 
        col.add(n); 
        while (i < col.size()){
            colAux.add((aux=(BinaryNode) col.get(i)).getElement()); 
            i++;
            if (aux.getLeft() != null){
                col.add(aux.getLeft());
            }
            if (aux.getRight()!= null){
                col.add(aux.getRight());
            }
        }
        
       c.addAll(colAux);
        }
    }
        

    
    private Iterator iteratorNodesInOrder() {
        Collection c = new ArrayList();
        iteratorNodesInOrder(root, c);
        return (c.iterator());
    }
    
    private void iteratorNodesInOrder(BinaryNode n, Collection c){
        if (n != null){
         
       iteratorNodesInOrder(n.getLeft(), c);
       c.add(n);
       iteratorNodesInOrder(n.getRight(), c);
       
        }
    }
    
    private Iterator iteratorNodesLevelOrder() {
        Collection c = new ArrayList();
        iteratorNodesLevelOrder(root, c);
        return c.iterator();
    }
    
    private void iteratorNodesLevelOrder(BinaryNode n, Collection c){

        if (n != null){
        int i = 0;
        //BinaryNode aux; 
        List col=new ArrayList(); 
        List colAux=new ArrayList(); 
        col.add(n); 
        while (i < col.size()){
            BinaryNode aux=(BinaryNode) col.get(i);
            colAux.add(aux); 
            i++;
            if (aux.getLeft() != null){
                col.add(aux.getLeft());
            }
            if (aux.getRight()!= null){
                col.add(aux.getRight());
            }
        }
        
       c.addAll(colAux);
        }
    }
    
    private BinaryNode parent(BinaryNode node) {
        if (node == root){
            return null;
        } else {
            Iterator it = this.iteratorNodesInOrder();
            
            while(it.hasNext()){
            BinaryNode p = (BinaryNode) it.next();
            Collection c = children(p);
            Iterator it2 = c.iterator();
                while(it2.hasNext()){
                    BinaryNode h = (BinaryNode) it2.next();
                    if (h == node){
                       return p;
                    }
                }
            
            }
            return null;
        }
    }
    
    private E parentElement(BinaryNode node) {
        if (node == root){
            return null;
        } else {
            Iterator it = this.iteratorNodesInOrder();
            
            while(it.hasNext()){
            BinaryNode p = (BinaryNode) it.next();
            Collection c = children(p);
            Iterator it2 = c.iterator();
                while(it2.hasNext()){
                    BinaryNode h = (BinaryNode) it2.next();
                    if (h == node){
                       return (E) p.getElement();
                    }
                }
            
            }
            return null;
        }
    }
    

    
    private Collection children(BinaryNode node) {
        Collection c = new ArrayList();
        BinaryNode nIzq = node.getLeft();
        BinaryNode nDer = node.getRight();
        if (nIzq != null)
            c.add(nIzq);
               
        if (nDer != null)
            c.add(nDer);
        
        return c;
    }
    
    public String toString() {
        Iterator it = this.iteratorNodesLevelOrder();
        String s = "\n| Arbol Level Order |\n";
           while(it.hasNext()){
               BinaryNode newNode = (BinaryNode) it.next();
               s += ("\n" + newNode.getElement() + 
                       "\nPadre: " + parentElement(newNode) +
                       "\n" + newNode);
           }
        return s;
    }
   
}
