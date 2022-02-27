package servlets;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import application.MR_Application;

public class UserGUI extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		
		// Set navtype
		//by default redirects to the MovieList template
		request.setAttribute("navtype", "logedin");
		if(MR_Application.getInstance().getMovieList()!=null) {
			request.setAttribute("Movies",MR_Application.getInstance().getMovieList());
			}
		else {
			request.setAttribute("message","No Movies in Database");
			}
		 try {
			request.setAttribute("pagetitle","MovieList");
			
			request.getRequestDispatcher("/templates/MovieList.ftl").forward(request,response);
			return;
	} catch (ServletException | IOException e) {
		e.printStackTrace();
	}
		 }
	
	
	 //doPost manages handling of submitted forms.
	 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		  
		//sets navtype
		request.setAttribute("navtype", "logedin");
		 //redirects to the Movie rating template(gets the corresponding Movie's id
		  String action = (request.getParameter("action") == null) ? "" : request.getParameter("action");
		  String movie_id = request.getParameter("movie_id");
		  if(action.equals("Rating")){			 
			 	request.setAttribute("pagetitle","Rate Movie");
			 try {
				 request.setAttribute("movie_id", movie_id);
				 request.getRequestDispatcher("/templates/Rating.ftl").forward(request,response);
			 } catch (ServletException | IOException e) {
					e.printStackTrace();
				}
		  }
		 else if(action.equals("showRatingMovie")) {
			 //In case of successful rating process
			 System.out.print("if statment");
			 System.out.print(request.getParameter("movie_id"));
			 if(MR_Application.getInstance().sendRatingInput(request.getParameter("username"),request.getParameter("rate"),request.getParameter("comment"),movie_id)) {
				 request.setAttribute("pagetitle","Rating Successful");
				 request.setAttribute("message","you have rated this movie successfully");
				 try {
					 request.getRequestDispatcher("/templates/sendOk.ftl").forward(request,response);
				 } catch (ServletException | IOException e) {
						e.printStackTrace();
					}
			 }
			 else {
				 //In case of a failed rating process
				 request.setAttribute("pagetitle","Rating Fail");
				 request.setAttribute("message","Rating this movie has failed");
				 try {
					 request.getRequestDispatcher("/templates/sendFail.ftl").forward(request,response);
				 } catch (ServletException | IOException e) {
						e.printStackTrace();
					}
			 }
		 }
		 else {
			 doGet(request,response);
		 }
	 }
	}


