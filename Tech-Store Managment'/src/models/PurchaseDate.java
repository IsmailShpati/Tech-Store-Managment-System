package models;

public class PurchaseDate implements DateComparable {

	int day, month, year;

	public PurchaseDate(int day, int month, int year) {
		this.day = day;
		this.month = month;
		this.year = year;
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
		String date = "";
		date += date + "/" + month + "/" + year;
		return date;
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
