package com.cts.ragul;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.GenericServlet;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class StudentRegisterGenericSrv extends GenericServlet{

	private Connection conn;
	private PreparedStatement ps;
	
	
	public void destroy() {

		if(conn!=null) {
			try {
				conn.close();
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public void init() throws ServletException {
		
		String dname=getInitParameter("DriverName");
		String url=getInitParameter("url");
		String uname=getInitParameter("UserName");
		String pass=getInitParameter("Password");
		
		
		try {
			Class.forName(dname);
		} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		
		Connection conn;
		try {
			conn = DriverManager.getConnection(url,uname,pass);
			ps=conn.prepareStatement("insert into studentdetails values(?,?,?)");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		
		
	}
	
	
	@Override
	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {

		String sroll=request.getParameter("roll");
		String name=request.getParameter("name");
		String sphone=request.getParameter("phone");
		
		int roll=Integer.parseInt(sroll.trim());
		long phone=new Integer(sphone.trim());
		
		ServletContext cxt=getServletContext();
		String admin_name=cxt.getInitParameter("AdminName");
		
		int x=0;
		
		try {
			
			ps.setInt(1, roll);
			ps.setString(2, name);
			ps.setLong(3, phone);
			
			 x=ps.executeUpdate();

		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		

		response.setContentType("text/html");
		PrintWriter pw=response.getWriter();
		
		pw.print("<body bgcolor='green'>");
		
		if(x>0) {
			pw.print("<h1 align='center'>Admin name "+admin_name+"</h1>");
			pw.print("<h1 align='center'>Record inserted successfully !!!</h1>");
			
		}
		else {
			pw.print("<h1 align='center'>Record not inserted :( </h1>");
		}
		
		
		
		pw.print("</body>");
		
	}

	
	

	
}
