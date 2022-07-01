/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paquetePrueba;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Roderick
 */
public class CNNProcess {
    
    public int[] arrayKernel;
    
    public CNNProcess(int ... a){ //CNNProcess p = new CNNProcess(3,5)
        arrayKernel = a;
    }
    
    public double [] procesamiento (double [][] m, int stride){
        
        ArrayList<double[][]> output_conv = new ArrayList<>();
        ArrayList<double[][]> output_final = null;
        ArrayList<double[][]> kernels = read_kernel("kernel.txt");
        
        int xdim=0, ydim=0;
        for(int i=0;i<arrayKernel.length;i++){
            output_conv.clear();
            
            for(int j=0;j<arrayKernel[i];j++){
                double[][] temp = null;
                if(i==0){
                    temp = convolucion(padding(m, 1),kernels.get(j) , stride);
                    output_conv.add(Relu(maxPooling(temp,2)));
                    
                    
                }else{
                    temp = new double[xdim][ydim];
                    
                    for (double[][] actual: output_final) {
                        double[][] algotemp = convolucion(padding(actual,1), kernels.get(j+ sumatotal(arrayKernel, i)), stride);
                        
                        temp = sumaMatrices(temp, algotemp);
                        
                        
                    }
                    output_conv.add(Relu(maxPooling(temp,2)));
                }
            }
            output_final = new ArrayList<>(output_conv);
            xdim = output_conv.get(0).length;
            ydim = output_conv.get(0)[0].length;
            
        }
        return flater(output_final);
    }
    
    public int sumatotal(int [] arr, int ind){
        int suma = 0;
        for(int i=0;i<ind;i++){
            suma += arr[i];
        }
        return suma;
    }
    
    public double [] normalizador(double [] p){
        double[] res = new double[p.length];
        double max = -1000000;
        
        for (int i = 0; i < p.length; i++) {
            if(max<p[i]) max=p[i];
        }
        
        for (int i = 0; i < p.length; i++) {
            res[i] = p[i]/max;
        }
        
        return res;
    }
    
    public double[][] sumaMatrices(double[][] A, double[][] B) {  
      double[][] C = new double[B.length][B[0].length]; 

      for (int i = 0; i < A.length; i++) 
          for (int j = 0; j < A[0].length; j++) 
              C[i][j] = A[i][j] + B[i][j]; 

      return C; 
    }
    
    public double[] flater(ArrayList<double[][]> imagenes){
        int tamanio = imagenes.size()*imagenes.get(0).length*imagenes.get(0)[0].length;
        double[] entrada = new double[tamanio];
        for(int i=0;i<imagenes.size();i++){
            int index = i*imagenes.get(0).length*imagenes.get(0)[0].length;
            for(int j=0;j<imagenes.get(0).length;j++){
                for(int k=0;k<imagenes.get(0)[0].length;k++ ){
                    //System.out.println(index+j*(imagenes.get(0).length > imagenes.get(0)[0].length ? imagenes.get(0)[0].length:imagenes.get(0).length) +k);
                    entrada[index+j*(imagenes.get(0).length > imagenes.get(0)[0].length ? imagenes.get(0)[0].length:imagenes.get(0).length) +k] = imagenes.get(i)[j][k];
                }
            }
        }
        return entrada;
  }
    
    
    public double [][] maxPooling(double [][] m, int dim){
    //TODO: generalizar
    
    int filas = m.length, columnas = m[0].length;
    
    if ((filas%dim) != 0 || (columnas%dim) != 0 ) {
        int resto = filas%dim;
        if(resto%2==0){
            double[][] m1 = padding(m, resto/2);
            int f1 = m1.length, c1= m1[0].length;
            double[][] res = new double[f1/dim][c1/dim];
            
            for(int i=0;i<f1/dim;i++){
                for(int j=0;j<c1/dim;j++){
                    double maximo = -10000;  
                    for(int x=i*dim;x<i*dim+dim;x++){          
                        for(int y=j*dim;y<j*dim+dim;y++){
            
                            if(maximo < m1[x][y]) {
                                maximo = m1[x][y];
                            }
                            //System.out.println(maximo);
                        }
                    }
                    res[i][j] = maximo;
                }
            }
            return res;
        }
        else {
            double [][] m1 = paddingRight(m, (resto-1)/2);
            int f1 = m1.length, c1= m1[0].length;
            double[][] res = new double[f1/dim][c1/dim];
            
            for(int i=0;i<f1/dim;i++){
                for(int j=0;j<c1/dim;j++){
                    double maximo = -10000;  
                    for(int x=i*dim;x<i*dim+dim;x++){          
                        for(int y=j*dim;y<j*dim+dim;y++){
            
                            if(maximo < m1[x][y]) {
                                maximo = m1[x][y];
                            }
                            //System.out.println(maximo);
                        }
                    }
                    res[i][j] = maximo;
                }
            }
            return res;
        }
            
            
    }else{
        double[][] res = new double[filas/dim][columnas/dim];

    for(int i=0;i<filas/dim;i++){
      for(int j=0;j<columnas/dim;j++){
        double maximo = -10000;  
        for(int x=i*dim;x<i*dim+dim;x++){          
          for(int y=j*dim;y<j*dim+dim;y++){
            
            if(maximo < m[x][y]) {
              maximo = m[x][y];
            }
            //System.out.println(maximo);
          }
        }
        res[i][j] = maximo;
      }
    }
    
    return res;
    } 
    
  }

  public double [][] padding(double [][] m, int p){
    double [][] res = new double[m.length + 2*p][m[0].length + 2*p];
    
    int x=p;
    for(int i=0;i<m.length; i++){
        int y=p;
      for(int j=0;j<m[0].length; j++){
          res[x][y] = m[i][j];
          y++;
      }
      x++;
    }
    return res;
  }
  
  public double [][] paddingRight(double [][]m,int p){
      double[][] res = padding(m, p);
      double[][] res2 = new double[res.length+1][res[0].length+1];
      
      for (int i = 0; i <res.length; i++) {
          for (int j = 0; j < res[0].length; j++) {
              res2[i][j] = res[i][j];
          }
      }
      
      return res2;
  }
  
  public double [][] convolucion(double [][]m, double [][]k, int stride){
      int resto = (m.length-k.length)%stride;
      int pad = stride-resto;
      double[][] aux;
      if(resto!=0){
      
      if(pad%2==0){
          aux = padding(m, pad/2);
      }
      else{
          aux = paddingRight(m, (pad-1)/2);
      }
      
      int dimension = (aux.length-k.length/stride) + 1;
      double[][] res = new double[dimension][dimension];
      
      for (int i = 0*stride; i <= aux.length-k.length; i++) {
          for (int j = 0*stride; j <= aux[0].length-k[0].length; j++) {
              double conv = 0;
              for (int x = 0; x < k.length; x++) {
                  for (int y = 0; y < k[0].length; y++) {
                     conv += aux[i+x][j+y]*k[x][y];
                  }
              }
              res[i/stride][j/stride] = conv;
          }
          
      }
      return res;
      }
      else{
      int dimension = (m.length-k.length/stride) + 1;
      double[][] res = new double[dimension][dimension];
      
      for (int i = 0*stride; i <= m.length-k.length; i++) {
          for (int j = 0*stride; j <= m[0].length-k[0].length; j++) {
              double conv = 0;
              for (int x = 0; x < k.length; x++) {
                  for (int y = 0; y < k[0].length; y++) {
                     conv += m[i+x][j+y]*k[x][y];  
                  }
              }
              res[i/stride][j/stride] = conv;
          }
          
      }
      return res;
      }
  }
  
  
    public ArrayList<double[][]> read_kernel(String ruta){
        ArrayList<double[][]> lista = new ArrayList<>();
        double[][] aux;
        try (BufferedReader in = new BufferedReader( new FileReader(ruta))){ 
            String line = in.readLine(); 
            //int k=0;
            while (line != null) {                
                String [] arrStrings = line.split(",");
                //int i=0;
                int dim = (int)Math.sqrt(arrStrings.length);
                aux = new double[dim][dim];
                //matrizLectura[k] = new double[arrStrings.length];
        
                //for(String s: arrStrings){
                    //aux[k][i] = Double.parseDouble(s);
                    //matrizLectura[k][i] = Double.parseDouble(s);
                    //i++;
                //}
                
                for (int x = 0; x < dim; x++) {
                    for (int y = 0; y < dim; y++) {
                        aux[x][y] = Double.parseDouble(arrStrings[x*dim+y]);
                    }
                    
                }
                lista.add(aux);
                line = in.readLine();
                //k++;
            }
        } catch (IOException e){
            System.out.println(e);
        }
        
        return lista;
    }
    
    public double fun_ReLU(double x) {
        if (x > 0) {
            return x;
        } else {
            return 0.0;
        }
    }

    public double[][] Relu(double[][] m) {
        double[][] res = new double[m.length][m[0].length];
        for (int i = 0; i < res.length; i++) {
            for (int j = 0; j < res[0].length; j++) {
                res[i][j] = fun_ReLU(m[i][j]);
            }
        }
        return res;
    }
    
    
  
    public static void main(String[] args) {
        CNNProcess proces = new CNNProcess();
        double [][] p = new double[71][71];
        double[][]identidad = new double[6][6];
        
        for (int i = 0; i < p.length; i++) {
            for (int j = 0; j < p[0].length; j++) {
                p[i][j]=1;
            }
        }
        
        for(int i=0;i<6;i++){
	    for(int j=0;j<6;j++){
	        if(i==j) identidad[i][j]=1;
	        else identidad[i][j]=0;
	    }
	}
        
        /*for (int i = 0; i < p.length; i++) {
            for (int j = 0; j < p[0].length; j++) {
                System.out.print(Math.round(p[i][j]) + " ");
            }
            System.out.println("");
        }
        
        System.out.println("\n");
        
        double[][] res = proces.paddingRight(p, 2);
        
        for (int i = 0; i < res.length; i++) {
            for (int j = 0; j < res[0].length; j++) {
                System.out.print(Math.round(res[i][j]) + " ");
            }
            System.out.println("");
        }
        
        System.out.println("\n");
        
        double[][] res2 = proces.maxPooling(res, 10);
        
        for (int i = 0; i < res2.length; i++) {
            for (int j = 0; j < res2[0].length; j++) {
                System.out.print(Math.round(res2[i][j]) + " ");
            }
            System.out.println("");
        }*/
        
        
        /*double[][] matriz_prueba = {{0,0,0,0,0,0},
                                    {0,0,0.6,0.6,0,0},
                                    {0,0.6,0,0,0.6,0},
                                    {0,0.6,0.6,0.6,0.6,0},
                                    {0,0.6,0,0,0.6,0},
                                    {0,0,0,0,0,0}};
        
        double[][] kernel_prueba = {{1,0,-1},
                                    {2,0,-2},
                                    {1,0,-1}};
	
        for (int i = 0; i < matriz_prueba.length; i++) {
            for (int j = 0; j < matriz_prueba[0].length; j++) {
                System.out.print(Math.round(matriz_prueba[i][j]*10)+" ");
            }
            System.out.println("");
        }
        
        System.out.println("\n");
        for (int i = 0; i < kernel_prueba.length; i++) {
            for (int j = 0; j < kernel_prueba[0].length; j++) {
                System.out.print(Math.round(kernel_prueba[i][j])+" ");
            }
            System.out.println("");
        }
        
        double[][] convolucion_prueba = proces.convolucion(proces.padding(matriz_prueba,1), kernel_prueba, 1);
        
        System.out.println("\n");
        for (int i = 0; i < convolucion_prueba.length; i++) {
            for (int j = 0; j < convolucion_prueba[0].length; j++) {
                System.out.printf("%.2f ", convolucion_prueba[i][j]);
            }
            System.out.println("");
        }*/
        
        ArrayList<double[][]> ker = proces.read_kernel("kernel.txt");
        
        for(double[][] r : ker){
            for (int i = 0; i < r.length; i++) {
                for (int j = 0; j < r[0].length; j++) {
                    System.out.print(r[i][j]+ " ");
                }
                System.out.println("");
            }
            System.out.println("");
        }
        
        
        double x = (double)1/16;
        System.out.println(x);
    }
    
    
   
}
