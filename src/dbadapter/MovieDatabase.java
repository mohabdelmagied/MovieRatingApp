package dbadapter;
import java.util.ArrayList;
import java.sql.Timestamp;

/**
 * Contains address informations about a moviedatabase.
 * 
 * @author swe.uni-due.de
 *
 */
public class MovieDatabase {

	private int movie_id;
	private String title;
	private Timestamp publishing_date;
	private String director;
	private String mainActors;
	private float avg_rating ;
	private ArrayList<rating> ratings;
	

	public MovieDatabase(int movie_id, String title, Timestamp publishing_date, String director, 
			String mainActors, float avg_rating) {
		this.movie_id = movie_id;
		this.title = title;
		this.director = director;
		this.mainActors = mainActors;
		this.avg_rating = avg_rating;
		this.publishing_date = publishing_date;
		this.ratings = new ArrayList<rating>();
		
	}
	public MovieDatabase(String title, Timestamp publishing_date, String director, 
			String mainActors, float avg_rating) {
		this.title = title;
		this.director = director;
		this.mainActors = mainActors;
		this.avg_rating = avg_rating;
		this.publishing_date = publishing_date;
		this.ratings = new ArrayList<rating>();
		
	}
	
	public MovieDatabase() {
	}
	
	public ArrayList<rating> getRatings() {
		return ratings;
	}

	public void setRating(ArrayList<rating> ratings) {
		this.ratings = ratings;
	}

	public int getMovie_id() {
		return movie_id;
	}

	public Timestamp getPublishing_date() {
		return publishing_date;
	}
	
	public String getDirector() {
		return director;
	}
	
	public String getMainActors() {
		return mainActors;
	}
	
	public float getAvg_rating() {
		return avg_rating;
	}
	
	public String getTitle() {
		return title;
	}
	
	public float calc_avg_rating(float avg_rating, int rate) {
		float new_avg_rating = (avg_rating + rate) / 2;
		return new_avg_rating;
	}
}
