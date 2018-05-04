/**
 *   
 * @author  b03505053
 * @version 3.0
 * @since   2017-06-11
 */

package gg.view;

import gg.model.Datasource;
import gg.model.Movie;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import gg.MainApp;

public class NormalDialogController {



	/**
	* These are the variable of this class
	*/
	private Stage dialogStage;
	private MainApp mainApp;
	private boolean okClicked = false;
	/**
	* This method is used to Updata.
	* @param mainApp The reference of mainApp
	*/
	public void setMainApp(MainApp mainApp){
		this.mainApp = mainApp;
	}


	/**
	* These are the variable of this class
	*/

	ObservableList sessionList = FXCollections.observableArrayList();

	ObservableList regionList = FXCollections.observableArrayList
	("Red","Yellow","Blue","任意");

	@FXML
	private ComboBox<String> movie;

	@FXML
	private ComboBox<String> sessionMenu;

	@FXML
	private ComboBox<String> regionMenu;
	
	@FXML
	private ComboBox<String> user;

	@FXML
	private Label movie_name;

	@FXML
	private Label session;

	@FXML
	private Label number1_label;

	@FXML
	private Label number2_label;

	@FXML
	private Label region;

	@FXML
	private TextArea resultArea;
	
	//@FXML
	private String userSelect;

	private int num1 = 1;
	private int num2 = 1;

	private boolean isBigRoom = true;
	private boolean isContinue = false;
	private String hour;
	private String minute;
	private String hall;


		

	/**
	* This method is used to initialize.
	*/
	@FXML
	private void initialize(){

		if (!Datasource.getInstance().open()) {
			System.out.println("FATAL ERROR: Couldn't connect to database");
		}
		//movieList.setItems(Datasource.getInstance().getMovieName()));
		movie.setItems(FXCollections.observableList(Datasource.getInstance().getMovieName()));
		user.setItems(FXCollections.observableList(Datasource.getInstance().getAllUserName()));
		number1_label.setText("  1");
		number2_label.setText("  1");
		region.setText("無");
		resultArea.setEditable(false);
	}
	/**
	* This method is used to handleMovoieSelct.
	*/
	@FXML
	private void handleMovieSelect(){
		sessionList.clear();
		sessionMenu.getItems().clear();
		String selected = movie.getValue().toString();
		movie_name.setText(selected);
		build_Session_list(movie_name.getText());
	}
	/**
	* This method is used to handdleSessionSelect.
	*/
	@FXML
	private void handdleSessionSelect(){
		try{
			String selected = sessionMenu.getValue().toString();
			session.setText(selected);
			String[] buff = selected.split("/");
			String hall_ = buff[1];
			String time_ = buff[0];
			set_Time_Hall(time_,hall_);
			biuld_Region_lsit(hall_);
		}catch(Exception e){
			session.setText("");
		}
	}

	/**
	* This method is used to handleRegionSelect.
	*/
	@FXML
	private void handleRegionSelect(){
		try{
			if(isBigRoom){
				String selected = regionMenu.getValue().toString();
				region.setText(selected);
			}else{
				region.setText("無");
			}
		}catch(Exception e){
			region.setText("");
		}
	}

	/**
	* This method is used to setDialogStage.
	* @param dialogStage This parameter is dialogStage.
 	*/
	public void setDialogStage(Stage dialogStage){
		this.dialogStage = dialogStage;
	}


	/**
	* This method is used to handleOk.
	*/
	@FXML
	private void handleOk(){
		okClicked = true;
		result();
		//dialogStage.close();
	}


	/**
	* This method is used to handleCancel.
	*/
	@FXML
	private void handleCancel(){
		dialogStage.close();
	}

	/**
	* This method is used to set data.
	* @param time This parameter is time
	* @param hall_
	*/
	private void set_Time_Hall(String time,String hall_){
		String[] time_array = time.split("：");
		//String[] hall_array = hall_name.split(" ");
		this.hour = time_array[0];
		this.minute = time_array[1];
		this.hall = hall_;
		//System.out.println(hour+":"+minute+","+hall);

	}

	/**
	* This method is used to build list.
	* @param name This parameter is name
	*/
	private void build_Session_list(String name){

		try{
			Movie movie_selected = new Movie(name);
			for (int i = 0;i < movie_selected.getNumOfSession();i++) {
				String s ="";
				s = s + movie_selected.getTime(i) + "/" + movie_selected.getHall(i);
				sessionList.add(new String(s));
			}
			sessionMenu.setItems(sessionList);
		}catch(Exception e){
			//Movie movie_selected = new Movie(name);
			System.out.println("build_Session_list error");
		}

	}

	/**
	* This method is used to build list.
	* @param hall
	*/
	private void biuld_Region_lsit(String hall){
		if (hall .equals("峨嵋") || hall .equals("崆峒")) {
			isBigRoom = false;
			regionMenu.getItems().clear();
			regionList.clear();
			regionList.add("小廳無法指定區域");
			regionMenu.setItems(regionList);
		}else{
			isBigRoom = true;
			regionMenu.getItems().clear();
			regionList.clear();
			regionList.add("Red");
			regionList.add("Yellow");
			regionList.add("Blue");
			regionList.add("任意");
			regionMenu.setItems(regionList);
		}
	}


	/**
	* This method is used to handleAddNum.
	*/
	@FXML
	private void handleAddNum(){
		num1 += 1;
		num2 = num1;
		setNumLabel();
	}

	/**
	* This method is used to handleMinusNum.
	*/
	@FXML 
	private void handleMinusNum(){
		if (num1 > 1) {
			num1 -= 1;
			num2 = num1;
			setNumLabel();
		}
	}


	/**
	* This method is used to verify isContinue.
	*/
	@FXML
	private void handleIsContinue(){
		isContinue = isContinue == true ? false : true;
	}


	/**
	* This method is used to set Number.
	*/
	private void setNumLabel(){
		String rs = "  0";
		if (num1 < 10) {
			rs = "   " + num1;
		}else if(num1 < 100){
			rs = "  " + num1;
		}else{
			rs = " " + num1;
		}
		number1_label.setText(rs);
		number2_label.setText(rs);
	}


	/**
	* This method is used to handleMinusNum.
	* @return boolean Verify is OK.
	*/
	public boolean isOkClicked(){
		return okClicked;
	}


	/**
	* This method is used to handle result.
	*/	
	public void result(){
		String movie_name = this.movie_name.getText();
		int num = this.num1;
		String region = this.region.getText();
		String hall_name = this.hall;
  		Connection c = null;
		Statement stmt = null;
		try{
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:movie_2.db");
			c.setAutoCommit(false);
			stmt = c.createStatement();

			ResultSet hall_data;
			if(isBigRoom){
				if (region.equals("Yellow")) {
					hall_data = stmt.executeQuery("SELECT id,row,seatNum,occupied FROM big_room_"+hour+"_"+minute+"_"+hall+" WHERE id IN (SELECT id FROM big_room_"+hour+"_"+minute+"_"+hall+" WHERE occupied = 0 AND region = 'yellow');");
				}else if(region.equals("Red")){
					hall_data = stmt.executeQuery("SELECT id,row,seatNum,occupied FROM big_room_"+hour+"_"+minute+"_"+hall+" WHERE id IN (SELECT id FROM big_room_"+hour+"_"+minute+"_"+hall+" WHERE occupied = 0 AND region = 'red');");
				}else if(region.equals("Blue")){
					hall_data = stmt.executeQuery("SELECT id,row,seatNum,occupied FROM big_room_"+hour+"_"+minute+"_"+hall+" WHERE id IN (SELECT id FROM big_room_"+hour+"_"+minute+"_"+hall+" WHERE occupied = 0 AND region = 'blue');");
				}else {
					hall_data = stmt.executeQuery("SELECT id,row,seatNum,occupied FROM big_room_"+hour+"_"+minute+"_"+hall+" WHERE id IN (SELECT id FROM big_room_"+hour+"_"+minute+"_"+hall+" WHERE occupied = 0 AND region = 'gray');");
				}
			}else{
					hall_data = stmt.executeQuery("SELECT id,row,seatNum,occupied FROM small_room_"+hour+"_"+minute+"_"+hall+" WHERE id IN (SELECT id FROM small_room_"+hour+"_"+minute+"_"+hall+" WHERE occupied = 0);");

			}
			//ResultSet hall_data = stmt.executeQuery("SELECT id,row,seatNum,occupied FROM big_room_"+hour+"_"+minute+"_"+hall+" WHERE id IN (SELECT id FROM big_room_"+hour+"_"+minute+"_"+hall+" WHERE occupied = 0);");
			ArrayList<String> ids = new ArrayList<String>();
			ArrayList<String> rows = new ArrayList<String>();
			ArrayList<Integer> seatNums = new ArrayList<Integer>();

			while(hall_data.next()){								
				ids.add(hall_data.getString("id"));
				rows.add(hall_data.getString("row"));
				Integer i = new Integer(hall_data.getInt("seatNum"));
				seatNums.add(i);
			}

			hall_data.close();
			stmt.close();
			c.commit();
			c.close();



			String conti_row = "N";
			ArrayList<Integer> conti_seatNums = new ArrayList<Integer>();


			int seatsLeft = ids.size();
			//
			//
			//System.out.println(seatsLeft);
			//
			//


			if (isContinue) {
				ArrayList<Integer> index_array = new ArrayList<Integer>();
				boolean isFind = find_conti_seat(index_array,num,rows,seatNums);
				//System.out.println(isFind);
				if (seatsLeft != 0 && num <= seatsLeft && isFind) {
					String x = "";
					for (int  i = 0; i < num ; i++) {
						int index = index_array.get(i).intValue();
						x = x+"票根流水號："+ids.get(index)+" ，"+rows.get(index)+seatNums.get(index).intValue()+"\n";
						//" ，『"+movie_name+"』於 "+hour+":"+minute+" 在指定區域仍有 "+(--seatsLeft)+"個剩餘座位";
						//System.out.println( "票根流水號："+ids.get(i)+" ，" + conti_row + conti_seatNums.get(i).intValue()+" ，『"+movie_name+"』於 "+time+" 仍有 "+(--seatsLeft)+"個剩餘座位");
						//System.out.println( "票根流水號："+ids.get(index)+" ，"+rows.get(index)+seatNums.get(index).intValue()+" ，『"+movie_name+"』於 "+hour+":"+minute+" 在指定區域仍有 "+(--seatsLeft)+"個剩餘座位");
						resultArea.setText(x);
						Update_occupied(ids.get(index),hour,minute,hall);
						Update_TicketSold(ids.get(index),hour,minute,hall);
						seatsLeft--;
					}
					x = x + "『"+movie_name+"』於 "+hour+":"+minute+"剩餘座位\n";
					if(isBigRoom){
					x = x + "Red : " + getSeatLeft("red")+"\n";
					x = x + "Blue : " + getSeatLeft("blue")+"\n"; 
					x = x + "Yellow : " + getSeatLeft("yellow")+"\n"; 
					x = x + "Gray : " + getSeatLeft("gray")+"\n"; 
					}else{
						x = x + seatsLeft +"\n"; 
					}
				
				}else{
					String x = "失敗，『"+movie_name+"』於 "+hour+":"+minute+" 在"+region+"區域 座位不足 ";
					resultArea.setText(x);
					//System.out.println( "失敗，『"+movie_name+"』於 "+hour+":"+minute+" 在"+region+"區域 座位不足 ");
				}
			}else{

				if (seatsLeft != 0 && num <= seatsLeft) {
					String x = "";
					for(int i = 0;i < num;i++){
						x = x+"票根流水號："+ids.get(i)+" ，"+rows.get(i)+seatNums.get(i).intValue()+"\n";
						//，『"+movie_name+"』於 "+hour+":"+minute+" 仍有 "+(--seatsLeft)+"個剩餘座位\n";
						resultArea.setText(x);
						//System.out.println( "票根流水號："+ids.get(i)+" ，"+rows.get(i)+seatNums.get(i).intValue()+" ，『"+movie_name+"』於 "+hour+":"+minute+" 仍有 "+(--seatsLeft)+"個剩餘座位");
						Update_occupied(ids.get(i),hour,minute,hall);
						Update_TicketSold(ids.get(i),hour,minute,hall);
						seatsLeft--;
					}
					x = x + "『"+movie_name+"』於 "+hour+":"+minute+"剩餘座位\n";
					if(isBigRoom){
					x = x + "Red : " + getSeatLeft("red")+"\n";
					x = x + "Blue : " + getSeatLeft("blue")+"\n"; 
					x = x + "Yellow : " + getSeatLeft("yellow")+"\n"; 
					x = x + "Gray : " + getSeatLeft("gray")+"\n"; 
					}else{
						x = x + seatsLeft +"\n"; 
					}
					//" 仍有 "+(seatsLeft)+"個剩餘座位";

					resultArea.setText(x);
				}else{
					String x =  "失敗，『"+movie_name+"』於 "+hour+":"+minute+" 在"+region+"區域 座位不足 ";
					resultArea.setText(x);
					//System.out.println( "失敗，『"+movie_name+"』於 "+hour+":"+minute+" 在"+region+"區域 座位不足 ");
				}

			}
			
			

		}catch(Exception e){
      		System.err.println( e.getClass().getName() + ": " + e.getMessage() );

      		System.exit(0);

		}


 	}


	/**
	* This method is used to count seat number.
	* @param region The region is selected.
	* @return int  Return the seat number
	*/
 	public int getSeatLeft(String region){
 		int r = 0;
 		try{
  		Connection c = null;
		Statement stmt = null;
		ArrayList<String> id = new ArrayList<String>();
		Class.forName("org.sqlite.JDBC");
		c = DriverManager.getConnection("jdbc:sqlite:movie_2.db");
		c.setAutoCommit(false);
		stmt = c.createStatement();
		ResultSet hall_data;
		

		//ResultSet hall_data;
		if(isBigRoom){
			hall_data = stmt.executeQuery("SELECT id FROM big_room_"+hour+"_"+minute+"_"+hall+" WHERE id IN (SELECT id FROM big_room_"+hour+"_"+minute+"_"+hall+" WHERE occupied = 0 AND region = '"+region+"');");
		}else{
			hall_data = stmt.executeQuery("SELECT id FROM small_room_"+hour+"_"+minute+"_"+hall+" WHERE id IN (SELECT id FROM small_room_"+hour+"_"+minute+"_"+hall+" WHERE occupied = 0);");
		}		
		while(hall_data.next()){
			//id.add(hall_data.getString("id"));
			r++;
		}
		//r = id.size();
		hall_data.close();
		stmt.close();
		c.commit();
		c.close();
		}catch(Exception e){
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		}

		return r;




 	}


	/**
	* This method is used to find continue seat.
	* @param index_array The index of array
	* @param num the num of seat
	* @param rows The row of seat.
	* @param seatNums The number of seat.
	* @return boolean Continue seats is find
	*/
 	public boolean find_conti_seat(ArrayList<Integer> index_array ,int num,ArrayList<String> rows,ArrayList<Integer> seatNums){
 		String pre_row = rows.get(0);
 		int continueNum = 1;
 		int pre_seat = seatNums.get(0).intValue();
 		//System.out.println(rows.size());
 		for (int i = 1; i < rows.size(); i ++) {
 			if (rows.get(i).equals(pre_row) && pre_seat+1 == seatNums.get(i).intValue() && isBigRoom == true) {
 				continueNum++;
 				pre_seat = seatNums.get(i).intValue();
 				pre_row = rows.get(i);
 			}else if(rows.get(i).equals(pre_row) && pre_seat+1 == seatNums.get(i).intValue() && isBigRoom == false && !(pre_seat == 12 || pre_seat == 4)){
				continueNum++;
 				pre_seat = seatNums.get(i).intValue();
 				pre_row = rows.get(i);				
 				
 			}else if(isBigRoom == false && (pre_seat == 12 || pre_seat == 4)){
 				//System.out.println(i);
				pre_seat = seatNums.get(i).intValue();
 				pre_row = rows.get(i);
 				continueNum = 1;
 			}else{
				pre_seat = seatNums.get(i).intValue();
 				pre_row = rows.get(i);
 				continueNum = 1;
 			}

 			if (continueNum == num) {
 				for (int j =1;j <= continueNum; j++) {
 					Integer index = new Integer(i - continueNum + j);
 					index_array.add(index);
 				}
 				return true;
 				
 			}
 		}
 		return false;

 	}


	/**
	* This method is used to Updata.
	* @param id The ticket id
	* @param hour The hour of time
	* @param minute The minute of time
	* @param hall  The hall of ticket
	*/
 	public void Update_occupied(String id,String hour,String minute,String hall){

  		Connection c = null;
		Statement stmt = null;
		try{
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:movie_2.db");
			c.setAutoCommit(false);
			stmt = c.createStatement();
			if(!isBigRoom){
				//System.out.println("update small room");
	 			String sql = "UPDATE small_room_"+hour+"_"+minute+"_"+hall+" set occupied = 1 WHERE id = '"+id+"';";
				stmt.executeUpdate(sql);
				c.commit();
			}else{
				String sql = "UPDATE big_room_"+hour+"_"+minute+"_"+hall+" set occupied = 1 WHERE id = '"+id+"';";
				stmt.executeUpdate(sql);
				c.commit();

			}

			stmt.close();
			c.close();
		}catch(Exception e){
			System.out.println("update error");
      		System.err.println( e.getClass().getName() + ": " + e.getMessage() );

			System.exit(0);
		}
 	}

	/**
	* This method is used to Updata.
	* @param id The ticket id
	* @param hour The hour of time
	* @param minute The minute of time
	* @param hall  The hall of ticket
	*/
 	public void Update_TicketSold(String id,String hour,String minute,String hall){
   		Connection c = null;
		Statement stmt = null;
		try{
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:movie_2.db");
			c.setAutoCommit(false);
			stmt = c.createStatement();
			//System.out.println("update TICKET");
		
	 		String sql = "INSERT INTO ticket_sold (id,time,hall) VALUES ('"+id+"','"+hour+"："+minute+"','"+hall+"');";
			stmt.executeUpdate(sql);
			c.commit();

			stmt.close();
			c.close();
		}catch(Exception e){
			System.out.println("update error");
      		System.err.println( e.getClass().getName() + ": " + e.getMessage() );

			System.exit(0);
		}

 	}
 	
 	@FXML
 	private void handle_userselect(){
		if(user.getValue()!=null){
			userSelect = user.getValue();
		//System.out.println(timeAndHall.getValue().toString());
		//userNameLabel.setText(userSelect);
		}

 	}

}
