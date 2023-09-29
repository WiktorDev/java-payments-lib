package tech.wiktor.libs.payments.entities;

public record GeneratedPaymentEntity(String url, String id) {
    public static GeneratedPaymentEntity create(String url, String id) {
        return new GeneratedPaymentEntity(url, id);
    }
}
