package tech.wiktor.libs.payments.providers.paybylink.transfer;

import kotlin.NotImplementedError;
import okhttp3.Response;
import tech.wiktor.libs.payments.entities.GeneratedPaymentEntity;
import tech.wiktor.libs.payments.exceptions.NotImplementedException;
import tech.wiktor.libs.payments.exceptions.PaymentException;
import tech.wiktor.libs.payments.providers.Params;
import tech.wiktor.libs.payments.providers.Provider;
import tech.wiktor.libs.payments.providers.Status;
import tech.wiktor.libs.payments.providers.paybylink.transfer.PayByLinkTransferParams;
import tech.wiktor.libs.payments.utils.HashBuilder;

import java.util.List;

import static tech.wiktor.libs.payments.utils.StringUtils.getValues;

public class PayByLinkTransferProvider extends Provider {
    private final String secretHash;
    private final int shopId;
    private PayByLinkTransferParams params;

    public PayByLinkTransferProvider(String secretHash, int shopId) {
        this.secretHash = secretHash;
        this.shopId = shopId;
    }

    @Override
    public GeneratedPaymentEntity createTransaction() throws PaymentException {
        this.params.signature = HashBuilder.sha256(String.format("%s|%s", this.secretHash, this.params.getValues()));
        var httpResponse = this.post("https://secure.pbl.pl/api/v1/transfer/generate", this.params.requestBody(), builder -> {});
        if (httpResponse == null) throw new PaymentException("Can't send http request!");
        Response response = httpResponse.response();

        if (response.code() != 200) {
            ErrorEntity error = this.gson().fromJson((String) httpResponse.object(), ErrorEntity.class);
            throw new PaymentException(error.error());
        }

        PaymentCreated paymentCreated = this.gson().fromJson((String) httpResponse.object(), PaymentCreated.class);
        return GeneratedPaymentEntity.create(paymentCreated.url, paymentCreated.transactionId);
    }

    @Override
    public String generateITNHash(Object object) throws PaymentException {
        if (!(object instanceof ITNPayload)) throw new PaymentException("Invalid payload type!");
        return HashBuilder.sha256(this.secretHash + "|" + getValues(object));
    }

    @Override
    public List<String> getAvailableIps() {
        throw new NotImplementedException("PayByLinkTransferProvider#getAvailableIps");
    }

    @Override
    public Status transactionInfo(String id) {
        throw new NotImplementedException("PayByLinkTransferProvider#transactionInfo");
    }

    @Override
    public void setParams(Params params) {
        this.params = (PayByLinkTransferParams) params;
        this.params.setShopId(this.shopId);
    }

    record ErrorEntity(int errorCode, String error) { }
    record PaymentCreated(String url, String transactionId) { }
    record ITNPayload(String transactionId, String control, String email, double amountPaid, int notificationAttempt, String paymentType, int apiVersion) { }
}
