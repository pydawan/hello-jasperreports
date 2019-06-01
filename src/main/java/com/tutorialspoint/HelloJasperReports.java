package com.tutorialspoint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

public class HelloJasperReports {
    
    public static void main(String[] args) {
        // 1. Carregar o arquivo de projeto do relatório.
        ClassLoader classLoader = HelloJasperReports.class.getClassLoader();
        String jrxmlFilePath = classLoader.getResource("Report1.jrxml").getPath();
//        String jasperFilePath = classLoader.getResource("Report1.jasper").getPath();
        try {
            // 2. Compilar o arquivo de design (.jrxml) para um template (.jasper).
            JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlFilePath);
            // 3. Preencher o template com dados.
            DataBeanList DataBeanList = new DataBeanList();
            ArrayList<DataBean> dataList = DataBeanList.getDataBeanList();
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(dataList);
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("ReportTitle", "List of Contacts");
            parameters.put("Author", "Prepared By Thiago Monteiro");
            
//            JasperFillManager.fillReportToFile(jasperFilePath, parameters, beanColDataSource);
            // 3.1 Gera um arquivo .jprint 
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
            if(jrxmlFilePath != null){
                // 3.1.1 Imprime na impressora.
//                JasperPrintManager.printReport(jasperPrint, true);
                
                // 3.1.2 Imprime para na tela do monitor.
                JasperViewer.viewReport(jasperPrint);
                
                // 3.1.3
                /*
                JasperViewer jrviewer = new JasperViewer(jasperPrint, false);
                jrviewer.setTitle("Relatório 1");
                jrviewer.setVisible(true);
                */
             }
            // 3.2 Exporta .jprint para o formato .pdf
            JasperExportManager.exportReportToPdfFile(jasperPrint, "./src/main/resources/report1.pdf");
        } catch (JRException e) {
            e.printStackTrace();
        }
    }
    
}
