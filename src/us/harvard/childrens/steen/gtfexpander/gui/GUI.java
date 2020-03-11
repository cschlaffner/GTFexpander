package us.harvard.childrens.steen.gtfexpander.gui;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

import us.harvard.childrens.steen.gtfexpander.worker.ExpansionType;
import us.harvard.childrens.steen.gtfexpander.worker.GTFexpander;
import javax.swing.JComboBox;

public class GUI extends JFrame implements ItemListener, ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final JFileChooser fc = new JFileChooser();
	private JFrame frmGtfExpander;
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frmGtfExpander.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		fc.setCurrentDirectory(new File(System.getProperty("user.home")));
		
		frmGtfExpander = new JFrame();
		frmGtfExpander.setTitle("GTF Expander");
		frmGtfExpander.setBounds(100, 100, 450, 240);
		frmGtfExpander.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {0, 0, 0};
		gridBagLayout.rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		frmGtfExpander.getContentPane().setLayout(gridBagLayout);
		
		JLabel lblNewLabel_2 = new JLabel("Add gene and transcript lines (required for PoGo)");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 13));
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_2.gridwidth = 2;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_2.gridx = 0;
		gbc_lblNewLabel_2.gridy = 0;
		frmGtfExpander.getContentPane().add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		JLabel lblNewLabel = new JLabel("Input GTF file");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 1;
		frmGtfExpander.getContentPane().add(lblNewLabel, gbc_lblNewLabel);
		
		textField_1 = new JTextField();
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.insets = new Insets(0, 0, 5, 5);
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.gridx = 0;
		gbc_textField_1.gridy = 2;
		frmGtfExpander.getContentPane().add(textField_1, gbc_textField_1);
		textField_1.setColumns(10);
		
		JButton btnNewButton = new JButton("Select");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fc.setMultiSelectionEnabled(false);
				fc.setAcceptAllFileFilterUsed(true);
				int refVal = fc.showOpenDialog(GUI.this);
				if(refVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					textField_1.setText(file.getAbsolutePath());
					String pathname = file.getAbsolutePath();
					String newname = pathname.replace(".gtf", "_exp.gtf");
					newname = pathname.replace(".gff", "_exp.gtf");
					textField.setText(newname);
				}
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton.gridx = 1;
		gbc_btnNewButton.gridy = 2;
		frmGtfExpander.getContentPane().add(btnNewButton, gbc_btnNewButton);
		
		JLabel lblNewLabel_1 = new JLabel("Output GTF file");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 3;
		frmGtfExpander.getContentPane().add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 0;
		gbc_textField.gridy = 4;
		frmGtfExpander.getContentPane().add(textField, gbc_textField);
		textField.setColumns(10);
		
		JLabel lblInputfileType = new JLabel("Inputfile Type");
		GridBagConstraints gbc_lblInputfileType = new GridBagConstraints();
		gbc_lblInputfileType.anchor = GridBagConstraints.WEST;
		gbc_lblInputfileType.insets = new Insets(0, 0, 5, 5);
		gbc_lblInputfileType.gridx = 0;
		gbc_lblInputfileType.gridy = 5;
		frmGtfExpander.getContentPane().add(lblInputfileType, gbc_lblInputfileType);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel<>(ExpansionType.values()));
		
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 0;
		gbc_comboBox.gridy = 6;
		frmGtfExpander.getContentPane().add(comboBox, gbc_comboBox);
		
		JButton btnNewButton_1 = new JButton("Select");
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				fc.setMultiSelectionEnabled(false);
				fc.setAcceptAllFileFilterUsed(true);
				int refVal = fc.showOpenDialog(GUI.this);
				if(refVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					textField.setText(file.getAbsolutePath());
				}
			}
		});
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton_1.gridx = 1;
		gbc_btnNewButton_1.gridy = 4;
		frmGtfExpander.getContentPane().add(btnNewButton_1, gbc_btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("START");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String inputfile = textField_1.getText();
				String outputfile = textField.getText();
				btnNewButton_2.setEnabled(false);
				final JOptionPane mapping2 = new JOptionPane();
				mapping2.setMessageType(JOptionPane.INFORMATION_MESSAGE);
				mapping2.setOptions(new Object[] {});
				mapping2.setMessage("GTF Expander is adding lines to your GTF file.\nPlease wait.\n");
				
				final JDialog mapping = mapping2.createDialog(GUI.this, "GTF Expander is working hard...");
				mapping.setLocationRelativeTo(GUI.this);
				mapping.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
				mapping.setModal(true);
				
				ExpansionType type = (ExpansionType)comboBox.getSelectedItem();
				
				class Worker extends SwingWorker<Void,Void> {
					private String input;
					private String output;
					
					public void setFiles(String input, String output) {
						this.input = input;
						this.output = output;
					}
					
					@Override
					protected Void doInBackground() {
						
						int exitcode = GTFexpander.expand(input, output, type);
						if(exitcode!=0) {
							JOptionPane.showMessageDialog(GUI.this, "GTF Expander terminated unexpectedly.\nPlease check input files and option settings and restart.", "GTF Expander Execution Error", JOptionPane.ERROR_MESSAGE);
						}
						
						return null;
					}
					
					@Override
					protected void done() {
						mapping.dispose();
					}
				};
				Worker myworker = new Worker();
				myworker.setFiles(inputfile, outputfile);
				myworker.execute(); 
				mapping.setVisible(true);
				try{
					myworker.get();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				JOptionPane.showMessageDialog(GUI.this, "GTF Expander terminated successfully.\nYou can now use the output GTF for genome mapping with PoGo.", "GTF Expander Finished", JOptionPane.OK_OPTION);
				btnNewButton.setEnabled(true);
			}
		});
		
		btnNewButton_2.setFont(new Font("Tahoma", Font.BOLD, 13));
		GridBagConstraints gbc_btnNewButton_2 = new GridBagConstraints();
		gbc_btnNewButton_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNewButton_2.gridwidth = 2;
		gbc_btnNewButton_2.gridx = 0;
		gbc_btnNewButton_2.gridy = 7;
		frmGtfExpander.getContentPane().add(btnNewButton_2, gbc_btnNewButton_2);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void itemStateChanged(ItemEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
