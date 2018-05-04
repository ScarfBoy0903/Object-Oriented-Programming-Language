/**
 * The TicketRefund program implements the refund-system in movie-system.
 *  
 * @author  b03505051
 * @version 1.0
 * @since   2017-06-01
 */
package gg.model;

import java.sql.* ;
import java.text.ParseException ;
import java.text.SimpleDateFormat ;
import java.util.Scanner ;
import java.util.Date ;
import java.util.Iterator;

import javax.xml.crypto.Data ;

public class TicketRefund {
	/**
	* This method is used to process the refund procedure.
	* @param inputid The id user enters.
	* @throws ClassNotFoundException, SQLException
	* @return String Return refund result.
	*/
	public String backend(String inputid) throws ClassNotFoundException, SQLException{
		SQLiteConn sql1 = new SQLiteConn() ;
		sql1.ConnDB() ;
		// Connection to movie.db
		
		String output ;
		
		String tkID = inputid ;
		//get user's ticket-id 
		
		PreparedStatement tkid_str =sql1.conn.prepareStatement(" SELECT id FROM ticket_sold WHERE id=?") ;
		tkid_str.setString(1, tkID);
		ResultSet tkid_rs = tkid_str.executeQuery() ;
		//select input ticket id	
		
		if(tkid_rs.next()){
			if(timeprocess(sql1,tkID)){
				PreparedStatement tkid_hall = sql1.conn.prepareStatement(" SELECT hall FROM ticket_sold WHERE id=? ") ;
				tkid_hall.setString(1, tkID);
				ResultSet tkhall_rs = tkid_hall.executeQuery() ;
				String room = tkhall_rs.getString(1) ;
				System.out.println((hallString(gettime(sql1,tkID),room)));
				String update_str = "UPDATE"+" "+hallString(gettime(sql1,tkID),room)+" "+"SET occupied=0 WHERE id ="+'"'+tkID+'"' ;
				System.out.println(update_str);
				PreparedStatement tkid_update = sql1.conn.prepareStatement(update_str) ;
				tkid_update.executeUpdate() ;
				//change the ticket to be unsold
				
				PreparedStatement tkid_del = sql1.conn.prepareStatement("DELETE FROM ticket_sold WHERE id=?") ;
				tkid_del.setString(1,tkID);
				tkid_del.executeUpdate() ;
				output = "Succeed, Full Refund ! See u next time " ;
				return output ;
			}
			else{
				output = "Hey bros the ticket is only refundable 20 minutes before the movie starts";
				return output ;
			}
		}
		else{
			output = "Hey bros u hava wrong Ticket ID" ;
			return output ;
		}
	}
		
		
		
	
	/* get the Unix time*/
	/**
	* This method is used to get the Unix time.
	* @return String Return unixtime.
	*/
	public static String getDateTime(){
		SimpleDateFormat sdFormat = new SimpleDateFormat("HH:mm");
		Date date = new Date();
		String unixtime = sdFormat.format(date);
		//System.out.println(strDate);
		return unixtime;
		}
	
	/*select input ticket time in database*/
	/**
	* This method is used to get the time .
	* @return String Return unixtime.
	*/
	public static String gettime(SQLiteConn sql1,String tkID) throws SQLException {
		String time ;
		PreparedStatement tkid_time = sql1.conn.prepareStatement(" SELECT time FROM ticket_sold WHERE id=?") ;
		tkid_time.setString(1, tkID);
		ResultSet tktime_rs = tkid_time.executeQuery() ;
		time = tktime_rs.getString(1) ;
		
		return time ;
	}
	/**
	* This method is used to process the time procedure.
	* @param sql1 The connection.
	* @param tkID The enter ticket id.
	* @throws SQLException
	* @return boolean Return true if the time after process is more than 20 minutes.
	*/
	public static boolean timeprocess(SQLiteConn sql1,String tkID) throws SQLException{
		PreparedStatement tkid_time = sql1.conn.prepareStatement(" SELECT time FROM ticket_sold WHERE id=?") ;
		tkid_time.setString(1, tkID);
		ResultSet tktime_rs = tkid_time.executeQuery() ;
		
		String tktime_str = tktime_rs.getString(1) ;
		//select input ticket time in database
		
		String unixtime = getDateTime() ;
		//get the time user use this system
		System.out.println(unixtime);
		int remtime = 0 ;
		int time1 = 0 ;
		int time2 = 0 ;
		String[] aArray = tktime_str.split("：") ;
		String[] bArray = unixtime.split(":") ;
		for(int i=0;i<2;i++){
			if(i==0){
				time1 = 60*Integer.parseInt(aArray[i]) ;
				time2 = 60*Integer.parseInt(bArray[i]) ;
			}
			if(i==1){
				time1 = time1+Integer.parseInt(aArray[i]) ;
				time2 = time2+Integer.parseInt(bArray[i]) ;
			}
		}
		//turn string to time
		remtime = time1 - time2 ;
		if(remtime>=20){
			return true ;
		}
		else return false ;
		
	}
	/**
	* This method is used to set searched string.
	* @param time is unanimously the time
	* @param hall is unanimously the hall
	* @throws SQLException
	* @return boolean Return true if the time after process is more than 20 minutes.
	*/
	public String hallString(String time, String hall) {
		String search ;
		String[] temp = time.split("：");
		switch (hall){
		case "武當":
		case "少林":
		case "華山":
				 search = "big_room_" + temp[0] + "_" + temp[1] + "_" + hall;
			break;
			default:
				 search = "small_room_" + temp[0] + "_" + temp[1] + "_" + hall;
		}
		
		return search;
	}
	
	public static void main(String[] args)  {
		
	}
}