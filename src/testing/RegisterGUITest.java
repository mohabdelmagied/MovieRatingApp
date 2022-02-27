package testing;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import net.sourceforge.jwebunit.junit.WebTester;


public class RegisterGUITest {
	
	private WebTester tester;

	/**
	 * Create a new WebTester object that performs the test.
	 */
	@Before
	public void prepare() {
		tester = new WebTester();
		tester.setBaseUrl("http://localhost:8080/MR/");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}
	
	//running system test for user registration

	@Test
	public void testRegisterUser() {
		// Start testing for registergui
		tester.beginAt("registergui");
		
		
		// Check all components of the Input form
		tester.assertTitleEquals("Movie Rating App - Registration");
		tester.assertFormPresent();
		tester.assertTextPresent("Required Information");
		tester.assertTextPresent("Username");
		tester.assertFormElementPresent("username");
		tester.assertTextPresent("Age");
		tester.assertFormElementPresent("age");
		tester.assertTextPresent("Email");
		tester.assertFormElementPresent("email");
		tester.assertButtonPresent("register");

		// Submit the form with given parameters(permitted parameters for registration
		tester.setTextField("username", "Mo Salah");
		tester.setTextField("age", "30");
		tester.setTextField("email", "mo@salah.de");

		tester.clickButton("register");

		// Check the representation of the success registration
		tester.assertTitleEquals("Movie Rating App - RegistrationConfirmation");
		tester.assertTextPresent("Thanks");
		
		//prohibited age parameter for registration(registration fail)
		tester.beginAt("registergui");
		
		tester.setTextField("username", "Mo");
		tester.setTextField("age", "15");
		tester.setTextField("email", "mo@s.de");
		
		tester.clickButton("register");
		
		// Check the representation of the fail registration 
		tester.assertTitleEquals("Movie Rating App - RegistratioFail");
		tester.assertTextPresent("Please Try Again, The Data is wrong");
		
			
	}

}
