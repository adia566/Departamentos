package bd_departamentos;

import java.util.ArrayList;

public class BD_Departamentos {

    static MySQL myDb = new MySQL();
    static Object[][] datos = null;
    
    public static void main(String[] args) {
        
        myDb.MySQLConnection("pepe", "pepa", "depemp2014");  

        datos = myDb.getValues("departamentos");
        System.out.println(myDb.mostrarTabla(datos));
        
        Panel p = new Panel(myDb);
        
        new Ventana(p);

//        myDb.closeConnection();   

    }

}
