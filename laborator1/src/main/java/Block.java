import java.util.Arrays;

public class Block {

    private int dim;
    private double[][] block;
    private TypeOfBlock type;
    private int positionX;
    private int positionY;

    public Block(int dim) {
        block = new double[dim + 1][dim + 1];
        this.dim = dim;
    }

    public Block(double[][] block, TypeOfBlock type, int positionX, int positionY) {
        this.block = block;
        this.type = type;
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public double[][] getBlock() {
        return block;
    }

    public TypeOfBlock getType() {
        return type;
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setBlock(double[][] block) {
        for (int i = 1; i <= dim; i++)
            for (int j = 1; j <= dim; j++)
                this.block[i][j] = (int)block[i][j];
    }

    public void setType(TypeOfBlock type) {
        this.type = type;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public void print() {
        for (int i = 1; i <= dim; i++) {
            for (int j = 1; j <= dim; j++)
                System.out.print(this.block[i][j] + " ");
            System.out.print("\n");
        }
    }
}
