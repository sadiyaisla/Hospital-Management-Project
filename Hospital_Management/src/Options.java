import java.util.Scanner;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;

public class Options {
	public static Scanner scan = new Scanner(System.in);
	static Connection conn = null;
	static PreparedStatement pstmt = null;
	
	static void create() {
      try {
      Class.forName("com.mysql.jdbc.Driver");

      conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "root", "root");

      System.out.println("Enter First Name: ");
      String fname = scan.nextLine().toLowerCase();

      System.out.println("Enter Last Name: ");
      String lname = scan.nextLine().toLowerCase();
      
      System.out.println("Enter Age: ");
      int age = scan.nextInt();
      
      System.out.println("Enter Phone Number: ");
      int phone = scan.nextInt();
      
      System.out.println("Enter birthdate (day month year): ");
      int day = scan.nextInt();
      int month = scan.nextInt();
      int year = scan.nextInt();
      scan.nextLine(); 
      
      String birthdate =  String.format("%04d-%02d-%02d", year, month, day);
      
      System.out.println("Enter Room Number: ");
      String roomNum = scan.nextLine().toUpperCase();

      String sql = "INSERT INTO patients (first_name, last_name, age, phone_num, date_of_birth, room_number) VALUES (?, ?, ?, ?, ?, ?)";

      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, fname);
      pstmt.setString(2, lname);
      pstmt.setInt(3, age);
      pstmt.setInt(4, phone);
      pstmt.setString(5, birthdate);
      pstmt.setString(6, roomNum);

      pstmt.executeUpdate();

      System.out.println("Insert Completed.");
      Main.main(null);
      
      } catch (Exception e) {
    	  e.printStackTrace();
      }
	}
	
	static void update(int id) {
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "root", "root");
			System.out.println("Update First Name:");
			String updateFname = scan.nextLine().toLowerCase();
			
			System.out.println("Update Last Name:");
			String updateLname = scan.nextLine().toLowerCase();
			
			System.out.println("Update Age: ");
		    int updateAge = scan.nextInt();
		      
		    System.out.println("Update Phone Number: ");
	        int updatePhone = scan.nextInt();
	      
	        System.out.println("Update birthdate (day month year): ");
	        int updateDay = scan.nextInt();
	        int updateMonth = scan.nextInt();
	        int updateYear = scan.nextInt();
	        scan.nextLine(); 
	      
	        String updateBirthdate =  String.format("%04d-%02d-%02d", updateYear, updateMonth, updateDay);
	      
	        System.out.println("Update Room Number: ");
	        String updateRoomNum = scan.nextLine().toUpperCase();
			
			String sql = "UPDATE patients SET first_name = ? , last_name = ?, age = ?, phone_num = ?, date_of_birth = ?, room_number = ? WHERE id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, updateFname);
			pstmt.setString(2, updateLname);
			pstmt.setInt(3, updateAge);
			pstmt.setInt(4, updatePhone);
			pstmt.setString(5, updateBirthdate);
			pstmt.setString(6, updateRoomNum);
			pstmt.setInt(7, id);
			
			int rowsAffected = pstmt.executeUpdate();
			if (rowsAffected > 0) {
			    System.out.println("Update successful! " + rowsAffected + " row(s) modified.");
			    Main.main(null);
			} else {
			    System.out.println("No records were updated.");
			}
			
			
		}
		catch(Exception e) {
			System.err.println("Error updating data: " + e.getMessage());
		    e.printStackTrace();
		}
		
		
	}
	
	static void search(String action) {
	    if (action.equalsIgnoreCase("update") || action.equalsIgnoreCase("delete")) {
	        try {
	            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "root", "root");
	            System.out.println("Please Enter the Name:");
	            String name = scan.nextLine().toLowerCase();

	            String sql = "SELECT * FROM patients WHERE last_name LIKE ? OR first_name LIKE ? OR CONCAT(first_name, ' ', last_name) LIKE ?";

	            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	                pstmt.setString(1, "%" + name + "%");
	                pstmt.setString(2, "%" + name + "%");
	                pstmt.setString(3, "%" + name + "%");

	                ResultSet rs = pstmt.executeQuery();

	                System.out.println("id\tfirst_name\tlast_name\tage\tphone\tbirthdate\troom");

	                while (rs.next()) {
	                    int id = rs.getInt("id");
	                    String fname = rs.getString("first_name");
	                    String lname = rs.getString("last_name");
	                    int age = rs.getInt("age");
	    			    int phone = rs.getInt("phone_num");
	    			    String date = rs.getString("date_of_birth");
	    			    String room = rs.getString("room_number");
	    			    
	    			    System.out.printf("%-10d%-15s%-15s%-8d%-10s%-15s%-10s\n", id, fname, lname, age, phone, date, room);
	                    
	                    if (action.equalsIgnoreCase("update")) {
	                        update(id);
	                    } else if (action.equalsIgnoreCase("delete")) {
	                    	System.out.println("Are you sure you want to delete it? (yes or no)");
	                    	String answer = scan.nextLine();
	                    	if(answer.equalsIgnoreCase("yes")) {
	                    		 delete(id);
	                    	}
	                    	else if(answer.equalsIgnoreCase("no")) {
	                    		Main.main(null);
	                    	}
	                    	else {
	                    		System.out.println("Invalid answer.");
	                    	}
	                    }
	                }
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    } else {
	        System.out.println("Invalid action specified.");
	    }
	}

	
	static void delete(int id) {
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "root", "root");
			String sql = "DELETE FROM patients WHERE id=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
			
            if(rowsAffected > 0) {
                System.out.println("Record deleted successfully.");
                Main.main(null);
            } else {
                System.out.println("Failed to delete record.");
            }
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	static void showAll() {
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "root", "root");
			String sql = "SELECT * FROM patients";
			
			pstmt = conn.prepareStatement(sql);
			
			ResultSet rs = pstmt.executeQuery(sql);
			System.out.println("id\tfirst_name\tlast_name\tage\tphone\tbirthdate\troom");

			while (rs.next()) {
			    int id = rs.getInt("id");
			    String fname = rs.getString("first_name");
			    String lname = rs.getString("last_name");
			    int age = rs.getInt("age");
			    int phone = rs.getInt("phone_num");
			    String date = rs.getString("date_of_birth");
			    String room = rs.getString("room_number");

			    System.out.printf("%-10d%-15s%-15s%-8d%-10s%-15s%-10s\n", id, fname, lname, age, phone, date, room);
			}

			Main.main(null);
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
