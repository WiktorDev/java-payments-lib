package tech.wiktor.libs.payments.example;

import tech.wiktor.libs.payments.Payment;
import tech.wiktor.libs.payments.providers.paybylinkTransfer.PayByLinkTransferParams;
import tech.wiktor.libs.payments.providers.paybylinkTransfer.PayByLinkTransferProvider;

public class Example {
    public static void main(String[] args) {
        Payment payment = new Payment(new PayByLinkTransferProvider("Ln2Km4Cd0Oc2Am8Rp8Eb2Lc3Ib0Js0O@", 1614));

        payment.generate(PayByLinkTransferParams.create(params -> {
            params.price = 10.99;
            params.control = "e";
            params.email = "w@w.pl";
        }), response -> {
            if (response.error() != null) {
                System.out.println(response.error().message());
                return;
            }
            System.out.println(response.url());
        });

        System.out.println(payment.generateITN(null));
    }
}
