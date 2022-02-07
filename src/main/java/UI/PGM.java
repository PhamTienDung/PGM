package UI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;
import java.util.List;

import app.PgmProduct;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class PGM extends JFrame {
    JButton jButtonImportCustomer;
    JButton jButtonExit;

    JFileChooser jFileChooserCustomer;

    List<PgmProduct> pgmProductList;

    public PGM(String title) {
        super(title);
        addControls();
        addEvents();
    }

    private void addEvents() {
        showChooseFileCustomer();
        Exit();
    }

    private void Exit() {
        jButtonExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
        int result = JOptionPane.showConfirmDialog(null, "終了しましょうか？", "確認下さい",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);

        if(result == JOptionPane.YES_OPTION){
            System.exit(0);
        }
        else{
            //何もしない
        }
    }
        });
    }

    public void showWindow() {
        setSize(460, 230);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void addControls() {
        Container container = getContentPane();
        JPanel jPanelMain = new JPanel();
        jPanelMain.setLayout(new BoxLayout(jPanelMain, BoxLayout.Y_AXIS));
        container.add(jPanelMain);

        JPanel jPanelTitle = new JPanel();
        jPanelTitle.setLayout(new FlowLayout());
        JLabel jLabelTitle = new JLabel("会計伝票");
        jLabelTitle.setFont(new Font("ＭＳ ゴシック",Font.BOLD,60));
        jPanelTitle.add(jLabelTitle);
        jPanelMain.add(jPanelTitle);

        JPanel jPanelHeader = new JPanel();
        jPanelHeader.setLayout(new FlowLayout());

        jButtonImportCustomer = new JButton("エクセルインポート⇒印刷");
        jButtonImportCustomer.setFont(new Font("ＭＳ ゴシック",Font.BOLD,21));
        jButtonImportCustomer.setPreferredSize(new Dimension(300,50));
        jButtonExit = new JButton("終了");
        jButtonExit.setFont(new Font("ＭＳ ゴシック",Font.BOLD,21));
        jButtonExit.setPreferredSize(new Dimension(80,50));
        jPanelHeader.add(jButtonImportCustomer);
        jPanelHeader.add(jButtonExit);
        jPanelMain.add(jPanelHeader);

        pgmProductList = new ArrayList<>();
    }

    private void showChooseFileCustomer() {
        jFileChooserCustomer = new JFileChooser();
        java.util.List<Object> listSheet = new ArrayList<>();
        jButtonImportCustomer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int returnVal = jFileChooserCustomer.showOpenDialog(null);

                if (returnVal == JFileChooser.APPROVE_OPTION) {

                    File f = jFileChooserCustomer.getSelectedFile();
                    String stringPath = f.getPath();

                    if (stringPath.contains("xlsx") == true || stringPath.contains(".xlsm") == true) {
                        try {
                            File fileCustomer = new File(stringPath);
                            FileInputStream fileInputStreamCustomer = new FileInputStream(fileCustomer);
                            Workbook workbook = new XSSFWorkbook(fileInputStreamCustomer);

                            listSheet.clear();
                            pgmProductList.clear();

                            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                                listSheet.add(workbook.getSheetName(i));
                            }

                            String stringSelectSheet = (String) JOptionPane.showInputDialog(null, "シートを選択してください", "シート選択",
                                    JOptionPane.QUESTION_MESSAGE, null, listSheet.toArray(), "");

                            if (stringSelectSheet != null) {
                                Sheet sheetCurrent = workbook.getSheet(stringSelectSheet);
                                Iterator<Row> rowIterator = sheetCurrent.iterator();
                                PgmProduct pgmProduct = null;

                                if(rowIterator.hasNext() ==false){
                                    JOptionPane.showMessageDialog(null, "データがありません。");
                                }else{
                                    while (rowIterator.hasNext()) {
                                        Row row = rowIterator.next();
                                        Iterator<Cell> cellIterator = row.cellIterator();
                                        int checkLen=0;
                                        while (cellIterator.hasNext()) {
                                            checkLen = checkLen +1;
                                            cellIterator.next();
                                            if(checkLen==15){
                                                pgmProduct = new PgmProduct(row.getCell(0).toString(), (int) row.getCell(1).getNumericCellValue(), (int) row.getCell(2).getNumericCellValue(),
                                                        (int) row.getCell(3).getNumericCellValue(), (int) row.getCell(4).getNumericCellValue(), (int) row.getCell(5).getNumericCellValue(),
                                                        (int) row.getCell(6).getNumericCellValue(), (int) row.getCell(7).getNumericCellValue(), row.getCell(8).toString(), row.getCell(9).toString(),
                                                        (int) row.getCell(10).getNumericCellValue(), row.getCell(11).toString(), (int) row.getCell(12).getNumericCellValue(), row.getCell(13).toString(),
                                                        (int) row.getCell(14).getNumericCellValue());
                                                pgmProductList.add(pgmProduct);
                                            }

                                        }if(checkLen!=15) {
                                            //何もしない
                                        }
                                    }
                                }

                                workbook.close();
                            }
//                            else {
//                                JOptionPane.showMessageDialog(null, "何も選択しない");
//                            }
                        } catch (FileNotFoundException ex) {
                            ex.printStackTrace();
                        } catch (IOException exx) {
                            exx.printStackTrace();
                        }

                        myPrint(pgmProductList);
                    }
                    else {
                        JOptionPane.showMessageDialog(null, ".xlsx .xlsm ファイル選択してください");
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "何も選択しない");
                }
            }
        });
    }

    public void myPrint(List<PgmProduct> productList){

        String jasperFilePath =  PGM.class.getResource("/DunDemo_A4_1.jrxml").getPath();
        JasperPrint jasperPrint = null;
        List<JasperPrint> jasperPrintList = new ArrayList<>();

        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(jasperFilePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        JasperDesign jasperDesign = null;
        try {
            jasperDesign = JRXmlLoader.load(inputStream);
        } catch (JRException e) {
            e.printStackTrace();
        }

        JasperReport jasperReport = null;
        try {
            jasperReport = JasperCompileManager.compileReport(jasperDesign);
        } catch (JRException e) {
            e.printStackTrace();
        }
//        System.out.println(productList.size());

        for(int i=0;i<productList.size();i++){
            PgmProduct pgmProduct = productList.get(i);

            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("company",pgmProduct.getCompanyName());
            parameters.put("price",pgmProduct.getPrice());
            parameters.put("year",pgmProduct.getYear());
            parameters.put("month",pgmProduct.getMonth());
            parameters.put("day",pgmProduct.getDay());
            parameters.put("year1",pgmProduct.getYear1());
            parameters.put("month1",pgmProduct.getMonth1());
            parameters.put("day1",pgmProduct.getDay1());
            parameters.put("comment",pgmProduct.getComment());
            parameters.put("subtotaltitle",pgmProduct.getSubtotaltitle());
            parameters.put("subtotal",pgmProduct.getSubtotal());
            parameters.put("taxtitle",pgmProduct.getTaxtitle());
            parameters.put("tax",pgmProduct.getTax());
            parameters.put("totaltitle",pgmProduct.getTotaltitle());
            parameters.put("total",pgmProduct.getTotal());

            try {
                jasperPrint = JasperFillManager.fillReport(jasperReport,parameters,new JREmptyDataSource());
                jasperPrintList.add(jasperPrint);
            } catch ( JRException u) {
                u.printStackTrace();
            }
        }
        for (int i=0;i<jasperPrintList.size();i++){
            jasperPrint.addPage(jasperPrintList.get(i).getPages().get(0));
        }
        if(jasperPrintList.size()>=1){ jasperPrint.removePage(0);}


        if(jasperPrintList.size()==0){
            JOptionPane.showMessageDialog(null, "インポートエラー");
        }else{
            JasperViewer.viewReport(jasperPrint,false);
        }
//        System.out.println("Path "+ jasperFilePath);
    }
}

