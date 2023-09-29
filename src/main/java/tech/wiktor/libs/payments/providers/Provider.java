package tech.wiktor.libs.payments.providers;

import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import tech.wiktor.libs.payments.entities.GeneratedPaymentEntity;
import tech.wiktor.libs.payments.entities.HttpResponseEntity;
import tech.wiktor.libs.payments.exceptions.PaymentException;

import java.util.List;
import java.util.function.Consumer;

public abstract class Provider {
    private final OkHttpClient httpClient = new OkHttpClient();
    public abstract GeneratedPaymentEntity createTransaction() throws PaymentException;
    public abstract String generateITNHash(Object object) throws PaymentException;
    public abstract List<String> getAvailableIps();
    public abstract Status transactionInfo(String id);
    public abstract void setParams(Params params);

    @SuppressWarnings("unchecked")
    public HttpResponseEntity post(String url, RequestBody requestBody, Consumer<Request.Builder>... builder) {
        var baseBuilder = new Request.Builder().url(url).post(requestBody);
        if (builder.length == 1) {
            builder[0].accept(baseBuilder);
        }
        Request request = baseBuilder.build();

        try (Response response = this.httpClient.newCall(request).execute()) {
            return new HttpResponseEntity(response, response.body().string());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public HttpResponseEntity get(String url, Consumer<Request.Builder> builder) {
        var baseBuilder = new Request.Builder().url(url).get();
        builder.accept(baseBuilder);
        Request request = baseBuilder.build();

        try (Response response = this.httpClient.newCall(request).execute()) {
            return new HttpResponseEntity(response, response.body().string());
        } catch (Exception e) {
            return null;
        }
    }
    public Gson gson() {
        return new Gson();
    }
}
