/*EPD08-P
*/


/*
@autor Jose Manuel Fernández Labrador
*/

import java.util.*;

public class Principal  {

    public static void main(String[] args) throws Exception{

        System.out.println("\nIMPLEMENTACION 1:\n");
        IAVLTree a1 = new Implementacion1AVLTree();
        
        a1.addElement(17);
        a1.addElement(12);
        a1.addElement(4);
        a1.addElement(9);
        a1.addElement(6);
        a1.addElement(2);
        a1.addElement(7);
        System.out.println("\nImprimir por pantalla punto 8\n\n"+a1);
        
        a1.removeElement(2);
        System.out.println("\nImprimir por pantalla punto 10\n\n"+a1);
        
        
        System.out.println("\nIMPLEMENTACION 2:\n");
        IAVLTree a2 = new Implementacion2AVLTree();
        
        a2.addElement(17);
        a2.addElement(12);
        a2.addElement(4);
        a2.addElement(9);
        a2.addElement(6);
        a2.addElement(2);
        a2.addElement(7);
        System.out.println("\nImprimir por pantalla punto 8\n\n"+a2);
        
        a2.removeElement(2);
        System.out.println("\nImprimir por pantalla punto 10\n\n"+a2);
   }
}
