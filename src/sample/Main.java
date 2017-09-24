package sample;

import com.sun.org.apache.bcel.internal.generic.NEW;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.File;

public class Main extends Application {
    String[][] Matriz;
    GridPane layout = new GridPane();
    Manejador_Archivo manejador_archivo = new Manejador_Archivo();
    String []numTerrenos = null;
    InfoSeresManager infoSeres = null;
    @Override
    public void start(Stage primaryStage) throws Exception{

        //tamaño de la pantalla
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        primaryStage.setX(bounds.getMinX());
        primaryStage.setY(bounds.getMinY());
        primaryStage.setWidth(bounds.getWidth());
        primaryStage.setHeight(bounds.getHeight());
        primaryStage.setTitle("Adrian no es hombre");
        BorderPane fondo = new BorderPane();
        fondo.setPrefWidth(bounds.getWidth());
        fondo.setPrefHeight(bounds.getHeight());
        SplitPane mainWindow = new SplitPane();
        mainWindow.setDividerPosition(0,0.7);
        //menu de opciones
        MenuBar barraOpciones = inicializarBarraMenu(primaryStage);
        //lado del mapa
        ScrollPane panelIzq = new ScrollPane();
        panelIzq.setPrefViewportHeight(bounds.getHeight());
        panelIzq.setPrefViewportWidth(bounds.getWidth()*0.7);
        Pane contenedorMapa = new Pane();
        contenedorMapa.setPrefSize(bounds.getWidth()*0.7,bounds.getHeight());
        //canvas de adrian
        final Canvas mapa = new Canvas(bounds.getWidth()*0.7,bounds.getHeight());
        GraphicsContext gc = mapa.getGraphicsContext2D();
        mapa.setId("canvasMapa");//para buscarlo en otros lados usas scene.lookup("#canvasMapa");.
        contenedorMapa.getChildren().addAll(mapa);
        panelIzq.setContent(contenedorMapa);
        //lado derecho
        SplitPane panelDer = new SplitPane();
        panelDer.setPrefHeight(bounds.getHeight());
        panelDer.setPrefWidth(bounds.getWidth()*0.3);
        panelDer.setDividerPosition(0,0.5);
        panelDer.setOrientation(Orientation.VERTICAL);
        ScrollPane panelDerSup = new ScrollPane();
        panelDerSup.setPrefViewportHeight(bounds.getHeight()/2);
        panelDerSup.setPrefViewportWidth(bounds.getWidth()*0.3);
        TabPane infoMapa = new TabPane();
        Tab info1 = new Tab();
        info1.setText("info 1");
        info1.setContent(new Rectangle(bounds.getWidth()*0.3,bounds.getHeight()/2, Color.LIGHTSTEELBLUE));
        Tab info2 = new Tab();
        info2.setText("info 2");
        info2.setContent(new Rectangle(bounds.getWidth()*0.3,bounds.getHeight()/2, Color.LIGHTSTEELBLUE));
        infoMapa.getTabs().addAll(info1, info2);
        panelDerSup.setContent(infoMapa);

        ScrollPane panelDerInf = new ScrollPane();
        panelDerInf.setPrefViewportHeight(bounds.getHeight()/2);
        panelDerInf.setPrefViewportWidth(bounds.getWidth()*0.3);
        infoSeres = new InfoSeresManager(numTerrenos, bounds);
        panelDerInf.setContent(infoSeres);

        panelDer.getItems().addAll(panelDerSup,panelDerInf);
        mainWindow.getItems().addAll(panelIzq,panelDer);
        fondo.setTop(barraOpciones);
        fondo.setCenter(mainWindow);
        primaryStage.setScene(new Scene(fondo, bounds.getWidth(), bounds.getHeight()));
        primaryStage.setMaximized(true);
        primaryStage.show();
    }
    private MenuBar inicializarBarraMenu(Stage stage){
        MenuBar barraOpciones = new MenuBar();

        //menu de abrir archivo
        Menu menuArchivo = new Menu("Archivo");
        //opciones
        MenuItem abrirArchivo = new MenuItem("Abrir Archivo");
        abrirArchivo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Abrir Archivo Mapa");
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("Archivos de Texto", "*.txt")
                );
                File selectedFile = fileChooser.showOpenDialog(stage);
                if (selectedFile != null) {
                    //mapaTerreno.leerArchivo(selectedFile.getPath());
                    manejador_archivo.checararchivo(selectedFile.toString());
                    numTerrenos = manejador_archivo.identificador(manejador_archivo.leer(selectedFile.toString()));
                    String todo =manejador_archivo.leer(selectedFile.toString());
                    String[]tod=todo.split(",");
                    String []Terrenos =Escojer_Terrenos.mostrar(numTerrenos);
                    infoSeres.updateIdTerrenos(numTerrenos,Terrenos);
                    Matriz= new String[manejador_archivo.getfilas()][manejador_archivo.getcolumnas()];

                    layout.setHgap(manejador_archivo.getfilas());
                    layout.setVgap(manejador_archivo.getcolumnas());


                    for (int i=0;i<manejador_archivo.getcolumnas(); i++){
                        Label map =new Label();
                        map.setText(""+i);
                        map.setMinSize(30,30);
                        map.setStyle("-fx-border-color: black; -fx-background-color: grey;");
                        layout.add(map,0,i);
                    }

                    for (int i=0;i<=manejador_archivo.getfilas(); i++){
                        Label map =new Label();
                        map.setText(Character.toString ((char) (i+65)));

                        map.setMinSize(30,30);
                        map.setStyle("-fx-border-color: black; -fx-background-color: grey;");
                        layout.add(map,i+1,0);
                    }

                    int k=0;
                    for (int i=0; i<manejador_archivo.getfilas();i++){
                        for (int j=0; j<manejador_archivo.getcolumnas(); j++){
                            Label map =new Label();
                            map.setText(tod[k]);
                            map.setMinSize(30,30);
                            map.setStyle("-fx-border-color: black;");
                            layout.add(map,j+1,i+1);
                            Matriz[i][j]=tod[k];
                            k++;
                        }
                    }




                }
            }
        });
        menuArchivo.getItems().addAll(abrirArchivo);
        barraOpciones.getMenus().addAll(menuArchivo);
        return barraOpciones;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
