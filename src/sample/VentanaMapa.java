package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.*;
import javafx.util.Pair;

public class VentanaMapa {

    Terreno[] listaTerrenos;
    ArrayList<Integer> posiciones;
    ArrayList<Par<Image, Boolean>>  imagenesDisponibles;
    int numImagenes;
    private Scene escena;
    public static Stage stage;

    public VentanaMapa(){
        imagenesDisponibles = llenarPosiblesImagenes();
    }

    private ArrayList<Par<Image, Boolean>> llenarPosiblesImagenes(){
        ArrayList<Par<Image, Boolean>> posiblesImagenes = new ArrayList<>();
        File folder = new File(System.getProperty("user.dir")+"\\src\\resources\\terrenos");
        File[] listOfFiles = folder.listFiles();
        if (listOfFiles!=null){
            for (int i=0; i<listOfFiles.length; i++){
                if (listOfFiles[i].isFile()) {
                    posiblesImagenes.add(new Par<>(new Image(listOfFiles[i].toURI().toString()),true));
                }
            }
        }
        return posiblesImagenes;
    }

    public void abrirVentana(Mapa mapaActual){
        listaTerrenos = new Terreno[mapaActual.regresaNumDifTerrenos()];
        posiciones = mapaActual.regresaTerrenos();
        stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(Main.primaryStage);
        stage.initStyle(StageStyle.DECORATED);
        stage.setTitle("Configuracion Mapa");
        HBox layoutPrincipal = new HBox();
        //Aqui ira cada terreno para elegir su imagen
        ScrollPane listaTerrenos = new ScrollPane();
        Button continuarButton = new Button("Continuar");
        crearContenedoresTierras(listaTerrenos, mapaActual);
        layoutPrincipal.getChildren().addAll(listaTerrenos, continuarButton);
        escena = new Scene(layoutPrincipal);
        stage.setScene(escena);
        stage.showAndWait();
    }

    private void crearContenedoresTierras(ScrollPane listaTerrenos, Mapa mapaActual){
        ArrayList<Integer> numerosTerrenos = mapaActual.regresaTerrenos();
        VBox vertical = new VBox();
        int i, tamanio = mapaActual.regresaNumDifTerrenos();
        for(i=0; i<tamanio; i++){
            vertical.getChildren().add(crearContenedor(numerosTerrenos.get(i)));
        }
        listaTerrenos.setContent(vertical);
    }

    private Pane crearContenedor(int numeroMapa){
        HBox horizontal = new HBox();
        Label etiquetaNumero = new Label("Numero en Mapa: "+Integer.toString(numeroMapa));
        TextField nombre = new TextField();
        nombre.setId(Integer.toString(numeroMapa)+"Nombre");
        Canvas muestraTerreno = new Canvas(128, 128);
        muestraTerreno.setId(Integer.toString(numeroMapa)+"Canvas");
        Button agregarNuevo = new Button("Agregar Nuevo Terreno");
        Button seleccionarExistente = new Button("Seleccionar Terreno existente");
        VBox vertical = new VBox();
        //Texto que aparecera antes de que escriban algo
        nombre.setPromptText("Nombre de la Casilla");
        //Damos una accion a Agregar Nuevo, en este caso abrira una ventana para definir un color
        agregarNuevo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {


            }
        });
        seleccionarExistente.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                VentanaTerrenoFijo vtf = new VentanaTerrenoFijo(imagenesDisponibles);
                if(vtf.tieneImagenes){
                    vtf.mostrarVentana();
                    if(vtf.confrimado){
                        Canvas cv = (Canvas)escena.lookup("#"+numeroMapa+"Canvas");
                        GraphicsContext gc = cv.getGraphicsContext2D();
                        gc.drawImage(vtf.imagenSeleccionada, 0, 0);
                        //Si tenia una imagen ya que la libere
                        if(null!=listaTerrenos[posiciones.indexOf(numeroMapa)]){
                            for (Par<Image, Boolean> parImagen:
                                    imagenesDisponibles) {
                                if(parImagen.getKey()==((TerrenoFijo)listaTerrenos[posiciones.indexOf(numeroMapa)]).getImagen()){
                                    parImagen.setValue(true);
                                    break;
                                }
                            }
                        }
                        //Guardar la imagen en el nuevo terreno
                        listaTerrenos[posiciones.indexOf(numeroMapa)] = new TerrenoFijo(vtf.imagenSeleccionada);
                        //Apartar la imagen para el terreno
                        for (Par<Image, Boolean> parImagen:
                             imagenesDisponibles) {
                            if(parImagen.getKey()==vtf.getImagenSeleccionada()){
                                parImagen.setValue(false);
                                break;
                            }
                        }
                    }
                } else {
                    Alert errorNoHayMas = new Alert(Alert.AlertType.ERROR);
                    errorNoHayMas.setTitle("No hay mas imagenes predefinidas");
                    errorNoHayMas.setHeaderText("No hay mas imagenes predefinidas");
                    errorNoHayMas.setContentText("Seleccione la otra opcion");
                }
            }
        });
        //Agregamos los elementos a un layout para que se vean
        vertical.getChildren().addAll(etiquetaNumero, nombre, seleccionarExistente, agregarNuevo);
        horizontal.getChildren().addAll(muestraTerreno, vertical);
        return horizontal;
    }
}
