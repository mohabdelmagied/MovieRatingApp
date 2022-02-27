package interfaces;

/**
 * Interface for DBFacade to provide all necessary database function.
 * 
 * @author swe.uni-due.de
 *
 */
public interface IUserDatabase {

	public boolean get_users(String username);

	public boolean registerUser(String username, int age, String email);


}
