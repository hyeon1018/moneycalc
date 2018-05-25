package Model;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.imageio.ImageIO;
import javax.swing.*;

import org.xml.sax.InputSource;


public class Describe {
	private int num;	//NUM
	private Calendar cal;	//DATETIME
	private String Desc;	//COMMENT
	private int amount;		//AMOUNT
	private ImageIcon image;	//RECIPE
	private int accountNum;	//ACCOUNTNUM
	private Integer sortNum;	//SORTNO
	private Integer alarmNum;		//ALARMNUM
	private boolean writed;


	public Describe(ResultSet rs) {
		try {
			this.num = rs.getInt(1);
		}catch(SQLException e){
			this.num = 0;
		}

		try {
			Timestamp ts = rs.getTimestamp(2);
			cal = Calendar.getInstance();
			cal.setTimeInMillis(ts.getTime());
		}catch(SQLException e){
			cal = Calendar.getInstance();
		}catch (NullPointerException nu) {
			// TODO: handle exception
			cal = null;
		}	

		try {
			this.Desc = rs.getString(3);
		}catch(SQLException e){
			this.Desc = null;
		}

		try {
			this.amount = rs.getInt(4);
		}catch(SQLException e){
			this.amount = 0;
		}


		try {
			Blob b = rs.getBlob(5);
			if(b != null){
				image = new ImageIcon(b.getBytes( 1L, (int)b.length()));
			}
		}catch(SQLException e){
			this.image = null;
		}

		try {
			this.accountNum = rs.getInt(6);
		}catch(SQLException e){
			this.accountNum = 0;
		}

		try {
			this.sortNum = (Integer) rs.getObject(7);
		}catch(SQLException e){
			this.sortNum = null;
		}

		try {
			this.alarmNum = (Integer) rs.getObject(8);
		}catch(SQLException e){
			this.alarmNum = null;
		}

		writed = true;
	}
	public Describe(){

		cal = Calendar.getInstance();
		writed = false;

	}
	public Describe(String Desc, int amount, int accountNum, int sortNum, int alarmNum){
		cal = Calendar.getInstance();
		this.Desc = Desc;
		this.amount = amount;
		this.accountNum = accountNum;
		this.sortNum = sortNum;
		this.alarmNum = alarmNum;

		writed = false;

	}
	
	public static Describe getQucik(ResultSet rs){
		Describe d = new Describe(rs);

		d.writed = false;

		return d;

	}
	public static Describe getDescribe(int i){
		ResultSet rs = SqlConnection.getInstance().sendQuery("SELECT * FROM DATA WHERE NUM = " + i);
		try {
			rs.next();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Describe d = new Describe(rs);

		return d;
	}

	//Getter And Setter;
	public int getNum(){
		return num;
	}
	public Calendar getCal() {
		return cal;
	}
	public String getCalString(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String out = sdf.format(cal.getTime());
		return out;
	}
	public String getDesc() {
		return Desc;
	}
	public int getAmount() {
		return amount;
	}
	public ImageIcon getImage() {
		return image;
	}
	public ImageIcon getScaledImage(Dimension d){
		if(image == null){
			return null;
		}
		Image ori = image.getImage();
		Image con = ori.getScaledInstance(d.width, d.height, Image.SCALE_FAST);

		return new ImageIcon(con);
	}
	public byte[] getImageBytes(){
		
		try {
			Image img = image.getImage();
			BufferedImage bimg = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);
			Graphics gra = bimg.getGraphics();
			gra.drawImage(img, 0, 0, null);
			gra.dispose();
			
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ImageIO.write(bimg, "jpeg", out);
			byte [] buf = out.toByteArray();
			return buf;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return null;
		}
		

	}
	public Integer getAccountNum() {
		if(accountNum == 0){
			return (Integer) null;
		}
		return accountNum;
	}
	public Integer getSortNum() {
		return sortNum;
	}
	public Integer getAlarmNum() {
		return alarmNum;
	}
	public boolean isWrited(){
		return writed;
	}
	public void setCal(Calendar cal) {
		this.cal = cal;
	}
	public void setDesc(String desc) {
		Desc = desc;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public void setImage(ImageIcon image) {
		this.image = image;
	}
	public void setAccountNum(int accountNum) {
		this.accountNum = accountNum;
	}
	public void setSortNum(int sortNum) {
		this.sortNum = sortNum;
	}
	public void setAutoSortNum(String comment){
		ResultSet rs = SqlConnection.getInstance().sendQuery("SELECT * FROM SORTSTRING");
		try{
			while(rs.next()){
				if(comment.contains(rs.getString(1))){
					this.sortNum = rs.getInt(2);
					return;
				}
			}
		}catch(Exception e){
			this.sortNum = null;
		}
		
	}
	public void removeSortNum(){
		this.sortNum = null;
	}
	public void setAlarmNum(int alarmNum) {
		this.alarmNum = alarmNum;
	}

	public void addDescribe(){
		SqlConnection.getInstance().addDescribe(this);
	}
	public void editDescribe(){
		SqlConnection.getInstance().editDescribe(this);
	}
	public void deleteDescribe(){
		SqlConnection.getInstance().removeDescribe(this);
	}
	
	public void addQuick(){
		SqlConnection.getInstance().addQuick(this);
	}
	public void editQuick(){
		SqlConnection.getInstance().editQuick(this);
	}
	public void deleteQuick(){
		SqlConnection.getInstance().removeQuick(this);
	}
}
