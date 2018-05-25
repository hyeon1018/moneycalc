package Frame;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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


public class StatFrame extends JFrame {

	private JPanel contentPane;
	private JTable table;
	
	
	
	/** Create the frame. **/
	public StatFrame() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 400, 300);
		setTitle("지출 통계");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		table.setFont(new Font("Malgun Gothic", Font.PLAIN, 13));
		scrollPane.setViewportView(table);
		
		setValue();
		this.setVisible(true);
	}
	
	public void setValue(){
		
		ResultSet rs = SqlConnection.getInstance().sendQuery("SELECT SORT.SORTINFO '분류명', SUM(DATA.AMOUNT) '금액 합계', COUNT(DATA.AMOUNT) '횟수' FROM DATA RIGHT OUTER JOIN SORT USING(SORTNO) GROUP BY SORTNO UNION SELECT SORT.SORTINFO '분류명', SUM(DATA.AMOUNT) '금액 합계', COUNT(DATA.AMOUNT) '횟수' FROM DATA LEFT OUTER JOIN SORT USING(SORTNO) WHERE DATA.AMOUNT < 0 GROUP BY SORTNO;");
		DefaultTableModel dtm = null;
		try{ 
			ResultSetMetaData metaData = rs.getMetaData();

			// names of columns
			Vector<String> columnNames = new Vector<String>();
			columnNames.add("분류명");
			columnNames.add("금액 합계");
			columnNames.add("횟수");
			
			// data of the table
			Vector<Vector<Object>> data = new Vector<Vector<Object>>();
			while (rs.next()) {
				Vector<Object> vector = new Vector<Object>();
				
				vector.add(rs.getString(1));
				vector.add(rs.getInt(2) * (-1));
				vector.add(rs.getInt(3));
				
				data.add(vector);
			}
			
			dtm = new DefaultTableModel(data, columnNames){
				Class[] columnTypes = new Class[] {
						String.class, Integer.class, Integer.class 
				};
				public Class getColumnClass(int columnIndex) {
					return columnTypes[columnIndex];
				}
				
				@Override
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		table.setModel(dtm);
		table.getTableHeader().setReorderingAllowed(false);
		
		
	}
	
	public void add(){
		String newSort = JOptionPane.showInputDialog("추가할 분류의 이름을 입력해 주세요.");
		if(!newSort.equals("")){
			SqlConnection.getInstance().addSort(newSort);
		}
		setValue();
	}

}
