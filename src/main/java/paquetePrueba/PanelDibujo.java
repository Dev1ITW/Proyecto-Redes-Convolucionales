/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paquetePrueba;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author Roderick
 */
public class PanelDibujo extends JPanel{
    ByteArrayOutputStream byteCon;
    Date ahora;
    DateFormat defaultDate;
    String ahoraString;
    JButton boton1;
    File archivo;
    String ruta;
    byte[] img;
    FileOutputStream salida;
    
    BufferedImage image; 
    Graphics2D g2d;
    
    public PanelDibujo(){
        super();
        init();
    }
    
    private void init(){
        setLayout(null);
        setBounds(50, 50, 280, 280);
        setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.WHITE));
        image = new BufferedImage(280,280,BufferedImage.TYPE_INT_RGB);
        g2d = image.createGraphics();
        
        g2d.setColor(Color.WHITE);
        g2d.setStroke(new BasicStroke(12));
        
        MouseAdapter mouseHandler = new MouseAdapter() {
            private Point curPoint = new Point();
            
            @Override
            public void mousePressed(MouseEvent e) {
                curPoint.setLocation(e.getPoint());
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                g2d.drawLine(curPoint.x, curPoint.y, e.getX(), e.getY());
                curPoint.setLocation(e.getPoint());
                repaint();
            }
            
            
        };
        addMouseListener(mouseHandler);
        addMouseMotionListener(mouseHandler);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null);
        
    }
   
    
    public void guardarArchivo(String archivo, byte [] img){
        try {
            salida = new FileOutputStream(archivo);
            salida.write(img);
        } catch (Exception e) {
        }
    }
    
}