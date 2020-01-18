/**
 * @author Leonard Woo
 */
module me.l6d.sendMail {
    requires javax.mail.api;

    requires javafx.controls;
    requires javafx.fxml;

    opens me.l6d to javafx.fxml;
    exports me.l6d;

    requires org.apache.logging.log4j;

    requires gson;

    requires org.bouncycastle.mail;
    requires org.bouncycastle.pg;
    requires org.bouncycastle.pkix;
    requires org.bouncycastle.provider;
}