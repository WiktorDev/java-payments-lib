package tech.wiktor.libs.payments.providers.paybylink.paysafecard;

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

import static tech.wiktor.libs.payments.utils.StringUtils.isValidJSON;

public class PayByLinkPaySafeCardProvider extends Provider {
    private PayByLinkPaySafeCardParams params;
    private final String pin;
    private final int userId;
    private final int shopId;

    public PayByLinkPaySafeCardProvider(int userId, int shopId, String pin) {
        this.pin = pin;
        this.userId = userId;
        this.shopId = shopId;
    }

    @Override
    public GeneratedPaymentEntity createTransaction() throws PaymentException {
        this.params.hash = HashBuilder.sha256(String.format("%s%s%s", this.userId, this.pin, this.params.amount));
        this.params.get_pid = true;

        HttpResponseEntity httpResponse = this.post("https://paybylink.pl/api/psc/", this.params.requestUrlencoded());
        String body = (String) httpResponse.object();
        if (!isValidJSON(body))
            throw new PaymentException("[PayByLink] " + body);

        PaymentCreated paymentCreated = this.gson().fromJson((String) httpResponse.object(), PaymentCreated.class);
        return GeneratedPaymentEntity.create(paymentCreated.url, paymentCreated.pid);
    }

    @Override
    public String generateITNHash(Object object) throws PaymentException {
        throw new NotImplementedException("PayByLinkPaySafeCardProvider#generateITNHash");
    }

    @Override
    public List<String> getAvailableIps() {
        HttpResponseEntity httpResponse = this.get("https://paybylink.pl/psc/ips/", builder -> {});
        if (httpResponse == null) return null;
        String body = (String) httpResponse.object();
        return Arrays.stream(body.split(",")).toList();
    }

    @Override
    public Status transactionInfo(String id) {
        throw new NotImplementedException("PayByLinkPaySafeCardProvider#transactionInfo");
    }

    @Override
    public void setParams(Params params) {
        this.params = (PayByLinkPaySafeCardParams) params;
        this.params.setUserid(this.userId);
        this.params.setShopid(this.shopId);
    }

    record PaymentCreated(String url, String pid) { }
    record ITNPayload(
            String status, String test, String userid, String shopid, String pid, String mtid, String amount, String control,
            String description, String hash, String hashsha256
    ) { }
}
