package tech.wiktor.libs.payments.entities;

import okhttp3.Response;

public record HttpResponseEntity(Response response, Object object) { }
