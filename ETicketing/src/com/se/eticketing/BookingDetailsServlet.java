package com.se.eticketing;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class Booking_details
 */
@WebServlet("/Bookingdetails")
public class BookingDetailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BookingDetailsServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub


		String test = request.getReader().lines().collect(Collectors.joining());
		ObjectMapper objectMapper = new ObjectMapper();

		Map<String, Object> map = objectMapper.readValue(test,Map.class);
		int n=0;

		String name = (String) map.get("name");
		String phoneno= (String) map.get("phone_no");
		List<Integer> seats=(List<Integer>) map.get("seat_no");
		int busid= (int) map.get("businfo");

		System.out.println(name);
		System.out.println(phoneno);
		System.out.println(seats);
		System.out.println(busid);
		for(int i=0;i<seats.size();i++)
		{
			try{  

				Class.forName("com.mysql.jdbc.Driver");  
				Connection con=DriverManager.getConnection(  
						"jdbc:mysql://localhost:3306/e_ticketing","root","root");  
				PreparedStatement stmt=con.prepareStatement("insert into booking_details(bus_info_id,seat_no,name,phone_no) values(?,?,?,?)");
				
				stmt.setInt(1,busid);
				stmt.setInt(2,seats.get(i));
				stmt.setString(3,name);
				stmt.setString(4,phoneno);
				stmt.executeUpdate();
				
				PreparedStatement stmt1=con.prepareStatement("select avail from bus_info where bus_info_id = ?");
				stmt1.setInt(1,busid);
				ResultSet rs=stmt1.executeQuery();
				while(rs.next())
				{
				n=rs.getInt(1);
				System.out.println(n);
				}
				n=n-1;
				
				
				PreparedStatement stmt2=con.prepareStatement("UPDATE bus_info SET avail = ? WHERE bus_info_id = ?");
				stmt2.setInt(1,n);
				stmt2.setInt(2, busid);
				stmt2.executeUpdate();
				
				con.close();
				
				
				
				
				
			}catch(Exception e){
				e.printStackTrace();
				System.out.println("Exception"+e);
			}  

		}


		doGet(request, response);
	}

}
