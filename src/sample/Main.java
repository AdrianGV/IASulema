package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {

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
        SplitPane mainWindow = new SplitPane();
        mainWindow.setDividerPosition(0,0.7);
        //lado del mapa
        ScrollPane panelIzq = new ScrollPane();
        panelIzq.setPrefViewportHeight(bounds.getHeight());
        panelIzq.setPrefViewportWidth(bounds.getWidth()*0.7);
        Pane contenedorMapa = new Pane();
        contenedorMapa.setPrefSize(bounds.getWidth()*0.7,bounds.getHeight());
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
        TabPane infoSeres = new TabPane();
        Tab ser1 = new Tab();
        ser1.setText("ser 1");
        ser1.setContent(new Rectangle(bounds.getWidth()*0.3,bounds.getHeight()/2, Color.LIGHTSTEELBLUE));
        Tab ser2 = new Tab();
        ser2.setText("ser 2");
        ser2.setContent(new Rectangle(bounds.getWidth()*0.3,bounds.getHeight()/2, Color.LIGHTSTEELBLUE));
        infoSeres.getTabs().addAll(ser1, ser2);
        panelDerInf.setContent(infoSeres);

        panelDer.getItems().addAll(panelDerSup,panelDerInf);
        mainWindow.getItems().addAll(panelIzq,panelDer);
        primaryStage.setScene(new Scene(mainWindow, bounds.getWidth(), bounds.getHeight()));
        primaryStage.setMaximized(true);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
