package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Optional;

public class InfoSeresManager extends TabPane{
    private String []idTerrenos;
    public InfoSeresManager(String []idTerr, Rectangle2D bounds){
        idTerrenos = idTerr;
        Tab ser = new Tab("Nuevo Ser");
        ser.setClosable(false);
        Button nuevo = new Button("Añadir");
        nuevo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String name = dialogPedirNombre();
                if(name != null) {
                    crearTabSer(name, bounds);
                }
            }
        });
        BorderPane cont = new BorderPane();
        cont.setPrefHeight(bounds.getHeight()/2);
        cont.setPrefWidth(bounds.getWidth()*0.3);
        cont.setCenter(nuevo);
        cont.setStyle("-fx-background-color: #a1b6e8;");
        ser.setContent(cont);
        this.getTabs().addAll(ser);
    }
    void updateIdTerrenos(String []idT){
        idTerrenos = idT;
    }
    String dialogPedirNombre(){
        final String[] nom = {null};
        TextInputDialog dialog = new TextInputDialog("Nombre del Ser");
        dialog.setTitle("Crear Nuevo Ser");
        dialog.setHeaderText("Por favor ingresa los siguientes datos.");
        dialog.setContentText("Nombre del ser:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> nom[0] = name);
        return nom[0];
    }
    void crearTabSer(String name, Rectangle2D bounds){
        Tab ser = new Tab(name);
        ser.setContent(crearListadoTerrenos(bounds));
        this.getTabs().addAll(ser);
    }
    VBox crearListadoTerrenos(Rectangle2D bounds){
        VBox listado = new VBox();
        listado.setPrefSize(bounds.getWidth()*0.3,bounds.getHeight()/2);
        for(int i = 0; i < idTerrenos.length; i++){
            HBox fila = new HBox();
            Label idT = new Label(idTerrenos[i].toString());
            Spinner costo = new Spinner();
            fila.getChildren().addAll(idT,costo);
            listado.getChildren().addAll(fila);
        }
        return listado;
    }
}
