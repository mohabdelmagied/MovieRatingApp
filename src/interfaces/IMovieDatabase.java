package interfaces;

import java.sql.Timestamp;
import java.util.ArrayList;

import dbadapter.MovieDatabase;

/**
 * Interface for DBFacade to provide all necessary database function.
 * 
 * @author swe.uni-due.de
 *
 */
public interface IMovieDatabase {

	public boolean addRating(String username, int rate, String comment, int movie_id);

	
	public boolean forwardRegisterMovie(String title, Timestamp publishing_date, String director, String mainActors);


	public ArrayList<MovieDatabase> requestMovieList();


}
