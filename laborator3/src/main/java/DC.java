public class DC extends EncodedObj{
    private int SIZE;
    private int AMPLITUDE;

    public DC(int SIZE, int AMPLITUDE) {
        this.SIZE = SIZE;
        this.AMPLITUDE = AMPLITUDE;
    }

    public int getSIZE() {
        return SIZE;
    }

    public int getAMPLITUDE() {
        return AMPLITUDE;
    }

    public void setSIZE(int SIZE) {
        this.SIZE = SIZE;
    }

    public void setAMPLITUDE(int AMPLITUDE) {
        this.AMPLITUDE = AMPLITUDE;
    }

    @Override
    public String toString() {
        return "DC{" +
                "SIZE=" + SIZE +
                ", AMPLITUDE=" + AMPLITUDE +
                '}';
    }
}
