package dbadapter;

import java.sql.Timestamp;


public class rating {

	int id;
	String username;
	Timestamp posting_date;
	int rate;
	String comment;
	int movie_id;

	public rating( int id, String username , Timestamp posting_date, int rate, String comment, int movie_id) {
		this.id = id;
		this.posting_date = posting_date;
		this.rate = rate ;
		this.comment = comment;
		this.movie_id = movie_id;
		this.username = username;
	}
	public rating( String username , int rate, String comment, int movie_id) {
		this.rate = rate;
		this.comment = comment;
		this.movie_id = movie_id;
		this.username = username;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public Timestamp getPosting_date() {
		return posting_date;
	}


	public void setPosting_date(Timestamp posting_date) {
		this.posting_date = posting_date;
	}


	public int getRate() {
		return rate;
	}


	public void setRate(int rate) {
		this.rate = rate;
	}


	public String getComment() {
		return comment;
	}


	public void setComment(String comment) {
		this.comment = comment;
	}


	public int getMovie_id() {
		return movie_id;
	}


	public void setMovie_id(int movie_id) {
		this.movie_id = movie_id;
	}

}
