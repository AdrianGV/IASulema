package sample;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Manejador_Archivo {

    public Manejador_Archivo() {

    }

    public int filas=0;
    public int columnas=0;

    public void  setfilas(int num) {
        filas=num;
    }
    public int getfilas() {
        return filas;
    }

    public void  setcolumnas(int num) {
        columnas=num;
    }
    public int getcolumnas() {
        return columnas;
    }

    //Funcion para medir el archivo para saber las dimensiones
    public void checararchivo (String ar) {
        String todo = "";
        int fil =0;
        int colu=0;
        File archivo = new File(ar);
        try {
            Scanner sc = new Scanner(archivo);
            while (sc.hasNext()) {

                todo = todo + sc.nextLine()+"-";
                fil++;

            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String []cont=todo.split("-");
        String []col =cont[0].split(",");
        colu=col.length;
        setcolumnas(colu);
        setfilas(fil);
        System.out.println(colu);
        System.out.println(fil);
    }

    // Funcion para leer el archivo 'ar' es el camino al archivo
    public String leer(String ar) {
        String todo = "";
        File archivo = new File(ar);
        try {
            Scanner sc = new Scanner(archivo);
            while (sc.hasNext()) {

                todo = todo + sc.nextLine() +",";

            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //regresa todo el archivo en un string
        return todo;
    }

    //Esta funcion regresa los numeros de manera unica osea si esta lleno de  1 y 0 lo deja un arreglo con [1,0]
    public String[] identificador(String todo) {
        todo =todo.replaceAll("\n",",");
        String Partes[] = todo.split(",");
        Set<String> mySet = new HashSet<String>(Arrays.asList(Partes));
        String[] arr = new String[mySet.size()];
        int index = 0;
        for(String i : mySet ) {
            arr[index++] = i;
        }

        return arr;
    }

}