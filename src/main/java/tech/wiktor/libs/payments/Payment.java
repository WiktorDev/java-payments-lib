package tech.wiktor.libs.payments;

import tech.wiktor.libs.payments.entities.ErrorEntity;
import tech.wiktor.libs.payments.entities.GeneratedPaymentEntity;
import tech.wiktor.libs.payments.exceptions.PaymentException;
import tech.wiktor.libs.payments.providers.Params;
import tech.wiktor.libs.payments.providers.Provider;

import java.util.function.Consumer;

public class Payment {
    private final Provider provider;
    public Payment(Provider provider) {
        this.provider = provider;
    }

    public void generate(Params params, Consumer<GeneratedPaymentEntity> payment) {
        this.provider.setParams(params);
        try {
            payment.accept(this.provider.createTransaction());
        } catch (PaymentException e) {
            payment.accept(new GeneratedPaymentEntity(null, null, new ErrorEntity(e.getMessage())));
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
}
