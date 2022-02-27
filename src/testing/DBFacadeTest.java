package testing;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dbadapter.*;

public class DBFacadeTest {
	
	private MovieDatabase testMD;
	private rating testR;
	private UserDatabase testUD;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		
		//constructing objects that defines the testing state of the database
		testMD = new MovieDatabase( 1, "Dont look up", Timestamp.valueOf("2021-01-01 01:01:00"), "jamy", 
				 "tom crouse", 2 );
		testR = new rating( 1 , "Tom" , Timestamp.valueOf("2022-01-21 00:00:00"), 6, "grate", 1);
		testUD = new UserDatabase("Tom", "tom@g.com", 22);
		ArrayList<rating> testratings = new ArrayList<rating>();
		testratings.add(testR);
		testMD.setRating(testratings);

		// SQL statements:Costructing the testing state of the database
		String sqlCleanDB = "DROP TABLE IF EXISTS rating, moviedatabase , userdatabase";
		String sqlCreateTableRating = "CREATE TABLE rating (id int(11) NOT NULL AUTO_INCREMENT,username varchar(500) not Null ,postingdate timestamp NULL DEFAULT NULL, comment longtext, rate int(11) NOT NULL, m_id double NOT NULL REFERENCES moviedatabase (movie_id), primary key (id));";
		String sqlCreateTableMovieDatabase = "CREATE TABLE moviedatabase (movie_id int(11) NOT NULL AUTO_INCREMENT, title varchar(500) NOT NULL, publishingDate timestamp NOT NULL,\r\n"
				+ "  director varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,mainActors varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL, avg_rating double(16,2) NOT NULL default 0.0, primary key (movie_id), CONSTRAINT CHK_rating CHECK ((avg_rating > -1 AND avg_rating < 11)));";
		String sqlVCreateTableUserDatabase = "CREATE TABLE userdatabase ( username varchar(500) NOT NULL unique, email varchar(500) NOT NULL UNIQUE, age int(11) NOT NULL CHECK ((age > 17)), primary key (username));";
		String sqlInsertMD = "INSERT INTO moviedatabase (movie_id, title, publishingDate, director, mainActors, avg_rating) VALUES (?,?,?,?,?,?)";
		String sqlInsertRating = "INSERT INTO rating (id, username ,postingdate, rate, comment, m_id) VALUES (?,?,?,?,?,?);";
		String sqlInsertUD = "INSERT INTO userdatabase ( username,  age,  email) VALUES (?,?,?);";
		
		// Perform database updates
		try (Connection connection = DriverManager
				.getConnection(
						"jdbc:" + Configuration.getType() + "://" + Configuration.getServer() + ":"
								+ Configuration.getPort() + "/" + Configuration.getDatabase(),
						Configuration.getUser(), Configuration.getPassword())) {

			try (PreparedStatement psClean = connection.prepareStatement(sqlCleanDB)) {
				psClean.executeUpdate();
			}
			try (PreparedStatement psCreateRating = connection.prepareStatement(sqlCreateTableRating)) {
				psCreateRating.executeUpdate();
			}
			try (PreparedStatement psCreateMovieDatabase = connection.prepareStatement(sqlCreateTableMovieDatabase)) {
				psCreateMovieDatabase.executeUpdate();
			}
			try (PreparedStatement PsCreateUserDatabase = connection.prepareStatement(sqlVCreateTableUserDatabase)) {
				PsCreateUserDatabase.executeUpdate();
			}
			try (PreparedStatement psInsertMovie = connection.prepareStatement(sqlInsertMD)) {
				psInsertMovie.setInt(1, testMD.getMovie_id());
				psInsertMovie.setString(2, testMD.getTitle());
				psInsertMovie.setTimestamp(3, testMD.getPublishing_date());
				psInsertMovie.setString(4, testMD.getDirector());
				psInsertMovie.setString(5, testMD.getMainActors());
				psInsertMovie.setFloat(6, testMD.getAvg_rating());
				psInsertMovie.executeUpdate();
			}
			try (PreparedStatement psInsertRating = connection.prepareStatement(sqlInsertRating)) {
				psInsertRating.setInt(1, testR.getId());
				psInsertRating.setString(2, testR.getUsername());
				psInsertRating.setTimestamp(3, testR.getPosting_date());
				psInsertRating.setInt(4, testR.getRate());
				psInsertRating.setString(5, testR.getComment());
				psInsertRating.setInt(6, testR.getMovie_id());
				psInsertRating.executeUpdate();
			}
			try (PreparedStatement psInsertUD = connection.prepareStatement(sqlInsertUD)) {
				psInsertUD.setString(1, testUD.getUsername());
				psInsertUD.setInt(2, testUD.getAge());
				psInsertUD.setString(3, testUD.getEmail());
				psInsertUD.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @throws java.lang.Exception
	 */
	
	@After
	public void tearDown() throws Exception {
		// SQL statements: Destructing the testing state in the database
		String sqlCleanDB = "DROP TABLE IF EXISTS moviedatabase, userdatabase,rating";

		// Perform database updates
		try (Connection connection = DriverManager
				.getConnection(
						"jdbc:" + Configuration.getType() + "://" + Configuration.getServer() + ":"
								+ Configuration.getPort() + "/" + Configuration.getDatabase(),
						Configuration.getUser(), Configuration.getPassword())) {

			try (PreparedStatement psClean = connection.prepareStatement(sqlCleanDB)) {
				psClean.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * Test method for {@link dbadapter.DBFacade#forwardRegisterMovie(java.lang.String, java.sql.Timestamp, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testForwardRegisterMovie() {
		
		MovieDatabase Movie = new MovieDatabase("mission Impossiple", Timestamp.valueOf("2015-01-01 01:01:00"), "sara", "tom crouse", 0 );
		
		//Test the return value of the function after inserting the given movie.
		//Existing movies in the database are tested in MR_Application component test where the check takes place
		assertTrue(DBFacade.getInstance().forwardRegisterMovie(Movie.getTitle(), Movie.getPublishing_date() , Movie.getDirector(), Movie.getMainActors()));
		
	}

	/**
	 * Test method for {@link dbadapter.DBFacade#addRating(java.lang.String, int, java.lang.String, int)}.
	 */
	@Test
	public void testAddRating() {
		rating rate_1 = new rating( 2 , "soso" , Timestamp.valueOf("2022-01-15 00:00:00"), 3 , "grate", 1);
		
		//Test1 is for users rating a movie they have not already rated
		//Test2 is for users rating a movie they have already rated
		assertTrue(DBFacade.getInstance().addRating(rate_1.getUsername(), rate_1.getRate(), rate_1.getComment(), rate_1.getMovie_id()));
		assertFalse(DBFacade.getInstance().addRating(testR.getUsername(), 4 , "gg", testR.getMovie_id()));
	
	}

	/**
	 * Test method for {@link dbadapter.DBFacade#checkMovie(java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testCheckMovie() {
		
		MovieDatabase Movie = new MovieDatabase("warth of a man", Timestamp.valueOf("2016-01-01 01:01:00"), "yoyo", "jason", 0 );
		
		//Testing return value of checkMovie for movies already in database and movies not in database
		assertFalse(DBFacade.getInstance().checkMovie(Movie.getTitle(), Movie.getDirector(), Movie.getMainActors()));
		assertTrue(DBFacade.getInstance().checkMovie(testMD.getTitle(), testMD.getDirector(), testMD.getMainActors()));
		
	}

	/**
	 * Test method for {@link dbadapter.DBFacade#requestMovieList()}.
	 */
	@Test
	public void testRequestMovieList() {
		ArrayList<MovieDatabase> MD =  DBFacade.getInstance().requestMovieList();
		System.out.print(MD.get(0).getAvg_rating() + "  "+ testMD.getAvg_rating());
		
		//Test if requestMovieList returns the movie in the database
		assertTrue(MD.size() == 1);
		assertEquals(MD.get(0).getMovie_id() , testMD.getMovie_id());
		assertTrue(MD.get(0).getAvg_rating() == testMD.getAvg_rating());
		assertEquals(MD.get(0).getDirector(), testMD.getDirector());
		assertEquals(MD.get(0).getMainActors(), testMD.getMainActors());
		assertEquals(MD.get(0).getPublishing_date(), testMD.getPublishing_date());
		
	}

	/**
	 * Test method for {@link dbadapter.DBFacade#is_rated(java.lang.String, int)}.
	 */
	@Test
	public void testIs_rated() {
		

		UserDatabase UD = new UserDatabase("soso", "soso@g.com", 23);
		
		//Test if user has already rated the movie and if not
		assertTrue(DBFacade.getInstance().is_rated(testUD.getUsername(),testMD.getMovie_id()));
		assertFalse(DBFacade.getInstance().is_rated(UD.getUsername(), testMD.getMovie_id()));
		 
		}

	/**
	 * Test method for {@link dbadapter.DBFacade#get_users(java.lang.String)}.
	 */
	@Test
	public void testGet_users() {
		
		UserDatabase User = new UserDatabase("soso", "soso@g.com", 23);
		
		//Test if username does not already exist and if it exists
		assertFalse(DBFacade.getInstance().get_users(User.getUsername()));
		assertTrue(DBFacade.getInstance().get_users(testUD.getUsername()));
	}

	/**
	 * Test method for {@link dbadapter.DBFacade#registerUser(java.lang.String, int, java.lang.String)}.
	 */
	@Test
	public void testRegisterUser() {
		
		UserDatabase User = new UserDatabase("DUDU", "dudu@g.com", 25);
		
		//Test registration if username already exists and if it does not
		assertTrue(DBFacade.getInstance().registerUser(User.getUsername(), User.getAge(), User.getEmail()));
		assertFalse(DBFacade.getInstance().registerUser(testUD.getUsername(), testUD.getAge(), testUD.getEmail()));
	}

}
