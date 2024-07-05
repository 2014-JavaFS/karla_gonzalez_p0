package src;

import java.util.Scanner;

public class BankingApplication {
    /*
    Must-Have Functionalities:
        1. Register new user account
            - Password
            - Username
            - First Name
            - Last Name
        2. Login
            - Password
            - Username
        3. Create at least one account
            - Account Type
                - Checking
                - Savings
            - Generate account number/id
        4. View account balances
            - Show account type
            - Show account balance
        5. Deposit funds
            - Double amount > 0
            - Account to deposit to
        6. Withdraw Funds
            - Double amount > 0 && <= amount in account
            - Account to withdraw from

    Suggested Bonus User Stories:
        1. view the transaction history for an account
        2. create multiple accounts per user (checking, savings, etc.)
        3. share a joint account with another user
        4. transfer money between accounts

    Minimum Features:
        1. Basic validation of user input (e.g. no registration for classes outside of registration window, no negative deposits/withdrawals, no overdrafting, etc.)
        2. Unit tests for all business-logic classes
        3. All exceptions are properly caught and handled
        4. Proper use of OOP principles
        5. Database is 3rd Normal Form Compliant
        6. Referential integrity (e.g. if a class is removed from the catalog, no students should be registered for it)
        7. Logging messages and exceptions to a file using a logger
        8. Generation of basic design documents (e.g. relational diagram, class diagram, flows, etc.)
    */


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to this bank app (Name TBD)");
        System.out.println("Please login or sign up to continue:");
        System.out.println("1. Log In /n2. Sign Up");
        System.out.println("3. Exit");

        int opt = scanner.nextInt();

        switch (opt) {
            case 1:
                System.out.println("Logging In");
                //TODO: Implement method to validate login credentials
                break;
            case 2:
                System.out.println("Signing Up");
                //TODO: Implement method to sign up new users
                break;
            case 3:
                System.out.println("Exiting Application");
                break;
            default:
                System.out.println("Invalid input. Please enter number 1 or 2");
        }

        //TODO: Implement method to check for successful login/signup before accessing next menu

        User user = new User();
        Account account = new Account();

        System.out.printf("Welcome %s %n", user.getFirstName());
        System.out.println("1. View Your Account Balance");
        System.out.println("2. Make a Deposit");
        System.out.println("3. Make a Withdrawal");
        System.out.println("4. Create a Checking/Savings Account");
        System.out.println("5. Exit Application");
        System.out.println("Please enter a numeric choice: ");
        opt = scanner.nextInt();

        switch (opt) {
            case 1:
                System.out.println("Viewing Account Balance(s)");
                //TODO: Implement method to view account balance(s)
                break;
            case 2:
                System.out.println("Making a Deposit");
                //TODO: Implement method to make deposits
                break;
            case 3:
                System.out.println("Making a Withdrawal");
                //TODO: Implement method to make withdrawals
                break;
            case 4:
                System.out.println("Creating an Account");
                //TODO: Implement method to create new accounts

                break;
            case 5:
                System.out.println("Exiting Application.");
                break;
            default:
                System.out.println("Invalid input. Please enter a number 1-4");
        }
    }
}
