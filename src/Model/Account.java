package Model;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class Account {
	int accountNum;
	String accountName;
	String accountDesc;
	int accountMoney;
	boolean isNew = false;
	
	public Account(){
		isNew = true;
	}

	public static Account getAccount(ResultSet rs){
		Account acct = new Account();

		try {
			acct.accountNum = rs.getInt(1);
			acct.accountName = rs.getString(2);
			acct.accountDesc = rs.getString(3);
			acct.accountMoney = rs.getInt(4);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
		}
		
		acct.isNew = false;

		return acct;

	}
	public static Account getAccount(int index){
		Account acct =  null; 
		SqlConnection sqlcon = SqlConnection.getInstance();
		ResultSet rs = sqlcon.sendQuery("SELECT * FROM ACCOUNT WHERE ACCOUNTNO = " + index + ";");
		try {
			rs.next();
			acct = getAccount(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		acct.isNew = false;

		return acct;
	}

	public int getNum(){
		return accountNum;
	}
	public String getName(){
		return accountName;
	}
	public String getDesc(){
		return accountDesc;
	}
	public int getMoney(){
		return accountMoney;
	}
	public void setName(String name){
		this.accountName = name;
	}
	public void setDesc(String desc){
		this.accountDesc = desc;
	}
	public boolean isNew(){
		return this.isNew;
	}
	public void notNew(){
		this.isNew = false;
	}
	
	public DefaultTableModel getTableModel(String keyword){
		ResultSet rs = SqlConnection.getInstance().sendQuery("SELECT DATA.NUM, DATA.DATETIME, DATA.COMMENT, DATA.AMOUNT, SORT.SORTINFO FROM DATA LEFT OUTER JOIN SORT USING(SORTNO) WHERE ACCOUNTNO = " + this.accountNum + " AND COMMENT LIKE '%" + keyword + "%';");
		DefaultTableModel dtm = null;
		try{ 
			ResultSetMetaData metaData = rs.getMetaData();

			// names of columns
			Vector<String> columnNames = new Vector<String>();
			columnNames.add("No.");
			columnNames.add("시간");
			columnNames.add("상세 설명");
			columnNames.add("+");
			columnNames.add("-");
			columnNames.add("분류");

			// data of the table
			Vector<Vector<Object>> data = new Vector<Vector<Object>>();
			while (rs.next()) {
				Vector<Object> vector = new Vector<Object>();

				vector.add(rs.getInt(1));
				//TODO get DefaultDatetime Model;
				Calendar cal = Calendar.getInstance();
				cal.setTimeInMillis(rs.getTimestamp(2).getTime());
				vector.add(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(cal.getTime()));
				vector.add(rs.getString(3));
				int amount = rs.getInt(4);
				if(amount > 0){
					vector.add(amount);
				}
				vector.add(null);
				if(amount < 0){
					vector.add(-amount);
				}
				
				vector.add(rs.getString(5));

				data.add(vector);
			}


			dtm = new DefaultTableModel(data, columnNames){
				Class[] columnTypes = new Class[] {
						Integer.class, Object.class, Object.class, Integer.class, Integer.class, Object.class 
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

		return dtm;

	}


	public void addAccount(){
		SqlConnection.getInstance().makeAccount(this);
	}
	public void editAccount(){
		SqlConnection.getInstance().editAccount(this);
	}
	public int updateAccountMoney(){
		accountMoney = SqlConnection.getInstance().updateAccountMoney(accountNum);
		return this.accountMoney;
	}	
	public void removeAccount(){
		SqlConnection.getInstance().deleteAccount(this);
	}
	
}
