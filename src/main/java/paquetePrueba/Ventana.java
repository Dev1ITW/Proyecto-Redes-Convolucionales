/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paquetePrueba;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Roderick
 */
public class Ventana extends JFrame{
    
    JPanel j2 = new Fondo();
    public Ventana(){
        
        init();
    }
    
    private void init(){
        setTitle("DrawApp");
        setMinimumSize(new Dimension(600,650));
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //setLayout(null);
        setContentPane(j2);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
