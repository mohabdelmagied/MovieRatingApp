package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import application.MR_Application;

public class RegisterGUI extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request,HttpServletResponse response) {
		
		// set pagetitle und navtype
		request.setAttribute("navtype", "register");
		request.setAttribute("pagetitle","Registration");
		//By default redirects to the RegistrationWebpage template
		try {
			request.getRequestDispatcher("/templates/RegistrationWebpage.ftl").forward(request,response);
		}  catch (ServletException | IOException e) {
			request.setAttribute("errormessage",
					"Template error: please contact the administrator");
			e.printStackTrace();
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		// sets navtype to register
		request.setAttribute("navtype", "register");
		//Confirms registration in case of succes of theregistration process
		if(MR_Application.getInstance().giveUserinfo(request.getParameter("username"),request.getParameter("age"),request.getParameter("email"))==true) {
			request.setAttribute("pagetitle","RegistrationConfirmation");
			try {
				request.setAttribute("message","Thanks");
				request.getRequestDispatcher("/templates/RegistrationConfirmation.ftl").forward(request , response);
			} catch (Exception e) {
				request.setAttribute("errormessage",
						"Template error: please contact the administrator");
				e.printStackTrace();
			}
		}
		else {
			//In case registration process failed
			request.setAttribute("pagetitle","RegistratioFail");
			try {
				request.setAttribute("message","Please Try Again, The Data is wrong");
				request.getRequestDispatcher("/templates/RegistrationFail.ftl").forward(request , response);
			} catch (Exception e) {
				request.setAttribute("errormessage",
						"Template error: please contact the administrator");
				e.printStackTrace();
			}
		}
	}

}
