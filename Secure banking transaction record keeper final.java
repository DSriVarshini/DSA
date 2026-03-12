import java.util.*;

class Transaction {
    String type;
    double amount;

    Transaction(String type, double amount) {
        this.type = type;
        this.amount = amount;
    }

    public String toString() {
        return type + " Rs " + amount;
    }
}

public class BankDemo {

    static Scanner sc = new Scanner(System.in);

    // HashMap → store accounts
    static HashMap<String, String> users = new HashMap<>();

    // List → transaction history
    static List<Transaction> history = new ArrayList<>();

    // Stack → undo last transaction
    static Stack<Transaction> undo = new Stack<>();

    // Queue → pending payments
    static Queue<Transaction> queue = new LinkedList<>();

    static double balance = 5000;

    public static void main(String[] args) {

        users.put("1001", "pass");

        login();
        menu();
    }

    static void login() {

        System.out.println("----------- WELCOME -----------");
        System.out.println();

        System.out.print("Enter Account no.: ");
        String acc = sc.nextLine();

        System.out.print("Password: ");
        String pass = sc.nextLine();

        if (users.containsKey(acc) && users.get(acc).equals(pass)) {
            System.out.println("Login Success!");
        } else {
            System.out.println("Invalid login");
            System.exit(0);
        }
    }

    static void menu() {

        while (true) {

            System.out.println("\n1. View Balance");
            System.out.println("2. Credit");
            System.out.println("3. Debit");
            System.out.println("4. View Transactions");
            System.out.println("5. Sort Transactions");
            System.out.println("6. Search Transaction");
            System.out.println("7. Undo");
            System.out.println("0 Exit");

            System.out.print("Enter Choice: ");
            int ch = sc.nextInt();

            switch (ch) {

                case 1:
                    viewBalance();
                    break;

                case 2:
                    credit();
                    break;

                case 3:
                    debit();
                    break;

                case 4:
                    show();
                    break;

                case 5:
                    sort();
                    break;

                case 6:
                    search();
                    break;

                case 7:
                    undo();
                    break;

                case 0:
                    return;
            }
        }
    }

    static void viewBalance() {
        System.out.println("Current Balance: Rs " + balance);
    }

    static void credit() {

        System.out.print("Enter Amount: ");
        double amt = sc.nextDouble();

        balance += amt;

        Transaction t = new Transaction("Credit", amt);

        history.add(t);     // List
        undo.push(t);       // Stack
        queue.add(t);       // Queue

        System.out.println("Credited Rs " + amt);
        System.out.println("New Balance: Rs " + balance);
    }

    static void debit() {

        System.out.print("Enter Amount: ");
        double amt = sc.nextDouble();

        if (amt > balance) {
            System.out.println("Not enough balance");
            return;
        }

        balance -= amt;

        Transaction t = new Transaction("Debit", amt);

        history.add(t);
        undo.push(t);
        queue.add(t);

        System.out.println("Debited Rs " + amt);
        System.out.println("New Balance: Rs " + balance);
    }

    static void show() {

        for (Transaction t : history) {
            System.out.println(t);
        }
    }

    // Bubble Sort
   static void sort() {

    for (int i = 0; i < history.size(); i++) {
        for (int j = 0; j < history.size() - i - 1; j++) {

            if (history.get(j).amount > history.get(j + 1).amount) {

                Transaction temp = history.get(j);
                history.set(j, history.get(j + 1));
                history.set(j + 1, temp);
            }
        }
    }

    System.out.println("Transactions Sorted by Amount");

    show();   // display sorted transactions
}

    // Linear Search
    static void search() {

        System.out.print("Enter amount to find: ");
        double key = sc.nextDouble();

        for (Transaction t : history) {

            if (t.amount == key) {
                System.out.println("Found: " + t);
                return;
            }
        }

        System.out.println("Not Found");
    }

    static void undo() {

        if (undo.isEmpty()) {
            System.out.println("Nothing to undo");
            return;
        }

        Transaction t = undo.pop();

        history.remove(t);

        if (t.type.equals("Credit"))
            balance -= t.amount;
        else
            balance += t.amount;

        System.out.println("Undo done");
        System.out.println("Balance: Rs " + balance);
    }
}