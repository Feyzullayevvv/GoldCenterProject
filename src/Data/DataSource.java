package Data;

import Model.Item;
import Model.Kassa;
import Model.Satis;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DataSource {
    private Connection connection;

    private PreparedStatement queryitemPrep;

    private PreparedStatement insertItemPrep;

    private PreparedStatement deleteItemPrep;
    private PreparedStatement insertPayment;
    private PreparedStatement insertSatis;

    private PreparedStatement increaseCashier;
    private PreparedStatement decreaseCashier;

    private PreparedStatement getItempricePRep;
    private PreparedStatement queryKassa;
    private PreparedStatement queryItemallinfo;
    private static final String DB_NAME="cakcuk.db";
    private static final String DB_PATH="jdbc:sqlite:";
    private static final String CONNECTION_STRING=DB_PATH+DB_NAME;


    private static final String TABLE_ANBAR= "Anbar";
    private static final String COLUMN_ANBAR_DATE="date";
    private static final String COLUMN_ANBAR_NR="nr";
    private static final String COLUMN_ANBAR_BARCODE="barcode";
    private static final String COLUMN_ANBAR_QRAM="qram";
    private static final String COLUMN_ANBAR_EYAR="eyar";
    private static final String COLUMN_ANBAR_AD="ad";
    private static final String COLUMN_ANBAR_MAYA="maya";

    private static final String TABLE_SATIS="Satis";
    private static final String COLUMN_SATIS_DATE="date";
    private static final String COLUMN_SATIS_NR="nr";
    private static final String COLUMN_SATIS_BARCODE="barcode";
    private static final String COLUMN_SATIS_QRAM="qram";

    private static final String COLUMN_SATIS_MAYA="maya";
    private static final String COLUMN_SATIS_MEBLEG="mebleg";


    private static final String TABLE_ALINANMALLAR="alinanMallar";
    private static final String COLUMN_ALINANMAL_NR="nr";
    private static final String COLUMN_ALINANMALLAR_DATE="date";
    private static final String COLUMN_ALINANMALLAR_BARCODE="barcode";
    private static final String COLUMN_ALINANMALLAR_QRAM="qram";
    private static final String COLUMN_ALINANMALLAR_EYAR="eyar";
    private static final String COLUMN_ALINANMALLAR_MAYAQIYMET="maya_qiymet";


    private static final String TABLE_KASSA="kassa";
    private static final String COLUMN_KASSA_DATE="date";
    private static final String COLUMN_KASSA_TYPE="type";
    private static final String COLUMN_KASSA_QEYD="Qeyd";
    private static final String COLUMN_KASSA_MEBLEG="mebleg";


    private static final String TABLE_CASHIER="cashier";
    private static final String COLUMN_CASHIER_TOTAL="total_money";


    private static final String QUERY_ITEMS="SELECT " + COLUMN_ANBAR_NR + " FROM " + TABLE_ANBAR + " WHERE " +
            COLUMN_ANBAR_BARCODE + " =?";





    private static final String INSERT_ITEM="INSERT INTO " + TABLE_ANBAR + '(' + COLUMN_ANBAR_DATE + "," +
            COLUMN_ANBAR_BARCODE + ", " + COLUMN_ANBAR_QRAM + ", " + COLUMN_ANBAR_EYAR + ", " + COLUMN_ANBAR_AD +", " +
            COLUMN_ANBAR_MAYA + ')'  + " VALUES(?,?,?,?,?,?)";

    private static final String DELETE_ITEM="DELETE FROM " + TABLE_ANBAR + " WHERE " + COLUMN_ANBAR_NR + " = ?";

    private static final String INSERT_PAYMENT="INSERT INTO " + TABLE_KASSA + '(' + COLUMN_KASSA_DATE +", "+
            COLUMN_KASSA_TYPE + ", " + COLUMN_KASSA_MEBLEG +", " + COLUMN_KASSA_QEYD+')' + " VALUES(?,?,?,?)";

    private static final String INCREASE_Cashier= "UPDATE " + TABLE_CASHIER + " SET " + COLUMN_CASHIER_TOTAL + " = " + COLUMN_CASHIER_TOTAL + " + ?";
    private static final String DECREASE_Cashier= "UPDATE " + TABLE_CASHIER + " SET " + COLUMN_CASHIER_TOTAL + " = " + COLUMN_CASHIER_TOTAL + " - ?";

    private static final String GETITEM_PRICE="SELECT " + COLUMN_ANBAR_MAYA + " FROM " + TABLE_ANBAR + " WHERE " + COLUMN_ANBAR_NR
            + " = ?";

    private static final String INSERT_SATIS="INSERT INTO " + TABLE_SATIS + '(' + COLUMN_SATIS_NR +", "+  COLUMN_SATIS_DATE +", "
            + COLUMN_SATIS_BARCODE + ", " + COLUMN_SATIS_QRAM + ", "+COLUMN_SATIS_MAYA+", " + COLUMN_SATIS_MEBLEG + ')' +
            " VALUES (?,?,?,?,?,?)";

    private static final String QUERY_KASSA="SELECT " + COLUMN_KASSA_DATE + ", " + COLUMN_KASSA_TYPE + ", " + COLUMN_KASSA_MEBLEG+
            ", "+ COLUMN_KASSA_QEYD + " FROM " + TABLE_KASSA + " WHERE " + COLUMN_KASSA_DATE + " =?";

    private static final String QUERY_CASHIER="SELECT " + COLUMN_CASHIER_TOTAL + " FROM " +  TABLE_CASHIER;

    private static final String QUERY_ITEM_ALL="SELECT " + COLUMN_ANBAR_NR +", " +COLUMN_ANBAR_DATE+", "+ COLUMN_ANBAR_BARCODE + ", "+
            COLUMN_ANBAR_QRAM + ", " + COLUMN_ANBAR_EYAR + ", " + COLUMN_ANBAR_AD + ", " + COLUMN_ANBAR_MAYA + " FROM " +
            TABLE_ANBAR + " WHERE " + COLUMN_ANBAR_BARCODE + " = ?";

    private static final String QUERY_ALLITEMS="SELECT * FROM " + TABLE_ANBAR;

    private static final String QUERY_SATIS="SELECT * FROM " + TABLE_SATIS;
    public boolean open(){
        try {
            connection= DriverManager.getConnection(CONNECTION_STRING);
            queryitemPrep=connection.prepareStatement(QUERY_ITEMS);
            insertItemPrep=connection.prepareStatement(INSERT_ITEM);
            deleteItemPrep=connection.prepareStatement(DELETE_ITEM);
            insertPayment=connection.prepareStatement(INSERT_PAYMENT);
            increaseCashier=connection.prepareStatement(INCREASE_Cashier);
            decreaseCashier=connection.prepareStatement(DECREASE_Cashier);
            getItempricePRep=connection.prepareStatement(GETITEM_PRICE);
            insertSatis= connection.prepareStatement(INSERT_SATIS);
            queryKassa=connection.prepareStatement(QUERY_KASSA);
            queryItemallinfo =connection.prepareStatement(QUERY_ITEM_ALL);
            return true;
        }catch (SQLException e){
            System.out.println("Something went wrong " + e.getMessage());
            return false;
        }
    }

    public void close(){
        try {
            if (connection!=null){
                connection.close();
            }
            if (queryitemPrep!=null){
                queryitemPrep.close();
            }
            if (insertItemPrep!=null){
                insertItemPrep.close();
            }
            if (deleteItemPrep!=null){
                deleteItemPrep.close();
            }
            if (insertPayment!=null){
                insertPayment.close();
            }
            if (increaseCashier!=null){
                increaseCashier.close();
            }
            if (decreaseCashier!=null){
                decreaseCashier.close();
            }
            if (getItempricePRep!=null){
                getItempricePRep.close();
            }
            if (insertSatis!=null){
                insertSatis.close();
            }
            if (queryKassa!=null){
                queryKassa.close();
            }
            if (queryItemallinfo !=null){
                queryItemallinfo.close();
            }
        }catch (SQLException e){
            System.out.println("Something went wrong " + e.getMessage());
        }
    }

    public int insertItem(int barcode,double qram,String eyyar,String ad,double maya){
        try {
            connection.setAutoCommit(false);
            queryitemPrep.setInt(1,barcode);
            ResultSet resultSet = queryitemPrep.executeQuery();
            if (resultSet.next()){
                return resultSet.getInt(1);
            }else {

                LocalDate date = LocalDate.now();
                insertItemPrep.setString(1, date.toString());
                insertItemPrep.setInt(2, barcode);
                insertItemPrep.setDouble(3, qram);
                insertItemPrep.setString(4, eyyar);
                insertItemPrep.setString(5, ad);
                insertItemPrep.setDouble(6, maya);
                int nrAffectedRows= insertItemPrep.executeUpdate();
                if (nrAffectedRows!=1){
                    throw new SQLException("couln't insert item");
                }
                ResultSet generetedKeys=insertItemPrep.getGeneratedKeys();
                if (generetedKeys.next()){
                    return generetedKeys.getInt(1);
                }else{
                    throw new SQLException("couldnt get an id");
                }
            }
        }catch (Exception e){
            System.out.println("Something went wrong " + e.getMessage());
            try {
                connection.rollback();
                System.out.println("connection rolled back");
            }catch (SQLException f){
                System.out.println("failed to roll back " + f.getMessage());
            }
        }
        finally {
            try {
                System.out.println("commiting the changes and setting to true");
                connection.setAutoCommit(true);
            }catch (SQLException g){
                System.out.println("couln't set auto commit to true " + g.getMessage());
            }
        }

        return 0;
    }

    public int lookforAnItem(int barcode){
        try {
            queryitemPrep.setInt(1,barcode);
            ResultSet resultSet= queryitemPrep.executeQuery();
            if (resultSet.next()){
                return resultSet.getInt(1);
            }
            return 0;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return 0;
        }

    }

    public double getItemPrice(int itemNumber){
        try {
            getItempricePRep.setInt(1,itemNumber);
            ResultSet resultSet = getItempricePRep.executeQuery();
            return resultSet.getDouble(1);
        }catch (SQLException e){
            System.out.println(e.getMessage());
            return 0;
        }
    }

    public void insertSatis(int barcode,double amount) {
        int num=lookforAnItem(barcode);
        if (num!=0){
            try {
                connection.setAutoCommit(false);
                LocalDate date = LocalDate.now();
                List<Item> items = queryItem(barcode);
                for (Item item:items){
                    insertSatis.setInt(1,item.getDb_number());
                    insertSatis.setString(2,date.toString());
                    insertSatis.setInt(3,item.getBarcode());
                    insertSatis.setDouble(4,item.getQram());
                    insertSatis.setDouble(5,item.getMaya());
                }
                insertSatis.setDouble(6,amount);

                int nrAffetedRow=insertSatis.executeUpdate();

                insertKassa(amount,"medaxil","Satış");
                deleteItemPrep.setInt(1,num);
                int affetedRow = deleteItemPrep.executeUpdate();
                if (affetedRow!=1 && nrAffetedRow ==1){
                    throw new SQLException("Error with handling item :  " + barcode);
                }
            } catch (SQLException e) {
                System.out.println("Something went wrong " + e.getMessage());
            }
        }
    }

    public void deleteItem(int barcode){
        int num=lookforAnItem(barcode);
        if (num!=0) {
            try {
                deleteItemPrep.setInt(1, num);
                int affetedRow = deleteItemPrep.executeUpdate();
                if (affetedRow != 1) {
                    throw new SQLException("Error with handling item :  " + barcode);
                }
            } catch (SQLException e) {
                System.out.println("Something went wrong " + e.getMessage());
            }
        }
    }

    public void insertKassa(double amount,String type,String qeyd){
        try {
            connection.setAutoCommit(false);
            LocalDate date= LocalDate.now();
            insertPayment.setString(1,date.toString());
            insertPayment.setString(2,type);
            if(type.equals("medaxil")){
                increaseCashier(amount);
            }
            if (type.equals("mexaric")){
                decreaseCashier(amount);
            }
            insertPayment.setDouble(3,amount);
            insertPayment.setString(4,qeyd);
            int nrAffectedRows= insertPayment.executeUpdate();
            if (nrAffectedRows ==1){
                connection.commit();
                System.out.println("commited");
            }else{
                throw  new SQLException("couldn't insert the payment");
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
            try {
                connection.rollback();
                System.out.println("connection rolled back");
            }catch (SQLException f){
                System.out.println("failed to roll back " + f.getMessage() );
            }
        }
        finally {
            try {
                System.out.println("saving changes and setting to true ");
                connection.setAutoCommit(true);
            }catch (SQLException h){
                System.out.println("failed to setting auto commit to true " + h.getMessage());
            }
        }
    }

    public void increaseCashier(double amount){
        try {
            increaseCashier.setDouble(1,amount);
            increaseCashier.executeUpdate();
        }catch (SQLException e) {
            System.out.println("Could't insert to cashier " + e.getMessage());
        }
    }

    public void decreaseCashier(double amount){
        try {
            decreaseCashier.setDouble(1, amount);
            decreaseCashier.executeUpdate();
        }catch (SQLException e){
            System.out.println("Something went wrong " + e.getMessage());
        }
    }

    public double queryCashier(){
        try(Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(QUERY_CASHIER)) {
            return resultSet.getDouble("total_money");
        }catch (SQLException e){
            System.out.println("something went wrong  " + e.getMessage());

        }
        return 0;
    }

    public List<Kassa> queryKassa(String date){
        try {
            queryKassa.setString(1,date);
            ResultSet resultSet = queryKassa.executeQuery();
            List<Kassa> transactions= new ArrayList<>();
            while (resultSet.next()) {
                Kassa kassa = new Kassa();
                kassa.setDate(resultSet.getString(1));
                kassa.setType(resultSet.getString(2));
                kassa.setMebleg(resultSet.getDouble(3));
                kassa.setQeyd(resultSet.getString(4));
                transactions.add(kassa);
            }
            return transactions;
        }catch (SQLException e){
            System.out.println("Something went wrong " + e.getMessage());
            return null;
        }
    }


    public List<Item> queryItem(int barcode){
        try {
            queryItemallinfo.setInt(1,barcode);
            ResultSet resultSet = queryItemallinfo.executeQuery();
            List<Item> items= new ArrayList<>();
            while (resultSet.next()){
                Item item = new Item();
                item.setDb_number(resultSet.getInt(1));
                item.setDate(resultSet.getString(2));
                item.setBarcode(resultSet.getInt(3));
                item.setQram(resultSet.getDouble(4));
                item.setEyyar(resultSet.getString(5));
                item.setAd(resultSet.getString(6));
                item.setMaya(resultSet.getDouble(7));
                items.add(item);
            }
            return items;
        }catch (SQLException e){
            System.out.println("Something went wrong " + e.getMessage());
            return null;
        }

    }

    public List<Item> queryAllItems(){
        try (Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(QUERY_ALLITEMS)){
            List<Item> items= new ArrayList<>();
            while (resultSet.next()){
                Item item = new Item();
                item.setDb_number(resultSet.getInt(1));
                item.setDate(resultSet.getString(2));
                item.setBarcode(resultSet.getInt(3));
                item.setQram(resultSet.getDouble(4));
                item.setEyyar(resultSet.getString(5));
                item.setAd(resultSet.getString(6));
                item.setMaya(resultSet.getDouble(7));
                items.add(item);
            }
            return items;
        }
        catch (SQLException e){
            System.out.println("Something went wrong " + e.getMessage());
            return null;
        }
    }

    public List<Satis> querySatis(){
        try (Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(QUERY_SATIS)){
            List<Satis> satisList = new ArrayList<>();
            while (resultSet.next()){
                Satis satis = new Satis();
                satis.setNr(resultSet.getInt(1));
                satis.setDate(resultSet.getString(2));
                satis.setBarcode(resultSet.getInt(3));
                satis.setQram(resultSet.getDouble(4));
                satis.setMaya(resultSet.getDouble(5));
                satis.setMebleg(resultSet.getDouble(6));
                satisList.add(satis);
            }
            return satisList;

        }
        catch (SQLException e ){
            System.out.println(e.getMessage());
            return null;
        }
    }



}
