// Banking Atm Application project

import java.util.*;

// INTERFACE FOR TRANSACTION OPERATIONS
interface Transactable {

    void deposit(double amt);

    void withdraw(double amt);

    void checkBalance();

    void showHistory();
}

// ═══════════════════════════════════════════════════════════════
// ACCOUNT CLASS
// ═══════════════════════════════════════════════════════════════
class Account implements Transactable {

    private String accountType;      // Savings or Current
    private double balance;
    private ArrayList<String> history;

    Account(String accountType, double initialBalance) {
        this.accountType = accountType;
        this.balance = initialBalance;
        this.history = new ArrayList<>();
        this.history.add("Account Created | Type: " + accountType + " | Initial Balance: " + initialBalance);
    }

    @Override
    public void deposit(double amt) {
        if (amt <= 0) {
            System.out.println(" Invalid amount! Amount must be greater than 0");
            return;
        }
        balance += amt;
        history.add("Deposit : +" + amt + " | New Balance: " + balance);
        System.out.println(" Deposit Successful! | New Balance: ₹" + String.format("%.2f", balance));
    }

    @Override
    public void withdraw(double amt) {
        if (amt <= 0) {
            System.out.println(" Invalid amount! Amount must be greater than 0");
        } else if (amt > balance) {
            System.out.println(" Insufficient balance! Current Balance: ₹" + String.format("%.2f", balance));
        } else {
            balance -= amt;
            history.add("Withdrawal : -" + amt + " | New Balance: " + balance);
            System.out.println("✅ Withdrawal Successful! | New Balance: ₹" + String.format("%.2f", balance));
        }
    }

    @Override
    public void checkBalance() {
        System.out.println("\n╔═════════════════════════════════╗");
        System.out.println("║     ACCOUNT BALANCE DETAILS     ║");
        System.out.println("╠═════════════════════════════════╣");
        System.out.println("║ Account Type: " + String.format("%-18s", accountType) + "║");
        System.out.printf("║ Balance: ₹%-24.2f║%n", balance);
        System.out.println("╚═════════════════════════════════╝\n");
    }

    @Override
    public void showHistory() {
        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.println("║       TRANSACTION HISTORY                  ║");
        System.out.println("╠════════════════════════════════════════════╣");
        if (history.isEmpty()) {
            System.out.println("║ No transactions yet                        ║");
        } else {
            for (String h : history) {
                System.out.println("║ → " + String.format("%-41s", h.substring(0, Math.min(40, h.length()))) + "║");
            }
        }
        System.out.println("╚════════════════════════════════════════════╝\n");
    }

    public String getAccountType() {
        return accountType;
    }

    public double getBalance() {
        return balance;
    }

    public ArrayList<String> getHistory() {
        return history;
    }
}

// ═══════════════════════════════════════════════════════════════
// CUSTOMER CLASS
// ═══════════════════════════════════════════════════════════════
class Customer {

    private String name;
    private String aadharNo;
    private String phoneNo;
    private String pin;
    private Account account;

    Customer(String name, String aadharNo, String phoneNo, String pin, Account account) {
        this.name = name;
        this.aadharNo = aadharNo;
        this.phoneNo = phoneNo;
        this.pin = pin;
        this.account = account;
    }

    public boolean validatePin(String inputPin) {
        return this.pin.equals(inputPin);
    }

    public String getName() {
        return name;
    }

    public String getAadharNo() {
        return aadharNo;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public Account getAccount() {
        return account;
    }

    public void displayDetails() {
        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.println("║          ACCOUNT DETAILS                   ║");
        System.out.println("╠════════════════════════════════════════════╣");
        System.out.println("║ Name: " + String.format("%-39s", name) + "║");
        System.out.println("║ Aadhar No: " + String.format("%-34s", aadharNo) + "║");
        System.out.println("║ Phone No: " + String.format("%-35s", phoneNo) + "║");
        System.out.println("║ Account Type: " + String.format("%-32s", account.getAccountType()) + "║");
        System.out.printf("║ Balance: ₹%-32.2f║%n", account.getBalance());
        System.out.println("╚════════════════════════════════════════════╝\n");
    }
}

// ═══════════════════════════════════════════════════════════════
// BANK CLASS - MANAGES ALL CUSTOMERS & ACCOUNTS
// ═══════════════════════════════════════════════════════════════
class Bank {

    private ArrayList<Customer> customers;
    private Scanner sc;

    Bank() {
        this.customers = new ArrayList<>();
        this.sc = new Scanner(System.in);
    }

    // CREATE NEW ACCOUNT
    public void createAccount() {
        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.println("║        CREATE NEW ACCOUNT                  ║");
        System.out.println("╚════════════════════════════════════════════╝\n");

        // Input Name
        System.out.print("Enter Your Name: ");
        String name = sc.nextLine();

        // Input Aadhar Number
        System.out.print("Enter Your Aadhar Number (12 digits): ");
        String aadharNo = sc.nextLine();
        if (aadharNo.length() != 12 || !aadharNo.matches("\\d{12}")) {
            System.out.println(" Invalid Aadhar Number! Must be 12 digits.");
            return;
        }

        // Input Phone Number
        System.out.print("Enter Your Phone Number (10 digits): ");
        String phoneNo = sc.nextLine();
        if (phoneNo.length() != 10 || !phoneNo.matches("\\d{10}")) {
            System.out.println(" Invalid Phone Number! Must be 10 digits.");
            return;
        }

        // Select Account Type
        System.out.println("\nSelect Account Type:");
        System.out.println("1. Savings Account");
        System.out.println("2. Current Account");
        System.out.print("Enter choice (1 or 2): ");
        int choice = sc.nextInt();
        sc.nextLine(); // Consume newline

        String accountType;
        if (choice == 1) {
            accountType = "Savings";
        } else if (choice == 2) {
            accountType = "Current";
        } else {
            System.out.println(" Invalid choice!");
            return;
        }

        // Set PIN
        System.out.print("\nSet your PIN (4 digits): ");
        String pin = sc.nextLine();
        if (pin.length() != 4 || !pin.matches("\\d{4}")) {
            System.out.println(" Invalid PIN! Must be exactly 4 digits.");
            return;
        }

        // Create account with initial balance 0
        Account account = new Account(accountType, 0);

        // Create customer
        Customer customer = new Customer(name, aadharNo, phoneNo, pin, account);

        // Add to customers list
        customers.add(customer);

        System.out.println("\n Account Created Successfully!");
        System.out.println(" Your Aadhar Number: " + aadharNo + " (Use this to login)");
        customer.displayDetails();
    }

    // LOGIN USING AADHAR & PIN
    public Customer login() {
        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.println("║              ATM LOGIN                     ║");
        System.out.println("╚════════════════════════════════════════════╝\n");

        System.out.print("Enter Your Aadhar Number: ");
        String aadharNo = sc.nextLine();

        // Find customer by Aadhar
        Customer customer = null;
        for (Customer c : customers) {
            if (c.getAadharNo().equals(aadharNo)) {
                customer = c;
                break;
            }
        }

        if (customer == null) {
            System.out.println(" Account not found! Please create an account first.");
            return null;
        }

        // Validate PIN
        System.out.print("Enter Your PIN: ");
        String pin = sc.nextLine();

        if (!customer.validatePin(pin)) {
            System.out.println(" Invalid PIN! Access Denied.");
            return null;
        }

        System.out.println(" Login Successful!");
        System.out.println(" Welcome, " + customer.getName() + "!\n");
        return customer;
    }

    // PERFORM TRANSACTIONS
    public void performTransactions(Customer customer) {
        Account account = customer.getAccount();
        String choice;

        do {
            System.out.println("╔════════════════════════════════════════════╗");
            System.out.println("║             ATM MENU                       ║");
            System.out.println("╠════════════════════════════════════════════╣");
            System.out.println("║ 1. Deposit Money                           ║");
            System.out.println("║ 2. Withdraw Money                          ║");
            System.out.println("║ 3. Check Balance                           ║");
            System.out.println("║ 4. Transaction History                     ║");
            System.out.println("║ 5. View Account Details                    ║");
            System.out.println("║ 6. Logout                                  ║");
            System.out.println("╚════════════════════════════════════════════╝");
            System.out.print("Enter your choice (1-6): ");

            int op = sc.nextInt();
            sc.nextLine(); // Consume newline

            switch (op) {
                case 1 -> {
                    System.out.print("Enter deposit amount (₹): ");
                    double amt = sc.nextDouble();
                    sc.nextLine();
                    account.deposit(amt);
                }
                case 2 -> {
                    System.out.print("Enter withdrawal amount (₹): ");
                    double amt = sc.nextDouble();
                    sc.nextLine();
                    account.withdraw(amt);
                }
                case 3 ->
                    account.checkBalance();
                case 4 ->
                    account.showHistory();
                case 5 ->
                    customer.displayDetails();
                case 6 -> {
                    System.out.println("\n╔════════════════════════════════════════════╗");
                    System.out.println("║  Thank you, " + String.format("%-28s", customer.getName() + "!") + "║");
                    System.out.println("║    Have a great day!                     ║");
                    System.out.println("╚════════════════════════════════════════════╝\n");
                    return;
                }
                default ->
                    System.out.println(" Invalid choice! Please select 1-6.");
            }

            System.out.print("\n\nContinue with more transactions? (YES/NO): ");
            choice = sc.nextLine();

        } while (choice.equalsIgnoreCase("YES") || choice.equalsIgnoreCase("Y"));

        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.println("║  Thank you for using ATM!               ║");
        System.out.println("╚════════════════════════════════════════════╝\n");
    }

    // DISPLAY MAIN MENU
    public void displayMainMenu() {
        String choice;
        do {
            System.out.println("╔════════════════════════════════════════════╗");
            System.out.println("║          WELCOME TO OUR BANK             ║");
            System.out.println("╠════════════════════════════════════════════╣");
            System.out.println("║ 1. Create New Account                      ║");
            System.out.println("║ 2. Login to Existing Account               ║");
            System.out.println("║ 3. Exit                                    ║");
            System.out.println("╚════════════════════════════════════════════╝");
            System.out.print("Enter your choice (1-3): ");

            int op = sc.nextInt();
            sc.nextLine(); // Consume newline

            switch (op) {
                case 1 ->
                    createAccount();
                case 2 -> {
                    Customer customer = login();
                    if (customer != null) {
                        performTransactions(customer);
                    }
                }
                case 3 -> {
                    System.out.println("\n╔════════════════════════════════════════════╗");
                    System.out.println("║ Thank you for banking with us!            ║");
                    System.out.println("╚════════════════════════════════════════════╝\n");
                    return;
                }
                default ->
                    System.out.println("❌ Invalid choice! Please select 1-3.\n");
            }
        } while (true);
    }
}

// ═══════════════════════════════════════════════════════════════
// MAIN CLASS
// ═══════════════════════════════════════════════════════════════
public class EnhancedATM {

    public static void main(String[] args) {
        Bank bank = new Bank();
        bank.displayMainMenu();
    }
}
