package expense_tracker;

import java.util.*;


public class Main {
    public static void main(String[] args) {
        ExpenseDAO expenseDAO = new ExpenseDAO();
        Scanner scanner = new Scanner(System.in);
        double income = 0;

        // user input for income
        System.out.print("Enter your total income: ");
        income = scanner.nextDouble();
        scanner.nextLine(); 
        // Adding expenses
        while (true) {
            System.out.print("Do you want to add an expense? (yes/no): ");
            String addMore = scanner.nextLine();
            if (addMore.equalsIgnoreCase("no")) {
                break;
            }

            // Create a new expense
            Expense expense = new Expense();
            System.out.print("Enter expense description: ");
            expense.setDescription(scanner.nextLine());

            System.out.print("Enter amount: ");
            expense.setAmount(scanner.nextDouble());
            scanner.nextLine(); 

            System.out.print("Enter category: ");
            expense.setCategory(scanner.nextLine());

            expense.setDate(new Date());

            // Add the expense to the database
            expenseDAO.addExpense(expense);
        }

        // Fetch and display all expenses
        System.out.println("\nList of Expenses:");
        double totalExpenses = 0;
        List<Expense> maxExpenses = new ArrayList<>();
        double maxAmount = 0;
        Set<String> uniqueExpenses = new HashSet<>();

        for (Expense exp : expenseDAO.getAllExpenses()) {
            String uniqueKey = exp.getDescription() + "-" + exp.getAmount();
            if (!uniqueExpenses.contains(uniqueKey)) {
                System.out.println(exp.getDescription() + " - " + exp.getAmount());
                totalExpenses += exp.getAmount();

                // Find the largest expenses
                if (exp.getAmount() > maxAmount) {
                    maxAmount = exp.getAmount();
                    maxExpenses.clear();
                    maxExpenses.add(exp);
                } else if (exp.getAmount() == maxAmount) {
                    maxExpenses.add(exp);
                }
                uniqueExpenses.add(uniqueKey);
            }
        }

        // Display total expenses
        System.out.println("\nTotal Expenses: " + totalExpenses);

        // Compare expenses with income
        if (totalExpenses <= income) {
            System.out.println("Your expenses are under your income.");
        } else {
            System.out.println("You have exceeded your income.");
        }

        // Display the highest expense(s)
        if (!maxExpenses.isEmpty()) {
            System.out.println("The highest expense(s):");
            for (Expense exp : maxExpenses) {
                System.out.println(exp.getDescription() + " - " + exp.getAmount());
            }
        } else {
            System.out.println("No expenses added.");
        }

        // Optionally delete an expense
        System.out.print("\nDo you want to delete an expense? (yes/no): ");
        String deleteChoice = scanner.nextLine();
        if (deleteChoice.equalsIgnoreCase("yes")) {
            System.out.print("Do you want to delete by ID or description? (id/description): ");
            String choice = scanner.nextLine();

            if (choice.equalsIgnoreCase("id")) {
                System.out.print("Enter the ID of the expense you want to delete: ");
                int expenseId = scanner.nextInt();
                expenseDAO.deleteExpenseById(expenseId);
            } else if (choice.equalsIgnoreCase("description")) {
                System.out.print("Enter the description of the expense you want to delete: ");
                String description = scanner.nextLine();
                expenseDAO.deleteExpenseByDescription(description);
            } else {
                System.out.println("Invalid choice. Please enter 'id' or 'description'.");
            }
        }

        scanner.close();
    }
}

