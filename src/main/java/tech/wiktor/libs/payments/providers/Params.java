package tech.wiktor.libs.payments.providers;

import com.google.gson.Gson;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import tech.wiktor.libs.payments.utils.StringUtils;

import static tech.wiktor.libs.payments.utils.StringUtils.toQueryParams;

public abstract class Params {
    public String getJsonString() {
        System.out.println(toQueryParams(this));
        return new Gson().toJson(this);
    }
    public String getValues() {
        return StringUtils.getValues(this);
    }
    public RequestBody requestBody() {
        return RequestBody.create(this.getJsonString(), MediaType.parse("application/json"));
    }
    public RequestBody requestUrlencoded() {
        return RequestBody.create(this.queryParams(), MediaType.parse("application/x-www-form-urlencoded"));
    }
    public String queryParams() {
        return toQueryParams(this);
    }
}
