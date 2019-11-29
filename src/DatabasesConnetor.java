import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
public class DatabasesConnetor {
		static int id, idcheck = 0, BookIdCheck;
		static String fName = null, lName = null, BookName, Bookname, id2;
		static boolean idTry = true;
		static int MemberChose, MorE, EmemberChose;
		static String Bookid;
		static boolean Eid = true;
		static Scanner input = new Scanner(System.in);
	public static void main(String[] args) {
	try{  
		//Class.forName("com.mysql.jdbc.Driver");  
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/Library?usessl= false","root","1118");  
		Statement stmt=con.createStatement(); 
		System.out.println("Connection Sucessful");
		//this is to find the MemeberId as a result set
		System.out.println("Are you a Member or Employee?");
		System.out.println("(1) member");
		System.out.println("(2) Employee");
		MorE = input.nextInt();
		//this is to see who is a member or not
		if(MorE == 1) {
		System.out.println("Library Id:");
		id = input.nextInt();
		ResultSet MId = stmt.executeQuery("select L.Member_ID from LibMember L where Member_ID = "+ id);
		while(MId.next()) {
			idcheck = Integer.valueOf(MId.getInt(1));
		}
		if(id == idcheck) {
		ResultSet Name = stmt.executeQuery("select Member_Fname, Member_Lname from LibMember whereMember_ID = "+ id);
		while(Name.next()) {
		fName = Name.getString(1);
		lName = Name.getString(2);
		}
			System.out.println("Welcome "+fName+" "+lName);
		}else {System.out.println("Wrong ID");
		System.exit(0);}
		Menu();
		while(MemberChose != 3) {
		if(MemberChose == 1) {
		System.out.println("what the name of the book: ");
		//this will find the book by the name and find the dewDecimalnum of the book
		BookName = input.nextLine();
		ResultSet Book = stmt.executeQuery("select DewDecimalNum, Number_Available from Book where NameOfBook like "+ BookName);
		while(Book.next()) {
			BookIdCheck = Integer.valueOf(Book.getString(1));
			System.out.println("Book Available:"+Book.getString(2));
		}
		//this will insert into the membertoBook
		ResultSet insert = stmt.executeQuery("insert into MemberToBook(MemberId, BookId) values("+ id+", "+BookIdCheck +")");
		System.out.println("Book Added to "+fName+" "+lName);
		Menu();
		}//this one for returning a book by deleting it from the membertobook
		else if(MemberChose == 2) {
			int countbook = 0;
			ResultSet findbookid = stmt.executeQuery("select BookId from MemberToBook where MemberId == "+id);
			while(findbookid.next()) {
						System.out.println(findbookid.getString(countbook));
						countbook++;
			}
			System.out.println("What is the bookId?");
			Bookid = input.nextLine();
				ResultSet deleteId = stmt.executeQuery("select BookId from MemberToBook where BookId == "+Bookid);
			if(Bookid.equalsIgnoreCase(deleteId.getString(1))) {
				ResultSet delete = stmt.executeQuery("Delete FROM MemberToBook WHERE BookId == "+Bookid);
			}else {
				System.out.println("no book found");
			}
			Menu();
		}
		else {MemberChose = 3;}
		}
		//this is the member secition
		}else if(MorE == 2) {
			System.out.println("EmployeeLibrary Id:");
			ResultSet EId = stmt.executeQuery("select e.Employee_SSN from Employee e where e.Employee_SSN =="+id);
			if(EId.getString(1).equalsIgnoreCase(id2)) {
				System.out.println("welcome Employee");
			}else {
				System.out.println("wrong ssn byee"); 
				Eid = false;
				System.exit(0);
			}
		}
		while(EmemberChose !=3) {
			if(EmemberChose == 1) {
				System.out.println("what is the book Name to order?");
				Bookname = input.nextLine();
				ResultSet Order = stmt.executeQuery("insert into OrderList(BookName) values("+BookName+");");
				Menu2();
			}else if(EmemberChose==2) {
				
			}
		}
	}//this will catch the error if it doesnt connect
	catch(Exception e){System.out.println(e);} 
	
}
	public static void Menu() {
		System.out.println("1 Book Search");
		System.out.println("2 Return Book");
		System.out.println("3 exit");
		MemberChose = input.nextInt();
	}
	public static void Menu2() {
		System.out.println("1 Orderlist");
		System.out.println("2 Employee information");
		System.out.println("3 exit");
		EmemberChose = input.nextInt();
	}

}
