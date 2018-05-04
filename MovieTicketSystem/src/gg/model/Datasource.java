package gg.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * <h1>Datasource</h1>
 * 
 * The funtion to access the database
 * <p>
 * 
 * @author Chien-Wei-Chin
 * @version 1.0
 * @since 2017-06-25
 */
public class Datasource {

	public static final String DB_NAME = "movie_2.db";

	public static final String CONNECTION_STRING = "jdbc:sqlite:" + DB_NAME;

	public static final String TABLE_MOVIE_INFO = "movie_info";
	public static final String COLUMN_MOVIE_ID = "id";
	public static final String COLUMN_MOVIE_TIME = "time";
	public static final String COLUMN_MOVIE_HALL = "hall";

	public static final String COLUMN_BIG_ROOM_OCCUPIED = "occupied";
	public static final String COLUMN_BIG_ROOM_ID = "id";
	public static final String COLUMN_BIG_ROOM_ROW = "row";
	public static final String COLUMN_BIG_ROOM_SEATNUM = "seatNum";
	private static Datasource instance = new Datasource();
	private Connection conn;
	private String search;

	/**
	 * Private constructor that prevents access the data directly
	 */
	private Datasource() {

	}

	/**
	 * Creates a Datasource object
	 * 
	 * @return an datasource object
	 */
	public static Datasource getInstance() {
		return instance;
	}

	/**
	 * Try to connect to the data base.
	 * 
	 * @return true if successfully access the database,false if not
	 * 
	 */
	public boolean open() {
		try {
			// System.out.println("success!");
			conn = DriverManager.getConnection("jdbc:sqlite:movie_2.db");
			return true;
		} catch (SQLException e) {
			System.out.println("Couldn't connect to database: " + e.getMessage());
			return false;
		}
	}

	/**
	 * The method to close the connection to the database
	 */
	public void close() {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("Couldn't close connection: " + e.getMessage());
		}
	}

	/**
	 * Generate search string for database by time and hall.
	 * 
	 * @param time
	 *            movie time
	 * @param hall
	 *            movie hall
	 */
	public void setSearchString(String time, String hall) {
		String[] temp = time.split("：");
		switch (hall) {
		case "武當":
		case "少林":
		case "華山":
			search = "big_room_" + temp[0] + "_" + temp[1] + "_" + hall;
			break;
		default:
			search = "small_room_" + temp[0] + "_" + temp[1] + "_" + hall;
		}

	}

	/**
	 * Method to book seats
	 * 
	 * @param num
	 *            number of tickets to be booked
	 * @param time
	 *            the movie time
	 * @param hall
	 *            the movie's hall
	 * @return list of tickets that was booked
	 */
	public List<Ticket> bookSeat(int num, String time, String hall) {

		try {
			Statement statement = conn.createStatement();
			String select = "SELECT * FROM " + search + " WHERE " + COLUMN_BIG_ROOM_OCCUPIED + "=" + "0" + " LIMIT "
					+ Integer.toString(num);
			ResultSet results = statement.executeQuery(select);
			List<Ticket> ticketBooked = new ArrayList<>();
			while (results.next()) {
				Ticket tickets = new Ticket();
				PreparedStatement pstmt = conn
						.prepareStatement("UPDATE " + search + " SET " + COLUMN_BIG_ROOM_OCCUPIED + "=1" + " WHERE "
								+ COLUMN_BIG_ROOM_ID + "=" + "'" + results.getString(COLUMN_BIG_ROOM_ID) + "'");
				pstmt.executeUpdate();
				tickets.setTicketId(results.getString(COLUMN_BIG_ROOM_ID));
				tickets.setTime(time);
				tickets.setHall(hall);
				tickets.setSeat(results.getString(COLUMN_BIG_ROOM_ROW) + "_"
						+ String.format("%02d", results.getInt(COLUMN_BIG_ROOM_SEATNUM)));
				ticketBooked.add(tickets);
			}
			for (Ticket t : ticketBooked) {
				saveTicket(t);
			}
			return ticketBooked;

		} catch (SQLException e) {
			System.out.println("Query failed: " + e.getMessage());
			return null;
		}

	}

	/**
	 * Get the gray area's seat left
	 * 
	 * @return number of seat left
	 */
	public int getGrayLeft() {
		try {
			Statement statement = conn.createStatement();
			ResultSet results = statement.executeQuery("Select COUNT(*) FROM " + search
					+ " AS count WHERE region = 'gray' AND " + COLUMN_BIG_ROOM_OCCUPIED + "=0");
			return results.getInt(1);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return 0;
		}
	}

	/**
	 * Get the yellow area's seat left
	 * 
	 * @return number of seat left
	 */
	public int getYellowLeft() {
		try {
			Statement statement = conn.createStatement();
			ResultSet results = statement.executeQuery("Select COUNT(*) FROM " + search
					+ " AS count WHERE region = 'yellow' AND " + COLUMN_BIG_ROOM_OCCUPIED + "=0");
			return results.getInt(1);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return 0;
		}
	}

	/**
	 * Get the red area's seat left
	 * 
	 * @return number of seat left
	 */
	public int getRedLeft() {
		try {
			Statement statement = conn.createStatement();
			ResultSet results = statement.executeQuery("Select COUNT(*) FROM " + search
					+ " AS count WHERE region = 'red' AND " + COLUMN_BIG_ROOM_OCCUPIED + "=0");
			return results.getInt(1);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return 0;
		}
	}

	/**
	 * Get the blue area's seat left
	 * 
	 * @return number of seat left
	 */
	public int getBlueLeft() {
		try {
			Statement statement = conn.createStatement();
			ResultSet results = statement.executeQuery("Select COUNT(*) FROM " + search
					+ " AS count WHERE region = 'blue' AND " + COLUMN_BIG_ROOM_OCCUPIED + "=0");
			return results.getInt(1);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return 0;
		}
	}

	/**
	 * Get the total area's seat left
	 * 
	 * @return number of total seat left
	 */
	public int getSeatLeft() {
		try {
			Statement statement = conn.createStatement();
			ResultSet results = statement.executeQuery(
					"Select COUNT(*) FROM " + search + " AS count WHERE " + COLUMN_BIG_ROOM_OCCUPIED + "=0");
			return results.getInt(1);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return 0;
		}
	}

	/**
	 * Get all the user's name from database.
	 * 
	 * @return All user name's list
	 */
	public List<String> getAllUserName() {

		try {
			Statement statement = conn.createStatement();
			String select = "SELECT name FROM user";
			ResultSet results = statement.executeQuery(select);
			List<String> Username = new ArrayList<>();
			while (results.next()) {

				Username.add(results.getString(1));
			}

			return Username;

		} catch (SQLException e) {
			System.out.println("Query failed: " + e.getMessage());
			return null;
		}
	}

	/**
	 * Save the ticket that was sold in ticket_sold table
	 * 
	 * @param ticketSave
	 *            the ticket that was sold
	 */
	private void saveTicket(Ticket ticketSave) {
		try {
			Statement stmt = conn.createStatement();
			stmt.execute("CREATE TABLE IF NOT EXISTS ticket_sold(id TEXT,time TEXT,hall TEXT)");
			stmt.execute("INSERT INTO ticket_sold(id,time,hall)VALUES('" + ticketSave.getTicketId() + "'" + "," + "'"
					+ ticketSave.getTime() + "'" + "," + "'" + ticketSave.getHall() + "'" + ")");

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Remove all the ticket_sold(helping method just in case)
	 */
	public void reset() {
		try {
			Statement stmt = conn.createStatement();
			ResultSet results = stmt.executeQuery("SELECT time, hall FROM movie_info");
			while (results.next()) {
				stmt.execute("UPDATE " + search + " SET occupied = 0 WHERE occupied = 1");
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Judge if the user is qualify to buy the ticket
	 * 
	 * @param userName
	 *            user's name
	 * @param movieName
	 *            movie's name
	 * @return null if is qualify,else return movie's classification
	 */
	public String qualify(String userName, String movieName) {
		int age = 0;
		String classification = null;
		try {
			Statement stmt = conn.createStatement();
			ResultSet results = stmt.executeQuery("SELECT age FROM user WHERE name=" + "'" + userName + "'");
			age = results.getInt(1);
			results = stmt.executeQuery(
					"SELECT classification FROM movie_info WHERE movie like" + "'%" + movieName + "%'" + " LIMIT 1");
			classification = results.getString(1);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		if (age < 12 && (classification.equals("輔導") || classification.equals("保護") || classification.equals("限制")))
			return classification;
		else if (age < 15 && (classification.equals("輔導") || classification.equals("限制")))
			return classification;
		else if (age < 18 && classification.equals("限制"))
			return classification;

		return null;
	}

	/**
	 * Get all the distinct movie name from database(for movie combobox)
	 * 
	 * @return List of distinct movie name
	 */
	public List<String> getMovieName() {
		try {
			Statement statement = conn.createStatement();
			String select = "SELECT DISTINCT movie FROM movie_info";
			ResultSet results = statement.executeQuery(select);
			List<String> movieName = new ArrayList<>();
			while (results.next()) {

				movieName.add(results.getString(1));
			}

			return movieName;

		} catch (SQLException e) {
			System.out.println("Query failed: " + e.getMessage());
			return null;
		}
	}

	/**
	 * Get the user's age by name
	 * 
	 * @param userName
	 *            the user's name
	 * @return the user's age
	 */
	public int getAge(String userName) {
		try {
			Statement stmt = conn.createStatement();
			ResultSet results = stmt.executeQuery("SELECT age FROM user where name = " + "'" + userName + "'");
			return results.getInt(1);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return 0;
	}
	// public static void main(String[] args) throws ClassNotFoundException {
	// Datasource.getInstance().open();
	// }

}