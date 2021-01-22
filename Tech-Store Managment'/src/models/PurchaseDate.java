package models;

import java.time.LocalDateTime;

public class PurchaseDate implements DateComparable {

	private int day, month, year;
	private int hour, minute;


	public PurchaseDate() {
		LocalDateTime currentTime = LocalDateTime.now();
		this.day = currentTime.getDayOfMonth();
		this.month = currentTime.getMonthValue();
		this.year = currentTime.getYear();
		this.hour = currentTime.getHour();
		this.minute = currentTime.getMinute();
	}
	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}
	
	public String toString() {
		return year + "-" + pad(month) + "-" 
				+ pad(day) + " " + pad(hour) + ":" + pad(minute);
	}
	
	private String pad(int num) {
		if(num < 10)
			return "0"+num;
		return num+"";
	}

	
	@Override
	public boolean compareDates(PurchaseDate current, PurchaseDate startP, PurchaseDate endP) {  
		if(current.getYear() >= startP.getYear() && current.getYear() <= endP.getYear()) {
			if(current.getMonth() >= startP.getMonth() && current.getMonth() <= endP.getMonth())
				if(current.getDay() >= startP.getDay() && current.getDay() <= endP.getDay())
					return true;
		}
		return false;
	}
	
}
