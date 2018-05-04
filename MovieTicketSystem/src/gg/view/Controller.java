
package gg.view;

import gg.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

/**
 * <h1>Controller</h1>
 * 
 * General booking's controller
 * <p>
 * 
 * @author Chien-Wei-Chin
 * @version 1.0
 * @since 2017-06-25
 */
public class Controller {

	private Connection conn;
	private String movieSelect;
	private String timeHallSelect;
	private String userSelect;
	private int num1 = 1;
	private int num2 = 1;
	@FXML
	private Button finish;
	private Stage dialogStage;
	// @FXML
	// ObservableList movieList =FXCollections.observableArrayList
	// ("逃出絕命鎮 Get Out",
	// "異形：聖約 Alien: Covenant",
	// "亞瑟：王者之劍 King Arthur: Legend of the Sword",
	// "電影版影子籃球員LAST GAME",
	// "攻殼機動隊1995 GHOST IN THE SHELL ",
	// "我和我的冠軍女兒 Dangal");
	// ObservableList userList;
	@FXML
	private ComboBox<String> movieCombo;
	@FXML
	private Label movieNameLabel;
	@FXML
	private Label timeHallLabel;
	@FXML
	private Label userNameLabel;
	@FXML
	private ComboBox<String> timeAndHallCombo;
	@FXML
	private ComboBox<String> userCombo;
	@FXML
	private ComboBox<String> ticketNumCombo;
	@FXML
	private Label number1_label;
	@FXML
	private Label number2_label;
	@FXML
	private TextArea result;
	@FXML
	private Button cancel;

	/**
	 * Set the dialog Stage
	 * 
	 * @param dialogStage
	 *            set stage
	 */
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	/**
	 * initialization for the window, set move, user comobox and set text Area
	 * not editable.
	 * 
	 * @throws ClassNotFoundException
	 */
	@FXML
	private void initialize() throws ClassNotFoundException {
		if (!Datasource.getInstance().open()) {
			System.out.println("FATAL ERROR: Couldn't connect to database");
		}
		userCombo.setItems(FXCollections.observableList(Datasource.getInstance().getAllUserName()));
		movieCombo.setItems(FXCollections.observableList(Datasource.getInstance().getMovieName()));
		number1_label.setText("   1");
		number2_label.setText("   1");
		result.setEditable(false);
	}

	/**
	 * The controller to handle movie select.
	 */
	@FXML
	private void handleMovieSelect() {
		timeHallLabel.setText("");
		// System.out.println(movie.getValue().toString());
		movieSelect = movieCombo.getValue().toString();
		movieNameLabel.setText(movieSelect);
		selectTimeAndHall();
	}

	/**
	 * Select time and hall from database.
	 */
	private void selectTimeAndHall() {
		timeAndHallCombo.getItems().clear();
		List<String> item = new ArrayList<>();
		try {
			conn = DriverManager.getConnection(Datasource.CONNECTION_STRING);
			Statement statement = conn.createStatement();
			ResultSet results = statement
					.executeQuery("SELECT time,hall FROM movie_info WHERE " + "movie like '%" + movieSelect + "%'");

			while (results.next()) {
				String timeAndHallItem = results.getString("time") + "/" + results.getString("hall");
				item.add(timeAndHallItem);
				// System.out.println(timeAndHallItem);
	
			}

		} catch (SQLException e) {
			System.out.println("Couldn't connect to database: " + e.getMessage());
			System.exit(1);
		}
		timeAndHallCombo.getItems().addAll(item);
	}

	/**
	 * Controller to handle the time select.
	 */
	@FXML
	private void handleTimeSelect() {
		if (timeAndHallCombo.getValue() != null) {
			timeHallSelect = timeAndHallCombo.getValue();
			timeHallLabel.setText(timeHallSelect);
		}
	}

	/**
	 * Controller to handle user select.
	 */
	@FXML
	private void handleUserSelect() {
		if (userCombo.getValue() != null) {
			userSelect = userCombo.getValue();
			// System.out.println(timeAndHall.getValue().toString());
			userNameLabel.setText(userSelect);
		}
	}

	/**
	 * Controller to handle adding the ticket number.
	 */
	@FXML
	private void handleAddNum() {
		num1 += 1;
		num2 = num1;
		setNumLabel();
	}

	/**
	 * Controller to handle minusing the ticket number.
	 */
	@FXML
	private void handleMinusNum() {
		if (num1 > 1) {
			num1 -= 1;
			num2 = num1;
			setNumLabel();
		}
	}

	/**
	 * Set the ticket number that the user selected
	 */
	private void setNumLabel() {
		String rs = "0";
		if (num1 < 10) {
			rs = "   " + num1;
		} else if (num1 < 100) {
			rs = "  " + num1;
		} else {
			rs = " " + num1;
		}
		number1_label.setText(rs);
		number2_label.setText(rs);
	}

	/**
	 * Controller to show the final result.
	 */
	@FXML
	private void handleFinish() throws NotAllSelException {
		String output = "";
		try{
		String hall = timeHallSelect.split("/")[1];
		String time = timeHallSelect.split("/")[0];
		String classfication = Datasource.getInstance().qualify(userSelect, movieSelect);
		Datasource.getInstance().setSearchString(time, hall);
		
		if (classfication != null) {
			output += (String.format("失敗，該電影分級為%s，%d歲無法購買\n", classfication,
					Datasource.getInstance().getAge(userSelect)));
		} else if (num1 > Datasource.getInstance().getSeatLeft()) {
			output += String.format("失敗，%s 於 %s 在 %s 廳 座位數量不夠\n", movieSelect, timeHallSelect.split("/")[0],
					timeHallSelect.split("/")[1]);
		} else {

			// System.out.println(timeHallSelect + movieSelect + num1);
			List<Ticket> ticketBought = Datasource.getInstance().bookSeat(num1, time, hall);

			for (Ticket t : ticketBought) {
				output += ("票根流水號" + t.getTicketId()+ "在" +t.getSeat());
				output += "\n";
			}
			switch (hall) {
			case "武當":
			case "少林":
			case "華山":
				output += ("於 " + ticketBought.get(1).getTime() + " 在 " + ticketBought.get(1).getHall() + " 廳"
						+ " 目前仍有 " + "gray: " + Datasource.getInstance().getGrayLeft() + " blue: "
						+ Datasource.getInstance().getBlueLeft() + " yellow: "
						+ Datasource.getInstance().getYellowLeft() + " red: " + Datasource.getInstance().getRedLeft());
				break;
			default:
				output += ("於 " + ticketBought.get(1).getTime() + " 在 " + ticketBought.get(1).getHall() + " 廳"
						+ " 目前仍有 " + Datasource.getInstance().getSeatLeft());
			}

		}
		
		result.setText(output);
		output = "";
		}catch(Exception e){
			output = "尚未選擇完成！";
			result.setText(output);
		}
		
	}
	
	/**
	 * Close the window by clicking cancel
	 */
	@FXML
	private void handleCancel() {
		Datasource.getInstance().close();
		dialogStage.close();
	}

}
