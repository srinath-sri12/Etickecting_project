package com.se.eticketing;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * Servlet implementation class HomeServlet
 */
@WebServlet("/buslist")
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HomeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		
		
		response.setContentType("text/html");  
//		PrintWriter out = response.getWriter();  
		          
		String src=request.getParameter("src");  
		int source=Integer.parseInt(src);
		String desc=request.getParameter("desc");
		int destination=Integer.parseInt(desc);
		String dt=request.getParameter("dat");  
		System.out.println(source+" "+destination+" "+dt);
		
		List <BusResponse> res = new ArrayList<>();
		
		try{  
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con=DriverManager.getConnection(  
			"jdbc:mysql://localhost:3306/e_ticketing","root","root");  
//			Statement stmt=con.createStatement();  

			PreparedStatement stmt=con.prepareStatement("select bus_info.bus_id,bus_info.avail,bus_info.time,bus_details.bus_name,bus_details.bus_type FROM bus_info INNER JOIN bus_details on bus_info.bus_id = bus_details.bus_id where bus_info.src= ? and bus_info.desc = ? and bus_info.date=?;");  
//			preparedstatm rs=stmt.executeQuery("select bus_info.bus_id,bus_info.avail,bus_info.time,bus_details.b_name,bus_details.b_type from bus_info INNERJOIN bus_details on bus_info.bus_id = bus-details.bus_id where src= ? and desc = ? and date = ? ");
			stmt.setInt(1,source);
			stmt.setInt(2,destination);
			stmt.setString(3,dt);
			ResultSet rs=stmt.executeQuery();
			
			while(rs.next()){  
			System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3)+"  "+rs.getString(4)+"  "+rs.getString(5));
			BusResponse b=new BusResponse();
			b.busId=rs.getInt(1);
			b.avail=rs.getInt(2);
			b.timing=rs.getString(3);
			b.busName=rs.getString(4);
			b.type=rs.getString(5);
			res.add(b);
			System.out.println(b);
			}
			
			con.close();  
			}catch(Exception e){ System.out.println(e);}  
			

		 Gson gson = new Gson();
		 String jsonString = gson.toJson(res);
		 PrintWriter out = response.getWriter();
	     response.setContentType("application/json");
	     response.setCharacterEncoding("UTF-8");
	     out.print(jsonString);
	     out.flush(); 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
