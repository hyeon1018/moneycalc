package Frame;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;

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
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.JButton;

import Model.Describe;
import Model.SqlConnection;

//Use made Picture

public class AddEditDesc extends JFrame {

	private JPanel contentPane;
	private JTextField textField;

	//TIME
	JSpinner spinner;
	JSpinner spinner_1;
	JSpinner spinner_2;
	JSpinner spinner_4;
	JSpinner spinner_5;

	JComboBox comboBox_1;
	JComboBox comboBox_2;
	JComboBox comboBox;
	JEditorPane editorPane;
	//image
	JLabel imagelabel;
	Dimension di;

	View view;

	Describe desc;

	/** Create the frame. **/
	public AddEditDesc(Describe d, View vf) {

		this.view = vf;
		this.desc = d;

		
		
		Main.setUIFont(new javax.swing.plaf.FontUIResource("Malgun Gothic",Font.PLAIN,13));
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 400, 300);
		setTitle("Add/Edit Describe - No." + desc.getNum());
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

		comboBox = new JComboBox();
		setAcctBox();
		comboBox.setBounds(12, 186, 173, 22);
		contentPane.add(comboBox);

		comboBox_1 = new JComboBox();
		setSortBox();
		comboBox_1.setBounds(12, 221, 173, 22);
		contentPane.add(comboBox_1);

		comboBox_2 = new JComboBox();
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
		panel.setBounds(197, 13, 173, 160);
		contentPane.add(panel);
		imagelabel = new JLabel();
		imagelabel.setHorizontalAlignment(JLabel.CENTER);
		panel.add(imagelabel);
		di = panel.getSize();

		JButton btnNewButton = new JButton("사진변경");
		btnNewButton.setBounds(197, 185, 89, 25);
		contentPane.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fileChoose();
			}
		});

		JButton btnRemove = new JButton("삭제");
		btnRemove.setBounds(298, 185, 72, 25);
		contentPane.add(btnRemove);
		btnRemove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				desc.setImage(null);
				setValue();
			}
		});

		JButton button = new JButton("기록");
		button.setBounds(197, 220, 89, 25);
		contentPane.add(button);
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				confirm();
			}
		});

		JButton button_1 = new JButton("취소");
		button_1.setBounds(298, 220, 72, 25);
		contentPane.add(button_1);
		button_1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
			}
		});


		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setValue();
		this.setVisible(true);
	}

	public void setValue(){

		this.setTitle(Integer.toString(desc.getNum()));

		spinner.setValue(desc.getCal().get(Calendar.YEAR));
		spinner_1.setValue(desc.getCal().get(Calendar.MONTH) + 1);
		spinner_2.setValue(desc.getCal().get(Calendar.DATE));
		spinner_4.setValue(desc.getCal().get(Calendar.HOUR));
		spinner_5.setValue(desc.getCal().get(Calendar.MINUTE));

		if(desc.getAmount() < 0 ){
			comboBox_2.setSelectedIndex(1);
		}

		textField.setText(Integer.toString(Math.abs(desc.getAmount())));

		if(desc.getDesc() != null){
			editorPane.setText(desc.getDesc());
		}

		try{
			imagelabel.setIcon(desc.getScaledImage(di));
		}catch(Exception e){
			e.printStackTrace();
		}

		ResultSet rs1 = SqlConnection.getInstance().sendQuery("SELECT ACCOUNTNAME FROM ACCOUNT WHERE ACCOUNTNO = " + desc.getAccountNum());
		try {
			rs1.next();
			String s = rs1.getString(1);
			comboBox.setSelectedItem(s);
		} catch (Exception e) {
			comboBox.setSelectedIndex(-1);
		}

		ResultSet rs2 = SqlConnection.getInstance().sendQuery("SELECT SORTINFO FROM SORT WHERE SORTNO = " + desc.getSortNum());
		try {
			rs2.next();
			String s = rs2.getString(1);
			comboBox_1.setSelectedItem(s);
		} catch (Exception e) {
			comboBox_1.setSelectedIndex(-1);
		}
	}

	public void getValue() throws SQLException, NullPointerException, ParseException{
		desc.getCal().set(Calendar.YEAR, (int)spinner.getValue());
		desc.getCal().set(Calendar.MONTH, (int)spinner_1.getValue() -1);
		desc.getCal().set(Calendar.DATE, (int)spinner_2.getValue());
		desc.getCal().set(Calendar.HOUR, (int)spinner_4.getValue());
		desc.getCal().set(Calendar.MINUTE, (int)spinner_5.getValue());

		desc.setDesc(editorPane.getText());

		int a = Integer.parseInt(textField.getText());
		if(comboBox_2.getSelectedIndex() == 1){
			a = -a;
		}
		desc.setAmount(a);

		ResultSet rs1 = SqlConnection.getInstance().sendQuery("SELECT ACCOUNTNO FROM ACCOUNT WHERE ACCOUNTNAME = '" + comboBox.getSelectedItem() +"';");

		rs1.next();
		desc.setAccountNum(rs1.getInt(1));
		
		try{
			ResultSet rs2 = SqlConnection.getInstance().sendQuery("SELECT SORTNO FROM SORT WHERE SORTINFO = '" + comboBox_1.getSelectedItem() + "';");
			rs2.next();
			desc.setSortNum(rs2.getInt(1));
		}catch(Exception e){
			desc.setAutoSortNum(editorPane.getText());
		}
	}

	public void confirm(){
		try {
			getValue();
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "오류가 발생하였습니다. 입력을 확인해 주세요", "ERROR", JOptionPane.ERROR_MESSAGE);
		}


		if(desc.isWrited()){
			desc.editDescribe();
		}
		else{
			desc.addDescribe();
		}


		view.updateView();
		dispose();

	}

	public void fileChoose(){
		try {
			getValue();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JFileChooser jfc = new JFileChooser();
		int choice = jfc.showOpenDialog(this);
		if(choice == JFileChooser.APPROVE_OPTION){
			File file = jfc.getSelectedFile();
			desc.setImage(new ImageIcon(file.getAbsolutePath()));
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
		comboBox.setModel(new DefaultComboBoxModel(v));
	}

	public void setSortBox(){
		ResultSet rs = SqlConnection.getInstance().sendQuery("SELECT SORTINFO FROM SORT");
		Vector<String> v = new Vector<String>();
		v.add(null);
		try{
			while(rs.next()){
				v.add(rs.getString(1));
			}
		}catch(Exception e){


		}
		comboBox_1.setModel(new DefaultComboBoxModel(v));
	}
}
