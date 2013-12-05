
//Baptiste 
//Matthew 
//Thomas

package co.uk.p00488;

import java.util.ArrayList;

public class ReadPhoneBook
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		String inFileName = args[0];
        Scanner scanner = new Scanner(inFileName);
        Parser parser = new Parser(scanner);
        
        ArrayList<PhoneEntry> pes = parser.Parse();
        System.out.println(parser.errors.count + " errors detected");
    	System.out.println("------------");
        
        for(PhoneEntry pe : pes)
        {
        	System.out.println(pe.getFullName());
        	for(PhoneInfo pi : pe.getPhones())
        	{
        		System.out.println("(" + pi.getType() + ")" + " " + pi.getFullNumber());
        	}
        	System.out.println("------------");
        }
	}

}
