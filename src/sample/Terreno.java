package sample;

import javafx.scene.canvas.GraphicsContext;

public abstract class Terreno {
    String nombre;
    int numero;

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public void setNumero(int numero){
        this.numero = numero;
    }

    public String getNombre() {
        return nombre;
    }

    public int getNumero() {
        return numero;
    }

    public abstract void dibujarTerreno(GraphicsContext gc, double posX, double posY, double lado);
}
