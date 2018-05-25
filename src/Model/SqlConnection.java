package Model;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlConnection{
	public static int count=0;
	private Connection con;
	private Statement stmt;
	
	private void SqlDisconnect(){
		try {
			stmt.close();
			con.close();
		} catch (SQLException e) {
			
		}

	}	
	private SqlConnection(){
		final String url = "jdbc:mariadb://dlddus.me:3306/moneyuser";
		final String username = "moneyuser";
		final String passwd = "dlddus.me";

		try {
			Class.forName("org.mariadb.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println(e.toString());
		}

		try {
			con = DriverManager.getConnection(url, username, passwd);
			stmt = con.createStatement();

		} catch(java.lang.Exception ex) {
			System.exit(1);
		}
	}

	private static SqlConnection sqlcon = new SqlConnection();
	public static SqlConnection getInstance(){
		return sqlcon;
	}
	public ResultSet sendQuery(String sql){
		count++;
		System.out.println(count + ":" + sql);
		ResultSet rs = null;

		try {
			rs = stmt.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return rs;
	}

	//Describe
	public int addDescribe(Describe desc) {

		ResultSet rs = null;

		try {
			PreparedStatement pstmt = con.prepareStatement("INSERT INTO DATA (DATETIME, COMMENT, AMOUNT, RECIPE, ACCOUNTNO, SORTNO, ALARMNO) VALUES (?,?,?,?,?,?,?);");
			
			pstmt.setString(1, desc.getCalString());
			pstmt.setString(2, desc.getDesc());
			pstmt.setInt(3, desc.getAmount());
			try{
				pstmt.setBytes(4, desc.getImageBytes());
				}catch(Exception e){
					pstmt.setObject(4, null);
				}
			
			pstmt.setInt(5, desc.getAccountNum());
			try{
				pstmt.setInt(6, desc.getSortNum());
			}catch(Exception e){
				pstmt.setObject(6, null);
			}
			
			pstmt.setObject(7, desc.getAlarmNum());
			pstmt.execute();
			
			updateAccountMoney(desc.getAccountNum());
			
			rs = stmt.executeQuery("SELECT LAST_INSERT_ID();");

			rs.next();
			return rs.getInt(1);
		} catch(java.lang.Exception ex) {
			ex.printStackTrace();
		}

		return 0;
	} 	
	public void editDescribe(Describe desc){
		
		ResultSet rs = SqlConnection.getInstance().sendQuery("SELECT ACCOUNTNO FROM DATA WHERE NUM = " + desc.getNum());
		int i;
		try{
			rs.next();
			i = rs.getInt(1);
		}catch(Exception e){
			i = 0;
		}
		
		try {
			PreparedStatement pstmt = con.prepareStatement("UPDATE DATA SET DATETIME = ?, COMMENT = ?, AMOUNT = ?, RECIPE = ?, ACCOUNTNO = ?, SORTNO = ? WHERE NUM = ?;");
		
			pstmt.setString(1, desc.getCalString());
			pstmt.setString(2, desc.getDesc());
			pstmt.setInt(3, desc.getAmount());
			pstmt.setObject(4, desc.getImageBytes());
			pstmt.setInt(5, desc.getAccountNum());
			pstmt.setObject(6, desc.getSortNum());
			pstmt.setObject(7, desc.getNum());
			pstmt.execute();
			
			updateAccountMoney(desc.getAccountNum());
		
		} catch (SQLException e) {

		}
		
		updateAccountMoney(i);
		updateAccountMoney(desc.getAccountNum());
	}
	public void removeDescribe(Describe desc){
		
		try {
			PreparedStatement pstmt = con.prepareStatement("DELETE FROM DATA WHERE NUM = ?");
			pstmt.setInt(1, desc.getNum());
			pstmt.execute();
		} catch (SQLException e) {
		}
		
		updateAccountMoney(desc.getAccountNum());
	}
	//Account
	public void makeAccount(Account acct){
		try {
			PreparedStatement pstmt = con.prepareStatement("INSERT INTO ACCOUNT (ACCOUNTNAME, ACCOUNTDESC, ACCOUNTMONEY) VALUES (?, ?, 0);");
			
			pstmt.setString(1, acct.getName());
			pstmt.setString(2, acct.getDesc());

			pstmt.execute();

		} catch (SQLException e) {
			
			
		}
	}
	public void editAccount(Account acct){
		try {
			PreparedStatement pstmt = con.prepareStatement("UPDATE ACCOUNT SET ACCOUNTNAME = ?, ACCOUNTDESC = ? WHERE ACCOUNTNO = ?");
			pstmt.setString(1, acct.getName());
			pstmt.setString(2, acct.getDesc());
			pstmt.setInt(3, acct.getNum());
			
			pstmt.execute();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
	}
	public int updateAccountMoney(int index){
		int money = 0;
		ResultSet rs = sendQuery("SELECT SUM(AMOUNT) FROM DATA WHERE ACCOUNTNO = " + Integer.toString(index));
		//get Account Money by SQL SUM() FUNCTION.
		try {
			rs.next();
			money = rs.getInt(1);
		} catch (SQLException e) {
			//ERROR REPORT;
			e.printStackTrace();
		}

		//UPDATE TO ACCOUNT MONEY FIELD;
		sendQuery("UPDATE ACCOUNT SET ACCOUNTMONEY = '" + Integer.toString(money) + "' WHERE ACCOUNTNO = " + Integer.toString(index));

		//RETURN ACCOUNT MONEY;
		return money;
	}	
	public void deleteAccount(Account acct){
		try{
			PreparedStatement pstmt = con.prepareStatement("DELETE FROM ACCOUNT WHERE ACCOUNTNO = ?");
			pstmt.setInt(1, acct.getNum());
			pstmt.execute();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public int addQuick(Describe quick) {

		ResultSet rs = null;

		try {
			PreparedStatement pstmt = con.prepareStatement("INSERT INTO QUICK (DATETIME, COMMENT, AMOUNT, RECIPE, ACCOUNTNO, SORTNO, ALARMNO) VALUES (?,?,?,?,?,?,?);");
			
			pstmt.setObject(1, null);
			pstmt.setString(2, quick.getDesc());
			pstmt.setInt(3, quick.getAmount());
			try{
				pstmt.setBytes(4, quick.getImageBytes());
				}catch(Exception e){
					pstmt.setObject(4, null);
				}
			
			pstmt.setInt(5, quick.getAccountNum());
			try{
				pstmt.setInt(6, quick.getSortNum());
			}catch(Exception e){
				pstmt.setObject(6, null);
			}
			
			if(quick.getAlarmNum() != null){
				pstmt.setInt(7, quick.getAlarmNum());
			}else {
				pstmt.setObject(7, null);
			}
			pstmt.execute();
			
		} catch(java.lang.Exception ex) {
			ex.printStackTrace();
		}

		return 0;
	} 	
	public void editQuick(Describe quick){
		
		try {
			PreparedStatement pstmt = con.prepareStatement("UPDATE QUICK SET DATETIME = ?, COMMENT = ?, AMOUNT = ?, RECIPE = ?, ACCOUNTNO = ?, SORTNO = ? WHERE NUM = ?;");
		
			pstmt.setObject(1, null);
			pstmt.setString(2, quick.getDesc());
			pstmt.setInt(3, quick.getAmount());
			pstmt.setObject(4, quick.getImageBytes());
			pstmt.setInt(5, quick.getAccountNum());
			pstmt.setObject(6, quick.getSortNum());
			pstmt.setObject(7, quick.getNum());
			pstmt.execute();
			
			updateAccountMoney(quick.getAccountNum());
		
		} catch (SQLException e) {

		}
	}
	public void removeQuick(Describe quick){
		try {
			PreparedStatement pstmt = con.prepareStatement("DELETE FROM QUICK WHERE NUM = ?");
			pstmt.setInt(1, quick.getNum());
			pstmt.execute();
		} catch (SQLException e) {
		}
		
	}

	public void addSort(String sortName){
		try{
			PreparedStatement pstmt = con.prepareStatement("INSERT INTO SORT (SORTINFO) VALUES (?)");
			
			pstmt.setString(1, sortName);
			
			
			pstmt.execute();
		}catch(Exception e){
			
		}
	}
	public void removeSort(String sortName){
		try{
			PreparedStatement pstmt = con.prepareStatement("DELETE FROM SORT WHERE SORTINFO = ?");
			
			pstmt.setString(1, sortName);
			
			
			pstmt.execute();
		}catch(Exception e){
			
		}
	}

	public String getSortName(int sortNum){
		try {
			PreparedStatement pstmt = con.prepareStatement("SELECT SORTINFO FROM SORT WHERE SORTNO = ?");
			pstmt.setInt(1, sortNum);
			ResultSet rs = pstmt.executeQuery();
			
			rs.next();
			String sortName = rs.getString(1);
			
			return sortName;
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
