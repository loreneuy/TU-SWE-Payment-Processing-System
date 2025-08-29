package org.example;

public class PayPalPayment extends PaymentMethod{

    public PayPalPayment(double amount, String currency) {
        super(amount, currency);
    }

    @Override
    public void processPayment() throws PaymentException {
        System.out.printf("Processing PayPal payment of %.2f %s.\n", amount, currency);
    }
}
