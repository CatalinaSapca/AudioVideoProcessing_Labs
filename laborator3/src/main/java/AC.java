public class AC extends EncodedObj{
    private int RUNLENGTH;
    private int SIZE;
    private int AMPLITUDE;

    public AC(int RUNLENGTH, int SIZE, int AMPLITUDE) {
        this.RUNLENGTH = RUNLENGTH;
        this.SIZE = SIZE;
        this.AMPLITUDE = AMPLITUDE;
    }

    public int getRUNLENGTH() {
        return RUNLENGTH;
    }

    public int getSIZE() {
        return SIZE;
    }

    public int getAMPLITUDE() {
        return AMPLITUDE;
    }

    public void setRUNLENGTH(int RUNLENGTH) {
        this.RUNLENGTH = RUNLENGTH;
    }

    public void setSIZE(int SIZE) {
        this.SIZE = SIZE;
    }

    public void setAMPLITUDE(int AMPLITUDE) {
        this.AMPLITUDE = AMPLITUDE;
    }

    @Override
    public String toString() {
        return "AC{" +
                "RUNLENGTH=" + RUNLENGTH +
                ", SIZE=" + SIZE +
                ", AMPLITUDE=" + AMPLITUDE +
                '}';
    }
}
