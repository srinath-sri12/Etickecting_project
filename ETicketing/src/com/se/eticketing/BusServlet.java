package com.se.eticketing;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * Servlet implementation class BusServlet
 */
@WebServlet("/BusServlet")
public class BusServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BusServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String info=request.getParameter("bus_info_id");  
		int busid =Integer.parseInt(info);
		int busno=0;
		int businfo=0;
		BusDetails p=new BusDetails();

		try{  
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con=DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/e_ticketing","root","root");  
			PreparedStatement stmt = con.prepareStatement("select * from bus_info where bus_info_id= ?");
			stmt.setInt(1,busid);

			ResultSet rs=stmt.executeQuery();
		
			while(rs.next())
			{
				
				//				p.name=rs.getString(1);
				p.date=rs.getString(5);
				p.time=rs.getString(6);
				p.busid=rs.getInt(1);
				p.businfoid=rs.getInt(2);
				busno=p.busid;
				businfo = p.businfoid;
				
				
			}
			
			PreparedStatement stmt1 = con.prepareStatement("select * from bus_details where bus_id= ?");
			stmt1.setInt(1, busno);
			ResultSet rs1=stmt1.executeQuery();
			while(rs1.next())
			{
				p.busname=rs1.getString(2);
				p.bustype=rs1.getString(3);
				
			}
			
			
			PreparedStatement stmt2 = con.prepareStatement("select seat_no from booking_details where bus_info_id= ?");
			stmt2.setInt(1, businfo);
			ResultSet rs2=stmt2.executeQuery();
			while(rs2.next())
			{
				int seat=rs2.getInt(1);
				p.seatno.add(seat);
			}
			
			con.close();
			System.out.println(p);




		}catch(Exception e){
			e.printStackTrace();
			System.out.println("Exception"+e);
		}  

		Gson gson = new Gson();
		String jsonString = gson.toJson(p);
		PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(jsonString);
        out.flush();
		
		
	}



	//		response.getWriter().append("Served at: ").append(request.getContextPath());


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub


		doGet(request, response);
	}

}
