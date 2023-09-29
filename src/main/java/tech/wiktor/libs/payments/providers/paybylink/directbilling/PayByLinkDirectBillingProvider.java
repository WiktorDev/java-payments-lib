package tech.wiktor.libs.payments.providers.paybylink.directbilling;

import okhttp3.Credentials;
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
import java.util.Objects;

public class PayByLinkDirectBillingProvider extends Provider {
    private PayByLinkDirectBillingParams params;
    private final String hash;
    private final String credentials;

    public PayByLinkDirectBillingProvider(String login, String password, String hash) {
        this.hash = hash;
        this.credentials = Credentials.basic(login, password);
    }

    @Override
    public GeneratedPaymentEntity createTransaction() throws PaymentException {
        this.params.price = this.params.price * 100;
        this.params.signature = HashBuilder.sha256(
                String.format("%s|%s|%s|%s",
                (int)this.params.price, this.params.description, this.params.control, this.hash)
        );

        HttpResponseEntity httpResponse = this.post("https://paybylink.pl/direct-biling/", this.params.requestBody(), builder -> {
            builder.header("Authorization", this.credentials);
        });

        if (httpResponse == null) throw new PaymentException("Can't send http request!");
        DirectBillingResponse db = this.gson().fromJson((String) httpResponse.object(), DirectBillingResponse.class);
        if (!Objects.equals(db.status, "success")) throw new PaymentException(String.format("%s: %s", db.code, db.message));
        return GeneratedPaymentEntity.create(
                db.clientURL,
                db.clientURL.replace("https://paybylink.pl/direct-biling/", "").replace("/", "")
        );
    }

    @Override
    public String generateITNHash(Object object) throws PaymentException {
        throw new NotImplementedException("PayByLinkDirectBillingProvider#transactionInfo");
    }

    @Override
    public List<String> getAvailableIps() {
        HttpResponseEntity httpResponse = this.get("https://paybylink.pl/psc/ips/", builder -> {});
        if (httpResponse == null) return null;
        String body = (String) httpResponse.object();
        return Arrays.stream(body.split(",")).toList();
    }

    @Override
    public TestStatus transactionInfo(String id) {
        throw new NotImplementedException("PayByLinkDirectBillingProvider#transactionInfo");
    }

    @Override
    public void setParams(Params params) {
        this.params = (PayByLinkDirectBillingParams) params;
    }

    record DirectBillingResponse(String status, int code, String message, String clientURL) { }

    public static class TestStatus implements Status {
        public String te;
    }
}
