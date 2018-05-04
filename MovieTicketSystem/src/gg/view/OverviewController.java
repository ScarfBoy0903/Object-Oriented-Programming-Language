/**
 *   
 * @author  b03505053
 * @version 3.0
 * @since   2017-06-11
 */
package gg.view;

import java.sql.SQLException;

import Crawl.Crawler;
import gg.MainApp;
import gg.model.Datasource;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class OverviewController {

	/**
	* This is the variable of this class
	*/
	private MainApp mainApp;
	
	/**
	* This is the the constructor of class OverviewController.
	*/
	public OverviewController(){}
	@FXML
	private TextArea onlineData;
	/**
	* This method is used to handle normal booking dialog. 
	*/
	@FXML
	private void initialize() throws ClassNotFoundException {
		onlineData.setEditable(false);
	}
	@FXML
	private void handleNormalBooking(){
		boolean okClicked = mainApp.showGeneralDialog("一般訂票");
		if (okClicked) {
			
		}
	}
	
	/**
	* This method is used to handle contitional booking dialog. 
	*/
	@FXML
	private void handleConditionalBooking(){
		boolean okClicked = mainApp.showNormalDialog("條件式訂票");
		if(okClicked){
			
		}


	}
	/**
	* This method is used to handle refund dialog. 
	*/	
	@FXML
	private void handlerRefund(){
		boolean okClicked = mainApp.showrefundOverview();
		if(okClicked){
			
		}
	}
	/**
	* This method is used to handle search dialog. 
	*/
	@FXML
	private void handleSearch(){
		boolean okClicked = mainApp.showSearch("查詢", mainApp);
		if(okClicked){
			
		}
	}
	
	/**
	* This method is used to handle exit. 
	*/
	@FXML
	private void handleExit(){

	}
	
	/**
	* This method is used to setMainApp.
	* @param mainApp this is the reference of mainApp
	*/
	public void setMainApp(MainApp mainApp){
		this.mainApp = mainApp;
	}
	@FXML
	private void handleUpdateMovie() throws ClassNotFoundException, SQLException{
		onlineData.setText("下載電影資訊中...請稍後");
		new Crawler().CrawlData();
		onlineData.setText("已從http://www.atmovies.com.tw/movie/now/ \n "
				+ "下載以下電影資訊至資料庫\n" +
		new Crawler().getCrawedMovieName());
	}
	

}
