package org.example;

public class CreditCardPayment extends PaymentMethod implements RequiresAuthentication {

    private boolean isAuthenticated = false;

    public CreditCardPayment(double amount, String currency) {
        super(amount, currency);
    }

    @Override
    public void processPayment() throws PaymentException {
        if (isAuthenticated) {
            System.out.printf("Processing credit card payment of %.2f %s.\n", amount, currency);
        } else {
            throw new PaymentException("Credit card has not been authenticated.");
        }
    }

    @Override
    public boolean authenticate() {
        System.out.println("Authenticating credit card...");
        isAuthenticated = true;

        return true;
    }
}
