package application;
import java.lang.String;
import java.util.ArrayList;
import java.util.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import interfaces.*;
import dbadapter.*;

public class MR_Application implements Usercmds {
	
	/**
	 * Implementation of the Singleton pattern.
	 * 
	 * @return
	 */
	
	private static MR_Application instance;
	
	public static MR_Application getInstance(){
		if(instance==null)
		{
			instance=new MR_Application();
		}
		return instance;
	}
	 
	 public boolean giveUserinfo(String username,String age,String email) {
			/*
			 * Function is called in the servlets component(RegisterGui) Forwards entered
			 * user info to dbadapter component after validation of username(has to be
			 * unique) and age>=18 age has to be parsed as an integer returns boolean value
			 * to indicate the success(True) or failure(False) of the operation
			 */
		 boolean result = false;
		 boolean queryRes = DBFacade.getInstance().get_users(username);
		 int Age = Integer.parseInt(age);
		 if (queryRes == true || Age < 18 ) {
			 return result;
		 }
		 else {
			 result=DBFacade.getInstance().registerUser(username,Age,email);
			 result = true;
		 }
		return result;
	 }
	 
	 public boolean sendRatingInput(String username,String rate,String comment,String movie_id) {
			/*
			 * function is called in the servlets component(UsserGui) forwards a movie
			 * rating input to the dbadapter component after alidating the rate
			 * variable(rate between 1 and 10) rate has to be parsed as an integer returns
			 * boolean value to indicate the success(True) or failure(False) of the
			 * operation
			 */
		 int Rate = Integer.parseInt(rate);
		 System.out.println(movie_id);
		 int Movie_id = Integer.parseInt(movie_id);
		 boolean result = true;
		 System.out.print(username + rate + comment + movie_id);
		 if (Rate < 0 || Rate > 10)
			 return false;
		  try {
			  result = DBFacade.getInstance().addRating(username,Rate,comment,Movie_id);
			  System.out.print(result);
		  }
		  catch (Exception e) {
				e.printStackTrace();
			}
			return result;
	 }
	 
	 public boolean registeringMovie(String title,String publishingDate,String director,String mainActors) {
			/*
			 * function is called in the servlets component(AddingMovieServlet)
			 * publishingDate is parsed as dateFormat and then casted into a timestamp
			 * forwards movie registration input to dbadapter component by calling
			 * forwardRegisterMovie returns boolean value to indicate the success(True) or
			 * failure(False) of the operation
			 */
		 boolean result = false;
		 if(DBFacade.getInstance().checkMovie(title, director, mainActors)) {
			 return false;
		 }
		 try {
			 DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date date = dateFormat.parse(publishingDate);
				long time = date.getTime();
				Timestamp publishing_Date = new Timestamp(time);
			 result=DBFacade.getInstance().forwardRegisterMovie(title,publishing_Date,director,mainActors);
		 }
		 catch (Exception e) {
				e.printStackTrace();
			}

			return result;
	 }
	 
	 public ArrayList<MovieDatabase> getMovieList(){
			/*
			 * function is called in servlets component(UserGui) 
			 * forwards call to requestMovieList in dbadapter 
			 * returns full movie list from the database
			 */		 ArrayList<MovieDatabase> result=new ArrayList<MovieDatabase>();
		 try {
			  result=DBFacade.getInstance().requestMovieList();
		 }
		 catch (Exception e) {
				e.printStackTrace();
			}
			return result;
	 }
	

}
