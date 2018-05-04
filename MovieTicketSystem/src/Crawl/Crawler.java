package Crawl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Crawler {
	List<Movie> movieData;
	private Connection conn;
	public static final String DB_NAME = "movie_2.db";
	public static final String CONNECTION_STRING = "jdbc:sqlite:" + DB_NAME;
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
	public void CrawlData() throws ClassNotFoundException, SQLException {
			
		try {
			System.out.println("connecting...");
			Document doc = Jsoup.connect("http://www.atmovies.com.tw/movie/now/").get();
			Elements masthead = doc.select("ul.filmListAll2 li > a");
			Element link = masthead.first();
			List<String> movieUrl = new ArrayList<>();
			for (Element e : masthead) {
				movieUrl.add("http://www.atmovies.com.tw" + e.attr("href"));
			}
			movieUrl.remove(movieUrl.size() - 1);
			List<Movie> movieData = new ArrayList<>();
			for (String urls : movieUrl) {
				doc = Jsoup.connect(urls).get();
				Movie movies = new Movie();
				movies.setUrl(urls);
				Elements title = doc.select("div#main div.filmTitle");
				movies.setMovie(title.text());
				Elements classfication = doc.select("div#main div.filmTitle img");
				movies.setClassification(classifi(classfication.attr("src")));
				Elements descri = doc.select("div#filmTagBlock > span:nth-child(3)");
				movies.setDescri(descri.first().ownText());
				Elements time = doc.select("div#filmTagBlock li:nth-child(1)");
				movies.setInfor(time.text());
				Elements ratingUrl = doc.select("iframe");
				doc = Jsoup.connect(ratingUrl.first().attr("src")).get();
				Elements total = doc.select("tr > td:nth-child(2)");
				movies.setScore(total.text());
				movieData.add(movies);
			}
			for(Movie m:movieData){
				System.out.println(m.getMovie());
				System.out.println(m.getUrl());
				System.out.println(m.getClassification());
				System.out.println(m.getDescri());
				System.out.println(m.getInfor());
				System.out.println(m.getScore());
			}
			save(movieData);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public static void main(String[] args) throws ClassNotFoundException, SQLException{
		Crawler cral = new Crawler();
		cral.CrawlData();
	}
	private static String classifi(String img) {
		switch (img) {
		case "/images/cer_F2.gif":
		case "/images/cer_F5.gif":
			return "輔導";
		case "/images/cer_P.gif":
			return "保護";
		case "/images/cer_G.gif":
			return "普遍";
		case "/images/cer_R.gif":
			return "限制";
		}
		return null;
	}
	public void save(List<Movie> movies) throws SQLException, ClassNotFoundException{
		if(!open()){
			close();
			return;
		}
		 Statement stmt = conn.createStatement();
         stmt.execute("CREATE TABLE IF NOT EXISTS new_movie(movie TEXT,url TEXT,classification TEXT,descri TEXT,infor TEXT,score TEXT)");
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
		close();
		}
	public String getCrawedMovieName() throws ClassNotFoundException{
		if(!open()){
			close();
			return null;
		}
		try {
			Statement statement = conn.createStatement();
			String select = "SELECT DISTINCT movie FROM new_movie";
			ResultSet results = statement.executeQuery(select);
			String movieName="";
			while (results.next()) {

				movieName += (results.getString(1)+"\n");
			}

			return movieName;

		} catch (SQLException e) {
			System.out.println("Query failed: " + e.getMessage());
			return null;
		}
	}

}