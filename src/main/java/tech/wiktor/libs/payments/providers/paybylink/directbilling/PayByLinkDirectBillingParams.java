package tech.wiktor.libs.payments.providers.paybylink.directbilling;

import tech.wiktor.libs.payments.providers.Params;

import java.util.function.Consumer;

public class PayByLinkDirectBillingParams extends Params {
    public double price;
    public String description;
    public String control;
    public String signature;

    public static PayByLinkDirectBillingParams create(Consumer<PayByLinkDirectBillingParams> params) {
        PayByLinkDirectBillingParams pblParams = new PayByLinkDirectBillingParams();
        params.accept(pblParams);
        return pblParams;
    }
}
