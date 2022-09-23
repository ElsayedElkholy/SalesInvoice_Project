
package com.controller;

import com.model.Invoice;
import com.model.InvoicesTableModel;
import com.model.Line;
import com.model.LinesTableModels;
import com.view.InvoiceDialog;
import com.view.InvoiceFirstFrame;
import com.view.LineDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class Controller implements ActionListener , ListSelectionListener{

    private InvoiceFirstFrame frame;
    private InvoiceDialog invoDialog;
    private LineDialog lineDialog;
    
    public Controller(InvoiceFirstFrame frame){
        this.frame = frame ;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        System.out.println("Action : " + actionCommand);
        switch (actionCommand){
           case "Load File" :
               loadFile();
                break;
           case "Save File" :
               saveFile();
                break;
           case "Create New Invoice" :
               createNewInvoice();
                break;
           case "Delete Invoice" :
               deleteInvoice();
                break; 
           case "Save" :
               save();
                break;
           case "Cancel" :
               cancel();
                break;
           case "CreatenewInvoiceSave":
               CreatenewInvoiceSave();
               break;
           case "CreateNewLineOK":
               CreateNewLineOK();
               break;
           case "CancelCreatenewInvoice":
               CancelCreatenewInvoice();
               break;
           case "CancelCreateNewLine":
               cancelCreateNewLine();
               break;
               
          
                
        }
    }
    @Override
    public void valueChanged(ListSelectionEvent e) {
        int selectedIndex = frame.getInvoiceTable().getSelectedRow();
        if(selectedIndex!=-1){
            System.out.println("Selected Row : " +selectedIndex);
            Invoice currentInvoice = frame.getInvoices().get(selectedIndex);
            frame.getInvoiceNoLabel().setText(" "+currentInvoice.getNum());
            frame.getInvoiceDateLabel().setText(currentInvoice.getDate());
            frame.getCustomerNameLabel().setText(currentInvoice.getCustomer());
            frame.getInvoiceTotalLabel().setText("" + currentInvoice.getTotalOfInvoice());
            LinesTableModels linesTableModel = new LinesTableModels(currentInvoice.getLines());
            frame.getLineTable().setModel(linesTableModel);
            linesTableModel.fireTableDataChanged();
        }


    }
    private void loadFile(){
        JFileChooser jfc  = new JFileChooser();
        try {
        int result = jfc.showOpenDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION){
            File invoiceHeaderFile = jfc.getSelectedFile();
            Path invoiceHeaderPath = Paths.get(invoiceHeaderFile.getAbsolutePath());
            List<String> invoiceHeaderLines = Files.readAllLines(invoiceHeaderPath);
            System.out.println("Invoice have been read");
            
            ArrayList<Invoice> invoicesList = new ArrayList<>();
            for (String invoiceHeaderLine : invoiceHeaderLines){
                try{
                String[] headerParts = invoiceHeaderLine.split(",");
                int invoiceNum = Integer.parseInt(headerParts[0]);
                String invoiceDate = headerParts[1];
                String customerName = headerParts[2];
                
                Invoice invoice = new Invoice(invoiceNum,invoiceDate,customerName); 
                invoicesList.add(invoice);
            }catch(Exception ex){
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame,"Line format error","Error",JOptionPane.ERROR_MESSAGE);
                }
            
            System.out.println("Check Point");
            result = jfc.showOpenDialog(frame);
            
            if (result == JFileChooser.APPROVE_OPTION) {
                File lineFile = jfc.getSelectedFile();
                Path linePath = Paths.get(lineFile.getAbsolutePath());
                List<String> lineLines = Files.readAllLines(linePath);                
            for (String lineLine : lineLines){
                try{
                String[] lineParts = lineLine.split(",");
                int invoiceNum = Integer.parseInt(lineParts[0]);
                String itemName = lineParts[1];
                double itemPrice = Integer.parseInt(lineParts[2]);
                int count = Integer.parseInt(lineParts[3]);
                Invoice inv = null;

                for (Invoice invoice : invoicesList) {
                    if (invoice.getNum()== invoiceNum){
                        inv = invoice ;
                        break;
                    }
                }
           
                Line line = new Line(itemName,itemPrice,count , inv);
                inv.getLines().add(line);
                
                }catch(Exception ex){
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(frame,"Line format error","Error",JOptionPane.ERROR_MESSAGE);
                }
                
                
                System.out.println("Check point");
            }
            frame.setInvoices(invoicesList);
            InvoicesTableModel invoicesTableModel=new InvoicesTableModel(invoicesList);
            frame.setInvoicesTableModel(invoicesTableModel);
            frame.getInvoiceTable().setModel(invoicesTableModel);
            frame.getInvoicesTableModel().fireTableDataChanged(); 

            }
            }
        }
        }catch (IOException ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Cannot Read , File not found", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveFile() {
        ArrayList<Invoice> invoices = frame.getInvoices();
        String headers = "";
        String lines = "";
        for (Invoice invoice : invoices) {
            String invoiceCSV = invoice.getCSVFile();
            headers += invoiceCSV;
            headers += "\n"; 
            
            for (Line line : invoice.getLines()) {
                String lineCSV = line.getCSVFile();
                lines += lineCSV;
                lines += "\n";
            }
        }
        System.out.println("Check point");
        try {
            JFileChooser jfc = new JFileChooser();
            int result = jfc.showSaveDialog(frame);
            if (result == JFileChooser.APPROVE_OPTION) {
                File headerFile = jfc.getSelectedFile();
                FileWriter hfw = new FileWriter(headerFile);
                hfw.write(headers);
                hfw.flush();
                hfw.close();
                result = jfc.showSaveDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File lineFile = jfc.getSelectedFile();
                    FileWriter lfw = new FileWriter(lineFile);
                    lfw.write(lines);
                    lfw.flush();
                    lfw.close();
                }
            }
        } catch (Exception ex) {
             ex.printStackTrace();
             JOptionPane.showMessageDialog(frame, "File path is not found", "Error", JOptionPane.ERROR_MESSAGE);
        }

      
       
    }

    private void createNewInvoice() {
        invoDialog = new InvoiceDialog(frame);
        invoDialog.setVisible(true);
    }

    private void deleteInvoice() {
        int selectedRow = frame.getInvoiceTable().getSelectedRow();
        if (selectedRow != -1){
            frame.getInvoices().remove(selectedRow);
            frame.getInvoicesTableModel().fireTableDataChanged();
        
        }
    }

    private void save() {
        lineDialog = new LineDialog(frame);
        lineDialog.setVisible(true);
    }

    private void cancel() {
        int selectedInvoice = frame.getInvoiceTable().getSelectedRow();
        int selectedRow = frame.getLineTable().getSelectedRow();
        if (selectedInvoice != -1 && selectedRow != -1  ){
            Invoice invoice = frame.getInvoices().get(selectedInvoice);
            invoice.getLines().remove(selectedRow);
            LinesTableModels linesTableModels = new LinesTableModels(invoice.getLines());
            frame.getLineTable().setModel(linesTableModels);
            linesTableModels.fireTableDataChanged();
    }

    }

    private void CancelCreatenewInvoice() {
        invoDialog.setVisible(false);
        invoDialog.dispose();
        invoDialog = null;
    }



    private void CreateNewLineOK() {
        
        String item = lineDialog.getItNameField().getText();
        String countStr = lineDialog.getItCountField().getText();
        String priceStr = lineDialog.getItPriceField().getText();
        int count = Integer.parseInt(countStr);
        double price = Double.parseDouble(priceStr);
        int selectedInvoice = frame.getInvoiceTable().getSelectedRow();
        if(selectedInvoice != -1){
            Invoice invoice = frame.getInvoices().get(selectedInvoice);
            Line lines = new Line(item , price, count, invoice);
            invoice.getLines().add(lines);
            LinesTableModels linesTableModels = (LinesTableModels) frame.getLineTable().getModel();
            linesTableModels.fireTableDataChanged();
            frame.getInvoicesTableModel().fireTableDataChanged();
           }
        lineDialog.setVisible(false);
        lineDialog.dispose();
        lineDialog = null;
    }
    
     private void CreatenewInvoiceSave() {
        String date = invoDialog.getDateField().getText();
        String customer = invoDialog.getCustomerField().getText();
        int num = frame.getNextInvNum();
         try {
              String[] dateParts = date.split("-");
            if (dateParts.length < 3) {
                JOptionPane.showMessageDialog(frame, "Wrong date format", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                int day = Integer.parseInt(dateParts[0]);
                int month = Integer.parseInt(dateParts[1]);
                int year = Integer.parseInt(dateParts[2]);
                if (day > 31 || month > 12) {
                    JOptionPane.showMessageDialog(frame, "Wrong date format", "Error", JOptionPane.ERROR_MESSAGE);
                } else {         
                    Invoice invoice = new Invoice(num , date , customer);
                    frame.getInvoices().add(invoice);
                    frame.getInvoicesTableModel().fireTableDataChanged();
                    invoDialog.dispose();
                    invoDialog = null;
                }
            }
     } 
         catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Wrong date format", "Error", JOptionPane.ERROR_MESSAGE);
        }}
    private void cancelCreateNewLine() {
        lineDialog.setVisible(false);
        lineDialog.dispose();
        lineDialog = null;
    }
}
