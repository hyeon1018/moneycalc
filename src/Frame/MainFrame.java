package Frame;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.JButton;

import java.awt.GridLayout;

import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Locale;
import java.util.Vector;

import javax.swing.UIManager;

import Model.Account;
import Model.Describe;
import Model.SqlConnection;

import java.awt.SystemColor;


public class MainFrame extends JFrame implements View{
	private JPanel contentPane;

	//Account
	private Account selAccount = null; //선택 계좌
	private JComboBox selectBox; //계좌 선택
	private JTextArea decribeText; //계좌 설명;
	private JLabel moneyBodyLabel;	//계좌 잔액

	//Quick
	private Describe selQuick; //선택 퀵
	private JComboBox QuickBox;	//퀵 선택
	private JLabel lblNewLabel_2; //
	private JLabel label_1;
	private JLabel label;	//지출 / 수입
	private JLabel lblNewLabel_3;

	/** Create the frame. **/
	public MainFrame() {
		Main.setUIFont(new javax.swing.plaf.FontUIResource("Malgun Gothic",Font.PLAIN,13));

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel titlePanel = new JPanel();
		contentPane.add(titlePanel, BorderLayout.NORTH);

		JLabel lblMoneycacl = new JLabel("MoneyCalc");
		lblMoneycacl.setFont(new Font("Malgun Gothic", Font.PLAIN, 25));
		titlePanel.add(lblMoneycacl);

		JPanel mainPanel = new JPanel();
		contentPane.add(mainPanel, BorderLayout.CENTER);
		mainPanel.setLayout(new GridLayout(1, 2, 0, 0));

		JPanel accountPanel = new JPanel();
		mainPanel.add(accountPanel);
		accountPanel.setLayout(null);

		selectBox = new JComboBox();
		selectBox.setBounds(12, 39, 212, 25);
		accountPanel.add(selectBox);
		selectBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				updateAccountInfo();
			}
		});

		decribeText = new JTextArea();
		decribeText.setBackground(UIManager.getColor("Button.background"));
		decribeText.setEditable(false);
		decribeText.setBounds(12, 74, 212, 52);
		accountPanel.add(decribeText);

		JLabel moneyHeadLable = new JLabel("잔액 : ");
		moneyHeadLable.setBounds(12, 145, 39, 16);
		accountPanel.add(moneyHeadLable);

		moneyBodyLabel = new JLabel("0");
		moneyBodyLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		moneyBodyLabel.setBounds(59, 146, 125, 16);
		accountPanel.add(moneyBodyLabel);

		JLabel moneyTailLabel = new JLabel("원");
		moneyTailLabel.setBounds(196, 145, 28, 16);
		accountPanel.add(moneyTailLabel);

		JButton selectButton = new JButton("계좌 선택");
		selectButton.setBounds(12, 174, 212, 39);
		accountPanel.add(selectButton);
		selectButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(selAccount.getName() != null){
					try {
						ViewFrame v = new ViewFrame(selAccount);
					} catch (SQLException e1) {
					}
				}
			}
		});

		JButton editButton = new JButton("계좌 편집");
		editButton.setBounds(12, 226, 97, 25);
		accountPanel.add(editButton);
		editButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new AddEditAccount(selAccount, MainFrame.this);
			}
		});

		JButton createButton = new JButton("계좌 생성");
		createButton.setBounds(127, 226, 97, 25);
		accountPanel.add(createButton);
		createButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Make add Account Frame, and Connect.
				new AddEditAccount(new Account(), MainFrame.this);
			}
		});


		JLabel lblNewLabel = new JLabel("계좌를 선택해 주세요.");
		lblNewLabel.setFont(new Font("Malgun Gothic", Font.PLAIN, 20));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(12, 13, 212, 25);
		accountPanel.add(lblNewLabel);

		JPanel quickPanel = new JPanel();
		mainPanel.add(quickPanel);
		quickPanel.setLayout(null);

		QuickBox = new JComboBox();
		QuickBox.setBounds(12, 38, 212, 25);
		quickPanel.add(QuickBox);
		QuickBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				updateQuickInfo();
			}
		});

		JLabel QuickLabel = new JLabel("빠른 기록");
		QuickLabel.setHorizontalAlignment(SwingConstants.CENTER);
		QuickLabel.setFont(new Font("Malgun Gothic", Font.PLAIN, 20));
		QuickLabel.setBounds(12, 13, 212, 25);
		quickPanel.add(QuickLabel);



		JButton QuickButton = new JButton("빠른 기록");
		QuickButton.setBounds(12, 176, 212, 39);
		quickPanel.add(QuickButton);
		QuickButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(JOptionPane.showConfirmDialog(null, selQuick.getDesc() +"를 기록합니까?", "기록 확인", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_NO_OPTION)
				{
					selQuick.setCal(Calendar.getInstance());
					selQuick.addDescribe();
					updateAccountInfo();
				};
			}
		});
		
		
		JButton btnNewButton_2 = new JButton("편집");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new AddEditQuick(selQuick, MainFrame.this);
			}
		});
		btnNewButton_2.setBounds(12, 228, 97, 25);
		quickPanel.add(btnNewButton_2);

		JButton btnNewButton_4 = new JButton("추가");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new AddEditQuick(new Describe(), MainFrame.this);
			}
		});
		btnNewButton_4.setBounds(127, 228, 97, 25);
		quickPanel.add(btnNewButton_4);
		
		
		lblNewLabel_2 = new JLabel("원");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_2.setBounds(77, 147, 147, 16);
		quickPanel.add(lblNewLabel_2);

		label_1 = new JLabel();
		label_1.setHorizontalAlignment(SwingConstants.RIGHT);
		label_1.setBounds(77, 89, 147, 16);
		quickPanel.add(label_1);
		
		lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_3.setBounds(77, 118, 147, 16);
		quickPanel.add(lblNewLabel_3);

		label = new JLabel();
		label.setBounds(12, 147, 53, 16);
		quickPanel.add(label);	
		
		JLabel lblNewLabel_1 = new JLabel("계좌 :");
		lblNewLabel_1.setBounds(12, 89, 53, 16);
		quickPanel.add(lblNewLabel_1);
		
		JLabel label_2 = new JLabel("분류 : ");
		label_2.setBounds(12, 118, 53, 16);
		quickPanel.add(label_2);

		JPanel buttonPanel = new JPanel();
		contentPane.add(buttonPanel, BorderLayout.SOUTH);

		JButton btnUpdate = new JButton("새로고침");
		buttonPanel.add(btnUpdate);
		btnUpdate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				updateView();
			}
		});

		JButton btnNewButton_3 = new JButton("통계");
		buttonPanel.add(btnNewButton_3);

		JButton btnNewButton_1 = new JButton("분류 관리");
		buttonPanel.add(btnNewButton_1);
		btnNewButton_1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new AdminSortFrame();
			}
		});

		JButton btnNewButton = new JButton("종료");
		buttonPanel.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
		});


		updateView();
	}

	public void updateAccountSelect(){
		int tmp = selectBox.getSelectedIndex();

		ResultSet rs = SqlConnection.getInstance().sendQuery("SELECT ACCOUNTNAME FROM ACCOUNT ORDER BY ACCOUNTNO ASC");
		Vector v = new Vector();
		try {
			while(rs.next()){
				v.add(rs.getString(1));
			}
		} catch (SQLException e) {

		}
		selectBox.setModel(new DefaultComboBoxModel(v));


		try{
			selectBox.setSelectedIndex(tmp);
		}catch(Exception e){
			selectBox.setSelectedIndex(-1);
		}

		updateAccountInfo();
	}
	public void updateAccountInfo(){
		String acctName = (String)(selectBox.getSelectedItem());
		ResultSet rs = SqlConnection.getInstance().sendQuery("SELECT * FROM ACCOUNT WHERE ACCOUNTNAME = '" + acctName + "';");
		try{
			rs.next();
			selAccount = Account.getAccount(rs);
			decribeText.setText(selAccount.getDesc());
			moneyBodyLabel.setText(Integer.toString(selAccount.getMoney()));

		}catch(Exception e){
			decribeText.setText("");
			moneyBodyLabel.setText("");
		}
	}

	public void updateQuickSelect(){
		int tmp = QuickBox.getSelectedIndex();

		ResultSet rs = SqlConnection.getInstance().sendQuery("SELECT COMMENT FROM QUICK ORDER BY NUM ASC");
		Vector v = new Vector();
		try {
			while(rs.next()){
				v.add(rs.getString(1));
			}
		} catch (SQLException e) {
		}
		QuickBox.setModel(new DefaultComboBoxModel(v));

		try{
			QuickBox.setSelectedIndex(tmp);
		}catch(Exception e){
			QuickBox.setSelectedIndex(-1);
		}
		updateQuickInfo();
	}
	public void updateQuickInfo(){
		String quickName = (String)(QuickBox.getSelectedItem());
		ResultSet rs = SqlConnection.getInstance().sendQuery("SELECT * FROM QUICK WHERE COMMENT = '" + quickName + "';");
		try{
			rs.next();
			selQuick = new Describe(rs);
			lblNewLabel_2.setText(Integer.toString(Math.abs(selQuick.getAmount())) + "원");

			Account a = Account.getAccount(selQuick.getAccountNum());
			label_1.setText(a.getName());

			lblNewLabel_3.setText(SqlConnection.getInstance().getSortName(selQuick.getSortNum()));
			
			if(selQuick.getAmount() > 0){
				label.setText("수입");
			}else{
				label.setText("지출");
			}
			
			
			

		}catch(Exception e){
			decribeText.setText("");
			moneyBodyLabel.setText("");
		}
	}

	@Override
	public void updateView(){
		updateAccountSelect();
		updateQuickSelect();
	}
}
