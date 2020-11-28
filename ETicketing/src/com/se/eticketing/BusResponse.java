package com.se.eticketing;

import java.io.Serializable;

public class BusResponse implements Serializable {
	int busId;
	int avail;
	String busName;
	String timing;
	String type;
	
	
	public String toString() {
        return busId + avail + busName + timing + type ;
    }
	
	
}
