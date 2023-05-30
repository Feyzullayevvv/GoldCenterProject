package Model;

public class Satis {
    private int nr;
    private String date;
    private int barcode;
    private double qram;
    private  double maya;

    public void setNr(int nr) {
        this.nr = nr;
    }

    public double getMaya() {
        return maya;
    }

    public void setMaya(double maya) {
        this.maya = maya;
    }

    private double mebleg;

    public int getNr() {
        return nr;
    }



    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getBarcode() {
        return barcode;
    }

    public void setBarcode(int barcode) {
        this.barcode = barcode;
    }

    public double getQram() {
        return qram;
    }

    public void setQram(double qram) {
        this.qram = qram;
    }

    public double getMebleg() {
        return mebleg;
    }

    public void setMebleg(double mebleg) {
        this.mebleg = mebleg;
    }
}
