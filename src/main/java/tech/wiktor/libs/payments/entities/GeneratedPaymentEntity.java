package tech.wiktor.libs.payments.entities;

public record GeneratedPaymentEntity(String url, String id, ErrorEntity error) {
    public static GeneratedPaymentEntity create(String url, String id) {
        return new GeneratedPaymentEntity(url, id, null);
    }
}
