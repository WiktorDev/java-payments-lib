package tech.wiktor.libs.payments.providers.microsms;

import tech.wiktor.libs.payments.entities.GeneratedPaymentEntity;
import tech.wiktor.libs.payments.entities.HttpResponseEntity;
import tech.wiktor.libs.payments.exceptions.NotImplementedException;
import tech.wiktor.libs.payments.exceptions.PaymentException;
import tech.wiktor.libs.payments.providers.Params;
import tech.wiktor.libs.payments.providers.Provider;
import tech.wiktor.libs.payments.providers.Status;
import tech.wiktor.libs.payments.utils.HashBuilder;

import java.util.Arrays;
import java.util.List;

public class MicroSMSTransferProvider extends Provider {
    private final int serviceId;
    private final String hash;
    private MicroSMSTransferParams params;

    public MicroSMSTransferProvider(int serviceId, String hash) {
        this.serviceId = serviceId;
        this.hash = hash;
    }

    @Override
    public GeneratedPaymentEntity createTransaction() throws PaymentException {
        this.params.signature = HashBuilder.sha256(this.serviceId+this.hash+this.params.amount);
        return new GeneratedPaymentEntity("https://microsms.pl/api/bankTransfer/?"+this.params.queryParams(), null);
    }

    @Override
    public String generateITNHash(Object object) throws PaymentException {
        throw new NotImplementedException("MicroSMSTransferProvider#generateITNHash");
    }

    @Override
    public List<String> getAvailableIps() {
        HttpResponseEntity httpResponse = this.get("https://microsms.pl/psc/ips/", builder -> {});
        if (httpResponse == null) return null;
        String body = (String) httpResponse.object();
        return Arrays.stream(body.split(",")).toList();
    }

    @Override
    public Status transactionInfo(String id) {
        throw new NotImplementedException("MicroSMSTransferProvider#transactionInfo");
    }

    @Override
    public void setParams(Params params) {
        this.params = (MicroSMSTransferParams) params;
        this.params.setServiceId(this.serviceId);
    }
}
