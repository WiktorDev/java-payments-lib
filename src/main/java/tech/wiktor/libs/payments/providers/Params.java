package tech.wiktor.libs.payments.providers;

import com.google.gson.Gson;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import tech.wiktor.libs.payments.utils.StringUtils;

public abstract class Params {
    public String getJsonString() {
        return new Gson().toJson(this);
    }
    public String getValues() {
        return StringUtils.getValues(this);
    }
    public RequestBody requestBody() {
        return RequestBody.create(this.getJsonString(), MediaType.parse("application/json"));
    }
}
