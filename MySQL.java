package bd_departamentos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author juan
 */
public class MySQL {

    private static Connection Conexion;
    ArrayList<String> header = new ArrayList<>();

    class Fila {

        Object[] columna;

        public Fila(int nCol) {
            columna = new Object[nCol];
        }

        public void MySQLConnection(String user, String pass, String db_name) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + db_name, user, pass);
                System.out.println("Se ha iniciado la conexión con el servidor de forma exitosa");
                
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
                
            } catch (SQLException ex) {
                Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public Connection MySQLConnection(String user, String pass, String db_name) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + db_name, user, pass);
            System.out.println("Se ha iniciado la conexión con el servidor de forma exitosa");
            
            return Conexion;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
            
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public void closeConnection() {
        try {
            Conexion.close();
            System.out.println("Se ha finalizado la conexión con el servidor");
        } catch (SQLException ex) {
            Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Object[][] getValues(String table_name) {
        Object[][] datos = null;
        header = new ArrayList<>();
        String Query = "SELECT * FROM " + table_name;
        datos = resumen(Conexion, Query);
        return datos;
    }

    public String consulta(String consulta) {
        String salida = "";
        try {
            String Query = consulta;
            Statement st = Conexion.createStatement();
            java.sql.ResultSet resultSet;
            if (consulta.contains("delete")) {
                st.executeUpdate(consulta);
                salida = "Borrado realizada.";
            }
            if (consulta.contains("update")) {
                st.executeUpdate(consulta);
                salida = "Actualizado correctamente.";
            }
            if (consulta.contains("insert")) {
                st.executeUpdate(consulta);
                salida = "Inserccion correcta.";
            }
        } catch (SQLException ex) {
//            JOptionPane.showMessageDialog(null, "Error en la consulta");
               salida = "Error en la consulta";
        }
        return salida;
    }
    
//    public String[][] getArrayDatos(ResultSet rs) throws SQLException{
//        
//        ResultSetMetaData metaData = rs.getMetaData();
//        int columnas = metaData.getColumnCount();
//        
//        rs.last();
//         int u=rs.getRow();
//        
//        String[][] datos = new String[][3];
//        rs.beforeFirst();
//        
//        int f=0;
//            
//        while(rs.next()){
//            
//            datos[f][0] = String.valueOf(rs.getInt("idDep"));
//            datos[f][1] = rs.getString("ciudad");
//            datos[f][2] = String.valueOf(rs.getDouble("objetivoAnual"));
//            
//        }
//            
//        return datos;
//    }
            

    public String select(String consulta) {
        String salida = "";
        header = new ArrayList();
        Object[][] datos = null;
        Boolean cabezera=false;
        datos = resumen(Conexion, consulta);
        salida = mostrarTabla(datos);
        return salida;
    }

    public String mostrarTabla(Object[][] tabla) {
        String salida = "";
        for (int i = 0; i <header.size(); i++) {
            salida+=header.get(i)+ "\t";
        }
        salida += "\n";
        for (int i = 0; i < tabla.length; i++) {
            for (int j = 0; j < tabla[i].length; j++) {
                salida += tabla[i][j] + "\t";
            }
            salida += "\n";
        }
        return salida;
    }

    public String[] bases() {
        Object[][] datos = null;
        String consulta="show databases";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection ConexionBase = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "pepe", "pepa");
            Boolean cabezera=false;
            datos=resumen(ConexionBase, consulta);
            String[] aux = new String[datos.length];
            for (int i = 0; i < aux.length; i++) {
                aux[i] = (String) datos[i][0];
            }
            ConexionBase.close();
            return aux;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error en mostrar bases");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        String[] salida = null;
        return salida;
    }

    public String[] mostrarTablas(String nombreBase) {
        Object[][] datos = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection ConexionBase = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + nombreBase, "pepe", "pepa");
            Boolean cabezera=false;
            String consulta="show tables";
            datos=resumen(ConexionBase, consulta);
            String[] aux = new String[datos.length];
            for (int i = 0; i < aux.length; i++) {
                aux[i] = (String) datos[i][0];
            }
            ConexionBase.close();
            return aux;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error en mostrar tablas");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        String[] salida = null;
        return salida;
    }

    public Object[][] resumen(Connection conexionD, String Query) {
        Object[][] datos = null;
        header = new ArrayList<>();
        try {
            Statement st = conexionD.createStatement();
            java.sql.ResultSet resultSet;
            resultSet = st.executeQuery(Query);

            ResultSetMetaData metaData = resultSet.getMetaData();
            int numberOfColumns = metaData.getColumnCount();

            for (int i = 1; i <= numberOfColumns; i++) {
                header.add(metaData.getColumnName(i));
            }
            ArrayList<Fila> filas = new ArrayList<Fila>();
            while (resultSet.next()) {
                Fila fila = new Fila(numberOfColumns);
                for (int i = 1; i <= numberOfColumns; i++) {
                    try {
                        fila.columna[i - 1] = (String) resultSet.getObject(i);
                    } catch (Exception e) {
                        fila.columna[i - 1] = String.valueOf(resultSet.getObject(i));
                    }
                }
                filas.add(fila);
            }
                datos = new Object[filas.size()][numberOfColumns];
                for (int i = 0; i < filas.size(); i++) {
                    for (int j = 0; j < numberOfColumns; j++) {
                        datos[i][j] = filas.get(i).columna[j];
                    }
                }
            return datos;
        } catch (SQLException ex) {
             String m=ex.getMessage();
            JOptionPane.showMessageDialog(null, m);
            return datos;
        }
    }
}