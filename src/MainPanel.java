import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * The main graphical panel used to display conversion components.
 * 
 * This is the starting point for the assignment.
 * 
 * The variable names have been deliberately made vague and generic, and most
 * comments have been removed.
 * 
 * You may want to start by improving the variable names and commenting what the
 * existing code does.
 * 
 * @author Riju Manandhar 77189982
 */
//manual
@SuppressWarnings("serial")
public class MainPanel extends JPanel {

	private final static String[] unit = { "inches/cm", "Miles/Kilometres", "Pounds/Kilograms", "Gallons/Litres",
			"Feet/Metres", "Celsius/Kelvin", "Acres/Hectare" };
	private JTextField userInput;
	private JLabel resultLabel;
	public static JLabel counter;
	private JComboBox<String> currencyCombo;
	private JButton clearButton;
	public static JCheckBox reverse;
	private ImageIcon fileImg, helpImg, exitImg, aboutImg, manualImg, importImg;
	public CurrencyPanel currencypanel;

	/**
	 * This functions sets up the menu and returns the setup of the menu bar
	 */
	public JMenuBar setupMenu() {

		JMenuBar menuBar = new JMenuBar();

		JMenu file = new JMenu("File");
		JMenu help = new JMenu("Help");

		// defines the path of image
		fileImg = new ImageIcon("file.png");
		helpImg = new ImageIcon("help.png");
		exitImg = new ImageIcon("exit.png");
		aboutImg = new ImageIcon("about.png");
		manualImg = new ImageIcon("manual.png");
		importImg= new ImageIcon("import.png");

		// adds Jmenu objects to the menu bar
		menuBar.add(file);
		menuBar.add(help);
		menuBar.setBorder(BorderFactory.createLineBorder(new Color(158,215,246)));
		menuBar.setBackground(new Color(240, 249, 253));

		file.setMnemonic('f'); // adds mnemonics to file Alt+f

		JMenuItem exit = new JMenuItem("Exit    (Alt+e)");
		file.add(exit);// adds exit inside the file
		file.setIcon(fileImg);// sets icon to file

		JMenuItem load = new JMenuItem("Load (Alt+l)");
		file.add(load); // adds load option in the menubar inside file
		load.addActionListener(new Import()); // calls the action listener to import files
		load.setMnemonic('l');// adds mnemocis tp load Alt+l
		load.setToolTipText("Click to select files"); // sets tooltips
		load.setIcon(importImg);
		load.setBackground(new Color(240, 249, 253));

		exit.setMnemonic('e');// adds mnemonics to exit Alt+e
		exit.setIcon(exitImg);// adds icon to exit
		exit.setToolTipText("Click to exit program"); // sets tool tips for exit
		exit.setBackground(new Color(240, 249, 253));

		JMenuItem about = new JMenuItem("About  (Alt+a)");
		help.add(about);// adds help inside about
		help.setMnemonic('h');// adds mnemonics to help Alt+h
		help.setIcon(helpImg);// sets icon to help

		about.setMnemonic('a');// adds mnemonics to about
		about.setToolTipText("About Us");// sets tool tips for about
		about.setIcon(aboutImg);// sets icon to about
		about.setBackground(new Color(240, 249, 253));

		JMenuItem manual = new JMenuItem("Manual   (Alt+m)");
		help.add(manual); // adds manual inside about
		manual.setMnemonic('m');// adds mnemonics to manual Alt+m
		manual.setIcon(manualImg);// sets icon to manual
		manual.addActionListener(new ManualListener());// calls action listener on clicking manual
		manual.setBackground(new Color(240, 249, 253));

		exit.addActionListener(new ExitListener());// calls action listener on clicking exit
		about.addActionListener(new AboutListener());// calls action listener on clicking about

		return menuBar; // returns the setup of the menu bar
	}

	/**
	 * This constructor initiates the panel for the application. Inside the panel
	 * there are convert button, clear button, labels, text box, combo box, reverse
	 * check box set up and added to the panel.
	 */
	public MainPanel() {

		ActionListener listener = new ConvertListener();

		currencyCombo = new JComboBox<String>(unit);// declares the string type for combo box
		//currencyCombo.addActionListener(listener); // convert values when option changed
		currencyCombo.setToolTipText("Select to convert");// sets tool tips for combo box

		JLabel inputLabel = new JLabel("Enter value:");

		JLabel counterLabel = new JLabel("Conversion Count : ");

		counter = new JLabel("0");// initiates the value of counter to 0
		counter.setToolTipText("Counts the number of times conversion happened");// sets tool tips for counter label

		JButton convertButton = new JButton("Convert");
		convertButton.addActionListener(listener); // convert values when the button is pressed
		convertButton.setToolTipText("Click to Convert");// sets tool tips for convert button
		convertButton.setBorder(BorderFactory.createLineBorder(new Color(158,215,246)));
		
		reverse = new JCheckBox("Reverse");
		reverse.setToolTipText("Reverse the conversion");// sets tool tips for reverse check box
		reverse.setBackground(new Color(226, 243, 252));

		clearButton = new JButton("Clear");
		clearButton.addActionListener(new ClearListener()); // calls for the action listener when pressed to clear the
															// label
		clearButton.setToolTipText("Click to clear everything");// sets tool tips for clear button
		clearButton.setBorder(BorderFactory.createLineBorder(new Color(158,215,246)));

		resultLabel = new JLabel("---");// stores the result after conversion
		userInput = new JTextField(5);// takes the number from the user for conversion
		userInput.setBorder(BorderFactory.createLineBorder(new Color(158,215,246)));
		userInput.setToolTipText("Enter a number to convert.");// sets tool tips for test field
		userInput.addActionListener(listener);// when return is pressed the action listener is called so the values are
												// converted

		// adds the objects to the panel
		add(currencyCombo);
		add(inputLabel);
		add(userInput);
		add(convertButton);
		add(resultLabel);
		add(clearButton);
		add(counterLabel);
		add(counter);
		add(reverse);

		// setting boundaries
		// setPreferredSize(new Dimension(800, 80));
		resultLabel.setForeground(Color.red);// changes the color of result label
		setPreferredSize(new Dimension(600, 400));
		setBackground(Color.LIGHT_GRAY);// adds background color
		currencyCombo.setBounds(50, 50, 150, 30);
		inputLabel.setBounds(250, 50, 150, 20);
		userInput.setBounds(233, 70, 100, 20);
		convertButton.setBounds(375, 50, 100, 40);
		reverse.setBounds(50, 150, 150, 20);
		resultLabel.setBounds(250, 100, 150, 20);
		clearButton.setBounds(375, 150, 100, 40);
		counterLabel.setBounds(233, 150, 150, 20);
		counter.setBounds(273, 170, 100, 20);
		setLayout(null);
	}

	/**
	 * This method clears all the values of the Input textbox, result label, counter
	 * of Main Panel and Currency Panel
	 */
	public void clear() {
		userInput.setText("");// clears the textfield
		resultLabel.setText("---");// removes any values from result
		counter.setText("0");// sets counter to 0
		currencypanel.currencyInput.setText("");// clears the text field in currency panel
		currencypanel.currencyResultLabel.setText("---"); // clears the result label in currency panel
	}

	/**
	 * This class implements the abstract class ActionListener and is called when
	 * events of convert button , clear button reverse selection, combo box
	 * selection and text box is triggered.
	 */
	private class ConvertListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			boolean checker = true; // checks if the value sent by user is valid or not.
			String text = userInput.getText().trim();// gets the value entered by the user and trims it.

			if (text.isEmpty() == false) {// if the value sent by user is not empty the program moves forward.
				double value = 0;
				int count = Integer.parseInt(counter.getText());// gets the value of counter which counts the number of
																// conversion.
				try {
					value = Double.parseDouble(text); // tries to convert the value sent by user from string to double
														// format.

				} catch (NumberFormatException e) { // if user enters invalid value error message is displayed.
					String message = "The number you have entered is not valid. Try Again.";
					JOptionPane.showMessageDialog(null, message, "Invalid Number", JOptionPane.ERROR_MESSAGE);
					checker = false; // if the value is not a number checker is set to false.
				}
				// the factor applied during the conversion
				double factor = 0;

				// the offset applied during the conversion.
				double offset = 0;
				String postfix = "";// stores the unit of conversion.

				if (reverse.isSelected()) { // when the reverse is checked the conversion is reversed.
					switch (currencyCombo.getSelectedIndex()) {// gets the selected option from the combobox.
					case 0: {// reverse inches/cm
						factor = 0.621371;
						postfix = "inches";
						break;
					}
					case 1: {// reverse Miles/Kilometres
						factor = 1.6093;
						postfix = "miles";
						break;
					}
					case 2: {// reverse Pounds/Kilograms
						factor = 2.20462;
						postfix = "pounds";
						break;
					}
					case 3: {// reverse Gallons/Litres
						factor = 0.264172;
						postfix = "gallons";
						break;
					}
					case 4: {// reverse Feet/Metres
						factor = 3.28084;
						postfix = "feet";
						break;
					}
					case 5: {// reverse Celsius/Kelvin
						factor = 1;
						offset = -273.15;
						postfix = "celsius";
						break;
					}
					case 6: {// reverse Acres/Hectare
						factor = 2.47105;
						postfix = "acres";
						break;
					}
					}

				} else { // if the reverse is not checked.
					switch (currencyCombo.getSelectedIndex()) { // gets the selected option from the combobox.

					case 0: {// inches/cm
						factor = 2.54;
						postfix = "cm";
						break;
					}
					case 1: {// Miles/Kilometres
						factor = 1.6093;
						postfix = "kilometers";
						break;
					}
					case 2: {// Pounds/Kilograms
						factor = 0.4536;
						postfix = "kilograms";
						break;
					}
					case 3: {// Gallons/Litres
						factor = 3.7853;
						postfix = "litres";
						break;
					}
					case 4: {// Feet/Metres
						factor = 0.3048;
						postfix = "meters";
						break;
					}
					case 5: {// Celsius/Kelvin
						factor = 1;
						offset = 273.15;
						postfix = "kelvin";
						break;
					}
					case 6: {// Acres/Hectare
						factor = 0.4047;
						postfix = "hectare";
						break;
					}
					}

				}

				// Setup the correct factor/offset values depending on required conversion

				DecimalFormat fmt = new DecimalFormat("0.##");// to display the result in two decimal format.
				double result = factor * value + offset; // formula for conversion.

				// label.setText(Double.toString(result));
				// to add the postfix to the result label
				String outcome = fmt.format(result); // to apply the decimal format to the result which converts the
														// result to string.
				if (checker) {// if checker is false this block is skipped.
					resultLabel.setText(outcome + " " + postfix); // sets the result with units in the result label.
					count++;// counts the number of conversion.
					counter.setText(Integer.toString(count));// converts the value of count into string and sets it to
																// counter label.
				}
			} else {// if null value is entered, an error message is displayed.
				String message = "The textbox is empty. Enter a value and Try Again";
				JOptionPane.showMessageDialog(null, message, "Null Value", JOptionPane.WARNING_MESSAGE);
			}
		}
	}

	/** 
	 * This action listener is triggered when clear button is pressed and calls the function clear to clear the labels and text fields of both panel
	 * */
	private class ClearListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			// if user clicks clearbutton it resets the records of previous conversions
			clear();
		}
	}

	/**
	 * This action listener is triggered when About is clicked from the menu bar and
	 * displays some information about the application
	 */
	private class AboutListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			ImageIcon abouImg = new ImageIcon("aboutBig.png");
			String description = "This program is a GUI based application designed to support conversion between different units. \n Author: Riju Manandhar (77189982) \n \u00a9 All rights reserved. 2018.";
			JOptionPane.showMessageDialog(null, description, "About", JOptionPane.INFORMATION_MESSAGE, abouImg);
		}

	}

	/**
	 * This action listener is triggered when Manual is clicked from the menu bar
	 * and displays some information about the mnemonics in the application
	 */
	private class ManualListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			String help = "File  (Alt+f) \n About  (Alt+a) \n Help  (Alt+h) \n Manual  (Alt+m) \n Import   (Alt+l) \n Exit  (Alt+e)";
			JOptionPane.showMessageDialog(null, help);
		}
	}
	
	/**
	 * This action listener is triggered when the Load from menu is clicked, it calls a method called chooser from Currency Panel to open the file
	 * */
	private class Import implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			currencypanel.chooser(); //calls the function chooser from Currency Panel
		}
	}

	/**
	 * This action listener is triggered when Exit is clicked. It exits the whole
	 * application
	 */
	private class ExitListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			System.exit(0);
			// System.exit(0) terminates the JVM therefore terminating the program.
		}
	}
}
