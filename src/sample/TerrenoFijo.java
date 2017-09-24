package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class TerrenoFijo extends Terreno{
    Image imagen;

    public TerrenoFijo(Image imagen){
        this.imagen = imagen;
    }

    public Image getImagen() {
        return imagen;
    }

    @Override
    public void dibujarTerreno(GraphicsContext gc, double posX, double posY, double lado) {
        gc.drawImage(this.imagen, 0,0, 128, 128, posX, posY, lado, lado);
    }
}
