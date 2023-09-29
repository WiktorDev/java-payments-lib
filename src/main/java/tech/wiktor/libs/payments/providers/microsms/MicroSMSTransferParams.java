package tech.wiktor.libs.payments.providers.microsms;

import tech.wiktor.libs.payments.providers.Params;
import tech.wiktor.libs.payments.providers.paybylink.transfer.PayByLinkTransferParams;

import java.util.function.Consumer;

public class MicroSMSTransferParams extends Params {
    public double amount;
    public String control;
    public String returnUrlc;
    public String returnUrl;
    public String description;
    private int shopid;
    public String signature;

    public void setServiceId(int serviceId) {
        this.shopid = serviceId;
    }

    public static MicroSMSTransferParams create(Consumer<MicroSMSTransferParams> consumer) {
        MicroSMSTransferParams params = new MicroSMSTransferParams();
        consumer.accept(params);
        return params;
    }
}
