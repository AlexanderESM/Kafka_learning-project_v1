package net.orekhov.paymentservice.service;

import net.orekhov.paymentservice.model.Payment;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class PaymentService {

    private final Map<Long, Payment> paymentStore = new HashMap<>();
    private long paymentIdCounter = 1;

    // Method to create a new payment
    public Payment createPayment(Payment payment) {
        payment.setId(paymentIdCounter++);
        paymentStore.put(payment.getId(), payment);
        return payment;
    }

    // Method to retrieve a payment by its ID
    public Payment getPaymentById(Long paymentId) {
        return paymentStore.get(paymentId);
    }

    // Method to update payment status
    public Payment updatePaymentStatus(Long paymentId, String status) {
        Payment payment = paymentStore.get(paymentId);
        if (payment != null) {
            payment.setStatus(status);
            return payment;
        }
        return null;  // Payment not found
    }

    // Method to delete a payment
    public boolean deletePayment(Long paymentId) {
        return paymentStore.remove(paymentId) != null;
    }

    // Additional methods (for example, you could add more business logic here)
    // For instance, checking if a payment is valid, or processing a payment.
}
