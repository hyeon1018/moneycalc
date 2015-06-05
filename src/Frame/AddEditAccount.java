package Frame;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.JButton;

import Model.Account;

public class AddEditAccount extends JFrame {
	
	Account acct;
	View view;
	
	private JPanel contentPane;
	private JTextField textField;
	JTextPane textPane;
	/** Create the frame. **/
	public AddEditAccount(Account acct, View mf) {
		this.acct = acct;
		this.view = mf;
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 400, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(86, 13, 284, 22);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel label = new JLabel("계좌 이름");
		label.setBounds(10, 13, 77, 22);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("계좌 설명");
		label_1.setBounds(10, 49, 77, 22);
		contentPane.add(label_1);
		
		textPane = new JTextPane();
		textPane.setBounds(86, 49, 284, 146);
		contentPane.add(textPane);
		
		JButton btnNewButton = new JButton("저장");
		btnNewButton.setBounds(10, 208, 97, 25);
		contentPane.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				confirm();
			}
		});
		
		JButton btnNewButton_1 = new JButton("계좌 삭제");
		btnNewButton_1.setBounds(143, 208, 97, 25);
		contentPane.add(btnNewButton_1);
		btnNewButton_1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(!acct.isNew()){
					acct.removeAccount();
					AddEditAccount.this.view.updateView();
				}
				dispose();
			}
		});
		
		JButton btnNewButton_2 = new JButton("취소");
		btnNewButton_2.setBounds(273, 208, 97, 25);
		contentPane.add(btnNewButton_2);
		btnNewButton_2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
			}
		});
	
		setValues();
		this.setVisible(true);
	}
	
	
	public void getValues(){
		acct.setName(textField.getText());
		acct.setDesc(textPane.getText());
	}
	public void setValues(){
		textField.setText(acct.getName());
		textPane.setText(acct.getDesc());
	}
	
	public void confirm(){
		getValues();
		
		if(acct.isNew()){
			acct.addAccount();
		}else{
			acct.editAccount();
		}
		
		view.updateView();
		dispose();
		
	}
	
}
