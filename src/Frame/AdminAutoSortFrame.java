package Frame;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.ListModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.JList;
import javax.swing.ListSelectionModel;

import Model.SqlConnection;


public class AdminAutoSortFrame extends JFrame {

	private JPanel contentPane;

	
	JList list;
	
	int sortNum;
	
	
	/** Create the frame. **/
	public AdminAutoSortFrame(int sortNum) {
		this.sortNum = sortNum;
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 400, 300);
		setTitle("자동 분류 문자열 관리");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel buttonPanel = new JPanel();
		contentPane.add(buttonPanel, BorderLayout.SOUTH);
		
		JButton btnNewButton_2 = new JButton("추가");
		buttonPanel.add(btnNewButton_2);
		btnNewButton_2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				add();
			}
		});
		
		JButton btnNewButton_1 = new JButton("삭제");
		buttonPanel.add(btnNewButton_1);
		btnNewButton_1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				remove();
			}
		});
		
		JButton btnNewButton = new JButton("종료");
		buttonPanel.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		list = new JList<String>();
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(list);
		
		
		setValue();
		this.setVisible(true);
	}
	
	public void setValue(){
		
		ResultSet rs = SqlConnection.getInstance().sendQuery("SELECT STRING FROM SORTSTRING WHERE SORTNO = " +  sortNum);
		DefaultListModel<String> dlm = new DefaultListModel<String>();
		
		try{
			while(rs.next()){
				dlm.add(0, rs.getString(1));
			}
		}catch(Exception e){
			
		}
		
		list.setModel(dlm);
	}
	
	public void add(){
		String newSort = JOptionPane.showInputDialog("추가할 분류의 이름을 입력해 주세요.");
		if(!newSort.equals("")){
			SqlConnection.getInstance().sendQuery("INSERT INTO SORTSTRING (STRING, SORTNO) VALUE ('" + newSort + "', " + sortNum + ")");
		}
		setValue();
	}
	public void remove(){
		String remove = (String) list.getSelectedValue();
		SqlConnection.getInstance().sendQuery("DELETE FROM SORTSTRING WHERE STRING = '" + remove + "'");
		setValue();
	}
	
	
	

}
