/**
 *@author:Dennis Lee
 *@version:1.0
 *@since:2017-6-25
 */
package gg.view;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import gg.MainApp;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class SearchTicketController {
	/**
     * These are variables of class SearchTicketController.
     */
	private Stage dialogStage;
	private boolean okClicked = false;
	
	
	private String movie_name;
	private String movie_time;
	private String movie_hall;
	private String movie_seat;
	private String classification;
	private String ticket_id;
	
	@FXML
	private TextField TicketIDField;
	
	@FXML
	private Label movie;
	
	@FXML
	private Label time;
	
	@FXML
	private Label hall;
	
	@FXML
	private Label seat;
	/**
     *This method is used to set the dialogstage.
     *@param dialogStage This is the parameter of setDialogStage method.
	 */
	public void setDialogStage(Stage dialogStage){
		this.dialogStage = dialogStage;
	}
	/**
     *This method is used to check whether the ID input format is correct.
	 *@return true/false
     */
	private boolean isInputValid() {
        String errorMessage = "";
        if (TicketIDField.getText() == null || TicketIDField.getText().length() == 0) {
            errorMessage += "No valid first name!\n"; 
            return false;
        }
        else {
        	return true;
        }
    }
	/**
     *This method is used to searching the ticket information from database and set the contents into labels.
     */
	@FXML
	private void handleOk(){
		if (isInputValid()) {
            ticket_id = TicketIDField.getText();
        }
		Connection c = null;
		Statement stmt = null;
		try{
			Connection conn = DriverManager.getConnection("jdbc:sqlite:movie_2.db");
			Statement statement = conn.createStatement();
			statement.execute("SELECT * FROM ticket_sold");
			ResultSet results = statement.getResultSet();
			while(results.next()){
				if(results.getString("id").equals(ticket_id)){
					String temp = results.getString("time");
					String [] time_temp = temp.split(":");
					movie_time = time_temp[0] + "_" + time_temp[1];
					movie_hall = results.getString("hall").trim();
					String movie_time_hall = "_" + movie_time.trim() + "_" + movie_hall.trim();
					movie_time = (time_temp[0] + "：" + time_temp[1]).trim();
					statement.execute("SELECT * FROM movie_info");
					ResultSet results0 = statement.getResultSet();
					while(results0.next()){
						if (((results0.getString("time")).equals(movie_time)) && ((results0.getString("hall").trim()).equals(movie_hall))){
							movie_name = (results0.getString("movie")).trim();
						}
					}
					if ((movie_hall.equals("峨嵋")) || (movie_hall.equals("崆峒"))){
						statement.execute("SELECT * FROM small_room"+ movie_time_hall);
						ResultSet results1 = statement.getResultSet();
						while(results1.next()){
							if (results1.getString("id").equals(ticket_id)){
								String seatnumber = null;
								if (results1.getInt("seatNum") / 10 == 0){
									seatnumber = ("0" + results1.getInt("seatNum")).trim();
								}
								else {
									seatnumber = results1.getString("seatNum");
								}
								movie_seat = results1.getString("row") + "_" + seatnumber;
							}
						}
						
					}
					else if ((movie_hall.equals("武當")) || (movie_hall.equals("少林")) || (movie_hall.equals("華山"))){
						statement.execute("SELECT * FROM big_room"+ movie_time_hall);
						ResultSet results2 = statement.getResultSet();
						while(results2.next()){
							if (results2.getString("id").equals(ticket_id)){
								String seatnumber = null;
								if (results2.getInt("seatNum") / 10 == 0){
									seatnumber = ("0" + results2.getInt("seatNum")).trim();
								}
								else {
									seatnumber = results2.getString("seatNum");
								}
								movie_seat = results2.getString("row") + "_" + seatnumber;
							}
						}
					}
				}
			}
		}catch(Exception e){
			System.err.println( e.getClass().getName() + ": " + e.getMessage() +"name ="+movie_name);
		}
		movie.setText(movie_name);
		time.setText(movie_time);
		hall.setText(movie_hall);
		seat.setText(movie_seat);
		okClicked = true;
	}
	/*This method is used to change the okClicked value.
	*@return okClicked
    */
	public boolean isOkClicked(){
		return okClicked;
	}
}