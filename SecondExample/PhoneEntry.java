
//Baptiste 
//Matthew 
//Thomas 

package co.uk.p00488;

import java.util.ArrayList;


public class PhoneEntry
{
	String firstName;
	String lastName;
	ArrayList<PhoneInfo> phones = new ArrayList<PhoneInfo>();
	
	
	public String getFirstName()
	{
		return firstName;
	}
	public void setFirstName(String firstName)
	{
		if(this.firstName == null || this.firstName == "")
			this.firstName = firstName;
		else
			this.firstName += " " + firstName;
	}
	public String getLastName()
	{
		return lastName;
	}
	public void setLastName(String lastName)
	{
		if(this.lastName == null || this.lastName == "")
			this.lastName = lastName;
		else
			this.lastName += " " + lastName;
	}
	public ArrayList<PhoneInfo> getPhones()
	{
		return phones;
	}
	public void setPhones(ArrayList<PhoneInfo> phones)
	{
		this.phones = phones;
	}
	
	public String getFullName()
	{
		return firstName + " " + lastName;
	}
	
}

