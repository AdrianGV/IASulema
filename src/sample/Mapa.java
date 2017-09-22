package sample;
import javafx.scene.canvas.GraphicsContext;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * Created by blalb on 21/09/2017.
 */
public class Mapa {

    private GraphicsContext gc;
    private int numFilas, numColumnas;

    public Mapa(GraphicsContext gc){

        this.gc = gc;
        this.numFilas = 0;
        this.numColumnas = 0;
    }

    public void leerArchivo(String fileName){
        Path path = Paths.get(fileName);
        try(Scanner scanner = new Scanner(path)){
            while (scanner.hasNextLine()){
                leerFila(scanner.nextLine());
            }
        } catch (IOException e){
            System.out.println("No se pudo leer el archivo");
        }
    }

    private void leerFila(String line){
        Scanner scanner = new Scanner(line).useDelimiter("\\s*,\\s*");
        while (scanner.hasNextInt()){
            //System.out.print(scanner.nextInt() + " ");

        }
    }
}
