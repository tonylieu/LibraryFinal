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
		static String NewNamef, NewNamel, NewAddress, NewMState, NewCity, NewCountry,SSN,job;
		int newId;
		static int Deleteid;
		static int NewLibrary;
		static boolean Eid = true;
		static Scanner input = new Scanner(System.in);
		static boolean checking = true;
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
		while(MemberChose != 5) {
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
		else if(MemberChose == 3) {
			ResultSet MemName = stmt.executeQuery("select Member_Fname, Member_Lname, Address, MState, MCity from LibMember where Member_ID == "+ id);
			while(MemName.next()) {
				System.out.println("Name: "+MemName.getString(1)+" "+MemName.getString(2)+"\n");
				System.out.println("ID: "+ id);
				System.out.println("Address: " + MemName.getString(3)+", "+MemName.getString(4)+", "+MemName.getString(5)+"\n");
			}
			Menu();
			// show book Checkout by Member
		}else if(MemberChose == 4) {
			ResultSet Books= stmt.executeQuery("select BookId from MemberToBooks where MemberId == " +id);
			int counting = 1;
			int BookIDNumber;
			while(Books.next()) {
				BookIDNumber = Integer.parseInt(Books.getString(counting)); 
				ResultSet BooksNames = stmt.executeQuery("select * b.BookNames from Book b where DewDecimalNum == "+BookIDNumber);
				System.out.println(BooksNames.getString(counting)+"\n");
				counting++;
			}
			Menu();
			//show book avaiable
		}else if(MemberChose == 5) {
			
			Menu();
		}
		}
		//this is the member secition
		}else if(MorE == 2) {
			System.out.println("EmployeeLibrary Id:");
			id = input.nextInt();
			ResultSet EId = stmt.executeQuery("select e.Employee_SSN from Employee e where e.Employee_SSN =="+id);
			if(EId.getString(1).equalsIgnoreCase(id2)) {
				System.out.println("welcome Employee");
			}else {
				System.out.println("wrong ssn byee"); 
				Eid = false;
				System.exit(0);
			}
		}
		while(EmemberChose !=10) {
			if(EmemberChose == 1) {
				System.out.println("what is the book Name to order?");
				Bookname = input.nextLine();
				ResultSet Order = stmt.executeQuery("insert into OrderList(BookName) values("+"'"+BookName+"'"+");");
				Menu2();
			}else if(EmemberChose==2) {
				ResultSet name = stmt.executeQuery("select Employee_Fname, Employee_Lname, Job, LibraryId from Employee where Employee_SSN like "+id);
				ResultSet location = stmt.executeQuery("select LibraryName, City, State from LibraryLocation where LibraryIdLocation == "+name.getString(4));
				while(name.next()) {
					System.out.println("Name: "+name.getString(1)+" "+ name.getString(2)+"\n");
					System.out.println("Job: "+ name.getString(3)+"\n");
					System.out.println("location: "+ location.getString(1)+" ,"+location.getString(2)+ ", " + location.getString(3));
				}
				Menu2();
				//this will show all the employees
			}else if(EmemberChose==3) {
				int countFn = 1;
				int countLn= 2;
				int countSSn = 3;
				int countjob = 4;
				int counthr = 5;
				int countLibraryId = 6;
				ResultSet Employeelist = stmt.executeQuery("select * from Employee;");
				while(Employeelist.next()) {
					System.out.println("Name :"+Employeelist.getString(countFn)+" "+Employeelist.getString(countLn)+"\n");
					System.out.println("SSN: "+Employeelist.getString(countSSn)+"\n");
					System.out.println("Job: "+Employeelist.getString(countjob)+"\n");
					System.out.println("Hours: "+Employeelist.getString(counthr)+"\n");
					System.out.println("LibraryId: "+Employeelist.getString(countLibraryId)+"\n");
					countFn = countFn+6;
					countLn = countLn+6;
					countSSn = countSSn+6;
					countjob = countjob+6;
					counthr = counthr+6;
					countLibraryId = countLibraryId+6;
				
				}
				Menu2();
				//this will show all memebers
			}else if(EmemberChose==4) {
				int countFn = 1;
				int countLn= 2;
				int countId = 3;
				int countAdress = 4;
				int countstate = 5;
				int countCity = 6;
				int countCounty = 7;
				int countLibraryId = 8;
				ResultSet Memberlist = stmt.executeQuery("select * from LibMember");
				while(Memberlist.next()) {
					System.out.println("Name: "+ Memberlist.getString(countFn)+" " +Memberlist.getString(countLn));
					System.out.println("id: "+Memberlist.getString(countId));
					System.out.println("Adress: "+Memberlist.getString(countAdress)+", "+Memberlist.getString(countstate)+", "+ Memberlist.getString(countCity)+", "+Memberlist.getString(countCounty)+"\n");
					System.out.println("LibraryId: "+Memberlist.getString(countLibraryId));
					countFn = countFn+8;
					countLn = countLn+8;
					countId = countId+8;
					countAdress= countAdress+8;
					countstate = countstate+8;
					countCity = countstate+8;
					countLibraryId = countLibraryId+8;
				}
				Menu2();
				//this is to create memebrs
			}else if(EmemberChose==5) {
				System.out.println("firstName: ");
				NewNamef = input.next();
				System.out.println("LastName: ");
				NewNamel = input.next();
				System.out.println("Address: " );
				NewAddress = input.next();
				System.out.println("City: ");
				NewMState = input.next();
				System.out.println("MCountry: ");
				NewCountry = input.next();
				System.out.println("LibraryId: ");
				NewLibrary = input.nextInt();
				while(checking) {
				if(NewLibrary < 0 && NewLibrary > 4) {
					ResultSet numberOfMembers = stmt.executeQuery("select COUNT(*) from LibMember");
					ResultSet newMember = stmt.executeQuery("insert into LibMember(Member_Fname, Member_Lname, Member_ID,Address,MState,MCity,MCountry,MLibary ) values("
							+"'"+ NewNamef+"'"+", "+"'"+NewNamel+"'"+", "+"'"+NewAddress+"'"+","+"'"+NewMState+"'"+","+"'"+NewCountry+"'"+","+NewLibrary+")");
					checking = false;
					
				}else {
					System.out.println("doesn't exist please try again");
					System.out.println("LibraryId: ");
					NewLibrary = input.nextInt();
				}
				}
				Menu2();
				//this is to create employees
			}else if(EmemberChose==6) {
				checking = true;
				System.out.println("firstName: ");
				NewNamef = input.next();
				System.out.println("LastName: ");
				NewNamel = input.next();
				System.out.println("SSN: ");
				SSN = input.next();
				System.out.println("Job: ");
				job = input.next();
				System.out.println("LibraryId: ");
				NewLibrary = input.nextInt();
				while(checking) {
				if(NewLibrary < 0 && NewLibrary > 4) {
					ResultSet NewE = stmt.executeQuery("insert into Employee(Employee_Fname, Employee_Lname, Employee_SSN, Job, LibraryId) values ( "
							+"'"+NewNamef +"'"+", "+"'"+NewNamel+"'"+", "+"'"+SSN+"'"+","+"'"+job+"'"+", "+NewLibrary+")" );
					System.out.println("Employee added"+ NewNamef+" " +NewNamel);
					
				}else {
					System.out.println("doesn't exist please try again");
					System.out.println("LibraryId: ");
					NewLibrary = input.nextInt();
				}
				}
				Menu2();
				//this is to show the orderlist
			}else if(EmemberChose == 7) {
				int BooknameC = 1;
				int BookE = 2;
				 ResultSet Orderlist = stmt.executeQuery("Select BookName, EmployeeOrder from OrderList");
				 while(Orderlist.next()) {
					 System.out.println("BookName: "+ Orderlist.getString(BooknameC)+" \n");
					 System.out.println("Employee: "+ Orderlist.getString(BookE)+"\n");
					 BookE = BookE+2;
					 BooknameC= BooknameC +2;
				 }
				Menu2();
				//this one is the delete for member
			}else if(EmemberChose == 8) {
				System.out.println("what is the memberId you wish to delete?");
				Deleteid = input.nextInt();
				ResultSet dele = stmt.executeQuery("Delete From LibMember where Member_ID == "+Deleteid);
				ResultSet deleb = stmt.executeQuery("Delete From MemberToBook where MemberId == "+Deleteid);
				System.out.println("Member has been deleted");
				Menu2();
				//this one is to delete for employee
			}else if(EmemberChose == 9) {
				System.out.println("what is the Employee Id you wish to delete?");
				Deleteid = input.nextInt();
				ResultSet dele = stmt.executeQuery("Delete From Employee where Employee_SSN like "+Deleteid);
				ResultSet deleo = stmt.executeQuery("Delete From OrderList where EmployeeOrder like "+Deleteid);
				System.out.println("Employee has been deleted");
				Menu2();
			}
		}
	}//this will catch the error if it doesnt connect
	catch(Exception e){System.out.println(e);} 
	
}
	public static void Menu() {
		System.out.println("1 Book Search");
		System.out.println("2 Return Book");
		System.out.println("3 Member Information");
		System.out.println("4 show book Checkout by Member");
		System.out.println("5 exit");
		MemberChose = input.nextInt();
	}
	public static void Menu2() {
		System.out.println("1 Orderlist");
		System.out.println("2 Employee information");
		System.out.println("3 Show all Employees");
		System.out.println("4 show all memebers");
		System.out.println("5 create Memeber");
		System.out.println("6 create Employee");
		System.out.println("7 show orderlist");
		System.out.println("8 delete member");
		System.out.println("9 delete Employee");
		System.out.println("10 exit");
		EmemberChose = input.nextInt();
	}
/*
 * Creating a way for employee to view what books are checked out and who checked them out

 */
}
