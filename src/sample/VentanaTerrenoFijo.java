package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Pair;

import java.util.ArrayList;

public class VentanaTerrenoFijo {

    ComboBox<Image> selectorImagenes;
    boolean tieneImagenes;
    boolean confrimado;
    Image imagenSeleccionada;

    public VentanaTerrenoFijo(ArrayList<Par<Image, Boolean>> posiblesImagenes){
        selectorImagenes = crearComboBox(posiblesImagenes);
        tieneImagenes = selectorImagenes.getItems().size() > 0;
        confrimado = false;
    }

    public boolean regresaTieneImagenes(){
        return tieneImagenes;
    }

    public Image getImagenSeleccionada() {
        return imagenSeleccionada;
    }

    public void mostrarVentana(){
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(VentanaMapa.stage);
        stage.initStyle(StageStyle.UTILITY);
        stage.setTitle("Seleccionar Terreno");
        //El selector de terrenos ya fue definido en el constructor, es el combobox
        HBox layoutPrincipal = new HBox();
        HBox layoutBotones = new HBox();
        Button aceptarButton = new Button("Aceptar");
        aceptarButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                confrimado=true;
                stage.close();
            }
        });
        Button cancelarButton = new Button("Cancelar");
        cancelarButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage.close();
            }
        });
        layoutBotones.getChildren().addAll(cancelarButton, aceptarButton);
        layoutPrincipal.getChildren().addAll(selectorImagenes, layoutBotones);
        Scene escena = new Scene(layoutPrincipal, 320, 150);
        stage.setScene(escena);
        stage.showAndWait();
        imagenSeleccionada = selectorImagenes.getValue();
        //Se guarada la nueva imagen seleccionada, para poder hacer uso de ella
    }

    private ComboBox<Image> crearComboBox(ArrayList<Par<Image, Boolean>> posiblesImagenes){
        ComboBox<Image> eleccion = new ComboBox<>();
        int i;
        int tamanio = posiblesImagenes.size();
        for (i=1; i<tamanio; i++){
            if (posiblesImagenes.get(i).getValue()){
                eleccion.getItems().add(posiblesImagenes.get(i).getKey());
            }
        }
        eleccion.setButtonCell(new ImageListCell());
        eleccion.setCellFactory(listView -> new ImageListCell());
        eleccion.setMinSize(140,140);
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
