import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

public class CurrencyPanel extends JPanel {

	private String[] currencyName = { "EUR", "USD", "AUD", "CAD", "ISK", "AED", "ZAR", "THB" }; // stores the currency
																								// for calculation
																								// incase no file is
																								// uploaded
	private String[] symbol = { "€", "$", "$", "$", "kr", "د.إ", "R", "฿" }; // stores symbols display incase no file is
																				// uploaded
	private Double[] factor = { 1.359, 1.34, 1.756, 1.71, 140.84, 4.92, 17.84, 43.58 }; // stores the rate
	public JTextField currencyInput;
	public JLabel currencyInputLabel, currencyResultLabel;
	private JComboBox<String> currencyCombo;
	private JButton convertButton;
	BufferedReader br; // object of buffer reader to read files
	String line;// stores the values from the file in order to split
	public MainPanel mainpanel; // object of main panel

	/**
	 * This constructor initiates the panel and add the necessary buttons, labels
	 * and text fields to the Currency Panel and adds necessary action listener
	 */
	public CurrencyPanel() {
		ActionListener listener = new ConvertListener();

		currencyCombo = new JComboBox<String>(currencyName);// declares the string type for combo box
		currencyCombo.setToolTipText("Select to convert");// sets tool tips for combo box

		JLabel inputLabel = new JLabel("Enter value: in GBP");

		convertButton = new JButton("Convert");
		convertButton.addActionListener(listener); // convert values when the button is pressed
		convertButton.setToolTipText("Click to Convert");// sets tool tips for convert button
		convertButton.setBorder(BorderFactory.createLineBorder(new Color(158,215,246)));
		

		currencyResultLabel = new JLabel("---");// stores the result after conversion
		
		currencyInput = new JTextField(5);// takes the number from the user for conversion
		currencyInput.setToolTipText("Enter a number to convert.");// sets tool tips for test field
		currencyInput.addActionListener(listener);// when return is pressed the action listener is called so the values
													// are
													// converted
		currencyInput.setBorder(BorderFactory.createLineBorder(new Color(158,215,246)));

		// adds the objects to the panel
		add(currencyCombo);
		add(inputLabel);
		add(currencyInput);
		add(convertButton);
		add(currencyResultLabel);

		// setting boundaries
		currencyResultLabel.setForeground(Color.red);// changes the color of result label
		setPreferredSize(new Dimension(600, 400));
		setBackground(Color.LIGHT_GRAY);// adds background color
		currencyCombo.setBounds(50, 50, 150, 30);
		inputLabel.setBounds(230, 50, 150, 20);
		currencyInput.setBounds(233, 70, 100, 20);
		convertButton.setBounds(375, 50, 100, 40);
		currencyResultLabel.setBounds(250, 100, 150, 20);

		setLayout(null);

	}

	/**
	 * This function is called then load is clicked from the menu. It opens a
	 * dialogue box to select the desired file and loads them into the system. The
	 * file is then read by buffer reader and the contents of the file is split
	 * using the delimeter ','. The first is considered to be the currency name,
	 * second is considered the factor and the third is the symbol of the particular
	 * currency. These are then stored into an array and then loaded into the combo
	 * box.
	 */
	public void chooser() {
		String[] currency_a = new String[8];// puts the currency name from imported file
		String[] symbol_a = new String[8];// puts the currency symbols from imported file
		Double[] rate_a = new Double[8];// stores the rate from imported file
		JFileChooser fc = new JFileChooser(); // object of jfilechooser
		fc.setCurrentDirectory(new java.io.File(".")); // sets the current directory to the project's directory
		fc.setDialogTitle("Choose File");
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);// only files can be selected
		FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");// only selects text
																									// files
		fc.setFileFilter(filter);// applys the filter to jfilechooser
		fc.showOpenDialog(null);// opens the jfilechooser dialog box
		int i = 0; // to store values in array
		try {
			br = new BufferedReader(
					new InputStreamReader(new FileInputStream(fc.getSelectedFile().getAbsolutePath()), "UTF8"));
			System.out.println("This is works ");
			// reads the files with UTF-8 encoded file
			// gets the file chosen by jfilechooser
			// stores the file content in br
			try {
				line= br.readLine();
				System.out.println("This is line: "+line);
				
				while ((line = br.readLine()) != null) {// reads a line of text. A line is considered to be terminated
														// by any one of a line feed ('\n')
					
					// if the file hasn't ended, the loop continues

					String[] temp = line.split(",");// splits the content of the file using , as token and stores them
													// in array temp
					try {
						currency_a[i] = temp[0].trim();// puts the value of currency from the file
						// temp 0 stores the first word in line and stores it in currency
						if (temp[0].trim().isEmpty()) {// checks and notifies if there is no value in temp
							String msg = "The currency name may be missing in line " + (i + 1) + ".";
							JOptionPane.showMessageDialog(null, msg, "Invalid Currency", JOptionPane.ERROR_MESSAGE);// displays
																													// error
																													// message
						}

						try {// tries to parse the string into double and is only successful if there are
								// nothing but numbers in the string

							rate_a[i] = Double.parseDouble(temp[1].trim());// puts the value of rate from the file and
																			// converts in into double because
																			// everything is in strings in the file

						} catch (NumberFormatException e) {// if the number can't be parsed, error message is displayed
							String msg = "The rate may not be a numeric value in line " + (i + 1) + ".";
							JOptionPane.showMessageDialog(null, msg, "Invalid Number", JOptionPane.ERROR_MESSAGE);
						}
						if (temp[1].trim().isEmpty()) {// checks and notifies if there are errors
							String msg = "The rate may be missing in line " + (i + 1) + ".";
							JOptionPane.showMessageDialog(null, msg, "Invalid Number", JOptionPane.ERROR_MESSAGE);
						}

						symbol_a[i] = temp[2].trim(); // stores the value of symbols from the file
						if (temp[2].trim().isEmpty()) {
							String msg = "The currency symbol may be missing in line " + (i + 1) + ".";
							JOptionPane.showMessageDialog(null, msg, "Invalid Symbol", JOptionPane.ERROR_MESSAGE);
						}
					} catch (ArrayIndexOutOfBoundsException e) { // if there are more or less values in the line, array
																	// out of bound error occurs
						String msg = "The field delimiter may be missing or wrong field delimiter is used in line "
								+ (i + 1) + ".";
						JOptionPane.showMessageDialog(null, msg, "Invalid Delimeter", JOptionPane.ERROR_MESSAGE);

					}
					i++; // increases the value of i
				}
			} catch (IOException e) {// input or output operation is failed or interpreted
				String msg = "Input Error";
				JOptionPane.showMessageDialog(null, msg, "Invalid File", JOptionPane.ERROR_MESSAGE);
			}
		} catch (UnsupportedEncodingException e) { // if file type is not supported
			String msg = "File Not Supported";
			JOptionPane.showMessageDialog(null, msg, "Invalid File", JOptionPane.ERROR_MESSAGE);
		} catch (FileNotFoundException e) { // if file is not found
			String msg = "File Not Found";
			JOptionPane.showMessageDialog(null, msg, "Invalid File", JOptionPane.ERROR_MESSAGE);
		}

		currencyCombo.removeAllItems(); // once the values are added into the array, remove all the content of combobox

		for (int j = 0; j < currency_a.length; j++) {

			// overrides the previous values in the array with new ones from the file
			currencyName[j] = currency_a[j];
			symbol[j] = symbol_a[j];
			factor[j] = rate_a[j];
			if (currencyName[j]!=null && symbol[j]!=null && factor[j] != null) {
				currencyCombo.addItem(currency_a[j]);// adds new currency name into currency combo read from files
			}else {
				currencyCombo.addItem("Invalid");
			}
		}
	}

	/**
	 * This class implements the abstract class ActionListener and is called when
	 * events of convert button , clear button reverse selection, combo box
	 * selection and text box is triggered.
	 */
	private class ConvertListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {

			if (event.getSource() == convertButton || event.getSource() == currencyInput) {
				boolean checker = true, valid=false;// checks if there are number format errors or errors from file
				double amount = 0;
				String result = "";

				DecimalFormat fmt = new DecimalFormat("0.##");// for final result to be in the format of 0.00

				String text = currencyInput.getText().trim();// gets the input from the user

				if (text.isEmpty() == false) {// if the value is not empty

					double value = 0;
					int count = Integer.parseInt(mainpanel.counter.getText());// counts the number of conversions

					try {
						value = Double.parseDouble(text); // tries to convert the value sent by user from string to
															// double
															// format.

					} catch (NumberFormatException e) { // if user enters invalid value error message is displayed.
						String message = "The number you have entered is not valid. Try Again.";
						JOptionPane.showMessageDialog(null, message, "Invalid Number", JOptionPane.ERROR_MESSAGE);
						checker = false; // if the value is not number checker is set to false.
					}

					// if reverse from main panel is selected, conversion is done in reverse
					if (MainPanel.reverse.isSelected()) {
						for (int j = 0; j < currencyName.length; j++) {//runs the loop until the end of array
							if (currencyName[j]!=null && symbol[j]!=null && factor[j] != null) {
								//chooses the selected index and calculates amount accordingly and then adds gbp symbol to the result
								if (currencyCombo.getSelectedIndex() == j) {
									amount = value / factor[j];
									result = "£ " + fmt.format(amount);
									valid=true;
								}
							}
						}
					} else {//if reverse is not selected
						for (int j = 0; j < currencyName.length; j++) {//runs the loop until the end of array
								//chooses the selected index and calculates amount accordingly and then adds symbol to the result accordingly
							if (currencyName[j]!=null && symbol[j]!=null && factor[j] != null) {	
								if (currencyCombo.getSelectedIndex() == j) {
									amount = value * factor[j];
									result = symbol[j] + " " + fmt.format(amount);
									valid=true;
								}
							}
						}
					}

					if (checker&&valid) {// if checker and valid is false this block is skipped.
						currencyResultLabel.setText(result); // sets the result with units in the result label.
						count++;// counts the number of conversion.
						mainpanel.counter.setText(Integer.toString(count));// converts the value of count into string
																			// and sets it to counter label.
					}

				} else {// if text is empty, displays error message
					String message = "The textbox is empty. Enter a value and Try Again";
					JOptionPane.showMessageDialog(null, message, "Null Value", JOptionPane.WARNING_MESSAGE);
				}
			}

		}

	}
}
