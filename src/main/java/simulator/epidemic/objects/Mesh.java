package simulator.epidemic.objects;

public class Mesh {

    private int sizeX;
    private int sizeY;

    public Mesh(int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public void setSizeX(int sizeX) {
        this.sizeX = sizeX;
    }

    public void setSizeY(int sizeY) {
        this.sizeY = sizeY;
    }

    @Override
    public String toString() {
        return "Mesh{" +
                "sizeX=" + sizeX +
                ", sizeY=" + sizeY +
                '}';
    }
}
