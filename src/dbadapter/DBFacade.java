package dbadapter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import interfaces.IMovieDatabase;
import interfaces.IUserDatabase;


public class DBFacade implements IMovieDatabase, IUserDatabase {
	private static DBFacade instance;

	/**
	 * Constructor which loads the corresponding driver for the chosen database type
	 */
	private DBFacade() {
		try {
			Class.forName("com." + Configuration.getType() + ".jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Implementation of the Singleton pattern.
	 * 
	 * @return
	 */
	public static DBFacade getInstance() {
		if (instance == null) {
			instance = new DBFacade();
		}

		return instance;
	}

	public static void setInstance(DBFacade dbfacade) {
		instance = dbfacade;
	}
	

	@Override
	public boolean forwardRegisterMovie(String title, Timestamp publishing_date, String director, 
			String mainActors) {
		String sqlInsert = "INSERT INTO MovieDatabase (title, publishingDate, director, mainActors) VALUES (?,?,?,?)";

		// Insert offer into database.
		try (Connection connection = DriverManager
				.getConnection(
						"jdbc:" + Configuration.getType() + "://" + Configuration.getServer() + ":"
								+ Configuration.getPort() + "/" + Configuration.getDatabase(),
						Configuration.getUser(), Configuration.getPassword())) {

			try (PreparedStatement ps = connection.prepareStatement(sqlInsert)) {
				ps.setString(1, title);
				ps.setTimestamp(2, publishing_date);
				ps.setString(3, director);
				ps.setString(4, mainActors);
				ps.executeUpdate();
				return true; 
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	@Override
	public boolean addRating( String username, int rate, String comment, int movie_id) {
		MovieDatabase m = null;

		// Declare necessary SQL queries.
		String sqlSelectmd = "SELECT * FROM MovieDatabase WHERE Movie_id = ?";
		String sqlInsertrating = "INSERT INTO rating (username ,postingdate, rate, comment, m_id) VALUES (?,?,?,?,?)";

		// Get selected offer
		try (Connection connection = DriverManager
				.getConnection(
						"jdbc:" + Configuration.getType() + "://" + Configuration.getServer() + ":"
								+ Configuration.getPort() + "/" + Configuration.getDatabase(),
						Configuration.getUser(), Configuration.getPassword())) {
			try (PreparedStatement psSelect = connection.prepareStatement(sqlSelectmd);
					PreparedStatement psInsert = connection.prepareStatement(sqlInsertrating,
							PreparedStatement.RETURN_GENERATED_KEYS)) {
				psSelect.setInt(1, movie_id);
				try (ResultSet rs = psSelect.executeQuery()) {
					if (rs.next()) {
						m = new MovieDatabase(rs.getInt(1), rs.getString(2), rs.getTimestamp(3),
								rs.getString(4), rs.getString(5), rs.getFloat(6));
					}
				}
				
					if (!is_rated(username, movie_id)) {
						Timestamp creationDate = new Timestamp(new Date().getTime());
						psInsert.setString(1, username);
						psInsert.setTimestamp(2, creationDate);
						psInsert.setFloat(3, rate);
						psInsert.setString(4, comment);
						psInsert.setInt(5, movie_id);
						psInsert.executeUpdate();
						try (ResultSet generatedKeys = psInsert.getGeneratedKeys()) {
							if (generatedKeys.next()) {
								float new_avg_rating = m.calc_avg_rating(m.getAvg_rating(), rate);
								update_avg_rating(new_avg_rating, movie_id);
								return true;
							}
							
						}
					} else
						m = null;
					return false;
				}
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
	}


	/**
	 * Checks if the entered Movie exists.
	 */
	public boolean checkMovie(String title, String director, 
			String mainActors) {

		// Declare necessary SQL query.
		String queryMD = "SELECT * FROM MovieDatabase WHERE\n"
				+ "(mainActors= ? AND title= ? AND director= ?)";

		// query data.
		try (Connection connection = DriverManager
				.getConnection(
						"jdbc:" + Configuration.getType() + "://" + Configuration.getServer() + ":"
								+ Configuration.getPort() + "/" + Configuration.getDatabase(),
						Configuration.getUser(), Configuration.getPassword())) {
			try (PreparedStatement psSelect = connection.prepareStatement(queryMD)) {
				psSelect.setString(1, mainActors);
				psSelect.setString(2, title);
				psSelect.setString(3, director);
				try (ResultSet rs = psSelect.executeQuery()) {
					return rs.next();
				}
			} 
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Function used to calculate the the avg rating after adding new rating  for a booking.
	 */

	@Override
	public ArrayList<MovieDatabase> requestMovieList() {
		ArrayList<MovieDatabase> result = new ArrayList<MovieDatabase>();
		String queryMD = "SELECT * FROM MovieDatabase ORDER BY avg_rating DESC";
		String qureyMDR = "SELECT * FROM rating WHERE m_id = ?";
		
		// Query all offers that fits to the given criteria.
		try (Connection connection = DriverManager
				.getConnection(
						"jdbc:" + Configuration.getType() + "://" + Configuration.getServer() + ":"
								+ Configuration.getPort() + "/" + Configuration.getDatabase(),
						Configuration.getUser(), Configuration.getPassword())) {

			try (PreparedStatement ps = connection.prepareStatement(queryMD)) {
				try (ResultSet rs = ps.executeQuery()) {
					while (rs.next()) {
						MovieDatabase temp = new MovieDatabase(rs.getInt(1), rs.getString(2), rs.getTimestamp(3),
								rs.getString(4), rs.getString(5), rs.getFloat(6));
						//result.add(temp);

						ArrayList<rating> Rating = new ArrayList<rating>();
						try (PreparedStatement psSelect = connection.prepareStatement(qureyMDR)) {
							psSelect.setInt(1, rs.getInt(1));
							try (ResultSet res = psSelect.executeQuery()) {
								while (res.next()) {
									rating temp2 = new rating(res.getInt(1), res.getString(2), res.getTimestamp(3),
											res.getInt(5), res.getString(4), res.getInt(6)); 
									if (res.getInt(6) == rs.getInt(1))
										Rating.add(temp2);
								}
								temp.setRating(Rating);
							}
						}
						result.add(temp);
					}
				} catch (Exception e) {
					e.printStackTrace();
					}
			} catch (Exception e) {
				e.printStackTrace();
						}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public boolean is_rated(String username, int movie_id) {
		// Declare necessary SQL query.
		String querymd = "SELECT * FROM rating WHERE username= ? AND m_id= ?";

		// query data.
		try (Connection connection = DriverManager
				.getConnection(
						"jdbc:" + Configuration.getType() + "://" + Configuration.getServer() + ":"
								+ Configuration.getPort() + "/" + Configuration.getDatabase(),
						Configuration.getUser(), Configuration.getPassword())) {
			try (PreparedStatement psSelect = connection.prepareStatement(querymd)) {
				psSelect.setString(1, username);
				psSelect.setInt(2, movie_id);
				try (ResultSet rs = psSelect.executeQuery()) {
					return rs.next();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private boolean update_avg_rating(float avg_rating, int m_id) {
		
		String sqlUpdate = "UPDATE MovieDatabase\n"
				+ "SET avg_rating= ? \n"
				+ "WHERE Movie_id = ? ";

		
		try (Connection connection = DriverManager
				.getConnection(
						"jdbc:" + Configuration.getType() + "://" + Configuration.getServer() + ":"
								+ Configuration.getPort() + "/" + Configuration.getDatabase(),
						Configuration.getUser(), Configuration.getPassword())) {

			try (PreparedStatement ps = connection.prepareStatement(sqlUpdate)) {
				ps.setFloat(1, avg_rating);
				ps.setInt(2, m_id);
				ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean get_users(String username) {
		String qureyMDR = "SELECT * FROM userdatabase WHERE username = ?";
		
		try (Connection connection = DriverManager
				.getConnection(
						"jdbc:" + Configuration.getType() + "://" + Configuration.getServer() + ":"
								+ Configuration.getPort() + "/" + Configuration.getDatabase(),
						Configuration.getUser(), Configuration.getPassword())) {
			try (PreparedStatement psSelect = connection.prepareStatement(qureyMDR)) {
				psSelect.setString(1, username);
				try (ResultSet rs = psSelect.executeQuery()) {
					return rs.next();
				}
			} 
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean registerUser(String username, int age, String email) {
		String sqlInsert = "INSERT INTO userDatabase ( username,  age,  email) VALUES (?,?,?)";
		// Insert offer into database.
		try (Connection connection = DriverManager
				.getConnection(
						"jdbc:" + Configuration.getType() + "://" + Configuration.getServer() + ":"
								+ Configuration.getPort() + "/" + Configuration.getDatabase(),
						Configuration.getUser(), Configuration.getPassword())) {

			try (PreparedStatement ps = connection.prepareStatement(sqlInsert)) {
				ps.setString(1, username);
				ps.setInt(2, age);
				ps.setString(3, email);
				ps.executeUpdate();
				return true; 
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

}


