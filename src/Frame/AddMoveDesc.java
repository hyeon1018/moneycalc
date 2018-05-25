package Frame;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JLabel;
import javax.swing.SpinnerListModel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.JEditorPane;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Vector;

import javax.swing.JButton;

import Model.Account;
import Model.Describe;
import Model.SqlConnection;

//Use made Picture

public class AddMoveDesc extends JFrame {

	Describe from = new Describe();
	Describe to = new Describe();
	Dimension di;
	
	
	private JPanel contentPane;
	private JTextField textField;
	
	JSpinner spinner;
	JSpinner spinner_1;
	JSpinner spinner_2;
	JSpinner spinner_4;
	JSpinner spinner_5;
	JComboBox comboBoxFrom;
	JComboBox comboBoxTo;
	JEditorPane editorPane;
	
	
	ViewFrame vf;

	JLabel imagelabel;
	
	/**
	 * Create the frame.
	 */
	public AddMoveDesc(ViewFrame vf) {
		Main.setUIFont(new javax.swing.plaf.FontUIResource("Malgun Gothic",Font.PLAIN,13));
		this.vf = vf;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 400, 300);
		setTitle("Move");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(2015, 1950, 2500, 1));
		spinner.setBounds(12, 13, 61, 22);
		contentPane.add(spinner);
		
		JLabel label = new JLabel(" / ");
		label.setBounds(74, 13, 18, 22);
		contentPane.add(label);
		
		spinner_1 = new JSpinner();
		spinner_1.setModel(new SpinnerNumberModel(5, 1, 12, 1));
		spinner_1.setBounds(85, 13, 44, 22);
		contentPane.add(spinner_1);
		
		JLabel label_1 = new JLabel(" / ");
		label_1.setBounds(127, 13, 18, 22);
		contentPane.add(label_1);
		
		spinner_2 = new JSpinner();
		spinner_2.setModel(new SpinnerNumberModel(20, 1, 31, 1));
		spinner_2.setBounds(141, 13, 44, 22);
		contentPane.add(spinner_2);
		
		spinner_4 = new JSpinner();
		spinner_4.setModel(new SpinnerNumberModel(0, 0, 23, 1));
		spinner_4.setBounds(84, 48, 44, 22);
		contentPane.add(spinner_4);
		
		spinner_5 = new JSpinner();
		spinner_5.setModel(new SpinnerNumberModel(0, 0, 60, 1));
		spinner_5.setBounds(141, 48, 44, 22);
		contentPane.add(spinner_5);
		
		JLabel label_2 = new JLabel(" / ");
		label_2.setBounds(127, 48, 18, 22);
		contentPane.add(label_2);
		
		comboBoxFrom = new JComboBox();
		comboBoxFrom.setModel(new DefaultComboBoxModel(new String[] {"[보내는 계좌]", "현금", "통장1", "통장2"}));
		comboBoxFrom.setBounds(12, 186, 173, 22);
		contentPane.add(comboBoxFrom);
		
		comboBoxTo = new JComboBox();
		comboBoxTo.setModel(new DefaultComboBoxModel(new String[] {"[받는 계좌]", "현금", "통장1", "통장2"}));
		comboBoxTo.setBounds(12, 221, 173, 22);
		contentPane.add(comboBoxTo);
		
		JComboBox comboBox_2 = new JComboBox();
		comboBox_2.setEnabled(false);
		comboBox_2.setModel(new DefaultComboBoxModel(new String[] {"입금", "출금"}));
		comboBox_2.setBounds(12, 151, 61, 22);
		contentPane.add(comboBox_2);
		
		textField = new JTextField();
		textField.setBounds(74, 151, 111, 22);
		contentPane.add(textField);
		textField.setColumns(10);
		
		editorPane = new JEditorPane();
		editorPane.setText("");
		editorPane.setBounds(12, 82, 173, 56);
		contentPane.add(editorPane);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(197, 13, 173, 126);
		contentPane.add(panel);
		imagelabel = new JLabel();
		imagelabel.setHorizontalAlignment(JLabel.CENTER);
		panel.add(imagelabel);
		di = panel.getSize();
		
		
		JButton btnNewButton = new JButton("사진 변경");
		btnNewButton.setBounds(197, 150, 89, 25);
		contentPane.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fileChoose();
			}
		});
		
		JButton btnRemove = new JButton("삭제");
		btnRemove.setBounds(298, 150, 72, 25);
		contentPane.add(btnRemove);
		
		JButton button = new JButton("기록");
		button.setBounds(197, 220, 81, 25);
		contentPane.add(button);
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				confirm();
			}
		});
		
		JButton button_1 = new JButton("취소");
		button_1.setBounds(290, 220, 81, 25);
		contentPane.add(button_1);
		button_1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		
		this.setVisible(true);
		setAcctBox();
		setValue();
	}
	
	public void setValue(){
		
		this.setTitle(Integer.toString(from.getNum()));
		
		spinner.setValue(from.getCal().get(Calendar.YEAR));
		spinner_1.setValue(from.getCal().get(Calendar.MONTH) + 1);
		spinner_2.setValue(from.getCal().get(Calendar.DATE));
		spinner_4.setValue(from.getCal().get(Calendar.HOUR));
		spinner_5.setValue(from.getCal().get(Calendar.MINUTE));
		
		try{
			imagelabel.setIcon(from.getScaledImage(di));
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public void getValue() throws SQLException, NullPointerException, ParseException{
		//From
		from.getCal().set(Calendar.YEAR, (int)spinner.getValue());
		from.getCal().set(Calendar.MONTH, (int)spinner_1.getValue() -1);
		from.getCal().set(Calendar.DATE, (int)spinner_2.getValue());
		from.getCal().set(Calendar.HOUR, (int)spinner_4.getValue());
		from.getCal().set(Calendar.MINUTE, (int)spinner_5.getValue());
		
		from.setDesc(editorPane.getText());
		
		from.setAmount(Integer.parseInt(textField.getText())*(-1));
		
		ResultSet rs1 = SqlConnection.getInstance().sendQuery("SELECT ACCOUNTNO FROM ACCOUNT WHERE ACCOUNTNAME = '" + comboBoxFrom.getSelectedItem() +"';");
		
		rs1.next();
		from.setAccountNum(rs1.getInt(1));
		
		//to
		to.getCal().set(Calendar.YEAR, (int)spinner.getValue());
		to.getCal().set(Calendar.MONTH, (int)spinner_1.getValue() -1);
		to.getCal().set(Calendar.DATE, (int)spinner_2.getValue());
		to.getCal().set(Calendar.HOUR, (int)spinner_4.getValue());
		to.getCal().set(Calendar.MINUTE, (int)spinner_5.getValue());
		
		to.setDesc(editorPane.getText());
		
		to.setAmount(Integer.parseInt(textField.getText()));
		
		ResultSet rs2 = SqlConnection.getInstance().sendQuery("SELECT ACCOUNTNO FROM ACCOUNT WHERE ACCOUNTNAME = '" + comboBoxTo.getSelectedItem() +"';");
		rs2.next();
		to.setAccountNum(rs2.getInt(1));
		
	}
	
	public void confirm(){
		try {
			getValue();
			
			from.addDescribe();
			to.addDescribe();
			
			vf.updateView();
			dispose();
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "오류가 발생하였습니다. 입력을 확인해 주세요", "ERROR", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void fileChoose(){
		try {
			getValue();
		} catch (NullPointerException | SQLException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JFileChooser jfc = new JFileChooser();
		int choice = jfc.showOpenDialog(this);
		if(choice == JFileChooser.APPROVE_OPTION){
			File file = jfc.getSelectedFile();
			from.setImage(new ImageIcon(file.getAbsolutePath()));
		}
		
		setValue();
		
	}
	
	public void setAcctBox(){
		ResultSet rs = SqlConnection.getInstance().sendQuery("SELECT ACCOUNTNAME FROM ACCOUNT");
		Vector<String> v = new Vector<String>();
		try{
			while(rs.next()){
				v.add(rs.getString(1));
			}
			
		}catch(Exception e){
			
		}
		comboBoxFrom.setModel(new DefaultComboBoxModel(v));
		comboBoxTo.setModel(new DefaultComboBoxModel(v));
	}
	
}
