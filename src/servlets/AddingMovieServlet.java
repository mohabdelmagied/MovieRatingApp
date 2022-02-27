package servlets;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import application.MR_Application;


public class AddingMovieServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		
		// Set navtype
		request.setAttribute("navtype", "logedin");
		//By default redirects to the AddingMovieWebpage template(for movie registration in the database
		 try {
			request.setAttribute("pagetitle","add movie");
			request.getRequestDispatcher("/templates/AddingMovieWebpage.ftl").forward(request,response);
	} catch (ServletException | IOException e) {
		e.printStackTrace();
	}
		 }
	
	
	 //doPost manages handling of submitted forms.
	 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		  
		//sets navtype
		request.setAttribute("navtype", "logedin");
		 
		  String action = (request.getParameter("action") == null) ? "" : request.getParameter("action");
		  
		  if(action.equals("AddingMovieWebpage")) {
			  //In case Movie registration procees is successful it redirects to AddingConfirmation template
				 if(MR_Application.getInstance().registeringMovie(request.getParameter("title"), request.getParameter("publishing_date") ,request.getParameter("director"),request.getParameter("mainActors"))) {
					 request.setAttribute("pagetitle","Movie registered");
					 request.setAttribute("message","You have successfully added this movie");
					 try {
						 request.getRequestDispatcher("/templates/AddingConfirmation.ftl").forward(request,response);
						 return;
					 } catch (ServletException | IOException e) {
							e.printStackTrace();
						}
				 }
					 else {
						 //In case movie registration process failed
						 request.setAttribute("pagetitle","AddFail");
						 request.setAttribute("message","we have failed to add this movie");
						 try {
							 request.getRequestDispatcher("/templates/AddingFail.ftl").forward(request,response);
							 return;
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


