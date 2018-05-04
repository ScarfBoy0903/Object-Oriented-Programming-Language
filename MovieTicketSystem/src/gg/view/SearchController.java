/**
 *@author:Dennis Lee
 *@version:1.0
 *@since:2017-6-25
 */
package gg.view;
import gg.MainApp;
import javafx.stage.Stage;
import gg.MainApp;
import gg.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
public class SearchController {
	/**
     * These are variables of class SearchController.
     */
	private Stage dialogStage;
	private MainApp mainApp;
	private boolean okClicked = false;
	/**
     *This method is used to set mainapp.
	 *@param mainApp This is the parameter of setMainApp method.
     */
	public void setMainApp(MainApp mainApp){
		this.mainApp = mainApp;
	}
	
	/**
     *This method is used to show the movie searching menu.
     */
	@FXML
	private void handSearchMovie(){
		boolean okClicked = mainApp.showSearchMovie("電影資訊");
		if(okClicked){
			
		}
	}
	
	/**
     *This method is used to show the ticket searching menu.
     */
	@FXML
	private void handSearchTicket(){
		boolean okClicked = mainApp.showSearchTicket("電影票資訊");
		if(okClicked){
			
		}
	}
	
	/**
     *This method is used to show the time searching menu.
     */
	@FXML
	private void handSearchTime(){
		boolean okClicked = mainApp.showSearchTime("電影時刻查詢");
		if(okClicked){
			
		}
	}
	
	/**
     *This method is used to close the main searching menu.
     */
	@FXML
	private void handleOk(){
		okClicked = true;
		dialogStage.close();
	}
	/**
     *This method is used to set the dialogstage.
     *@param dialogStage This is the parameter of setDialogStage method.
	 */
	public void setDialogStage(Stage dialogStage){
		this.dialogStage = dialogStage;
	}
	/**
     *This method is used to change the okClicked value.
	 *@return okClicked
     */
	public boolean isOkClicked(){
		return okClicked;
	}
}
