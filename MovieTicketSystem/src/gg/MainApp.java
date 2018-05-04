/**
 *   
 * @author  b03505053
 * @version 3.0
 * @since   2017-06-11
 */
package gg;
import java.io.IOException;


import gg.model.Datasource;
import gg.view.Controller;
import gg.view.NormalDialogController;
import gg.view.OverviewController;
import gg.view.RefundOverviewController;
import gg.view.SearchController;
import gg.view.SearchMovieController;
import gg.view.SearchTicketController;
import gg.view.SearchTimeController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainApp extends Application {

	/**
	* These are the variable of this class
	*/
	private Stage primaryStage;
	private BorderPane rootLayout;

	/**
	* This method is used to start.
	* @param primaryStage
	*/
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Demo");

		initRootLayout();

		showOverview();
	}

	/**
	* This method is used to initialize.
	* @throws Exception
	*/
	@Override
    public void init() throws Exception {
        super.init();
        if(!Datasource.getInstance().open()) {
            System.out.println("FATAL ERROR: Couldn't connect to database");
            Platform.exit();
        }
    }

	/**
	* This method is used to initialize RootLayout.
	*/	
	public void initRootLayout(){
		try{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();

			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	/**
	* This method is used to show Oview dialog.
	*/
	public void showOverview(){
		try{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/Overview.fxml"));
			AnchorPane overview = (AnchorPane) loader.load();
			rootLayout.setCenter(overview);

			OverviewController controller = loader.getController();
			controller.setMainApp(this);
		}catch(IOException e){
			e.printStackTrace();
		}		
	}

	/**
	* This method is used to show conditional dialog.
	* @param title This is the variable of title.
	* @return boolean Does show successifully.
	*/
	public boolean showNormalDialog(String title){
		try{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/NormalDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			Stage dialogStage = new Stage();
			dialogStage.setTitle(title);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			

			NormalDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			dialogStage.showAndWait();

			return controller.isOkClicked();
		}catch(IOException e){
			System.out.println("show dialog error:");
			return false;
		}
	}

	/**
	* This method is used to show refund overview.
	* @return boolean Does show successifully.
	*/
	public boolean showrefundOverview() {
		// TODO Auto-generated method stub
		try{
			//Load refundOverView
			FXMLLoader loader = new FXMLLoader() ;
			loader.setLocation(MainApp.class.getResource("view/RefundOverview.fxml"));
			AnchorPane refundOverview = (AnchorPane) loader.load() ; 
			
			//Show the scene 
			Scene scene = new Scene(refundOverview) ;
			Stage dialogStage = new Stage();
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			dialogStage.setScene(scene);
						
			//Give the controller access to mainapp
			RefundOverviewController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			dialogStage.showAndWait();
			
			return true;
		}catch(IOException e){
			e.printStackTrace() ;
			return false ;
		}
		
	}

	/**
	* This method is used to show general dialog.
	* @return boolean Does show successifully.
	*/
	public boolean showGeneralDialog(String title){
		try{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/General.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			Stage dialogStage = new Stage();
			dialogStage.setTitle(title);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			

			Controller controller = loader.getController();
			controller.setDialogStage(dialogStage);
			dialogStage.showAndWait();

			return true;
		}catch(IOException e){
			System.out.println("show dialog error:");
			return false;
		}
	}
	/**
	* This method is used to show search dialog.
	* @param title This is the variable of title.
	* @param mainApp This is the reference of mainApp
	* @return boolean Does show successifully.
	*/
	public boolean showSearch(String title,MainApp mainApp){
		try{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/SearchOverview.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle(title);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			SearchController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setMainApp(mainApp);
			dialogStage.showAndWait();
			return true;
		}catch(IOException e){
			System.out.println("show dialog error:");
			return false;
		}
	}
	/**
	* This method is used to show search movie dialog.
	* @param title This is the variable of title.
	* @return boolean Does show successifully.
	*/
	public boolean showSearchMovie(String title){
		try{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/SearchMovie.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			
			Stage dialogStage = new Stage();
			dialogStage.setTitle(title);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			
			SearchMovieController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			dialogStage.showAndWait();
			
			return true;
		}catch(IOException e){
			System.out.println("show dialog error:");
			return false;
		}
	}

	/**
	* This method is used to show search ticket dialog.
	* @param title This is the variable of title.
	* @return boolean Does show successifully.
	*/
	public boolean showSearchTicket(String title){
		try{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/SearchTicket.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			
			Stage dialogStage = new Stage();
			dialogStage.setTitle(title);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			
			SearchTicketController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			dialogStage.showAndWait();
			
			return true;
		}catch(IOException e){
			System.out.println("show dialog error:");
			return false;
		}
	}

	/**
	* This method is used to show search time dialog.
	* @param title This is the variable of title.
	* @return boolean Does show successifully.
	*/
	public boolean showSearchTime(String title){
		try{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/SearchTime.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			
			Stage dialogStage = new Stage();
			dialogStage.setTitle(title);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			
			SearchTimeController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			dialogStage.showAndWait();
			
			return true;
		}catch(IOException e){
			System.out.println("show dialog error:");
			return false;
		}
	}
	
	/**
	* This method is used to get primary stage.
	* @return Stage the stage.
	*/	
	public Stage getPrimaryStage(){
		return primaryStage;
	}
	public static void main(String[] args) {
		launch(args);
	}
}