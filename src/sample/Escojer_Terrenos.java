package sample;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.function.Function;


public class Escojer_Terrenos {
    static int i;
    static String op;
    static String[] Nombreterreno;
    static BufferedImage[] images;

    static ObservableList<String> options =
            FXCollections.observableArrayList(
                    "Grava","Pasto","Hojas","Hielo","Muro","Mugre","Nieve","Montaña","Piedra","Camino","Agua","Arena","Bosque","Lava");

    void loadImages() throws IOException {

        for (int i = 0; i < options.size(); i++) {
            images[i] = ImageIO.read(getClass().getResource("/Terrenos/" + options.get(i) + ".png"));
        }
    }

    private static void Funcion(Stage ventana, String []ori, ComboBox[] combo){
        int ok =0;
        String [] terr = new String[ori.length];
        int[] cont = new int[ori.length];
        for (int i = 0; i < ori.length; i++) {

            String opc = (String) combo[i].getValue();
            switch (opc){
                case "Grava":cont[i]=0; break;
                case "Pasto":cont[i]=1; break;
                case "Hojas":cont[i]=2; break;
                case "Hielo":cont[i]=3; break;
                case "Muro": cont[i]=4;break;
                case "Mugre": cont[i]=5;break;
                case "Nieve":cont[i]=6; break;
                case "Montaña":cont[i]=7; break;
                case "Piedra":cont[i]=8; break;
                case "Camino": cont[i]=9;break;
                case "Agua": cont[i]=10;break;
                case "Arena": cont[i]=11;break;
                case "Bosque": cont[i]=12;break;
                case "Lava":cont[i]=13; break;
            };


            //System.out.println(cont[i]);
            //if(cont[i]==0) {
            //  ok++;
            //}
            terr[i]=options.get(cont[i]);

        }
        if(ok==0) {
            Nombreterreno=terr;
            ventana.close();
        }

    }


    public static String[] mostrar(String []id ){
        Stage ventana= new Stage();
        ObservableList<String> options =
                FXCollections.observableArrayList(
                        "Grava","Pasto","Hojas","Hielo","Muro","Mugre","Nieve","Montaña","Piedra","Camino","Agua","Arena","Bosque","Lava");

        ComboBox[] combo = new ComboBox[id.length];
        Label[] ids = new Label[id.length];
        Label[] imgter = new Label[id.length];
        Nombreterreno = new String[id.length];

        Label labe = new Label();
        labe.setText("Id"+"\t");
        Label labe1 = new Label();
        labe1.setText("Terreno"+"\t");
        Label labe2 = new Label();
        labe2.setText("Imagen"+"\t");


        GridPane layout = new GridPane();
        layout.setHgap(2);
        layout.setVgap(3);
        ventana.initModality(Modality.APPLICATION_MODAL);
        ventana.setTitle("Escojer_Terrenos");
        //ventana.setMinWidth(300);
        layout.add(labe,1,0);
        layout.add(labe1,2,0);
        layout.add(labe2,3,0);
        for (i = 0; i < id.length; i++) {

            imgter[i] =new Label();
            combo[i] = new ComboBox();
            combo[i].setItems(options);

            combo[i].valueProperty().addListener( new ChangeListener<String>() {
                @Override public void changed(ObservableValue ov, String t, String t1){
                    // System.out.println(ov);
                    //System.out.println(t);
                    //System.out.println(t1);
                }
            });

            ids[i] = new Label(""+id[i]);
            layout.add(combo[i],2,(i+1));
            layout.add(ids[i],1,(i+1));
            layout.add(imgter[i],3,(i+1));
        }




        Button Aceptar = new Button("Confirmar");

        Aceptar.setOnAction(e ->Funcion(ventana,id,combo));
        layout.add(Aceptar,2,id.length+2);
        Scene scene = new Scene(layout);
        ventana.setScene(scene);
        ventana.showAndWait();


        return Nombreterreno;
    }




}