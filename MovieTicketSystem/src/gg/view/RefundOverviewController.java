/**
 * The RefundOverviewContrller program is for fxml.
 *  
 * @author  b03505051
 * @version 1.0
 * @since   2017-06-01
 */
package gg.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

import gg.MainApp;
import gg.model.TicketRefund;



public class RefundOverviewController {
	/**these are javafx variable**/
	private Stage dialogStage;
	@FXML
	private Label result ;
	@FXML
	private TextField tkidField ;
	
	//Reference to the main application
	private MainApp mainApp ;
	/**
     * This is a constructor of this class
     *     
     */
	public RefundOverviewController(){		
	}
	/**
     * This is a method to initialize.
     *     
     */
	@FXML
	private void initialize(){
	}
	/**
	* This method is used to set dialog stage.
	* @param dialogStage The dialog stage.
	*/
	public void setDialogStage(Stage dialogStage){
		this.dialogStage = dialogStage;
	}
	/**
	* This method is used to set mainapp.
	* @param mainapp The mainapp.
	*/
	public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
	/**
	* This method is used to hand button.
	* @param event The action event.
	*/
	@FXML
	private void handleSentTicket(ActionEvent event) throws ClassNotFoundException, SQLException {
		
		String text = tkidField.getText();
		TicketRefund proc = new TicketRefund() ;
		String output = proc.backend(text) ;
        result.setText(output);
		
	}
	
}
