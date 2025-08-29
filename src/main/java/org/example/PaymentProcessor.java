package org.example;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class PaymentProcessor
{
    public static void main( String[] args )
    {
        Scanner kb = new Scanner(System.in);
        ArrayList<PaymentMethod> paymentMethods = new ArrayList<PaymentMethod>();

        int input = 0;

        while (input != 4) {
            printMenu();
            try {
                input = Integer.parseInt(kb.nextLine());
                if (input < 1 || input > 4) {
                    System.out.println("Invalid input.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input.");
            }

            switch (input) {
                case 1: addCreditCardPayment(kb, paymentMethods);
                    break;
                case 2: addPayPalPayment(kb, paymentMethods);
                    break;
                case 3: processAllPayments(paymentMethods);
                    break;
                case 4:
                    System.out.println("Exiting the application. Goodbye!");
            }
        }

        kb.close();
    }

    private static void printMenu() {
        System.out.printf(
                "\n--- Payment Processor Menu ---\n" +
                "1. Add a Credit Card Payment\n" +
                "2. Add a PayPal Payment\n" +
                "3. Process All Payments\n" +
                "4. Exit\n" +
                "Choose an option (1-4): ");
    }

    private static void addCreditCardPayment(Scanner kb, ArrayList<PaymentMethod> paymentMethods) {
        paymentMethods.add(getPaymentDetails(kb, "credit card"));
        System.out.println("Credit Card payment added successfully.");
    }

    private static void addPayPalPayment(Scanner kb, ArrayList<PaymentMethod> paymentMethods) {
        paymentMethods.add(getPaymentDetails(kb, "PayPal"));
        System.out.println("PayPal payment added successfully.");
    }

    private static PaymentMethod getPaymentDetails(Scanner kb, String type) {
        boolean validInput = false;
        double amount = 0;
        String currency = "";

        while (!validInput) {
            try {
                System.out.printf("Enter %s payment amount: ", type);
                amount = Double.parseDouble(kb.nextLine());
                if (amount <= 0) {
                    System.out.println("Invalid input.");
                } else {
                    validInput = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input.");
            }
        }

        System.out.print("Enter currency (e.g. USD, EUR): ");
        currency = kb.nextLine();

        if (type.equals("credit card")) {
            return new CreditCardPayment(amount, currency);
        }

        return new PayPalPayment(amount, currency);
    }

    private static void processAllPayments(ArrayList<PaymentMethod> paymentMethods) {
        if (paymentMethods.isEmpty()) {
            System.out.println("No payments to process.");
            return;
        }

        for (PaymentMethod payment : paymentMethods) {
            if (payment instanceof CreditCardPayment) {
                ((CreditCardPayment) payment).authenticate();
            }

            try {
                payment.processPayment();
            } catch (PaymentException e) {
                System.out.println(e.getMessage());
            }
        }

        paymentMethods.clear();
    }
}
