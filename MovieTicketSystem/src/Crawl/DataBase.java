package Crawl;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;


public class DataBase {
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
	private Connection conn;
	public boolean open() throws ClassNotFoundException {
		try {
			//System.out.println("success!");
			conn = DriverManager.getConnection(CONNECTION_STRING);
			return true;
		} catch (SQLException e) {
			System.out.println("Couldn't connect to database: " + e.getMessage());
			return false;
		}
	}
	public void close() {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("Couldn't close connection: " + e.getMessage());
		}
	}
	public void save(List<Movie> movies) throws SQLException{
		//Statement statement = conn.createStatement();
		for(Movie movie:movies){
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO new_movie(movie,url,classification,descri,infor,score) VALUES(?,?,?,?,?,?)");
			pstmt.setString(1, movie.getMovie());
			pstmt.setString(2, movie.getUrl());
			pstmt.setString(3, movie.getClassification());
			pstmt.setString(4, movie.getDescri());
			pstmt.setString(5, movie.getInfor());
			pstmt.setString(6, movie.getScore());
			pstmt.executeUpdate();
		}
		}
	
}
