import Data.DataSource;
import Model.Item;
import Model.Kassa;
import Model.Satis;
import org.jdesktop.swingx.JXDatePicker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainGUI extends JFrame {

    private JPanel mainPanel;
    private JPanel buttonPanel;
    private JPanel outputPanel;
    private JButton kassaButton;
    private JButton deleteButton;
    private JButton malEkleButton;
    private JButton satisButton;


    private JButton anbarButton;
    private JButton cixisButton;

    private JButton ayTotalButton;
    private JTextArea outputTextArea;

    private DataSource ds;

    public MainGUI() {
        ds = new DataSource();

        createUI();
        addEventListeners();
    }

    private void createUI() {
        mainPanel = new JPanel(new BorderLayout());

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(9, 1));

        kassaButton = new JButton("KASSA");
        malEkleButton = new JButton("MAL ƏLAVƏ ETMƏK");
        satisButton = new JButton("SATIŞ");
        anbarButton = new JButton("ANBAR");
        cixisButton = new JButton("ÇIXIŞ");
        deleteButton= new JButton("MAL SİLMƏK");
        ayTotalButton = new JButton("AYLIQ MƏNFƏƏT");





//        buttonPanel.add(Box.createVerticalGlue());
        buttonPanel.add(kassaButton);
//        buttonPanel.add(Box.createVerticalStrut(60));
        buttonPanel.add(malEkleButton);
//        buttonPanel.add(Box.createVerticalStrut(30));
        buttonPanel.add(deleteButton);
//        buttonPanel.add(Box.createVerticalStrut(30));
        buttonPanel.add(satisButton);
//        buttonPanel.add(Box.createVerticalStrut(30));
        buttonPanel.add(anbarButton);
//        buttonPanel.add(Box.createVerticalStrut(30));
        buttonPanel.add(ayTotalButton);
//        buttonPanel.add(Box.createVerticalStrut(30));
        buttonPanel.add(cixisButton);

        buttonPanel.add(Box.createVerticalGlue());

        outputPanel = new JPanel(new BorderLayout());
        outputTextArea = new JTextArea();
        outputTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputTextArea);
        outputPanel.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(buttonPanel, BorderLayout.WEST);
        mainPanel.add(outputPanel, BorderLayout.CENTER);

        add(mainPanel);

        setTitle("Kassa App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }

    private void addEventListeners() {
        kassaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showKassaMenu();
            }
        });

        malEkleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showMalEkleMenu();
            }
        });


        ayTotalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAyliqMenfeet();
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showDeleteItem();
            }
        });

        satisButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showSatisMenu();
            }
        });

        anbarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAnbarMenu();
            }
        });

        cixisButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }




    private void showKassaMenu() {
        String[] kassaOptions = { "Mədaxil", "Məxaric", "Günə görə hərəkəti izləmək", "Qalıq" };
        String actionType = (String) JOptionPane.showInputDialog(
                this,
                "ƏMƏLİYYAT NÖVÜ:",
                "Kassa",
                JOptionPane.PLAIN_MESSAGE,
                null,
                kassaOptions,
                kassaOptions[0]);

        if (actionType != null) {
            switch (actionType) {
                case "Mədaxil":
                    double amount = Double.parseDouble(JOptionPane.showInputDialog(this, "MƏBLƏĞ:"));
                    String note = JOptionPane.showInputDialog(this, "Qeyd:");
                    ds.open();
                    ds.insertKassa(amount, "medaxil", note);
                    ds.close();
                    break;
                case "Məxaric":
                    double amount2 = Double.parseDouble(JOptionPane.showInputDialog(this, "MƏBLƏĞ:"));
                    String note2 = JOptionPane.showInputDialog(this, "Qeyd:");
                    ds.open();
                    ds.insertKassa(amount2, "mexaric", note2);
                    ds.close();
                    break;
                case "Günə görə hərəkəti izləmək":
                    LocalDate date = LocalDate.now();
                    String[] searchOptions = { "Bu günki hərəkət", "Tarixi daxil etmək" };
                    String search = (String) JOptionPane.showInputDialog(
                            this,
                            "AXTARIŞ NÖVÜ:",
                            "Kassa - Günə görə hərəkət izləmək",
                            JOptionPane.PLAIN_MESSAGE,
                            null,
                            searchOptions,
                            searchOptions[0]);

                    if (search != null) {
                        switch (search) {
                            case "Bu günki hərəkət":
                                ds.open();
                                List<Kassa> transactions = ds.queryKassa(date.toString());
                                ds.close();
                                showKassaTransactions(transactions);
                                break;
                            case "Tarixi daxil etmək":
                                JXDatePicker datePicker = new JXDatePicker();

                                int option = JOptionPane.showOptionDialog(
                                        this,
                                        new Object[]{"TARİX:", datePicker},
                                        "Tarixi daxil etmək",
                                        JOptionPane.OK_CANCEL_OPTION,
                                        JOptionPane.PLAIN_MESSAGE,
                                        null,
                                        null,
                                        null
                                );

                                if (option == JOptionPane.OK_OPTION) {
                                    LocalDate selectedDate = datePicker.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                                    String tarix = selectedDate.toString();

                                    ds.open();
                                    List<Kassa> transactions2 = ds.queryKassa(tarix);
                                    ds.close();
                                    showKassaTransactions(transactions2);
                                }
                                break;

                        }
                    }
                    break;
                case "Qalıq":
                    ds.open();
                    double sum = ds.queryCashier();
                    ds.close();
                    JOptionPane.showMessageDialog(this, "Qalıq: " + sum);
                    break;
            }
        }
    }

    private void showKassaTransactions(List<Kassa> transactions) {
        if (transactions.isEmpty()) {
            outputTextArea.setText("Heç bir hərəkət tapılmadı.");
        } else {
            StringBuilder sb = new StringBuilder();
            for (Kassa kassa : transactions) {
                sb.append("Tarix: ").append(kassa.getDate()).append("\n");
                sb.append("Növ: ").append(kassa.getType()).append("\n");
                sb.append("Məbləğ: ").append(kassa.getMebleg()).append("\n");
                sb.append("Qeyd: ").append(kassa.getQeyd()).append("\n");
                sb.append("\n");
            }
            outputTextArea.setText(sb.toString());
        }
    }

    private void showAyliqMenfeet() {
        JXDatePicker fromDateDatePicker = new JXDatePicker();
        JXDatePicker toDateDatePicker = new JXDatePicker();

        int option = JOptionPane.showOptionDialog(
                this,
                new Object[]{"Tarixdən:", fromDateDatePicker, "Tarixə:", toDateDatePicker},
                "Select Dates",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                null
        );

        if (option == JOptionPane.OK_OPTION) {
            LocalDate fromDate = fromDateDatePicker.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate toDate = toDateDatePicker.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            ds.open();
            List<Satis> satisList = ds.querySatis();
            double totalSales = 0.0;
            double totalMenfeet = 0.0;
            double totalMaya = 0.0;
            for (Satis satis : satisList) {
                LocalDate satisDate = LocalDate.parse(satis.getDate());
                if ((satisDate.isAfter(fromDate) || satisDate.isEqual(fromDate))
                        && (satisDate.isBefore(toDate) || satisDate.isEqual(toDate))) {
                    totalSales += satis.getMebleg();
                    double menfeet = satis.getMebleg() - satis.getMaya();
                    totalMaya += satis.getMaya();
                    totalMenfeet += menfeet;
                }
            }
            StringBuilder sb = new StringBuilder();
            sb.append("Ümumi satış " + fromDate + " və " + toDate + ": " + totalSales).append("\n").append("\n");
            sb.append("Ümumi maya " + fromDate + " və " + toDate + ": " + totalMaya).append("\n").append("\n");
            sb.append("Ümumi mənfəət " + fromDate + " və " + toDate + ": " + totalMenfeet).append("\n").append("\n");
            sb.append("\n");
            ds.close();

            outputTextArea.setText(sb.toString());
        }
    }

    private void showMalEkleMenu() {
        int barcode = Integer.parseInt(JOptionPane.showInputDialog(this, "BARCODU DAXİL EDİN:"));
        double gram = Double.parseDouble(JOptionPane.showInputDialog(this, "QRAMI DAXİL EDİN:"));
        String eyyar="";
        String[] eyyarOption = { "583", "585", "750"};
        String actionType = (String) JOptionPane.showInputDialog(
                this,
                "ƏYAR:",
                "Kassa",
                JOptionPane.PLAIN_MESSAGE,
                null,
                eyyarOption,
                eyyarOption[0]);

        if (actionType != null) {
            switch (actionType){
                case "583":
                    eyyar="583";
                    break;
                case "585":
                    eyyar="585";
                    break;
                case "750":
                    eyyar="750";
                    break;
            }
        }
            String ad = JOptionPane.showInputDialog(this, "MAL ADI:");
        double maya = Double.parseDouble(JOptionPane.showInputDialog(this, "MAYA:"));

        ds.open();
        ds.insertItem(barcode, gram, eyyar, ad, maya);
        ds.close();
    }


    private void showDeleteItem(){
        int barcode = Integer.parseInt(JOptionPane.showInputDialog(this, "MAL NÖMRƏSİ:"));
        ds.open();
        List<Item> items = ds.queryItem(barcode);
        ds.close();

        if (items == null || items.isEmpty()) {
            outputTextArea.setText("Sistemdə bu nömrəli mal yoxdur");
        } else {
            StringBuilder sb = new StringBuilder();
            for (Item item : items) {
                sb.append("Sistem nömrəsi: ").append(item.getDb_number()).append("\n");
                sb.append("Alındığı tarix: ").append(item.getDate()).append("\n");
                sb.append("Barcode: ").append(item.getBarcode()).append("\n");
                sb.append("Qram: ").append(item.getQram()).append("\n");
                sb.append("Əyyar: ").append(item.getEyyar()).append("\n");
                sb.append("Məhsulun adı: ").append(item.getAd()).append("\n");
                sb.append("Maya dəyəri: ").append(item.getMaya()).append("\n");
                sb.append("\n");
            }

            int confirmation = JOptionPane.showConfirmDialog(this, sb.toString() + "həyata keçirmək istəyirsiniz?", "SİLMƏK", JOptionPane.YES_NO_OPTION);
            if (confirmation == JOptionPane.YES_OPTION) {
                ds.open();
                ds.deleteItem(barcode);
                ds.close();
            }

        }
    }

    private void showSatisMenu() {
        int barcode = Integer.parseInt(JOptionPane.showInputDialog(this, "MAL NÖMRƏSİ:"));
        ds.open();
        List<Item> items = ds.queryItem(barcode);
        ds.close();

        if (items == null || items.isEmpty()) {
            outputTextArea.setText("Sistemdə bu barkodlu mal yoxdur");
        } else {
            StringBuilder sb = new StringBuilder();
            for (Item item : items) {
                sb.append("Sistem nömrəsi: ").append(item.getDb_number()).append("\n");
                sb.append("Alındığı tarix: ").append(item.getDate()).append("\n");
                sb.append("Barcode: ").append(item.getBarcode()).append("\n");
                sb.append("Qram: ").append(item.getQram()).append("\n");
                sb.append("Əyyar: ").append(item.getEyyar()).append("\n");
                sb.append("Məhsulun adı: ").append(item.getAd()).append("\n");
                sb.append("Maya dəyəri: ").append(item.getMaya()).append("\n");
                sb.append("\n");
            }

            int confirmation = JOptionPane.showConfirmDialog(this, sb.toString() + "Satışı həyata keçirmək istəyirsiniz?", "Satış", JOptionPane.YES_NO_OPTION);
            if (confirmation == JOptionPane.YES_OPTION) {
                double price = Double.parseDouble(JOptionPane.showInputDialog(this, "SATIŞ QİYMƏTİ:"));
                ds.open();
                ds.insertSatis(barcode, price);
                ds.close();
            }
        }
    }


    private void showAnbarMenu() {
        String[] options = { "Bütün mallar", "Malın barkoduna görə","Ümumi Mallar" };
        String choice = (String) JOptionPane.showInputDialog(
                this,
                "Seçiminizi edin:",
                "Anbar",
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                options[0]);

        if (choice != null) {
            switch (choice) {
                case "Bütün mallar":
                    ds.open();
                    List<Item> items = ds.queryAllItems();
                    ds.close();
                    showItems(items);
                    break;
                case "Malın barkoduna görə":
                    int barcode = Integer.parseInt(JOptionPane.showInputDialog(this, "Zəhmət olmasa malın barkodunu daxil edin:"));
                    ds.open();
                    List<Item> items2 = ds.queryItem(barcode);
                    ds.close();
                    showItems(items2);
                    break;
                case "Ümumi Mallar":
                    ds.open();
                    List<Item> items11 = ds.queryAllItems();
                    ds.close();
                    showTotal(items11);
                    break;

            }
        }
    }

    private void showItems(List<Item> items) {
        if (items.isEmpty()) {
            outputTextArea.setText("Sistemdə mal yoxdur");
        } else {
            StringBuilder sb = new StringBuilder();
            for (Item item : items) {
                sb.append("Sistem nömrəsi: ").append(item.getDb_number()).append("\n");
                sb.append("Alındığı tarix: ").append(item.getDate()).append("\n");
                sb.append("Barcode: ").append(item.getBarcode()).append("\n");
                sb.append("Qram: ").append(item.getQram()).append("\n");
                sb.append("Əyyar: ").append(item.getEyyar()).append("\n");
                sb.append("Məhsulun adı: ").append(item.getAd()).append("\n");
                sb.append("Maya dəyəri: ").append(item.getMaya()).append("\n");
                sb.append("\n");
            }
            outputTextArea.setText(sb.toString());
        }
    }

    private void showTotal(List<Item> items){
        if (items.isEmpty()) {
            outputTextArea.setText("Sistemdə mal yoxdur");
        } else {
            StringBuilder sb = new StringBuilder();
            int total_count = 0;
            double total_maya = 0;
            double total_qram = 0;
            String eyyar_583 = "583";
            int total_count_583 = 0;
            double total_maya_583 = 0;
            double total_qram_583 = 0;
            String eyyar_585 = "585";
            int total_count_585 = 0;
            double total_maya_585 = 0;
            double total_qram_585 = 0;
            String eyyar_750 = "750";
            int total_count_750 = 0;
            double total_maya_750 = 0;
            double total_qram_750 = 0;
            for (Item item : items) {
                total_count += 1;
                total_maya += item.getMaya();
                total_qram += item.getQram();
                if (item.getEyyar().equals(eyyar_583)) {
                    total_count_583 += 1;
                    total_maya_583 += item.getMaya();
                    total_qram_583 += item.getQram();
                }
                if (item.getEyyar().equals(eyyar_585)) {
                    total_count_585 += 1;
                    total_maya_585 += item.getMaya();
                    total_qram_585 += item.getQram();
                }
                if (item.getEyyar().equals(eyyar_750)) {
                    total_count_750 += 1;
                    total_maya_750 += item.getMaya();
                    total_qram_750 += item.getQram();
                }
            }
            sb.append("Ümumi malların sayı: ").append(total_count).append("\n");
            sb.append("Ümumi maya məbləği: ").append(total_maya).append("\n");
            sb.append("Ümumi qram: ").append(total_qram).append("\n");
            sb.append("\n");
            sb.append("583 əyarın sayı: ").append(total_count_583).append("\n");
            sb.append("583 əyar malların ümumi mayası: ").append(total_maya_583).append("\n");
            sb.append("583 əyarın malların ümumi qramı: ").append(total_qram_583).append("\n");
            sb.append("\n");
            sb.append("585 əyarın sayı: ").append(total_count_585).append("\n");
            sb.append("585 əyar malların ümumi mayası: ").append(total_maya_585).append("\n");
            sb.append("585 əyarın malların ümumi qramı: ").append(total_qram_585).append("\n");
            sb.append("\n");
            sb.append("750 əyarın sayı: ").append(total_count_750).append("\n");
            sb.append("750 əyar malların ümumi mayası: ").append(total_maya_750).append("\n");
            sb.append("750 əyarın malların ümumi qramı: ").append(total_qram_750).append("\n");
            sb.append("\n");
            outputTextArea.setText(sb.toString());
        }

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainGUI();
            }
        });
    }
}
