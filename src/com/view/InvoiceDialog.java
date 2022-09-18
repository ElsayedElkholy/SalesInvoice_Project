
package com.view;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
public class InvoiceDialog  extends JDialog{
    private JTextField customerField;
    private JTextField dateField;
    private JButton cancelButton;
    private JButton saveButton;
    private JLabel customerNameLabel;
    private JLabel dateLabel;
    
    
    public InvoiceDialog(InvoiceFirstFrame frame){
        customerNameLabel=new JLabel("Customer Name: ");
        customerField=new JTextField(20);
        dateLabel=new JLabel("Invoice Date: ");
        dateField=new JTextField(20);
        saveButton=new JButton("Save");
        cancelButton=new JButton("Cancel");
        saveButton.setActionCommand("CreatenewInvoiceSave");
        cancelButton.setActionCommand("CancelCreatenewInvoice");
        
        saveButton.addActionListener(frame.getController());
        cancelButton.addActionListener(frame.getController());
        setLayout(new GridLayout(3,2));
        add(dateLabel);
        add(dateField);
        add(customerNameLabel);
        add(customerField);
        add(saveButton);
        add(cancelButton);
        
        pack();
        
    }

    public JTextField getCustomerField() {
        return customerField;
    }

    public JTextField getDateField() {
        return dateField;
    }
    
}