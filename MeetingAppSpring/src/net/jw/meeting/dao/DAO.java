package net.jw.meeting.dao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class DAO<T,Q> implements IDAO<T, Q>{
	
	
	public String getCurrentDate()
	{
		String dateTime = "";
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		dateTime = dateFormat.format(date); 
		
		return dateTime;
	}
}
