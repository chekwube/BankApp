import java.util.Scanner;
import java.util.ArrayList;

public class Menu {
	//instance variables
	Scanner keyboard = new Scanner(System.in);
	Bank bank = new Bank();
	boolean exit;
	private String firstName;
	private String lastName;
	
	public static void main(String[] args){
		Menu menu = new Menu();
		menu.runMenu();
	}
	
	public void runMenu(){
		printHeader();
		while(!exit){
			printMenu();
			int choice = getInput();
			performAction(choice);
		}
	}
	
	private void printHeader(){
		System.out.println("+-----------------------------------------------+");
		System.out.println("|              Bank Application,           		|");
		System.out.println("|			   Built with java.					|");
		System.out.println("+-----------------------------------------------+");

	}
	
	private void printMenu(){
		System.out.println("Please make a selection");
		System.out.println("1) Create a new Account");
		System.out.println("2) Make a deposit");
		System.out.println("3) Make a withdrawal");
		System.out.println("4) List acccount balance");
		System.out.println("0) Exit");
	}
	
	private int getInput(){
		int choice = -1;
		do{
			System.out.println("Enter your choice: ");
			try{
				choice = Integer.parseInt(keyboard.nextLine());
			}catch(NumberFormatException e){
				System.out.println("Invalid selection. Number only please.");
			}
		}while(choice < 0 || choice >4);
		return choice;
	}
	
	private void performAction(int choice){
		switch(choice){
		case 0:
			System.out.println("Thank you for using our application.");
			System.exit(0);
			break;
		case 1:
			//createAnAccount
			createAnAccount();
			break;
		case 2:
			//makeDeposit
			makeDeposit();
			break;
		case 3:
			//makeAWithdrawal
			makeAWithdrawal();
			break;
		case 4:
			//listBalance
			listBalances();
			break;
		default:
			System.out.println("Unknown error has occured.");
		}
	}
	
	private void createAnAccount(){
		String firstname, lastname, ssn, accountType = "";
		double initialDeposit = 0;
		boolean valid = false;
		while(!valid){
			System.out.print("Please enter an account type(checking/savings):");
			accountType = keyboard.nextLine();
			if(accountType.equalsIgnoreCase("checking") || accountType.equalsIgnoreCase("savings"));{
				valid = true;
			}
		}
		System.out.println("Please enter your first number");
		firstname = keyboard.nextLine();
		System.out.print("Please enter your last name: ");
		lastname = keyboard.nextLine();
		System.out.print("Please enter your social security number: ");
		ssn = keyboard.nextLine();
		valid = false;
		
		while(!valid){
			System.out.println("Please enter an initial deposit: ");
			try{
				initialDeposit = Double.parseDouble(keyboard.nextLine());
			}catch(NumberFormatException e){
				System.out.println("Deposit must be a number.");
			}
			if(accountType.equalsIgnoreCase("checking")){
				if(initialDeposit < 100){
					System.out.println("checking account requires a minimum of $100 dollars to open");
				}else{
					valid = true;
				}
			}else if(accountType.equalsIgnoreCase("savings")){
				if(initialDeposit < 50){
				System.out.println("savings account requires a minimum of $50 dollars to open");
				}else{
					valid = true;
				}
			}
		}
		//creating an account
		Account account;
		if(accountType.equalsIgnoreCase("checking")){
			account = new Checking(initialDeposit);
		}else{
			account = new Savings(initialDeposit);
		}
		
		Customer customer = new Customer(firstName, lastName, ssn, account);
		bank.addCustomer(customer);
	}
	
	private void makeDeposit(){
		int account = selectAccount();
		if(account >= 0){
		System.out.print("How much would you like to deposit?");
		double amount = 0;
		try{
			amount = Double.parseDouble(keyboard.nextLine());
		}catch(NumberFormatException e){
			amount = 0;
		}
		bank.getCustomer(account).getAccount().deposit(amount);
		}
	}
	
	private void makeAWithdrawal(){
		int account = selectAccount();
		if(account >= 0){
		System.out.print("How much would you like to withdraw?");
		double amount = 0;
		try{
			amount = Double.parseDouble(keyboard.nextLine());
		}catch(NumberFormatException e){
			amount = 0;
		}
		bank.getCustomer(account).getAccount().withdraw(amount);
		}
	}
	
	private void listBalances(){
		int account = selectAccount();
		if(account >= 0){
		
		System.out.println(bank.getCustomer(account).getAccount());
		}
	}
	
	private int selectAccount() {
		ArrayList<Customer> customers = bank.getCustomers();
		if(customers.size() <= 0){
			System.out.println("No customers at your bank");
			return -1;
		}
		System.out.println("Select an account: ");
		for(int i = 0; i < customers.size();i++){
			System.out.println((i+1) + ")" + customers.get(i).basicInfo());
		}
		int account = 0;
		System.out.println("Please enter your selection: ");
		try{
			account = Integer.parseInt(keyboard.nextLine()) - 1;
		}catch(NumberFormatException e){
			account = -1;
		}
		if(account < 0 || account > customers.size()){
			System.out.println("Invalid account selected.");
			account = -1;
		}
		return account;
	}

}





























