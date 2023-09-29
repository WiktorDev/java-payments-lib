package tech.wiktor.libs.payments.example;

import tech.wiktor.libs.payments.Payment;
import tech.wiktor.libs.payments.providers.microsms.MicroSMSTransferParams;
import tech.wiktor.libs.payments.providers.microsms.MicroSMSTransferProvider;
import tech.wiktor.libs.payments.providers.paybylink.directbilling.PayByLinkDirectBillingParams;
import tech.wiktor.libs.payments.providers.paybylink.directbilling.PayByLinkDirectBillingProvider;
import tech.wiktor.libs.payments.providers.paybylink.paysafecard.PayByLinkPaySafeCardParams;
import tech.wiktor.libs.payments.providers.paybylink.paysafecard.PayByLinkPaySafeCardProvider;
import tech.wiktor.libs.payments.providers.paybylink.transfer.PayByLinkTransferParams;
import tech.wiktor.libs.payments.providers.paybylink.transfer.PayByLinkTransferProvider;

public class Example {
    public static void main(String[] args) {
        Payment payment = new Payment(new MicroSMSTransferProvider(1, "reqw"));
        payment.generate(MicroSMSTransferParams.create(params -> {

        }), (generatedPaymentEntity, e) -> {
            System.out.println(generatedPaymentEntity.url());
        });
//        Payment payment = new Payment(new PayByLinkTransferProvider("Ln2Km4Cd0Oc2Am8Rp8Eb2Lc3Ib0Js0O@", 1614));
//
//        payment.generate(PayByLinkTransferParams.create(params -> {
//            params.price = 10.99;
//            params.control = "e";
//            params.email = "w@w.pl";
//        }), (response, error) -> {
//            if (error != null) {
//                System.out.println(error.getMessage());
//                return;
//            }
//            System.out.println(response.url());
//        });
//
//        payment.getTransaction("e");
//
//        System.out.println(payment.generateITN(null));

//        Payment payment = new Payment(new PayByLinkPaySafeCardProvider(4448, 519, "Jy9Ws1Mn5Gn2Ad9Ym2Ky0Rt4Mw8Dy0Oh"));
//        payment.generate(PayByLinkPaySafeCardParams.create(params -> {
//            params.amount = 10.23;
//            params.return_ok = "https://google.com";
//            params.return_fail = "https://google.com";
//            params.url = "https://google.com";
//            params.control = "e";
//        }), response -> {
//            if (response.error() != null) {
//                System.out.println(response.error().message());
//                return;
//            }
//            System.out.println(response.url());
//        });
//        System.out.println(payment.isIpAllowed("193.70.125.103"));

//        Payment payment = new Payment(new PayByLinkDirectBillingProvider("paybylink.pl", "jhgHgdhjIuytr67&%5yhd@!", "mNJBHVGFTYU*(87656yud23fg"));
//        payment.generate(PayByLinkDirectBillingParams.create(params -> {
//            params.price = 10;
//            params.control = "1";
//            params.description = "e";
//        }), response -> {
//            if (response.error() != null) {
//                System.out.println(response.error().message());
//                return;
//            }
//            System.out.println(response.url());
//            System.out.println(response.id());
//        });
    }
}
