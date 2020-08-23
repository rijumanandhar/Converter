import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * The main driver program for the GUI based conversion program.
 * 
 * @author Riju Manandhar 77189982
 */
public class Converter {
	 
    public static void main(String[] args) {
    	
        JFrame frame = new JFrame("Converter"); //creating a frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBackground(new Color(158,215,246));
        
        JPanel masterpanel= new JPanel(); //creating a masterpanel
        
        masterpanel.setLayout(new BoxLayout(masterpanel, BoxLayout.PAGE_AXIS)); //box layout for master panel
 
        MainPanel panel = new MainPanel();
        frame.setJMenuBar(panel.setupMenu());//adding the menubar
		panel.setBackground(new Color(226, 243, 252));
        
        CurrencyPanel currencyPanel= new CurrencyPanel();
        currencyPanel.setBackground(new Color(240, 249, 253));
        currencyPanel.setBorder(BorderFactory.createLineBorder(new Color(158,215,246)));
        
        masterpanel.add(panel); //adding panel 1 to masterpanel
        masterpanel.add(currencyPanel); //adding currency Panel to masterpanel
        
    
        frame.getContentPane().add(masterpanel); //adding masterpanel to the frame
        
        panel.currencypanel = currencyPanel;
        currencyPanel.mainpanel= panel;
        
        
        frame.pack();
        frame.setVisible(true);
        frame.setSize(600, 600);
        frame.setLocationRelativeTo(null);
    }
}

