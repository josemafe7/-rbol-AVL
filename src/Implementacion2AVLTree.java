
import java.util.*;


public class Implementacion2AVLTree  <E extends Comparable<E>>implements IAVLTree<E> {
    private List<ArrayNodes> tree;
    private int tamano;

    public Implementacion2AVLTree() {
        this.tree = new ArrayList();
        this.tamano = 0;
    }

   
    public List getTree() {
        return tree;
    }

    public void setTree(List tree) {
        this.tree = tree;
    }

    public int getTamano() {
        return tamano;
    }

    public void setTamano(int tamano) {
        this.tamano = tamano;
    }
    
    private void incrementarTamano(){
        setTamano(getTamano()+1);
    }
    
    private void decrementarTamano(){
        setTamano(getTamano()-1);
    }
    
    
    
    private ArrayNodes getLeft(int pos) {
        int left = 2*pos+1;
        if(left < size())
            return tree.get(2*pos+1);
        else
            return null;

    }
    
    private ArrayNodes getRight(int pos) {
        int right = 2*pos+2;
        if(right < size())
            return tree.get(right);
        else
            return null;
    }
    
    private int getLeftPos(int pos) {
        return 2*pos+1;

    }
    
    private int getRightPos(int pos) {
        return 2*pos+2;
    }
    
    private void reestructurarNulos(int oldp){
        int p = height();
        if (p > oldp){
            int aux = (int) Math.pow(2,p);
            for(int i = 0; i < aux; i++){
                tree.add(null);
            }
        }
    }

    public boolean addElement(E element) {
        System.out.print("****Insertar "+element+": ");
            if (isEmpty()){
                ArrayNodes n = new ArrayNodes(element);
                tree.add(n);
                tree.add(null);
                tree.add(null);
                incrementarTamano();
                System.out.println("\n");
                return true;
            } else {
                if (this.contains(element) || element == null){
                    System.out.println("\n");
                    return false;
                } else {
                    int p = height();
                    addElement(tree.get(0), element);
                    reestructurarNulos(p);
                    incrementarTamano();
                    this.balanced();
                    System.out.println("\n");
                    return true;
                } 
            
        }
        
    }

    private void addElement(ArrayNodes n, E element){
        E nActual = (E) n.getElement();
        E nEle = (E) element;
        
        if(nActual.compareTo(nEle) > 0){
            if (getLeft(tree.indexOf(n)) != null)
                addElement(getLeft(tree.indexOf(n)), element);
            else{
                ArrayNodes newNode = new ArrayNodes(element);
                tree.set(getLeftPos(tree.indexOf(n)), newNode);
            }
        } else if (nActual.compareTo(nEle) < 0){
             if (getRight(tree.indexOf(n)) != null){
                 addElement(getRight(tree.indexOf(n)), element);
            }else{
                ArrayNodes newNode = new ArrayNodes(element);
                int b = this.size();
                tree.set(getRightPos(tree.indexOf(n)), newNode);
             }
        }
    }

    
    public boolean removeElement(E element) {
        System.out.print("****Eliminar "+element+": ");
        if(this.contains(element)){
            if(tree.get(0).equals(element)){
                Iterator it = this.iteratorArrayNodesInOrder();
                        boolean enc= false;
                            while(!enc){
                                ArrayNodes newNode = (ArrayNodes) it.next();
                                if(newNode.getElement().equals(element))
                                    enc = true;
                            }
                        ArrayNodes newNode = (ArrayNodes) it.next();
                        ArrayNodes hizq = tree.get(1);
                        ArrayNodes hder = tree.get(2);
                        
                        tree.set(0, newNode);
                        tree.set(getLeftPos(0), hizq);
                        tree.set(getRightPos(0), hder);
                        
            } else {
                removeElement(0, element);
            }
            decrementarTamano();
            this.balanced();
            System.out.println("\n");
            return true;
        } else {
            System.out.println("\n");
            return false; //No contiene elemento
        }
    }
    
    private void removeElement(int pos, E element){
        int nodoBorrar = findNode(0, element);
        int padre = parent(nodoBorrar);
        if (isExternal(nodoBorrar)){
            tree.set(tree.indexOf(tree.get(nodoBorrar)), null);
        } else if (getLeft(pos) == null){
            tree.get(padre).setElement(getRight(pos).getElement());
        } else if (getRight(pos) == null){
            tree.get(padre).setElement(getLeft(pos).getElement());
        } else {
            
            Iterator it = this.iteratorArrayNodesInOrder();
                        boolean enc= false;
                            while(!enc){
                                ArrayNodes newNode = (ArrayNodes) it.next();
                                if(newNode.getElement().equals(element))
                                    enc = true;
                            }
                            ArrayNodes newNode = (ArrayNodes) it.next();
                            ArrayNodes hizq = getLeft(nodoBorrar);
                            ArrayNodes hder = getRight(nodoBorrar);
                            
                            tree.set(padre, newNode);
                            tree.set(getLeftPos(padre), hizq);
                            tree.set(getRightPos(padre), hder);

        }
       
    }

    private boolean isExternal(int pos){
       return (getLeft(pos) == null && getRight(pos) == null);
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
            return findMin(0);
    }
    
    private E findMin(int pos){
        if(getLeft(pos) == null){
            return (E) tree.get(pos).getElement();
        }else {
            return findMin(getLeftPos(pos));
        }
    }

    public E findMax() {
        if(isEmpty())
            return null;
        else
            return findMax(0);
    }
    
    private E findMax(int pos){
        if(getRight(pos) == null){
            return (E) tree.get(pos).getElement();
        }else {
            return findMax(getRightPos(pos));
        }
    }

    @Override
    public void removeLeftSubtree() {
        tree.set(getLeftPos(0), null);
        this.balanced();
        if(!isBalanced(tree.get(0))){
            E rootEle = (E) tree.get(0).getElement();
            removeElement(rootEle);
            addElement(rootEle);
        }
    }
    

    @Override
    public void removeRightSubtree() {
        tree.set(getRightPos(0), null);
        this.balanced();
        if(!isBalanced(tree.get(0))){
            E rootEle = (E) tree.get(0).getElement();
            removeElement(rootEle);
            addElement(rootEle);
        }
    }
    
    
    

    public void removeAllElements() {
      tree.clear();
    }

    
    public boolean isEmpty() {
        return tree.isEmpty();
    }
    
    
   
    
    private void balanced(){
        this.calcularBalance(); // Actualizar los elemento balance de cada nodo
        Iterator it = this.iteratorArrayNodesPostOrder();
            while(it.hasNext()){
                ArrayNodes n = (ArrayNodes) it.next();
                if (!isBalanced(n)){
                    balanced(n);
                    this.calcularBalance();
                }
            }
        
        
    }
    
    private void balanced(ArrayNodes n){
        int p = n.getBalance();
        int hizq = 0;
        int hder = 0;
        if(getLeft(tree.indexOf(n)) != null)
            hizq = getLeft(tree.indexOf(n)).getBalance();
        
        if (getRight(tree.indexOf(n)) != null)
            hder = getRight(tree.indexOf(n)).getBalance();

        
        if(p < -1 && hizq == -1){
            rotacionDerechaAVL(tree.indexOf(n));
            System.out.print("1 rotación simple");
        } else if (p > 1 && hder == 1){
            rotacionIzquierdaAVL(tree.indexOf(n));
            System.out.print("1 rotación simple");
        } else if (p > 1 && hder == -1){
            rotacionDerechaIzquierdaAVL(tree.indexOf(n));
            System.out.print("1 rotación doble");
        } else if (p < -1 && hizq == 1){
            rotacionIzquierdaDerechaAVL(tree.indexOf(n));
            System.out.print("1 rotación doble");
        }
    }
    
    public void rotacionDerechaAVL(int pos){
        ArrayNodes actual = tree.get(pos);
        ArrayNodes hizq = getLeft(pos);
        ArrayNodes nder = getRight(getLeftPos(pos));
        
        if (pos == 0)
            tree.set(0, hizq);
        else{
            int parentNode = parent(pos);
            ArrayNodes a = tree.get(parentNode);
            if(actual.getElement().compareTo(a.getElement()) > 0){
                tree.set(getLeftPos(parentNode), hizq);
            } else {
                tree.set(getRightPos(parentNode), hizq);
            }
        }
        
        tree.set(getRightPos(pos), actual);
        tree.set(getLeftPos(pos), nder);
        this.tieneHijos(getLeftPos(getLeftPos(pos)));
        this.tieneHijos(getRightPos(getLeftPos(pos)));


    }
    
    
    
    public void rotacionIzquierdaAVL(int pos){
        ArrayNodes actual = tree.get(pos);
        ArrayNodes hder = getRight(pos);
        ArrayNodes nizq = getLeft(getRightPos(pos));
        
        if (pos == 0)
            tree.set(0, hder);
        else{          
            int parentNode = parent(pos);
            ArrayNodes a = tree.get(parentNode);
            if(actual.getElement().compareTo(a.getElement()) > 0){
                tree.set(getRightPos(parentNode), hder);
            } else {
                tree.set(getLeftPos(parentNode), hder);
            }

        }
        tree.set(getLeftPos(pos), actual);
        tree.set(getRightPos(pos), nizq);
        this.tieneHijos(getRightPos(getRightPos(pos)));
        this.tieneHijos(getLeftPos(getRightPos(pos)));

        
    }
    
    
    
    private void rotacionDerechaIzquierdaAVL(int pos){

        ArrayNodes hder = getRight(pos);
        ArrayNodes nizq = getLeft(getRightPos(pos));
        
        tree.set(getRightPos(pos), nizq);
        tree.set(getRightPos(getLeftPos(getRightPos(pos))), hder);
        tree.set(getLeftPos(getRightPos(pos)), null);

        this.tieneHijos(getLeftPos(getRightPos(pos)));
        this.tieneHijos(getRightPos(getRightPos(pos)));

        rotacionIzquierdaAVL(pos);

    }
    
    private void rotacionIzquierdaDerechaAVL(int pos){
        ArrayNodes hizq = getLeft(pos);
        ArrayNodes nder = getRight(getLeftPos(pos));
        
        tree.set(getLeftPos(pos), nder);
        tree.set(getLeftPos(getRightPos(getLeftPos(pos))), hizq);
        tree.set(getRightPos(getLeftPos(pos)), null);

        this.tieneHijos(getRightPos(getLeftPos(pos)));
        this.tieneHijos(getLeftPos(getLeftPos(pos)));

        
        rotacionDerechaAVL(pos);
    }
    
    private void tieneHijos(int pos){
        if (tree.get(pos) != null){
            
            tieneHijos(getLeftPos(pos));       
            tieneHijos(getRightPos(pos));
            ArrayNodes newNode = tree.get(pos);
            tree.set(tree.indexOf(newNode), null);
            tree.set(parent(pos), newNode);
       
        }
    }
    
    
    private boolean isBalanced(ArrayNodes n){
        int l = countHeight(getLeft(tree.indexOf(n)));
        int r = countHeight(getRight(tree.indexOf(n)));
        int bf = r-l;
        return ((-1 <= bf) && (bf <= 1));
    }
    
    private void calcularBalance(){
        int i = 0;
        int pos = 0;
        while(pos < getTamano()){
            if(tree.get(i) != null){
                calcularBalance(i);
                pos++;
            }
        i++;
        }
    }
    
    
    
    private void calcularBalance(int pos){
        
        int l = countHeight(getLeft(pos));
        int r = countHeight(getRight(pos));
        tree.get(pos).setBalance(r-l);
    }

    private int height(){
        return countHeight(tree.get(0));
    }
    
    private int countHeight(ArrayNodes n){
        
        if(n == null)
            return 0;
        else {

            return 1+(Math.max(countHeight(getLeft(tree.indexOf(n))), countHeight(getRight(tree.indexOf(n)))));
        }
   
    }
    
    
    public int size() {
        return tree.size();
    }
    

    
    public boolean contains(E element) {
        boolean enc = false;
        if(!isEmpty()){
        if(element!=null)
          enc = contains(0, element);
        }
        return enc;
    }
    
    private boolean contains(int pos, E element){

            if(tree.get(pos).getElement().compareTo(element) == 0) {
                return true;
            } else if (tree.get(pos).getElement().compareTo(element) > 0){
                if(getLeft(pos) == null)
                    return false;
                else
                     return contains(getLeftPos(pos), element);
            } else{
                if(getRight(pos) == null)
                    return false;
                else
                     return contains(getRightPos(pos), element);           
            }
    }
    
    public E find(E element) {
        E ele = null;
        if(element!=null && contains(element))
          ele = find(0, element);
        return (E) ele;
    }
    
    private E find(int pos, E element){

            if(tree.get(pos).getElement().compareTo(element) == 0) {
                return (E) tree.get(pos).getElement();
            } else if (tree.get(pos).getElement().compareTo(element) > 0){
                return find(getLeftPos(pos), element);
            } else{
                return find(getRightPos(pos), element);
     
            }
    }
    
    private int findNode(int pos, E element){

            if(tree.get(pos).getElement().compareTo(element) == 0) {
                return pos;
            } else if (tree.get(pos).getElement().compareTo(element) > 0){
                return findNode(getLeftPos(pos), element);

            } else{
                return findNode(getRightPos(pos), element);       
            }
    }

    public Iterator iteratorInOrder() {
        Collection c = new ArrayList();
        iteratorInOrder(tree.get(0), c);
        return (c.iterator());
    }
    
    private void iteratorInOrder(ArrayNodes n, Collection c){
        if (n != null){
         
       iteratorInOrder(getLeft(tree.indexOf(n)), c);
       c.add(n.getElement());
       iteratorInOrder(getRight(tree.indexOf(n)), c);
       
        }
    }

    public Iterator iteratorPreOrder(){
        Collection c = new ArrayList();
        iteratorPreOrder(0, c);
        return (c.iterator());
    }
    
    private void iteratorPreOrder(int pos, Collection c){
        if (tree.get(pos) != null){
            c.add(tree.get(pos).getElement());
            iteratorPreOrder(getLeftPos(pos), c);       
            iteratorPreOrder(getRightPos(pos), c);
       
        }
    }

    public Iterator iteratorPostOrder() {
        Collection c = new ArrayList();
        iteratorPostOrder(0, c);
        return (c.iterator());
    }
    
    private void iteratorPostOrder(int pos, Collection c){
        if (tree.get(pos) != null){
            
            iteratorPostOrder(getLeftPos(pos), c);       
            iteratorPostOrder(getRightPos(pos), c);
            c.add(tree.get(pos).getElement());
       
        }
    }
    
    private Iterator iteratorArrayNodesPostOrder() {
        Collection c = new ArrayList();
        iteratorArrayNodesPostOrder(tree.get(0), 0, c);
        return (c.iterator());
    }
    
    private void iteratorArrayNodesPostOrder(ArrayNodes n, int pos, Collection c){
        if (n != null){
            
            iteratorArrayNodesPostOrder(getLeft(pos), getLeftPos(pos), c);       
            iteratorArrayNodesPostOrder(getRight(pos), getRightPos(pos), c);
            c.add(n);
       
        }
    }
    
    public Iterator iteratorLevelOrder() {
        Collection c = new ArrayList();
        iteratorLevelOrder(c);
        return c.iterator();
    }

    private void iteratorLevelOrder(Collection c){
        int i = 0;
        int pos = 0;
        while(pos < getTamano()){
            if (tree.get(i) != null){
                c.add(tree.get(i).getElement());
                pos++;
            }
            i++;
        }
    }
        

    
    private Iterator iteratorArrayNodesInOrder() {
        Collection c = new ArrayList();
        iteratorArrayNodesInOrder(tree.get(0), c);
        return (c.iterator());
    }
    
    private void iteratorArrayNodesInOrder(ArrayNodes n, Collection c){
        if (n != null){
         
       iteratorArrayNodesInOrder(getLeft(tree.indexOf(n)), c);
       c.add(n);
       iteratorArrayNodesInOrder(getRight(tree.indexOf(n)), c);
       
        }
    }
    
    private Iterator iteratorArrayNodesLevelOrder() {
        Collection c = new ArrayList();
        iteratorArrayNodesLevelOrder(c);
        return c.iterator();
    }
    
    private void iteratorArrayNodesLevelOrder(Collection c){

        int i = 0;
        int pos = 0;
        while(pos < getTamano()){
            if (tree.get(i) != null){
                c.add(tree.get(i));
                pos++;
            }
            i++;
        }
    }
    
    private int parent(int pos) {
           return (pos-1)/2;
    }
    
    
   
    
    public String toString() {
        Iterator it = this.iteratorArrayNodesLevelOrder();
        String s = "\n| Arbol Level Order |\n";
        E hderE = null;
        E hizqE = null;
        E padreE = null;
           while(it.hasNext()){
               ArrayNodes n = (ArrayNodes) it.next();
               ArrayNodes padre = tree.get(parent(tree.indexOf(n)));
               ArrayNodes hizq = getLeft(tree.indexOf(n));
               if(n == tree.get(0)){
                   padreE = null;
               } else {
                   padreE = (E) padre.getElement();
               }
               if (hizq == null){
                   hizqE = null;
               } else {
                   hizqE = (E) hizq.getElement();
               }
               ArrayNodes hder = getRight(tree.indexOf(n));
               if (hder == null){
                   hderE = null;
               } else {
                   hderE = (E) hder.getElement();
               }
               int balance = n.getBalance();
               s += ("\n" + n.getElement() + 
                       "\nPadre: " + padreE +
                       "\nHijo Izquierdo: " + hizqE +
                       "\nHijo Derecho: " + hderE +
                       "\nFactor de Equilibrio: " + balance + "\n");
           }
        return s;
    }
        
    
    

        

    
    
    



    
}
