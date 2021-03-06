package comEX;

import java.sql.*; 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class item {
	
	private static Statement stmt = null;
	static int id = 0;
	
	public Connection connect(){
		
		Connection con = null;
		
		try{
				
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test","root", "");

			//For Testing
			System.out.println("Successfully connected");
			
		}
		
		catch(Exception e){
			e.printStackTrace();
		}
		return con;
	}
	
	public String insertItem(String code, String name, String price, String desc) {
		
		String output = "";
		
		try {
			
			Connection con = connect();
			
			if(con == null) {
				
				return "Error while connecting to Database";
				
			}
			
			//Create Prepared Statement
			String query = "INSERT INTO item(`itemID`,`itemCode`,`itemName`,`itemPrice`,`itemDesc`) VALUES(?,?,?,?,?)" ;
			
			PreparedStatement preparedStmt  = con.prepareStatement(query);
			
			//binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, code);
			preparedStmt.setString(3, name);
			preparedStmt.setDouble(4, Double.parseDouble(price));
			preparedStmt.setString(5, desc);
			
			//execute the statement
			preparedStmt.execute();
			con.close();
			
			output = "Inserted Succesfully";
			
			
		}catch(Exception e) {
			output = "Error while inserting";
			System.err.println(e.getMessage());
		}
		
		return output;
	}
	
	public String readItems()
	{
		String output = "";
		try{
			
			Connection con = connect();
			
			if (con == null){
				return "Error while connecting to the database for reading.";
			}
			
			// Prepare the HTML table to be displayed
	
			output = "<table border='1'><tr><th>Item Code</th>"
			+"<th>Item Name</th><th>Item Price</th>"
			+ "<th>Item Description</th>"
			+ "<th>Update</th><th>Remove</th></tr>";
			
			String query = "select * from item";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			// iterate through the rows in the result set
			while (rs.next()){
		
				String itemID = Integer.toString((rs.getInt("itemID")));
				String itemCode = rs.getString("itemCode");
				String itemName = rs.getString("itemName");
				String itemPrice = Double.toString(rs.getDouble("itemPrice"));
				String itemDesc = rs.getString("itemDesc");
				
				// Add a row into the HTML table
				output += "<tr><td>" + itemCode + "</td>";
				output += "<td>" + itemName + "</td>";
				output += "<td>" + itemPrice + "</td>";output += "<td>" + itemDesc + "</td>";
				
				// buttons
				output += "<td>"
							+ "<form method='post' action='updateItem.jsp'>"
								+ "<input name='btnUpdate' type='submit' value='Update' class='btn btn-warning'>"
								+ "<input name='itemID' type='hidden' value=' " + itemID + "'>"
								+ "<input name='itemCode' type='hidden' value=' " + itemCode + "'>"
								+ "<input name='itemName' type='hidden' value=' " + itemName + "'>"
								+ "<input name='itemPrice' type='hidden' value=' " + itemPrice + "'>"
								+ "<input name='itemDesc' type='hidden' value=' " + itemDesc + "'>"
							+ "</form></td>"
						+ "<td>"
							+ "<form method='post' action='items.jsp'>"
								+ "<input name='btnRemove' type='submit' value='Delete' class='btn btn-danger'>"
								+ "<input name='itemID' type='hidden' value='" + itemID + "'>"
							+ "</form>"
						+ "</td></tr>";
				
			}
			
			con.close();
			// Complete the HTML table
			output += "</table>";
		}
		catch (Exception e){
			output = "Error while reading the items.";
			System.err.println(e.getMessage());
			
		}
		
		return output;
	}
	
	public String deleteData(String itemId) {
		
		String output = "";
		
		try {
			
			Connection con = connect();
			
			if(con == null) {
				
				return "Error while connecting to Database";
				
			}
			
			//create prepared statement
			String query = "delete from item where itemId = ? ";
			
			PreparedStatement preparedStmt  = con.prepareStatement(query);
			
			preparedStmt.setInt(1, Integer.valueOf(itemId));
			
			preparedStmt.execute();
			con.close();
			
			output = "Item Deleted";
			
			
		}catch(Exception e) {
			output = "Error while Deleting";
			System.err.println(e.getMessage());
			
		}
		
		return output;
	}
	
	public String updateItem( String itemName, String itemPrice, String itemDesc, String itemCode)  
	{
		String output = "";	
		try {
						
			Connection con = connect();
		
			
			if (con == null)
			{
				return "Error while connecting to the database for reading.";
			}

			
			stmt = con.createStatement();
						
			String update="UPDATE test.item"
					+ " SET itemName = '"+itemName+"', itemPrice = '"+itemPrice+"' , itemDesc = '"+itemDesc+"'"
					+ " WHERE itemId = '"+itemCode+"'";
			
			int rsv = stmt.executeUpdate(update);
			

			if(rsv > 0) {
				output = "Updated successfully";
			}
			con.close();
			
			
			
		}catch(Exception e) {
			output = "Error while updating";
			System.err.println(e.getMessage());
		}
		return output;
		
		
	}
	
	
}
