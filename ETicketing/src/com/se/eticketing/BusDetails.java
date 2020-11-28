package com.se.eticketing;

import java.util.*;

public class BusDetails {
	String busname;
	String bustype;
	int busid;
	int businfoid;
	String time;
	String date;
	List<Integer> seatno = new ArrayList<Integer>();
	
	public String toString() {
        return busname + "  " + bustype + "  " + busid  + "  " + time + " " + date +"  "+ seatno;
    }

}
