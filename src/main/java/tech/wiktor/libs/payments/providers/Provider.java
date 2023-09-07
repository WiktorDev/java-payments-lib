package tech.wiktor.libs.payments.providers;

import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import tech.wiktor.libs.payments.entities.GeneratedPaymentEntity;
import tech.wiktor.libs.payments.entities.HttpResponseEntity;
import tech.wiktor.libs.payments.exceptions.PaymentException;

public abstract class Provider {
    private final OkHttpClient httpClient = new OkHttpClient();
    public abstract GeneratedPaymentEntity createTransaction() throws PaymentException;
    public abstract String generateITNHash(Object object) throws PaymentException;
    public abstract void setParams(Params params);

    public HttpResponseEntity post(String url, RequestBody requestBody) {
        Request request = new Request.Builder().url(url).post(requestBody).build();

        try (Response response = this.httpClient.newCall(request).execute()) {
            return new HttpResponseEntity(response, response.body().string());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public Gson gson() {
        return new Gson();
    }
}
