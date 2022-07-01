/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paquetePrueba;

/**
 *
 * @author Roderick
 */
import java.util.*;
import java.io.IOException;
import java.io.File;
import java.io.*; 

public class RN{

  public int[] array;
  public double[][] input;
  public double[][] output;

  public ArrayList<double[][]> pesos = new ArrayList<double[][]>();
  public ArrayList<double[]> nodos = new ArrayList<double[]>();
  public ArrayList<double[]> errores = new ArrayList<double[]>();

  final Random rand = new Random();

  double getRandom() {
		return (rand.nextDouble() * 2 - 1); // [-1;1[
  }
  
  public double sigmoidea(double x){
    return 1/(1+Math.exp(-x));
  }

  
  public double[][] randPeso(int fila,int columna){
    double[][] m = new double[fila][columna];
    //double[][] m = {{1,1},{2,1},{-2,3}};
    for(int i=0;i<fila;i++){
      for(int j=0;j<columna;j++){
          m[i][j] = getRandom();
      }
    }
    return m;
  }

  public double[][] matrizSigmoidea(double[][] m){
    double[][] m2 = new double[m.length][m[0].length];
    for(int i=0;i<m.length;i++){
      for(int j = 0; j<m[0].length;j++){
        m2[i][j] = sigmoidea(m[i][j]);
      }
    }
    return m2;
  }

  
  public double[][] matrizPeso(File peso){
    
    FileInputStream fis = null;
    DataInputStream entrada = null;
    double [][] matrizError = {{0,0,0},{0,0,0}};
    try {
        fis = new FileInputStream(peso);
        entrada = new DataInputStream(fis);
        int filas = entrada.readInt();            //se lee el primer entero del fichero                           
        int columnas = entrada.readInt();
        //System.out.println(filas+" "+columnas);
        double [][] matriz = new double[filas][columnas];         //se lee el segundo entero del fichero
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {  // se leen los double y se guarda en una matriz                
                matriz[i][j] = entrada.readDouble();
            }
        }
        return matriz;
    } catch (FileNotFoundException e) {
        System.out.println(e.getMessage());
    } catch (EOFException e) {
        System.out.println("Fin de fichero");
    } catch (IOException e) {
        System.out.println(e.getMessage());
    } finally {
        try {
            if (fis != null) {
                fis.close();
            }
            if (entrada != null) {
                entrada.close();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());                                                               
        }
    }
    return matrizError;
  }

  public void read_pesos(){
    for(int i=0;i<array.length - 1;i++){
      File f1 = new File("");
      File fiche = new File(f1.getAbsolutePath()+"\\w"+(i+1)+".dat");
      pesos.add(matrizPeso(fiche));
    }
  }

  public RN(Boolean leer, int ...a){ // Spreat operator
    this.array = a;

    if(!leer){
      //System.out.println("Entre random ");
      for(int i=0;i<a.length-1;i++){
        this.pesos.add(randPeso(a[i], a[i+1]));
      }
    }else{
      read_pesos();
    }
  }


  public double[][] dotArMa(double[] a, double[][] b){
    
    int fil_a = 1;
    int col_a = a.length;
		
    int fil_b = b.length;
    int col_b = b[0].length;
    
    if (col_a != fil_b)
      throw new RuntimeException("No se pueden multiplicar dotArMa las matrices");

    double[][] multiplicacion = new double[fil_a][col_b]; // c

    for (int i = 0; i < fil_a; i++) {
      for (int j = 0; j < col_b; j++) {
        for (int k = 0; k < col_a; k++) {
          multiplicacion[i][j] += a[k] * b[k][j];
        }
      }
    }
    return multiplicacion;
  }

  public double[][] dotMaAr(double[][] a, double[] b){
    
    int fil_a = a.length;
    int col_a = a[0].length;

    int fil_b = b.length;
    int col_b = 1;
    
    if (col_a != fil_b)
      throw new RuntimeException("No se pueden multiplicar dotMaAr las matrices");

    double[][] multiplicacion = new double[fil_a][col_b]; // c

    for (int i = 0; i < fil_a; i++) {
      for (int j = 0; j < col_b; j++) {
        for (int k = 0; k < col_a; k++) {
          multiplicacion[i][j] += a[i][k] * b[k];
        }
      }
    }

    return multiplicacion;
  }

  public double[] SumaArrays(double[] a1, double[] a2){
    double[] suma = new double[a1.length];
    if(a1.length != a2.length)
      throw new RuntimeException("No se pueden sumar los vectores.");
    
    for(int i=0;i<a1.length;i++){
      suma[i] = a1[i] + a2[i];
    }
    return suma;
  }

  public double[] restaArrays(double[] a1, double[] a2){
    double[] resta = new double[a1.length];
    if(a1.length != a2.length)
      throw new RuntimeException("No se pueden restar los vectores.");
    
    for(int i=0;i<a1.length;i++){
      resta[i] = a1[i] - a2[i];
    }
    return resta;
  }

  public double[][] prodEsc(double a, double[][] m){
    double [][] producto = new double[m.length][m[0].length];
    for(int i=0;i<m.length;i++){
      for(int j = 0;j<m[0].length;j++){
        producto[i][j] = m[i][j]* a;
      }
    }
    return producto;
  }

  public double[] prodArrays(double[] a1, double[] a2){
    double[] prod = new double[a1.length];
    if(a1.length != a2.length)
      throw new RuntimeException("No se pueden mult los vectores.");
    
    for(int i=0;i<a1.length;i++){
      prod[i] = a1[i] * a2[i];
    }
    return prod;
  }
  
  public double[] sumEscalarArray(double num,double[] a){
    double[] m = new double[a.length];
    for(int i = 0;i<a.length;i++){
      m[i] = num - a[i];
    }
    return m;
  }


  public double[][] sumMatriz(double[][] a, double[][] b){
    if(a.length != b.length  || a[0].length != b[0].length)
      throw new RuntimeException("No se pueden sumar las matrices.");
    double [][] suma = new double[a.length][a[0].length];
    for(int i=0;i<a.length;i++){
      for(int j = 0;j<a[0].length;j++){
        suma[i][j] = a[i][j] + b[i][j];
      }
    }
    return suma;
  
  }

  public double[][] dotArray(double[] x, double[] e){
    int fil_x = x.length;
    int col_e = e.length;
    
    double[][] multiplicacion = new double[fil_x][col_e];
    for (int i = 0; i < fil_x; i++) {
      for (int j = 0; j < col_e; j++) {
        multiplicacion[i][j] += x[i] * e[j];
      }
    }

    return multiplicacion;
  }

  public static double[][] transpuesta(double[][] m){
    double[][] mT = new double[m[0].length][m.length];
    for (int x=0; x < m.length; x++) {
      for (int y=0; y < m[x].length; y++) {
        mT[y][x] = m[x][y];
      }
    }
    return mT;
  }


  public void entrenar(int i){
    errores.clear();
    nodos.clear();
    nodos.add(input[i]);

    for(int ii=1;ii<array.length;ii++){
      nodos.add(
        matrizSigmoidea(dotArMa(nodos.get(ii-1),
        pesos.get(ii-1)))[0]);
    }
    
    errores.add(prodArrays(prodArrays(restaArrays(output[i], nodos.get(array.length-1)),nodos.get(array.length-1)),sumEscalarArray(1,nodos.get(array.length-1))));
    
    
    for(int ii=1;ii<array.length-1;ii++){
      errores.add(
        prodArrays(
          transpuesta(
            dotMaAr(pesos.get(pesos.size()-ii),
                    errores.get(ii-1))
          )[0],
          prodArrays(
            nodos.get(array.length-1-ii),//y'(1-y')
            sumEscalarArray(
              1,
              nodos.get(array.length-1-ii)
            )
          )
        )
      );
    }
    
    for(int ii = 0; ii<pesos.size();ii++){
     
      pesos.set(ii,
        sumMatriz(
          pesos.get(ii),
          prodEsc(0.05, //0.1 0.2 0.05 0.15
            dotArray(
              nodos.get(ii),
              errores.get(errores.size()-1-ii)
            )
          )
        )
      );
    }

  }

  public double[][] read_file(String rutaFile,int x){
        
    double[][] matrizLectura = new double[x][];
          
    try (BufferedReader in = new BufferedReader( new FileReader(rutaFile))){ 
      String line = in.readLine(); 
      int k=0;
      while (line != null) {                
        String [] arrStrings = line.split(",");
        int i=0;
        matrizLectura[k] = new double[arrStrings.length];
        
        for(String s: arrStrings){
          matrizLectura[k][i] = Double.parseDouble(s);
          i++;
        }
        line = in.readLine();
        k++;
      }
    } catch (IOException e){
      System.out.println(e);
    }
          
    return matrizLectura;
  }

  public void entrenamiento_file(String ruta_x, String ruta_y, int veces){
    this.input = read_file(ruta_x,200); // [10000][784]
    this.input = reescalado(input);
    this.output = read_file(ruta_y,200); // [10000][10]
    
    double maxX = maximoMatriz(input);
    double maxY = maximoMatriz(output);
    
      System.out.println("max X: "+maxX);
      System.out.println("max Y: "+maxY);

    // System.out.println("Train");
    // System.out.println(this.input.length);
    // System.out.println(this.input[0].length);
    
    // System.out.println("Test");
    // System.out.println(this.output.length);
    // System.out.println(this.output[0].length);
    for(int v=0; v<veces;v++){ //numero de epocas 
      System.out.println("Epoca: " + v);
      for(int i=0;i<input.length;i++){
        entrenar(i);
      }
    
    }
  }
  
  public double maximoMatriz(double [][]m){
      double[][] max = new double[m.length][m[0].length];
      double maximo = -1000000;
      for (int i = 0; i < m.length; i++) {
          for (int j = 0; j < m[0].length; j++) {
              if(maximo<m[i][j]) maximo = m[i][j];
          }
      }
      
      
      
      return maximo;
  }
  
  public double [][] reescalado (double [][] matrix){
      double[][] aux = new double[matrix.length][matrix[0].length];
      for (int i = 0; i < matrix.length; i++) {
          for (int j = 0; j < matrix[0].length; j++) {
              aux[i][j] = matrix[i][j]/122;
          }
      }
      
      return aux;
  }

  public void guardaPesos(){
    for(int i=0;i<array.length-1;i++){
      FileOutputStream fos = null;
      DataOutputStream salida = null;
      
      int filas = pesos.get(i).length;
      int columnas = pesos.get(i)[0].length;

      try {
        //crear el fichero de salida
        File f1 = new File("");
        File fiche = new File(f1.getAbsolutePath()+"\\w"+(i+1)+".dat");
        fos = new FileOutputStream(fiche);
        salida = new DataOutputStream(fos);

        //escribir el número de filas y columnas en el fichero                                                
        salida.writeInt(filas);
        salida.writeInt(columnas);
      
      //escribir la matriz en el fichero
        for (int ii = 0; ii < filas; ii++) {
            for (int jj = 0; jj < columnas; jj++) {
                salida.writeDouble(pesos.get(i)[ii][jj]);
            }
        }
      } catch (FileNotFoundException e) {
          System.out.println(e.getMessage());
      } catch (IOException e) {
          System.out.println(e.getMessage());                                                                   
      } finally {
        try {
            if (fos != null) {
                fos.close();
            }
            if (salida != null) {
                salida.close();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
      }
    }
    System.out.println("Pesos Guardados");
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
    
    public double[] dReLu(double [] m){
        double[] dr = new double[m.length];
        for(int i =0;i<m.length;i++){
            if(m[i] < 0){
                dr[i] = 0.0;
            }else{
                dr[i] = 1.0;
            } 
        }
        return dr;
  }
}