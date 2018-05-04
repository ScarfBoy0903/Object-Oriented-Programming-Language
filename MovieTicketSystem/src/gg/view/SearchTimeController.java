/**
 *@author:Dennis Lee
 *@version:1.0
 *@since:2017-6-25
 */
package gg.view;

import gg.MainApp;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import gg.MainApp;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import gg.model.Movie;

public class SearchTimeController {
	/**
     * These are variables of class SearchTimeController.
     */
	private ArrayList<String> movie_record = new ArrayList<String>();
	private ArrayList<String> time_record = new ArrayList<String>();
	private ArrayList<String> id_record = new ArrayList<String>();
	private ArrayList<String> movie = new ArrayList<String>();
	private ArrayList<String> time = new ArrayList<String>();
	private ArrayList<String> id = new ArrayList<String>();
	private static String[] movie_record_1 = new String[30];
	private static String[] movie_record_2 = new String[30];
	private static String[] movie_record_3 = new String[30];
	private static String[] movie_record_4 = new String[30];
	private static String[] movie_record_5 = new String[30];
	private static String[] time_record_1 = new String[30];
	private static String[] time_record_2 = new String[30];
	private static String[] time_record_3 = new String[30];
	private static String[] time_record_5 = new String[30];
	private static String[] hall_record = new String[30];
	private static String[] id_record_1 = new String[30];
	private static String[] id_record_2 = new String[30];
	private static String[] id_record_3 = new String[30];
	private int[] small_occupied_record = new int[150];
	private int[] big_occupied_record = new int[500];
	private String[] small_row_record = new String[150];
	private String[] big_row_record = new String[500];
	private String[] region_record = new String[500];
	private int[] big_seatNum_record = new int[500];
	private int[] small_seatNum_record = new int[500];
	private int[] time_uplimit = new int[2];
	private int[] time_downlimit = new int[2];
	private boolean isContinue = false;
	private Stage dialogStage;
	private MainApp mainApp;
	private boolean okClicked = false;
	private int movie_score;
	private int shortest_runtime;
	private int longest_runtime;
	private String movie_region;
	private int num1 = 1;
	private int num2 = 1;
	private int seat = 0;
	public ObservableList<Movie>  movieData = FXCollections.observableArrayList();
	
	/**
     *This method is used to set the dialogstage.
     *@param dialogStage This is the parameter of setDialogStage method.
	 */
	public void setDialogStage(Stage dialogStage){
		this.dialogStage = dialogStage;
	}
	
	/**
     *This method is used to close the time searching menu.
     */
	@FXML
	private void handleOk(){
		okClicked = true;
		dialogStage.close();
	}
	/**
     *This method is used to change the okClicked value.
	 *@return okClicked
     */
	public boolean isOkClicked(){
		return okClicked;
	}
	/**
     * These are variables of class SearchTimeController.
     */
	ObservableList scoreList = FXCollections.observableArrayList ("1","2","3","4","5","6","7","8","9");
	
	ObservableList regionList = FXCollections.observableArrayList ("Red","Yellow","Blue","任意");
	
	ObservableList uptime_hour_List = FXCollections.observableArrayList ("0","1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24");
	
	ObservableList downtime_hour_List = FXCollections.observableArrayList ("0","1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24");
	
	ObservableList uptime_minute_List = FXCollections.observableArrayList ("0","5","10","15","20","25","30","35","40","45","50","55","60");
	
	ObservableList downtime_minute_List = FXCollections.observableArrayList ("0","5","10","15","20","25","30","35","40","45","50","55","60");
	
	ObservableList biggestlong_minute_List = FXCollections.observableArrayList ("0","30","60","90","120","150","180","210","240","270","300");
	
	ObservableList smallestlong_minute_List = FXCollections.observableArrayList ("0","30","60","90","120","150","180","210","240","270","300");
	
	ObservableList Bigroom_rowList = FXCollections.observableArrayList ("A","B","C","D","E","F","G","H","I","J","K","L","M","任意");
	
	ObservableList Smallroom_rowList = FXCollections.observableArrayList ("A","B","C","D","E","F","G","H","I","任意");
	
	@FXML
	private ComboBox<String> score;
	
	@FXML
	private ComboBox<String> region;
	
	@FXML
	private ComboBox<String> uptime_hour;
	
	@FXML
	private ComboBox<String> downtime_hour;
	
	@FXML
	private ComboBox<String> uptime_minute;
	
	@FXML
	private ComboBox<String> downtime_minute;
	
	@FXML
	private ComboBox<String> biggestlong_minute;
	
	@FXML
	private ComboBox<String> smallestlong_minute;
	
	@FXML
	private ComboBox<String> Bigroom_row;
	
	@FXML
	private ComboBox<String> Smallroom_row;
	
	@FXML
	private Label number1_label;

	@FXML
	private Label number2_label;
	
	@FXML
	private TableView<Movie> movieTable;
	
	@FXML
    private TableColumn<Movie,String> MovieNameColumn;
    
	@FXML
    private TableColumn<Movie,String> MovieIDColumn;
	
	@FXML
    private TableColumn<Movie,String> MovieTimeColumn;
	
	/**
     *This method is used to set the initial contents into the combobox/tablecolumn.
	 */
	@FXML
	private void initialize(){
		score.setItems(scoreList);
		region.setItems(regionList);
		uptime_hour.setItems(uptime_hour_List);
		downtime_hour.setItems(downtime_hour_List);
		uptime_minute.setItems(uptime_minute_List);
		downtime_minute.setItems(downtime_minute_List);
		biggestlong_minute.setItems(biggestlong_minute_List);
		smallestlong_minute.setItems(smallestlong_minute_List);
		Bigroom_row.setItems(Bigroom_rowList);
		Smallroom_row.setItems(Smallroom_rowList);
		number1_label.setText("  1");
		number2_label.setText("  1");
		movieTable.setItems(movieData);
		MovieNameColumn.setCellValueFactory(cellData -> cellData.getValue().movieNameProperty());
        MovieIDColumn.setCellValueFactory(cellData -> cellData.getValue().movieIDProperty());
        MovieTimeColumn.setCellValueFactory(cellData -> cellData.getValue().movietimeProperty());
	}
	/**
     *This method is used to show the request numbers of the seats. .
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
		seat = Integer.parseInt(rs.trim());
		if (Integer.parseInt(rs.trim()) == 0){
			seat = 1;
		}
	}
	/**
     *This method is used to add the request numbers of the seats. .
	 */
	@FXML
	private void handleAddNum(){
		num1 += 1;
		num2 = num1;
		setNumLabel();
	}
	/**
     *This method is used to minus the request numbers of the seats. .
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
     *This method is used to get the movie data. .
	 */
	public ObservableList<Movie> getmovieData() {
        return movieData;
    }
	/**
     *This method is used to select the movie whose score is upper than we pointed.
	 */
	private void handleScoreSelect(){
		movie_score = Integer.parseInt(score.getValue().toString());
		try{
			Connection conn = DriverManager.getConnection("jdbc:sqlite:movie_2.db");
			Statement statement = conn.createStatement();
			statement.execute("SELECT * FROM movie_info");
			ResultSet results = statement.getResultSet();
			int index = 0;
			while(results.next()){
				if (results.getInt("score") >= movie_score){
					movie_record_1 [index] = results.getString("movie");
					time_record_1 [index] = results.getString("time");
					id_record_1 [index] = results.getString("id");
				}
				else{
					movie_record_1 [index] = null;
					time_record_1 [index] = null;
					id_record_1 [index] = null;
				}
				index = index + 1;
			}
			results.close();
			statement.close();
		} 
		catch(SQLException e){
			System.out.println("error");
		}
	}
	/**
     *This method is used to select the movie whose runtime is between we pointed.
	 */
	private void handleLongSelect(){
		shortest_runtime = Integer.parseInt(smallestlong_minute.getValue().toString());
		longest_runtime = Integer.parseInt(biggestlong_minute.getValue().toString());
		try{
			Connection conn = DriverManager.getConnection("jdbc:sqlite:movie_2.db");
			Statement statement1 = conn.createStatement();
			statement1.execute("SELECT * FROM movie_info");
			ResultSet results1 = statement1.getResultSet();
			int index = 0;
			while(results1.next()){
				String temp = results1.getString("infor");
				String [] Array1 = temp.split("：");
				temp = Array1[1];
				String [] Array2 = temp.split("分");
				temp = Array2[0];
				int runtime = Integer.parseInt(temp);
				if ((runtime >= shortest_runtime) && (runtime <= longest_runtime)){
					movie_record_2 [index] = results1.getString("movie");
					time_record_2 [index] = results1.getString("time");
					id_record_2 [index] = results1.getString("id");
					
				}
				else {
					movie_record_2 [index] = null;
					time_record_2 [index] = null;
					id_record_2 [index] = null;
				}
				index = index + 1;
			}
		} 
		catch(SQLException e){
			System.out.println("error");
		}
	}
	/**
     *This method is used to select the movie whose broadcast time is between we pointed.
	 */
	private void handleTimeSelect(){
		try{
			time_uplimit[0] = Integer.parseInt(uptime_hour.getValue().toString());
			time_downlimit[0] = Integer.parseInt(downtime_hour.getValue().toString());
			time_uplimit[1] = Integer.parseInt(uptime_minute.getValue().toString());
			time_downlimit[1] = Integer.parseInt(downtime_minute.getValue().toString());
			Connection conn = DriverManager.getConnection("jdbc:sqlite:movie_2.db");
			Statement statement1 = conn.createStatement();
			statement1.execute("SELECT * FROM movie_info");
			ResultSet results1 = statement1.getResultSet();
			int index = 0;
			while(results1.next()){
				String temp = results1.getString("time");
				String [] time_temp = temp.split("：");
				int [] time = new int[2];
				time [0] = Integer.parseInt(time_temp[0]);
				time [1] = Integer.parseInt(time_temp[1]);
				if ((time[0] > time_uplimit[0]) && (time[0] < time_downlimit[0])){
					movie_record_3 [index] = results1.getString("movie");
					time_record_3 [index] = results1.getString("time");
					id_record_3 [index] = results1.getString("id");
					
				}
				else if ((time[0] == time_uplimit[0]) && (time[1] >= time_uplimit[1]) && (time[0] == time_downlimit[0]) && (time[1] <= time_downlimit[1])){
					movie_record_3 [index] = results1.getString("movie");
					time_record_3 [index] = results1.getString("time");
					id_record_3 [index] = results1.getString("id");
				}
				else {
					movie_record_3 [index] = null;
					time_record_3 [index] = null;
					id_record_3 [index] = null;
				}
				index = index + 1;
			}
		} 
		catch(SQLException e){
			System.out.println("error");
		}
	}
	/**
     *This method is used to determnie whether requested seats should be continue..
	 */
	@FXML
	private void handleIsContinue (){
		isContinue = isContinue == true ? false : true;
	}
	/**
     *This method is used to select the movie whose seats place we pointed are still empty.  .
	 */
	private void handlePlaceSelect(){
		try{
			Connection conn = DriverManager.getConnection("jdbc:sqlite:movie_2.db");
			Statement statement1 = conn.createStatement();
			statement1.execute("SELECT * FROM movie_info");
			ResultSet results = statement1.getResultSet();
			int index = 0;
			String [] movie_time_hall = new String[500];
			while(results.next()){
				String temp = results.getString("time");
				String [] time = temp.split("：");
				movie_record_5[index] = results.getString("movie").trim();
				time_record_5[index] = results.getString("time").trim();
				hall_record[index] = results.getString("hall").trim();
				movie_time_hall[index] = ("_" + time[0] + "_" + time[1] + "_" + results.getString("hall")).trim();
				index = index + 1;
			}
			for (int i = 0;i < index;i = i + 1){
				if ((hall_record[i].trim().equals("峨嵋")) || (hall_record[i].trim().equals("崆峒"))){
					String setrow = Smallroom_row.getValue().toString().trim();
					statement1.execute("SELECT * FROM small_room" + movie_time_hall[i]);
					ResultSet results1 = statement1.getResultSet();
					int index2 = 0;
					while(results1.next()){
						small_occupied_record[index2] = results1.getInt("occupied");
						small_row_record[index2] = results1.getString("row");
						small_seatNum_record[index2] = results1.getInt("seatNum");
						index2 = index2 + 1;
					}
					results1.close();
					String temp_movie = movie_record_5[i];
					String temp_time = time_record_5[i];
					movie_record_5[i] = null;
					time_record_5[i] = null;
					if (setrow.equals("任意")){
						if (isContinue == true){	
							for (int l = 0;l < index2;l = l + 1){
								if (small_seatNum_record[l] <= 4){
									int count = 0;
									for (int k = 0;k < seat;k = k + 1){
										if (l + k >= 150){
											break;
										}
										if ((small_occupied_record[l + k] == 0) && (small_seatNum_record[l + k] >= 1) && (small_seatNum_record[l + k] <= 4) && (small_seatNum_record[l] <= small_seatNum_record[l + k])){
											count = count + 1;
											if (count >= seat){
												movie_record_5[i] = temp_movie;
												time_record_5[i] = temp_time;
												break;
											}
										}
									}
								}
								else if ((small_seatNum_record[l] > 4) && (small_seatNum_record[l] < 13)){
									int count = 0;
									for (int k = 0;k < seat;k = k + 1){
										if (l + k >= 150){
											break;
										}
										if ((small_occupied_record[l + k] == 0) && (small_seatNum_record[l + k] >= 5) && (small_seatNum_record[l + k] <= 12) && (small_seatNum_record[l] <= small_seatNum_record[l + k])){
											count = count + 1;
											if (count >= seat){
												movie_record_5[i] = temp_movie;
												time_record_5[i] = temp_time;
												break;
											}
										}
									}
								}
								else if (small_seatNum_record[l] >= 13){
									int count = 0;
									for (int k = 0;k < seat;k = k + 1){
										if (l + k >= 150){
											break;
										}
								   		if ((small_occupied_record[l + k] == 0) && (small_seatNum_record[l + k] >= 13) && (small_seatNum_record[l + k] <= 16) && (small_seatNum_record[l] <= small_seatNum_record[l + k])){
								   			
								   			count = count + 1;
								   			if (count >= seat){
								   				movie_record_5[i] = temp_movie;
												time_record_5[i] = temp_time;
												break;
								   			}
								   		}
								   	}
								}
							}
						}
						else if (isContinue == false){
							int count = 0;
							for (int l = 0;l < index2;l = l + 1){
								if (small_occupied_record [l] == 0){
									count = count + 1;
									if (count >= seat){
										movie_record_5[i] = temp_movie;
										time_record_5[i] = temp_time;
										break;
									}
								}
							}
						}
					}
					else if((setrow.equals("任意")) == false){
						if (isContinue == true){	
							for (int l = 0;l < index2;l = l + 1){
								if (small_row_record[l].equals(setrow)){	
									if (small_seatNum_record[l] <= 4){
										int count = 0;
										for (int k = 0;k < seat;k = k + 1){
											if (l + k >= 150){
												break;
											}
											if ((small_occupied_record[l + k] == 0) && (small_seatNum_record[l + k] >= 1) && (small_seatNum_record[l + k] <= 4) && (small_seatNum_record[l] <= small_seatNum_record[l + k])){
												count = count + 1;
												if (count >= seat){
													movie_record_5[i] = temp_movie;
													time_record_5[i] = temp_time;
												}
											}
										}
									}
									else if ((small_seatNum_record[l] > 4) && (small_seatNum_record[l] < 13)){
										int count = 0;
										for (int k = 0;k < seat;k = k + 1){
											if (l + k >= 150){
												break;
											}
											if ((small_occupied_record[l + k] == 0) && (small_seatNum_record[l + k] >= 5) && (small_seatNum_record[l + k] <= 12) && (small_seatNum_record[l] <= small_seatNum_record[l + k])){
												count = count + 1;
												if (count >= seat){
													movie_record_5[i] = temp_movie;
													time_record_5[i] = temp_time;
													break;
												}
											}
										}
									}
									else if (small_seatNum_record[l] >= 13){
										int count = 0;
										for (int k = 0;k < seat;k = k + 1){
											if (l + k >= 150){
												break;
											}
											if ((small_occupied_record[l + k] == 0) && (small_seatNum_record[l + k] >= 13) && (small_seatNum_record[l + k] <= 16) && (small_seatNum_record[l] <= small_seatNum_record[l + k])){
									   			count = count + 1;
									   			if (count >= seat){
									   				movie_record_5[i] = temp_movie;
													time_record_5[i] = temp_time;
													break;
									   			}
									   		}
									   	}
									}
								}
							}
						}
						else if (isContinue == false){
							int count = 0;
							for (int l = 0;l < index2;l = l + 1){
								if (small_row_record[l].equals(setrow)){
									if (small_occupied_record [l] == 0){
										count = count + 1;
										if (count >= seat){
											movie_record_5[i] = temp_movie;
											time_record_5[i] = temp_time;
											break;
										}
									}
								}	
							}
						}
					}
				}
				else if ((hall_record[i].trim().equals("武當")) || (hall_record[i].trim().equals("少林")) || (hall_record[i].trim().equals("華山"))){						
					String setrow = Bigroom_row.getValue().toString().trim();
					String setregion = region.getValue().toString().trim();
					statement1.execute("SELECT * FROM big_room"+ movie_time_hall[i]);
					ResultSet results3 = statement1.getResultSet();
					int index2 = 0;
					while(results3.next()){
						big_occupied_record[index2] = results3.getInt("occupied");
						big_row_record[index2] = results3.getString("row");
						region_record[index2] = results3.getString("region");
						big_seatNum_record[index2] = results3.getInt("seatNum");
						index2 = index2 + 1;
					}
					String temp_movie = movie_record_5[i];
					String temp_time = time_record_5[i];
					movie_record_5[i] = null;
					time_record_5[i] = null;
					results3.close();
					if ((setrow.equals("任意")) || (setregion.equals("任意"))){
						if (isContinue == true){
							for (int l = 0;l < index2;l = l + 1){
								if ((big_seatNum_record[l] >= 1) && (big_seatNum_record[l] <= 38)){
									int count = 0;
									for (int k = 0;k < seat;k = k + 1){
										if (l + k >= 500){
											break;
										}
										if ((big_occupied_record[l + k] == 0) && (big_seatNum_record[l] <= big_seatNum_record[l + k]) && (big_seatNum_record[l + k] >= 1) && (big_seatNum_record[l + k] <= 38)){
											count = count + 1;
											if (count >= seat){
												movie_record_5[i] = temp_movie;
												time_record_5[i] = temp_time;
												break;
											}
										}
									}
								}
							}
						}
						else if (isContinue == false){
							int count = 0;
							for (int l = 0;l < index2;l = l + 1){
								if (big_occupied_record [l] == 0){
									count = count + 1;
									if (count >= seat){
										movie_record_5[i] = temp_movie;
										time_record_5[i] = temp_time;
										break;
									}
								}
							}
						}
					}
					else if ((setrow.equals("任意")) || (setregion.equals("任意")) == false){
						if (isContinue == true){
							for (int l = 0; l < index2;l = l + 1){
								if ((big_row_record[l].equals(setrow)) || (region_record[l].equals(setregion))){
									if ((big_seatNum_record[l] >= 1) && (big_seatNum_record[l] <= 38)){
										int count = 0;
										for (int k = 0;k < seat;k = k + 1){
											if (l + k >= 500){
												break;
											}
											if ((big_occupied_record[l + k] == 0) && (big_seatNum_record[l] <= big_seatNum_record[l + k]) && (big_seatNum_record[l + k] >= 1) && (big_seatNum_record[l + k] <= 38)){
												count = count + 1;
												if (count >= seat){
													movie_record_5[i] = temp_movie;
													time_record_5[i] = temp_time;
													break;
												}
											}
										}
									}
								}
							}							
						}
						else if (isContinue == false){
							int count = 0;
							for (int l = 0; l < index2;l = l + 1){
								if ((big_row_record[l].equals(setrow)) || (region_record[l].equals(setregion))){
									if (big_occupied_record [l] == 0){
										count = count + 1;
										if (count >= seat){
											movie_record_5[i] = temp_movie;
											time_record_5[i] = temp_time;
											break;
										}
									}
								}
							}		
						}
					}
				}			
			}
		}
		catch(SQLException e){
			System.out.println("error");
		}
	}
	/**
     * These are variables of class SearchTimeController.
     */
	@FXML
	private Label showTime;
	
	private void inputTime(String timelist){
		
	}
	/**
     *This method is used to do all the function at the same time and address the movie we selected.  .
	 */
	@FXML
	private void conclude(){
		setNumLabel();
		handleScoreSelect();
		handleLongSelect();
		handleTimeSelect();
		handlePlaceSelect();
		
		for (int i = 0;i < 30;i = i + 1){
			if ((movie_record_2[i] == null) || (movie_record_3[i] == null) || (movie_record_5[i] == null)){
				movie_record_1[i] = null;
				time_record_1[i] = null;
				id_record_1[i] = null;
			}
		}
		String name = null;
		String time_temp = "";
		for (int i = 0;i < 27;i = i + 1){
			if (movie_record_1[i] != null){
				movie_record.add(movie_record_1[i]);
				time_record.add(time_record_1[i]);
				id_record.add(id_record_1[i]);
			}
		}
		name = "cre";
		for (int i = 0;i < movie_record.size();i = i + 1){
			if (((name.equals(movie_record.get(i))) == false) || (name == "cre")){
				movie.add(movie_record.get(i));
				id.add(id_record.get(i));
				time_temp = time_record.get(i);
				for (int j = 0;j < movie_record.size();j = j + 1){
					if (movie_record.get(i).equals(movie_record.get(j))){
						time_temp = time_temp + " | " + (time_record.get(j));
					}
				}
				time.add(time_temp);
			}
			name = movie_record.get(i);
		}
		
		for (int i = 0;i < movie.size();i = i + 1){
			movieData.add(new Movie(movie.get(i), id.get(i), time.get(i)));
		}
	}
}
