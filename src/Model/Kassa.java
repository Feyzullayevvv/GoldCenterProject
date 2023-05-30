package Model;

public class Kassa {
    private String date;
    private String type;

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String qeyd;
    private double mebleg;

    public String getDate() {
        return date;
    }



    public String getQeyd() {
        return qeyd;
    }

    public void setQeyd(String qeyd) {
        this.qeyd = qeyd;
    }

    public double getMebleg() {
        return mebleg;
    }

    public void setMebleg(double mebleg) {
        this.mebleg = mebleg;
    }
}
