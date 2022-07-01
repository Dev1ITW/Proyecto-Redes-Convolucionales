/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paquetePrueba;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.sound.sampled.*;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 *
 * @author Roderick
 */
public class Fondo extends JPanel {
    
    private Clip audioClip;
    private AudioInputStream audioStream;
    
    private Image imagenFondo;
    private String rutaFondo;
    private Image imagenFondo2;
    private String rutaFondo2;
    private File archivoRutaFondo;
    PanelDibujo PD = new PanelDibujo();
    private JButton boton1;
    private JButton boton2;
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JLabel label4;
    private JLabel label5;
    private JTextField text1;
    private JTextField text2;
    private JTextField text3;
    private JTextField text4;

    @Override
    public void paint(Graphics g) {
        archivoRutaFondo = new File("");
        rutaFondo = archivoRutaFondo.getAbsolutePath() + "\\imagenfondo\\logofinal.jpg";
        imagenFondo = new ImageIcon(rutaFondo).getImage();
        rutaFondo2 = archivoRutaFondo.getAbsolutePath() + "\\imagenfondo\\unilogo4.png";
        imagenFondo2 = new ImageIcon(rutaFondo2).getImage();
            
        
        g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
        //g.drawImage(imagenFondo2, 410, 430, 130, 170, this);
            
        setOpaque(false);
            
        super.paint(g); //To change body of generated methods, choose Tools | Templates.
    }
    
    public Fondo(){
        setLayout(null);
        this.add(PD);
        Componentes();
        BotonGuardar();
        BotonLimpiar();
    }
    
    private void BotonGuardar(){
        boton1 = new JButton();
        boton1.setText("Procesar Imagen");
        boton1.setBounds(205, 460, 160, 50);
        boton1.setFont(new Font(null,1,15));
        this.add(boton1);
        ActionListener guardar = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PD.byteCon = new ByteArrayOutputStream();
                PD.archivo = new File("");
                PD.ahora = new Date();
                PD.defaultDate = DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.MEDIUM);
                PD.ahoraString = PD.defaultDate.format(PD.ahora);
                String auxString = PD.ahoraString.replace(' ', '_');
                PD.ruta = PD.archivo.getAbsolutePath() + "\\imagenes\\img_n_" + auxString.replace(':', '/').replace('/', '_') + ".jpg";
                
                try {
                    ImageIO.write(PD.image, "jpg", PD.byteCon);
                } catch (IOException ex) {
                    
                }
                PD.img = PD.byteCon.toByteArray();
                System.out.println(PD.ruta);
                PD.guardarArchivo(PD.ruta, PD.img);
                
                double[][] respuestas = new double[2][2];
                try{
                    Test prueba = new Test(245,200,100,50,10);
                    respuestas = prueba.test_unit(PD.ruta);
                }catch (IOException ex){
                }
                
                File audioFile = new File(PD.archivo.getAbsolutePath()+"\\audioimagenprocesada.wav");
                try {
                    audioStream = AudioSystem.getAudioInputStream(audioFile);
                } catch (UnsupportedAudioFileException | IOException evException) {
                    evException.printStackTrace();
                }
                AudioFormat audioFormat = audioStream.getFormat();
                var info = new DataLine.Info(Clip.class ,audioFormat);
                try {
                    audioClip = AudioSystem.getClip();
                    audioClip.open(audioStream);
                } catch (Exception eventException) {
                    eventException.printStackTrace();
                }
                
                audioClip.start();
                
                text1.setText(String.valueOf(respuestas[0][0]));//numero1
                text2.setText(String.valueOf(Math.round(respuestas[0][1]*10000)/100.0)+"%");//porcentaje1
                text3.setText(String.valueOf(respuestas[1][0]));//numero2
                text4.setText(String.valueOf(Math.round(respuestas[1][1]*10000)/100.0)+"%");//porcentaje2
                
            }
        };
        
        
        boton1.addActionListener(guardar);
    }
    
    private void BotonLimpiar(){
        boton2 = new JButton();
        boton2.setText("Limpiar Imagen");
        boton2.setBounds(105, 360, 160, 50);
        boton2.setFont(new Font(null,1,15));
        this.add(boton2);
        
        ActionListener limpiar = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                Color color = PD.g2d.getColor();
                PD.g2d.setColor(Color.BLACK);
                PD.g2d.fillRect(0, 0, 280, 280);
                PD.g2d.setColor(color);
                PD.repaint();
                text1.setText("");//numero1
                text2.setText("");//porcentaje1
                text3.setText("");//numero2
                text4.setText("");//porcentaje2
            }
            
        };
        boton2.addActionListener(limpiar);
    }
    
    private void Componentes(){
        label1 = new JLabel();
        label1.setText("Panel de Dibujo");
        label1.setBounds(100, 20, 180, 30);
        label1.setOpaque(true);
        label1.setHorizontalAlignment(SwingConstants.CENTER);
        label1.setBackground(Color.BLACK);
        label1.setForeground(Color.WHITE);
        label1.setFont(new Font(null,1,14));
        label1.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.WHITE));
        this.add(label1);
        
        label2 = new JLabel();
        label2.setText("Numero Dibujado");
        label2.setBounds(350,60,180,30);
        label2.setOpaque(true);
        label2.setHorizontalAlignment(SwingConstants.CENTER);
        label2.setBackground(Color.LIGHT_GRAY);
        label2.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.BLACK));
        this.add(label2);
        
        text1 = new JTextField();
        text1.setBounds(410, 100, 50, 30);
        text1.setHorizontalAlignment(SwingConstants.CENTER);
        text1.setFont(new Font(null,1,30));
        //text1.setText(String.valueOf(5));
        text1.setEditable(false);
        this.add(text1);
        
        label3 = new JLabel();
        label3.setText("Con un porcentaje de precisión");
        label3.setBounds(340,140,200,30);
        label3.setOpaque(true);
        label3.setHorizontalAlignment(SwingConstants.CENTER);
        label3.setBackground(Color.LIGHT_GRAY);
        label3.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.BLACK));
        this.add(label3);
        
        text2 = new JTextField();
        text2.setBounds(390, 180, 100, 30);
        text2.setHorizontalAlignment(SwingConstants.CENTER);
        text2.setFont(new Font(null,1,20));
        //text2.setText(String.valueOf(90.876)+"%");
        text2.setEditable(false);
        this.add(text2);
        
        label4 = new JLabel();
        label4.setText("Numero Dibujado");
        label4.setBounds(350,260,180,30);
        label4.setOpaque(true);
        label4.setHorizontalAlignment(SwingConstants.CENTER);
        label4.setBackground(Color.LIGHT_GRAY);
        label4.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.BLACK));
        this.add(label4);
        
        text3 = new JTextField();
        text3.setBounds(410, 300, 50, 30);
        text3.setHorizontalAlignment(SwingConstants.CENTER);
        text3.setFont(new Font(null,1,30));
        //text3.setText(String.valueOf(6));
        text3.setEditable(false);
        this.add(text3);
        
        label5 = new JLabel();
        label5.setText("Con un porcentaje de precisión");
        label5.setBounds(340,340,200,30);
        label5.setOpaque(true);
        label5.setHorizontalAlignment(SwingConstants.CENTER);
        label5.setBackground(Color.LIGHT_GRAY);
        label5.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.BLACK));
        this.add(label5);
        
        text4 = new JTextField();
        text4.setBounds(390, 380, 100, 30);
        text4.setHorizontalAlignment(SwingConstants.CENTER);
        text4.setFont(new Font(null,1,20));
        //text4.setText(String.valueOf(83.056)+"%");
        text4.setEditable(false);
        this.add(text4);
    }
}