package interfaces;
import java.util.ArrayList;

import dbadapter.MovieDatabase;

public interface Usercmds {
	
	public boolean giveUserinfo(String username,String age,String email);
	
	public boolean registeringMovie(String title,String publishingDate,String director,String mainActors);
	
	 public boolean sendRatingInput(String username,String rate,String comment,String movie_id);
	
	public ArrayList<MovieDatabase> getMovieList();

}
