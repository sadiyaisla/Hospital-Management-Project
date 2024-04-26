import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
    	Options opt = new Options();
    	
    	
    	System.out.println("Welcome To Hospital Management!");
    	System.out.println("");
    	System.out.println("Choose action below (Type create or update or delete) : ");
    	
    	Scanner scan = new Scanner(System.in);
    	
    	System.out.println("> Show All"+ " " + "> Create" + " " + "> Update" + " " + "> Delete");
    	String userSelect = scan.nextLine();
    	
    	if(userSelect.equalsIgnoreCase("create")) {
    		opt.create();
    	}
    	
    	else if(userSelect.equalsIgnoreCase("Show All")) {
    		opt.showAll();
    	}
    	
    	else if(userSelect.equalsIgnoreCase("update")) {
    		opt.search("update");
    	}
    	else if(userSelect.equalsIgnoreCase("delete")) {
    		opt.search("delete");
    	}
    	else {
    		System.out.println("Invalid option.");
    		Main.main(args);
    	}

    }
}
