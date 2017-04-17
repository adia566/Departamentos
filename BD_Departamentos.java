package bd_departamentos;

import java.sql.Connection;
import java.util.ArrayList;

public class BD_Departamentos {

    static MySQL myDb = new MySQL();
    static Object[][] datos = null;
    static Connection conexion;
    
    public static void main(String[] args) {
        
        conexion = myDb.MySQLConnection("pepe", "pepa", "depemp2014");  

        datos = myDb.getValues("departamentos");
        System.out.println(myDb.mostrarTabla(datos));
        
        Panel p = new Panel(myDb, conexion);
        
        new Ventana(p);

//        myDb.closeConnection();   

    }

}
