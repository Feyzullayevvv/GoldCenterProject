import Data.DataSource;
import Model.Item;
import Model.Kassa;
import Model.Satis;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class MenuTUI {
    public static void userTUI(){
        boolean condition= true;
            Scanner scanner = new Scanner(System.in);
            DataSource ds = new DataSource();
            while (condition){
                try {
                    printMenu();
                    System.out.println("Zəhmət olmasa həyata keçirmək istədiyiniz əməliyyatın nömrəsini daxil edin: ");
                    int command = scanner.nextInt();
                    switch (command) {
                        case 1:
                            System.out.println("1.Mədaxil \n2.Məxaric \n3.Günə görə hərəkəti izləmək \n4.Qalıq ");
                            int actionType = scanner.nextInt();
                            scanner.nextLine();
                            switch (actionType) {
                                case 1:
                                    ds.open();
                                    System.out.println("Zəhmət olmasa məbləği qeyd edin: ");
                                    double amount = scanner.nextDouble();
                                    System.out.println("Qeyd : ");
                                    scanner.nextLine();
                                    String note = scanner.nextLine();
                                    ds.insertKassa(amount, "medaxil", note);
                                    ds.close();
                                    break;
                                case 2:
                                    ds.open();
                                    System.out.println("Zəhmət olmasa məbləği qeyd edin: ");
                                    double amount2 = scanner.nextDouble();
                                    System.out.println("Qeyd : ");
                                    scanner.nextLine();
                                    String note2 = scanner.nextLine();
                                    ds.insertKassa(amount2, "mexaric", note2);
                                    ds.close();
                                    break;
                                case 3:
                                    LocalDate date = LocalDate.now();
                                    System.out.println("1.Bu günki hərələkət \n2.Tarixi daxil etmək(Format 2000-05-23): ");
                                    String search = scanner.nextLine();
                                    switch (search) {
                                        case "1":
                                            ds.open();
                                            List<Kassa> transactions = ds.queryKassa(date.toString());
                                            for (Kassa kassa : transactions) {
                                                System.out.println("Tarix : " + kassa.getDate());
                                                System.out.println("Növ : " + kassa.getType());
                                                System.out.println("Məbləğ : " + kassa.getMebleg());
                                                System.out.println("Qeyd : " + kassa.getQeyd());
                                            }
                                            ds.close();
                                            break;
                                        case "2":
                                            ds.open();
                                            System.out.println("Zəhmət olmasa tarixi qeyd edin(Format 2000-05-23): ");
                                            String tarix = scanner.nextLine();
                                            List<Kassa> transactions2 = ds.queryKassa(tarix);
                                            for (Kassa kassa : transactions2) {
                                                System.out.println("Tarix : " + kassa.getDate());
                                                System.out.println("Növ : " + kassa.getType());
                                                System.out.println("Məbləğ : " + kassa.getMebleg());
                                                System.out.println("Qeyd : " + kassa.getQeyd());
                                            }
                                            ds.close();
                                            break;
                                    }

                                case 4:
                                    ds.open();
                                    double sum = ds.queryCashier();
                                    System.out.println(sum);
                                    ds.close();
                                    break;
                            }
                            break;
                        case 2:
                            try {
                                ds.open();
                                System.out.println("Zəhmət olmasa barcodu daxil edin: ");
                                int barcode = scanner.nextInt();
                                System.out.println("Zəhmət olmasa qramı daxil edin: ");
                                double gram = scanner.nextDouble();
                                scanner.nextLine();
                                System.out.println("Zəhmət olmasa əyyarı daxil edin: ");
                                String eyyar = scanner.nextLine();

                                System.out.println("Zəhmət olmasa mal adını(qeyd üçün): ");
                                String ad = scanner.nextLine();
                                System.out.println("Zəhmət olmasa maya qiymətini daxil edin: ");
                                double maya = scanner.nextDouble();
                                ds.insertItem(barcode, gram, eyyar, ad, maya);
                                ds.close();
                                break;
                            } catch (Exception e) {
                                System.out.println("Xəta baş verdi : " + e.getMessage());
                            }
                            break;
                        case 3:
                            try {
                                ds.open();
                                System.out.println("Zəhmət olmasa malın barkodunu daxil edin : ");
                                int barcode = scanner.nextInt();
                                List<Item> items = ds.queryItem(barcode);
                                if (items == null) {
                                    System.out.println("Sistemdə bu barkodlu mal yoxdur");
                                    break;
                                }
                                for (Item item : items) {
                                    System.out.println("Sistem nömrəsi:" + item.getDb_number());
                                    System.out.println("Alındığı tarix:" + item.getDate());
                                    System.out.println("Barcode: " + item.getBarcode());
                                    System.out.println("Qram: " + item.getQram());
                                    System.out.println("Əyyar: " + item.getEyyar());
                                    System.out.println("Məhsulun adı: " + item.getAd());
                                    System.out.println("Maya dəyəri: " + item.getMaya());
                                }
                                scanner.nextLine();
                                System.out.println("Satışı həyata kecirmək istəyrinisiz?yes/No");
                                String choice = scanner.nextLine();
                                if (choice.equals("yes")) {
                                    System.out.println("Zəhmət olmasa satış qiymətini daxil edin:");
                                    double pric = scanner.nextDouble();
                                    ds.insertSatis(barcode, pric);
                                    ds.close();
                                    break;
                                }
                                ds.close();
                                break;
                            } catch (Exception e) {
                                System.out.println("Xəta baş verdi " + e.getMessage());
                                break;
                            }
                        case 4:
                            System.out.println("1.Bütün mallar \n2.Malın barkoduna görə \n3.Ümumi Mallar ");
                            int choice = scanner.nextInt();
                            switch (choice) {
                                case 1:
                                    List<Item> items = ds.queryAllItems();
                                    if (items == null) {
                                        System.out.println("Sistemdə  mal yoxdur");
                                        break;
                                    }
                                    for (Item item : items) {
                                        System.out.println("Sistem nömrəsi:" + item.getDb_number());
                                        System.out.println("Alındığı tarix:" + item.getDate());
                                        System.out.println("Barcode: " + item.getBarcode());
                                        System.out.println("Qram: " + item.getQram());
                                        System.out.println("Əyyar: " + item.getEyyar());
                                        System.out.println("Məhsulun adı: " + item.getAd());
                                        System.out.println("Maya dəyəri: " + item.getMaya());
                                        System.out.println("\n \n");
                                    }
                                    ds.close();
                                    break;
                                case 2:
                                    try {
                                        ds.open();
                                        System.out.println("Zəhmət olmasa malın barkodunu daxil edin : ");
                                        int barcode = scanner.nextInt();
                                        List<Item> items2 = ds.queryItem(barcode);
                                        if (items2 == null) {
                                            System.out.println("Sistemdə bu barkodlu mal yoxdur");
                                            break;
                                        }
                                        for (Item item : items2) {
                                            System.out.println("Sistem nömrəsi:" + item.getDb_number());
                                            System.out.println("Alındığı tarix:" + item.getDate());
                                            System.out.println("Barcode: " + item.getBarcode());
                                            System.out.println("Qram: " + item.getQram());
                                            System.out.println("Əyyar: " + item.getEyyar());
                                            System.out.println("Məhsulun adı: " + item.getAd());
                                            System.out.println("Maya dəyəri: " + item.getMaya());
                                        }
                                        ds.close();
                                        break;
                                    } catch (Exception e) {
                                        System.out.println("Xəta baş verdi " + e.getMessage());
                                        break;
                                    }
                                case 3:
                                    ds.open();
                                    List<Item> items1= ds.queryAllItems();
                                    if (items1==null){
                                        System.out.println("Mal yoxdur");
                                        ds.close();
                                        break;
                                    }
                                    int total_count=0;
                                    double total_maya=0;
                                    double total_qram=0;
                                    String eyyar_583="583";
                                    int total_count_583=0;
                                    double total_maya_583=0;
                                    double total_qram_583=0;
                                    String eyyar_585="585";
                                    int total_count_585=0;
                                    double total_maya_585=0;
                                    double total_qram_585=0;
                                    String eyyar_750="750";
                                    int total_count_750=0;
                                    double total_maya_750=0;
                                    double total_qram_750=0;
                                    for (Item item:items1){
                                         total_count+=1;
                                         total_maya+=item.getMaya();
                                         total_qram+=item.getQram();
                                         if (item.getEyyar().equals(eyyar_583)){
                                             total_count_583+=1;
                                             total_maya_583+=item.getMaya();
                                             total_qram_583+=item.getQram();
                                         }
                                        if (item.getEyyar().equals(eyyar_585)){
                                            total_count_585+=1;
                                            total_maya_585+=item.getMaya();
                                            total_qram_585+=item.getQram();
                                        }
                                        if (item.getEyyar().equals(eyyar_750)){
                                            total_count_750+=1;
                                            total_maya_750+=item.getMaya();
                                            total_qram_750+=item.getQram();
                                        }
                                    }
                                    System.out.println("Ümumi malların sayı: " + total_count);
                                    System.out.println("Ümumi maya məbləği: " + total_maya);
                                    System.out.println("Ümumi qram: " + total_qram);
                                    System.out.println("583 əyarın sayı: " + total_count_583);
                                    System.out.println("583 əyar malların ümumi mayası: " + total_maya_583);
                                    System.out.println("583 əyarın malların ümumi qramı: " + total_qram_585);
                                    System.out.println("585 əyarın sayı: " + total_count_585);
                                    System.out.println("585 əyar malların ümumi mayası: " + total_maya_585);
                                    System.out.println("585 əyarın malların ümumi qramı: " + total_qram_585);
                                    System.out.println("750 əyarın sayı: "+ total_count_750);
                                    System.out.println("750 əyar malların ümumi mayası: " + total_maya_750);
                                    System.out.println("750 əyarın malların ümumi qramı: " + total_qram_750);

                                case 5:
                                    List<Satis> res = new ArrayList<>();
                                    System.out.println("Tarixdən");
                                    String fromDate = scanner.nextLine();
                                    System.out.println("Tarixə");
                                    String toDate = scanner.nextLine();

                                    LocalDate fromD = LocalDate.parse(fromDate);
                                    LocalDate toD = LocalDate.parse(toDate);

                                    ds.open();
                                    List<Satis> satisList = ds.querySatis();

                                    double totalSales = 0.0;
                                    double totalMenfeet=0.0;
                                    double totalMaya=0.0;

                                    for (Satis satis : satisList) {
                                        LocalDate satisDate = LocalDate.parse(satis.getDate());
                                        if ((satisDate.isAfter(fromD) || satisDate.isEqual(fromD))&& (satisDate.isBefore(toD) || satisDate.isEqual(toD))) {
                                            totalSales += satis.getMebleg();
                                            double menfeet=satis.getMebleg()-satis.getMaya();
                                            totalMaya += satis.getMaya();
                                            totalMenfeet+=menfeet;
                                            res.add(satis);
                                        }
                                    }
                                    System.out.println("Ümumi satış " + fromDate + " and " + toDate + ": " + totalSales);
                                    System.out.println("Ümumi maya " + fromDate + " and " + toDate + ": " + totalMaya);
                                    System.out.println("Ümumi mənfəət " + fromDate + " and " + toDate + ": " + totalMenfeet);
                                    ds.close();
                                    break;


                                case 6:
                                    condition = false;
                                    break;
                                default:
                                    System.out.println("Zəhmət olmasa 1-5 arası rəqəmlər seçin");

                            }
                    }
                }catch (Exception e){
                    System.out.println("Xəta baş verdi : " + e.getMessage());
                    break;
                }
            }
    }

    public static void printMenu(){
            System.out.println("1.Kassa \n2.Mal Əlavə etmək \n3.Satış \n4.Anbar \n5.Aylıq mənfəət \n6.Çıxış");
    }



}
