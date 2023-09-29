package tech.wiktor.libs.payments;

import tech.wiktor.libs.payments.entities.GeneratedPaymentEntity;
import tech.wiktor.libs.payments.exceptions.PaymentException;
import tech.wiktor.libs.payments.providers.Params;
import tech.wiktor.libs.payments.providers.Provider;
import tech.wiktor.libs.payments.providers.Status;

import java.util.function.BiConsumer;

public class Payment {
    private final Provider provider;
    public Payment(Provider provider) {
        this.provider = provider;
    }

    public void generate(Params params, BiConsumer<GeneratedPaymentEntity, PaymentException> payment) {
        this.provider.setParams(params);
        try {
            payment.accept(this.provider.createTransaction(), null);
        } catch (PaymentException e) {
            payment.accept(null, e);
        }
    }
    public String generateITN(Object object) {
        try {
            return this.provider.generateITNHash(object);
        } catch (PaymentException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean isIpAllowed(String ip) {
        return this.provider.getAvailableIps().contains(ip);
    }
    public Status getTransaction(String id) {
        return this.provider.transactionInfo(id);
    }
}
