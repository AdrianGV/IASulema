package sample;
import javafx.scene.canvas.GraphicsContext;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by blalb on 21/09/2017.
 */
public class Mapa {

    private GraphicsContext gc;
    private int numFilas, numColumnas;
    //Por convencion [Filas] [Columnas]
    private int[][] matrizmapa;

    public Mapa(GraphicsContext gc){

        this.gc = gc;
        this.numFilas = 0;
        this.numColumnas = 0;
    }

    public boolean leerArchivo(String fileName){
        Path path = Paths.get(fileName);
        int contFilas = 0;
        ArrayList<ArrayList<Integer>> matriz = new ArrayList<ArrayList<Integer>>();
        try(Scanner scanner = new Scanner(path)){
            while (scanner.hasNextLine()){
                ArrayList<Integer> fila = new ArrayList<Integer>();
                //Si no coinciden las columnas
                if(!leerFila(scanner.nextLine(), fila)){
                    //Termina de buscar, no funcionan las columnas, no hay necesidad de buscar mas
                    return false;
                }
                matriz.add(fila);
                contFilas++;
            }
            numFilas = contFilas;
        } catch (IOException e){
            System.out.println("No se pudo leer el archivo");
        }
        matrizmapa = llenarMatrizMapa(matriz);
        return true;
    }

    private boolean leerFila(String line, ArrayList<Integer> fila){
        Scanner scanner = new Scanner(line).useDelimiter("\\s*,\\s*");
        int contColumnas = 0;
        boolean correcto = true;
        while (scanner.hasNextInt()){
            //System.out.print(scanner.nextInt() + " ");
            fila.add(scanner.nextInt());
            contColumnas++;
        }
        if(numColumnas!=0){
            if(contColumnas!=numColumnas){
                correcto = false;
            }
        } else {
            numColumnas = contColumnas;
        }
        return correcto;
    }

    private int[][] llenarMatrizMapa(ArrayList<ArrayList<Integer>> matrizEnLista){
        int[][] matriz = new int[numFilas][numColumnas];
        int i, j;
        for (i=0; i<numFilas; i++){
            for (j=0; j<numColumnas; j++){
                matriz[i][j] = (matrizEnLista.get(i)).get(j);
            }
        }
        return matriz;
    }
}
