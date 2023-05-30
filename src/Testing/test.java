package Testing;

import Model.Satis;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class test {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Satis> res = new ArrayList<>();
        System.out.println("Tarixdən");
        String fromDate = scanner.nextLine();
        System.out.println("Tarixə");
        String toDate = scanner.nextLine();

        LocalDate fromD = LocalDate.parse(fromDate);
        LocalDate toD = LocalDate.parse(toDate);


        List<Satis> satisList = new ArrayList<>();
        Satis satis1= new Satis();
        satis1.setNr(1);
        satis1.setBarcode(1);
        satis1.setDate("2023-04-11");
        satis1.setMaya(300);
        satis1.setQram(3);
        satis1.setMebleg(500);
        Satis satis2= new Satis();
        satis2.setNr(3);
        satis2.setBarcode(2);
        satis2.setDate("2023-05-23");
        satis2.setMaya(250);
        satis2.setQram(4);
        satis2.setMebleg(600);

        satisList.add(satis1);
        satisList.add(satis2);
        double totalSales = 0.0;

        for (Satis satis : satisList) {
            // Convert satis date to LocalDate object
            LocalDate satisDate = LocalDate.parse(satis.getDate());

            // Check if the satis date is within the specified range
            if ((satisDate.isAfter(fromD) || satisDate.isEqual(fromD))&& (satisDate.isBefore(toD) || satisDate.isEqual(toD))) {
                // Add the sale amount to the total
                totalSales += satis.getMebleg();
                res.add(satis); // Optional: add the sale to the result list
            }
        }
        System.out.println("Total sales between " + fromDate + " and " + toDate + ": " + totalSales);


    }
}
