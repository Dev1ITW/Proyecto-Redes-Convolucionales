/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paquetePrueba;

import java.io.File;
import java.io.IOException;

/**
 *
 * @author Roderick
 */
public class Principal {
    public static void main(String[] args) throws IOException {
        //ImagenMatriz im = new ImagenMatriz();
        //im.guardarArchivo(im.aplanarMatriz(im.leerMatriz()));
        //im.leerMatriz("train/0/img_1.jpg");
        
        Ventana v1 = new Ventana();
        /*LeerArchivos LA = new LeerArchivos();
        File archivoprueba = new File("");
        String rutaarchivoprueba = archivoprueba.getAbsolutePath() + "\\imagenes";
        File archivofinal = new File(rutaarchivoprueba);
        LA.mostrarArray(LA.leerCarpeta(archivofinal));*/
        
        /*Ejecutar ejec = new Ejecutar();
        ejec.executeTrain();
        ejec.executeTest();*/
        
        /*RN prob = new RN(true,245,200,100,50,10);
        prob.entrenamiento_file("train_x_cnn_3.csv", "train_y_cnn_3.csv", 300);
        prob.guardaPesos();
        
        Test prueba = new Test(245,200,100,50,10);
        System.out.printf("%.2f\n",prueba.accuracy("test_x_cnn_3.csv","test_y_cnn_3.csv",100));
        System.out.printf("%.2f\n",prueba.accuracy("train_x_cnn_3.csv","train_y_cnn_3.csv",200));
        //prueba.mostrarPrimerPeso();
        //prueba.mostrarSalidas();
        
        /*CNNProcess cnn = new CNNProcess(3,5);
        double[] aux = cnn.procesamiento(im.leerMatriz("train/0/img_1.jpg"), 1);
        for (int i = 0; i < aux.length; i++) {
            System.out.printf("%.3f   ",aux[i]);
        
        }
        System.out.println("\n"+aux.length);*/
        //cnn.procesamiento(im.leerMatriz("train/0/img_1.jpg"), 1);
    }
}
