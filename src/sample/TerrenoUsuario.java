package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class TerrenoUsuario extends Terreno{
    //En Hexa
    Color rgb;

    public TerrenoUsuario(double r, double g, double b){
        rgb = Color.color(r, g, b);
    }

    @Override
    public void dibujarTerreno(GraphicsContext gc, int posX, int posY, int lado) {
        gc.setFill(rgb);
        gc.fillRect(posX, posY, lado, lado);
    }
}
