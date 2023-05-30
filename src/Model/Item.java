package Model;

public class Item {
    private int db_number;
    private String Date;

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    private int barcode;
    private double qram;
    private String eyyar;
    private String ad;
    private double maya;

    public int getDb_number() {
        return db_number;
    }

    public void setDb_number(int db_number) {
        this.db_number = db_number;
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

    public String getEyyar() {
        return eyyar;
    }

    public void setEyyar(String eyyar) {
        this.eyyar = eyyar;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public double getMaya() {
        return maya;
    }

    public void setMaya(double maya) {
        this.maya = maya;
    }
}
