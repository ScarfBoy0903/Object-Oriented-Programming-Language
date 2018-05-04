package gg.model;

import java.sql.*;
import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Movie {

	/**
	* These are the variables of this method.
	*/
	private String name;
	private ArrayList<String> time;
	private ArrayList<String> hall;
	private ArrayList<String> id;
	private String classification;
	private StringProperty movieName;
    private StringProperty movieID;
    private StringProperty timelist;

	/**
     * This is a constructor of Movie class
     *     
     */   
	public Movie(){
		this(null);
		//classification = "普遍";
	}


	/**
     * This is a constructor of Movie class
     * @param name the movie name    
     */
	public Movie(String name){
		this.name = name;
		//time = new ArrayList<String>();
		//hall = new ArrayList<String>();
		set_infor();
	}

	/**
     * This is a constructor of Movie class
     * @param name the movie name  
	 * @param id the movie id
	 * @param time the time of movie
     */
	public Movie(String name,String id,String time){
		//time = new ArrayList<String>();
		//hall = new ArrayList<String>();
		movieName = new SimpleStringProperty(name);
		movieID = new SimpleStringProperty(id);
		timelist = new SimpleStringProperty(time);
	}
	
	/**
     * This is used to set infor
     */
	public void set_infor(){
		Connection c = null;
		Statement stmt = null;
		time = new ArrayList<String>();
		hall = new ArrayList<String>();
		try{
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:movie_2.db");
			c.setAutoCommit(false);
			//System.println("name: "+name);
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT classification,hall,time FROM movie_info WHERE movie LIKE'"+ name +"%';");
			classification = rs.getString("classification");
			while(rs.next()){
				time.add(rs.getString("time"));
				hall.add(rs.getString("hall"));
			}
			rs.close();
			stmt.close();
			c.commit();
			c.close();
		}catch(Exception e){
			System.err.println( e.getClass().getName() + ": " + e.getMessage() +"name ="+name);
		}
	}

	/**
     * This is used to return movie name property
	 * @return StringProperty movie name
     */
	public StringProperty movieNameProperty() {
        return movieName;
    }

	/**
     * This is used to return movie id property
	 * @return StringProperty movieid
     */
	public StringProperty movieIDProperty() {
        return movieID;
    }

	/**
     * This is used to return movie time property
	 * @return StringProperty timelist
     */
	public StringProperty movietimeProperty() {
        return timelist;
    }

	/**
     * This is used to return the number of session
	 * @return int time_size
     */
	public int getNumOfSession(){
		return time.size();
	}
	
	/**
     * This is used to return number of halls
	 * @return int hall_size
     */
	public int getNumOfHall(){
		return hall.size();
	}

	/**
     * This is used to return the time
	 * @param index the index of the time_array
	 * @return String time
     */
	public String getTime(int index){
		return time.get(index);
	}
	
	/**
     * This is used to return the hall
	 * @param index The index of the hall_array 
	 * @return String hall
     */
	public String getHall(int index){
		return hall.get(index);
	}
	
	/**
     * This is used to return the Classification
	 * @return String classification
     */
	public String getClassification(){
		return classification;
	}
}
