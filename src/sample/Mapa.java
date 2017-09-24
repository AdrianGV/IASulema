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
    private double height, width;
    private int numFilas, numColumnas;

    //Por convencion [Filas] [Columnas]
    private int[][] matrizmapa;
    private ArrayList<Integer> conjuntoTerrenos;
    private Terreno[] listaTerrenos;

    public Mapa(GraphicsContext gc, double height, double width){

        this.gc = gc;
        this.height = height;
        this.width = width;
        this.numFilas = 0;
        this.numColumnas = 0;
        conjuntoTerrenos = new ArrayList<Integer>();
    }

    public void dibujarMapa(){
        //Supongo canvas cuadrado
        double lado = tamanioLado();
        double posX, posY;
        int i, j;
        for(i=0, posY=0; i<numFilas; i++, posY+=lado){
            for (j=0, posX=0; j<numColumnas; j++, posX+=lado){
                for (Terreno t:
                     listaTerrenos) {
                    if(t.getNumero()==matrizmapa[i][j]){
                        t.dibujarTerreno(gc, posX, posY, lado);
                    }
                }
            }
        }
    }

    private double tamanioLado(){
        double lado;
        if (numFilas>numColumnas){
            lado = width/numFilas;
        } else {
            lado = height/numColumnas;
        }
        return lado;
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
                    System.out.println("Error");
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
        int aux;
        boolean correcto = true;
        while (scanner.hasNextInt()){
            aux = scanner.nextInt();
            fila.add(aux);
            if(!conjuntoTerrenos.contains(aux)){
                conjuntoTerrenos.add(aux);
            }
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

    public int regresaNumDifTerrenos(){
        return conjuntoTerrenos.size();
    }

    public ArrayList<Integer> regresaTerrenos(){
        return conjuntoTerrenos;
    }

    public void setListaTerrenos(Terreno[] listaTerrenos) {
        this.listaTerrenos = listaTerrenos;
    }


    //Esta es la lista que tiene los terrenos (nombre, imagen y numero)
    public Terreno[] getListaTerrenos() {
        return listaTerrenos;
    }
}
