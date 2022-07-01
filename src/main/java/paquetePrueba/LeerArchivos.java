/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paquetePrueba;

import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author Roderick
 */
public class LeerArchivos {
    
    public ArrayList<String> leerCarpeta(File rutaFile) {
        ArrayList<String> Elementos = new ArrayList<>();
        
        for(File archivoFile : rutaFile.listFiles()){
            if(!archivoFile.isDirectory()){
                Elementos.add(archivoFile.getName());
            }
            else{
                leerCarpeta(archivoFile);
            }
        }
        
        return Elementos;
        
}
    public void mostrarArray (ArrayList<String> arraynombre){
        for(String s : arraynombre){
            System.out.println(s);
        }
    }
}
