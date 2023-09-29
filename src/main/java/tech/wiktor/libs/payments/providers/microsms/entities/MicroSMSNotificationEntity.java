package tech.wiktor.libs.payments.providers.microsms.entities;

public class MicroSMSNotificationEntity {
    private String status;
    private String userid;
    private String email;
    private String orderID;
    private String amountUni;
    private String amountPay;
    private String description;
    private String control;
    private String test;

    public String getStatus() {
        return status;
    }

    public String getUserid() {
        return userid;
    }

    public String getEmail() {
        return email;
    }

    public String getOrderID() {
        return orderID;
    }

    public String getAmountUni() {
        return amountUni;
    }

    public String getAmountPay() {
        return amountPay;
    }

    public String getDescription() {
        return description;
    }

    public String getControl() {
        return control;
    }

    public String getTest() {
        return test;
    }
}
