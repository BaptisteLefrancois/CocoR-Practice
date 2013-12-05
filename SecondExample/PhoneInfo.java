
//Baptiste 
//Matthew 
//Thomas

package co.uk.p00488;

public class PhoneInfo
{
	String type;
	String countryCode;
	String cityCode;
	String number;
	
	public String getNumber()
	{
		return number;
	}
	public void setNumber(String number)
	{
		if(this.number == null || this.number == "")
			this.number = number;
		else
			this.number += " " + number;
	}
	public String getType()
	{
		return type;
	}
	public void setType(String type)
	{
		this.type = type;
	}
	public String getCountryCode()
	{
		return countryCode;
	}
	public void setCountryCode(String countryCode)
	{
		this.countryCode = countryCode;
	}
	public String getCityCode()
	{
		return cityCode;
	}
	public void setCityCode(String cityCode)
	{
		this.cityCode = cityCode;
	}
	
	public String getFullNumber()
	{
		return countryCode + " " + cityCode + " " + number;
	}
	
	
}