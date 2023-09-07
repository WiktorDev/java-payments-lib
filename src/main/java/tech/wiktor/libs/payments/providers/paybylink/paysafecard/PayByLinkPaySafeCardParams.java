package tech.wiktor.libs.payments.providers.paybylink.paysafecard;

import tech.wiktor.libs.payments.providers.Params;
import java.util.function.Consumer;

public class PayByLinkPaySafeCardParams extends Params {
    private int userid;
    private int shopid;
    public double amount;
    public String return_ok;
    public String return_fail;
    public String url;
    public String control;
    public String hash;
    public boolean get_pid;
    public String description;

    public void setUserid(int userId) {
        this.userid = userId;
    }
    public void setShopid(int shopId) {
        this.shopid = shopId;
    }

    public static PayByLinkPaySafeCardParams create(Consumer<PayByLinkPaySafeCardParams> params) {
        PayByLinkPaySafeCardParams payByLinkPaySafeCardParams = new PayByLinkPaySafeCardParams();
        params.accept(payByLinkPaySafeCardParams);
        return payByLinkPaySafeCardParams;
    }
}
