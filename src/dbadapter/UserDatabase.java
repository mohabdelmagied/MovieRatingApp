package dbadapter;

/**
 * Contains address informations about a Userdatabase.
 * 
 * @author swe.uni-due.de
 *
 */
public class UserDatabase {

	
	private String username;
	private String email;
	private int age;
	

	public UserDatabase(String username, String email, int age) {
		this.username = username;
		this.email = email;
		this.age = age;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getEmail() {
		return email;
	}	
	
	public int getAge() {
		return age;
	}
		
	public void setUsername(String username) {
		this.username = username;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}	
	public void setAge(int age) {
		this.age = age;
	}

}
