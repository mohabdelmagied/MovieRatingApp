package testing;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.sql.Timestamp;

import org.junit.Test;

import application.*;
import dbadapter.DBFacade;
import junit.framework.TestCase;
import org.powermock.api.mockito.PowerMockito;

public class MR_ApplicationTest extends TestCase {
	
	public MR_ApplicationTest() {
		super();
	
	}
	/**
	 * Test method for {@link application.MR_Application#giveUserinfo(java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testGiveUserinfo() {
		//constructing test stub for DBFacade
		DBFacade stub = mock(DBFacade.class);
		DBFacade.setInstance(stub);
		
		//Testing the function return value for parameters with age>=18 and age<18
		//Test for similar username is done in DNFacade component test for registerUser where the check for username is specified
		assertTrue(MR_Application.getInstance().giveUserinfo("Moh", "21" , "moh@g.com"));
		assertFalse(MR_Application.getInstance().giveUserinfo("Jo", "16" , "Jo@g.com"));
		
		//Testing number of calls of registerUser with given parameters
		verify(stub, times(1)).registerUser("Moh", 21,"moh@g.com");
		verify(stub, times(0)).registerUser("Jo", 16 , "Jo@g.com");
	}
	
	/**
	 * Test method for {@link application.MR_Application#sendRatingInput(java.lang.String, java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testSendRatingInput() {
		//constructing test stub for DBFacade
		DBFacade stub = mock(DBFacade.class);
		DBFacade.setInstance(stub);
		
		//simulating the return value for addRating
		PowerMockito.when(stub.addRating("Moh", 5, "Thanks", 1)).thenReturn(true);
		PowerMockito.when(stub.addRating("roro", 11 , "Great", 1)).thenReturn(false);
		
		//Test for sendRatingInput with rate>0 and rate<=10 and rate>10, rate<0 has not been specified as it is in the same equivilance class as rate>10
		//Test for users who already rated the same movie is done in DBfacade component test
		assertTrue(MR_Application.getInstance().sendRatingInput("Moh", "5", "Thanks", "1"));
		assertFalse(MR_Application.getInstance().sendRatingInput("roro", "11" , "Great", "1"));
		
		//test number of calls for addRating with given parameters
		verify(stub, times(0)).addRating("roro", 11 , "Great", 1);
		verify(stub, times(1)).addRating("Moh", 5, "Thanks", 1);
	}

	/**
	 * Test method for {@link application.MR_Application#registeringMovie(java.lang.String, java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testRegisteringMovie() {
		//constructing test stub for DBFacade
		DBFacade stub = mock(DBFacade.class);
		DBFacade.setInstance(stub);
		
		//simulating return value for forwardRegisterMovie with the movie Moh2 assumed as already being in the database
		PowerMockito.when(stub.forwardRegisterMovie("Moh", Timestamp.valueOf("2022-01-15 00:00:00"), "a", "b")).thenReturn(true);
		PowerMockito.when(stub.checkMovie("Moh2", "a", "b")).thenReturn(true);
	
		//Test return value of registeringMovie where the movie does not exist in the database and where it exists in the database
		assertTrue(MR_Application.getInstance().registeringMovie("Moh", "2022-01-15 00:00:00", "a", "b"));
		assertFalse(MR_Application.getInstance().registeringMovie("Moh2", "2022-01-15 00:00:00", "a", "b"));
		
		//test number of calls for forwardRegisterMovie 
		verify(stub, times(1)).forwardRegisterMovie("Moh", Timestamp.valueOf("2022-01-15 00:00:00"), "a", "b");
		verify(stub, times(0)).forwardRegisterMovie("Moh2", Timestamp.valueOf("2022-01-15 00:00:00"), "a", "b");
	}

	/**
	 * Test method for {@link application.MR_Application#getMovieList()}.
	 */
	@Test
	public void testGetMovieList() {
		//constructing test stub for DBFacade
		DBFacade stub = mock(DBFacade.class);
		DBFacade.setInstance(stub);
		
		//Test that the return value of getMovieList is exactly the same list(same object) returned by requestMovieList
		assertEquals(MR_Application.getInstance().getMovieList(), stub.requestMovieList());
		
		//Test number of callsof requestMovieList in this function
		verify(stub, times(2)).requestMovieList();
		
	}

}