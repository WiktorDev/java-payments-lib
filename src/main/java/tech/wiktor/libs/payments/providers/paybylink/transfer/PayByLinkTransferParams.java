package tech.wiktor.libs.payments.providers.paybylink.transfer;

import tech.wiktor.libs.payments.providers.Params;

import java.util.function.Consumer;

public class PayByLinkTransferParams extends Params {
    private int shopId;
    public double price;
    public String control;
    public String description;
    public String email;
    public String notifyURL;
    public String returnUrlSuccess;
    public boolean returnUrlSuccessTidPass;
    public boolean hideReceiver;
    public String customFinishNote;
    public String signature;

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public static PayByLinkTransferParams create(Consumer<PayByLinkTransferParams> params) {
        PayByLinkTransferParams payByLinkTransferParams = new PayByLinkTransferParams(1);
        params.accept(payByLinkTransferParams);
        return payByLinkTransferParams ;
    }

    public PayByLinkTransferParams(int shopId) {
        this.shopId = shopId;
    }
}
