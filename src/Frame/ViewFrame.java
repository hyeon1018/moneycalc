package Frame;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.GridBagLayout;

import javax.swing.JButton;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Font;

import javax.swing.JTextField;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.ListSelectionModel;

import Model.Account;
import Model.Describe;
import Model.SqlConnection;


public class ViewFrame extends JFrame implements View{
	
	private JPanel contentPane;
	private JTextField textField;
	private JTable table;

	
	private JLabel accountNameLabel;
	private JLabel label;
	private JLabel lblNewLabel;

	
	private Account acct;

	/** Create the frame. **/
	public ViewFrame(Account acct) throws SQLException {
		this.acct = acct;
		Main.setUIFont(new javax.swing.plaf.FontUIResource("Malgun Gothic",Font.PLAIN,13));
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 500, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel TitlePanel = new JPanel();
		contentPane.add(TitlePanel, BorderLayout.NORTH);
		GridBagLayout gbl_TitlePanel = new GridBagLayout();
		gbl_TitlePanel.columnWidths = new int[] {200, 270};
		gbl_TitlePanel.rowHeights = new int[] {30, 30};
		gbl_TitlePanel.columnWeights = new double[]{0.0, 0.0};
		gbl_TitlePanel.rowWeights = new double[]{0.0, 0.0};
		TitlePanel.setLayout(gbl_TitlePanel);
		
		accountNameLabel = new JLabel();
		GridBagConstraints gbc_accountNameLabel = new GridBagConstraints();
		gbc_accountNameLabel.insets = new Insets(0, 0, 5, 5);
		gbc_accountNameLabel.gridx = 0;
		gbc_accountNameLabel.gridy = 0;
		TitlePanel.add(accountNameLabel, gbc_accountNameLabel);
		
		label = new JLabel("");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(0, 0, 5, 0);
		gbc_label.anchor = GridBagConstraints.EAST;
		gbc_label.gridx = 1;
		gbc_label.gridy = 0;
		TitlePanel.add(label, gbc_label);
		
		lblNewLabel = new JLabel("지갑에 들고 다니는 현금 량");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.gridwidth = 2;
		gbc_lblNewLabel.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 1;
		TitlePanel.add(lblNewLabel, gbc_lblNewLabel);
		
		JPanel MainPanel = new JPanel();
		contentPane.add(MainPanel, BorderLayout.CENTER);
		MainPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel searchPanel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) searchPanel.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		MainPanel.add(searchPanel, BorderLayout.NORTH);
		
		textField = new JTextField();
		searchPanel.add(textField);
		textField.setColumns(20);		
		
		JButton btnNewButton_3 = new JButton("검색");
		searchPanel.add(btnNewButton_3);
		btnNewButton_3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				updateTable();
			}
		});
		JPanel ViewPanel = new JPanel();
		MainPanel.add(ViewPanel, BorderLayout.CENTER);
		ViewPanel.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		ViewPanel.add(scrollPane);
		
		table = new JTable( );
		table.setFont(new Font("Malgun Gothic", Font.PLAIN, 13));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setShowVerticalLines(false);
		scrollPane.setViewportView(table);
		
		JPanel ButtonPanel = new JPanel();
		contentPane.add(ButtonPanel, BorderLayout.SOUTH);
		
		JButton btnNewButton_4 = new JButton("이동");
		ButtonPanel.add(btnNewButton_4);
		btnNewButton_4.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				move();
			}
		});
		
		JButton btnNewButton = new JButton("추가");
		ButtonPanel.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				add();
			}
		});
		
		JButton btnNewButton_1 = new JButton("편집");
		ButtonPanel.add(btnNewButton_1);
		btnNewButton_1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO getDescribe from selected colume;
				edit();
			}
		});
		
		JButton btnNewButton_2 = new JButton("삭제");
		ButtonPanel.add(btnNewButton_2);
		btnNewButton_2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO getDecrbie from selected colume;
				// TODO remove Decribe;
				delete();
			}
		});
		
		JButton button = new JButton("종료");
		ButtonPanel.add(button);
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
			}
		});
		
		updateView();
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}
	
	public void updateView(){
		this.acct = Account.getAccount(acct.getNum());
		accountNameLabel.setText(acct.getName());
		label.setText("잔액 : " + acct.getMoney() + " 원");
		lblNewLabel.setText(acct.getDesc());
		
		updateTable();
	}
	
	public void updateTable(){
		String s = textField.getText();
		table.setModel(acct.getTableModel(s));
		
		table.getColumnModel().getColumn(0).setMinWidth(30);
		table.getColumnModel().getColumn(0).setMaxWidth(30);	
		table.getColumnModel().getColumn(1).setMinWidth(110);
		table.getColumnModel().getColumn(1).setMaxWidth(110);	
		table.getColumnModel().getColumn(2).setMinWidth(140);
		table.getColumnModel().getColumn(2).setPreferredWidth(150);
		table.getColumnModel().getColumn(3).setPreferredWidth(60);
		table.getColumnModel().getColumn(4).setPreferredWidth(60);
		table.getColumnModel().getColumn(2).setPreferredWidth(50);
		
		table.getTableHeader().setReorderingAllowed(false);
		 

	}
	
	public void add(){
		new AddEditDesc(new Describe(), this);
	}
	
	public void move(){
		new AddMoveDesc(this);
	}
	
	public void edit(){
		int row = table.getSelectedRow();
		Object value = table.getValueAt(row, 0);
		
		Describe d = Describe.getDescribe((int)value);
		new AddEditDesc(d, this);
	}
	
	public void delete(){
		int row = table.getSelectedRow();
		
		Object value = table.getValueAt(row, 0);
		System.out.println(value);
		Describe d = Describe.getDescribe((int)value);
		d.deleteDescribe();
		
		this.updateView();
	}

}
