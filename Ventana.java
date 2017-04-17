/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bd_departamentos;

import javax.swing.JFrame;

/**
 *
 * @author prg
 */
public class Ventana extends JFrame{
    
    public Ventana(Panel p) {
        
        this.setContentPane(p);
        
        this.setTitle("BD_Departamentos");
        
        //Poner propiedades a la ventana.
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //this es el Jframe, como cierra
        this.setSize(300, 200); //Tamaño
        this.setVisible(true); //¿Visibilidad?
        
    }
}
