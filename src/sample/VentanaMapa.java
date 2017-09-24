package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.*;

public class VentanaMapa {

    ArrayList<TerrenoFijo> posiblesTerrenos;

    public VentanaMapa(){
        posiblesTerrenos = llenarPosiblesTerrenos();

    }

    private ArrayList<TerrenoFijo> llenarPosiblesTerrenos(){
        //Buscar donde estan las imagenes ../ para ir a la carpeta que contiene
        ArrayList<TerrenoFijo> listaPosibles = new ArrayList<>();
        File folder = new File(System.getProperty("user.dir")+"\\src\\resources\\terrenos");
        System.out.println(folder);
        File[] listOfFiles = folder.listFiles();
        if (listOfFiles!=null)
        for (int i=0; i<listOfFiles.length; i++){
            if (listOfFiles[i].isFile()) {
                listaPosibles.add(new TerrenoFijo(new Image(listOfFiles[i].toURI().toString()), listOfFiles[i].getName().replace(".png", "")));
            }
        }
        return listaPosibles;
    }

    public void abrirVentana(Mapa mapaActual){
            Stage stage = new Stage();
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(Main.primaryStage);
            stage.initStyle(StageStyle.DECORATED);
            stage.setTitle("Configuracion Mapa");
            HBox layoutPrincipal = new HBox();
            //Aqui ira cada terreno para elegir su imagen
            VBox listaTerrenos = new VBox();
            Button continuarButton = new Button("Continuar");
            crearContenedoresTierras(listaTerrenos, mapaActual);
            layoutPrincipal.getChildren().addAll(listaTerrenos, continuarButton);
            stage.setScene(new Scene(layoutPrincipal));
            stage.show();
    }

    private void crearContenedoresTierras(Pane listaTerrenos, Mapa mapaActual){
        ArrayList<Integer> numerosTerrenos = mapaActual.conjuntoTerrenos;
        int i, tamanio = mapaActual.conjuntoTerrenos.size();
        for(i=0; i<tamanio; i++){
            listaTerrenos.getChildren().add(crearContenedor(numerosTerrenos.get(i)));
        }
    }

    private Pane crearContenedor(int numeroMapa){
        HBox horizontal = new HBox();
        Label etiquetaNumero = new Label(Integer.toString(numeroMapa));
        TextField nombre = new TextField();
        ComboBox<Image> eleccion = crearComboBox();
        /*
        ComboBox<Image> eleccion = new ComboBox<>();
        Image aux = null;
        int i;
        int tamanio = posiblesTerrenos.size();
        for (i=0; i<tamanio; i++){
            if(posiblesTerrenos.get(i).getNombre()!="Otros"){
                eleccion.getItems().add(posiblesTerrenos.get(i).getImagen());
            }
            else {
                aux = posiblesTerrenos.get(i).getImagen();
            }
        }
        eleccion.setValue(aux);
        */
        nombre.setPromptText("Nombre de la Casilla");
        horizontal.getChildren().addAll(etiquetaNumero, eleccion, nombre);
        return horizontal;
    }

    private ComboBox<Image> crearComboBox(){
        ComboBox<Image> eleccion = new ComboBox<>();
        int i;
        int tamanio = posiblesTerrenos.size();
        for (i=0; i<tamanio; i++){
            if(posiblesTerrenos.get(i).getNombre()!="Otros"){
                eleccion.getItems().add(posiblesTerrenos.get(i).getImagen());
            }
        }
        eleccion.setButtonCell(new ImageListCell());
        eleccion.setCellFactory(listView -> new ImageListCell());
        eleccion.getSelectionModel().select(0);
        return eleccion;
    }

    class ImageListCell extends ListCell<Image> {
        private final ImageView view;

        ImageListCell() {
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            view = new ImageView();
        }

        @Override protected void updateItem(Image item, boolean empty) {
            super.updateItem(item, empty);

            if (item == null || empty) {
                setGraphic(null);
            } else {
                view.setImage(item);
                setGraphic(view);
            }
        }

    }


}
