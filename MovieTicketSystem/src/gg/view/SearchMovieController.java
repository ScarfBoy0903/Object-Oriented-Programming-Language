/**
 *@author:Dennis Lee
 *@version:1.0
 *@since:2017-6-25
 */
package gg.view;
import java.util.ArrayList;

import gg.MainApp;
import gg.model.Movie;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
public class SearchMovieController {
	/**
     * These are variables of class SearchMovieController.
     */
	private String time_temp = null;
	private String hall_temp; 
	private Stage dialogStage;
	private boolean okClicked = false;
	/**
     *This method is used to set the dialogstage.
     *@param dialogStage This is the parameter of setDialogStage method.
	 */
	public void setDialogStage(Stage dialogStage){
		this.dialogStage = dialogStage;
	}
	/**
     * These are variables of class SearchMovieController.
     */
	ObservableList movieList = FXCollections.observableArrayList
			(" 逃出絕命鎮 Get Out",
			" 異形：聖約 Alien: Covenant",
			" 亞瑟：王者之劍 King Arthur: Legend of the Sword",
			" 電影版影子籃球員LAST GAME",
			" 攻殼機動隊1995 GHOST IN THE SHELL ",
			" 我和我的冠軍女兒 Dangal"
			);
	/**
     * These are variables of class SearchMovieController.
     */
	@FXML
	private ComboBox<String> movie;
	@FXML
	private Label movie_name;
	@FXML
	private Label movie_classification;
	@FXML
	private Label movie_time;
	@FXML
	private Label movie_hall;
	/**
     *This method is used to set the movielist contents into the movie combobox.
	 */
	@FXML
	private void initialize(){
		movie.setItems(movieList);
	}
	/**
     *This method is used to set the movie contents into the label to show the information.
	 */
	@FXML
	private void handleMovieSelect(){
		String selected = movie.getValue().toString();
		movie_name.setText(selected);
		Movie CatchData = new Movie(selected);
		hall_temp = CatchData.getHall(0);
		time_temp = CatchData.getTime(0);		
		for (int i = 1;i < CatchData.getNumOfSession();i = i + 1){
			time_temp = time_temp + "  |  " + CatchData.getTime(i);
		}
		movie_time.setText(time_temp);
		movie_hall.setText(hall_temp);
		movie_classification.setText(CatchData.getClassification());
	}
	/**
     *This method is used to close the movie searching menu.
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
}
