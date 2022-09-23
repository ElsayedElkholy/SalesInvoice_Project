package com.view;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class LineDialog extends  JDialog{
    private JTextField itNameField;
    private JTextField itCountField;
    private JTextField itPriceField;
    private JLabel itNamedLbl;
    private JLabel itCountLbl;
    private JLabel itPriceLbl;
    private JButton okButton;
    private JButton cancelButton;
    
    public LineDialog(InvoiceFirstFrame frame){
        itNameField=new JTextField(20);
        itNamedLbl=new JLabel("Line Name ");
        itCountField=new JTextField(20);
        itCountLbl=new JLabel("Line Count");
        itPriceField=new JTextField(20);
        itPriceLbl=new JLabel("Line Price");
        okButton=new JButton("OK");
        cancelButton=new JButton("Cancel");
        
        okButton.setActionCommand("CreateNewLineOK");
        cancelButton.setActionCommand("CancelCreateNewLine");
        
        okButton.addActionListener(frame.getController());
        cancelButton.addActionListener(frame.getController());
        setLayout(new GridLayout(4,2));
        add(itNamedLbl);
        add(itNameField);
        add(itCountLbl);
        add(itCountField);
        add(itPriceLbl);
        add(itPriceField);
        add(okButton);
        add(cancelButton);
        pack();
    }

    public JTextField getItNameField() {
        return itNameField;
    }

    public JTextField getItCountField() {
        return itCountField;
    }

    public JTextField getItPriceField() {
        return itPriceField;
    }
    
    
}
