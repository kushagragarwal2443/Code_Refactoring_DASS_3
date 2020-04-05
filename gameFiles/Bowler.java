/*
 * Bowler.java
 *
 * Version:
 *     $Id$
 *
 * Revisions:
 *     $Log: Bowler.java,v $
 *     Revision 1.3  2003/01/15 02:57:40  ???
 *     Added accessors and and equals() method
 *
 *     Revision 1.2  2003/01/12 22:23:32  ???
 *     *** empty log message ***
 *
 *     Revision 1.1  2003/01/12 19:09:12  ???
 *     Adding Party, Lane, Bowler, and Alley.
 *
 */

/**
 *  Class that holds all bowler info
 *
 */

public class Bowler {

    private final String fullName;
    private final String nickName;
    private final String email;

    public Bowler( String nick, String full, String mail ) {
	nickName = nick;
	fullName = full;
  	email = mail;
    }


    public String getNickName() {

        return nickName;  

    }

	public String getFullName ( ) {
			return fullName;
	}


	public String getEmail ( ) {
		return email;	
	}
	
	public boolean bowlerEquals ( Bowler b) {
		boolean return_value = true;
		if ( !(nickName.equals(b.getNickName())) ) {
				return_value = false;
		}
		if ( !(fullName.equals(b.getFullName())) ) {
			    return_value = false;
		}	
		if ( !(email.equals(b.getEmail())) ) {
			    return_value = false;
		}
		return return_value;
	}
}