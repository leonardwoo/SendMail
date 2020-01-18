/*
 * Copyright 2017 author and authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.l6d;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.cms.AttributeTable;
import org.bouncycastle.asn1.cms.SignerInfo;
import org.bouncycastle.asn1.smime.SMIMECapabilitiesAttribute;
import org.bouncycastle.asn1.smime.SMIMECapability;
import org.bouncycastle.asn1.smime.SMIMECapabilityVector;
import org.bouncycastle.asn1.smime.SMIMEEncryptionKeyPreferenceAttribute;
import org.bouncycastle.cms.SignerInformation;
import org.bouncycastle.cms.SignerInformationStore;
import org.bouncycastle.cms.jcajce.JcaSimpleSignerInfoGeneratorBuilder;
import org.bouncycastle.mail.smime.SMIMEException;
import org.bouncycastle.mail.smime.SMIMESignedGenerator;
import org.bouncycastle.mail.smime.SMIMEUtil;
import org.bouncycastle.operator.OperatorCreationException;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.UnsupportedEncodingException;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.Properties;

public class MailUtil {
    private static Logger logger = LogManager.getLogger("MailUtil");

    public static Session getMailSession(MailUserEntity sender) {
        Properties props = new Properties();

        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", sender.getTls());
        props.put("mail.smtp.host", sender.getStmpAddress());
        props.put("mail.smtp.port", sender.getPort().toString());

        return Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(sender.getUsername(), sender.getPassword());
                    }
                });
    }

    public static Message getMailMessage(Session mailSession, MailUserEntity sender, MailEntity mail) throws MessagingException, UnsupportedEncodingException {
        Message message = new MimeMessage(mailSession);
        message.setFrom(new InternetAddress(sender.getMailAddress(), sender.getDisplayName()));

        if (!mail.getToList().isEmpty()) {
            for (String to : mail.getToList()) {
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            }
        }
        if (!mail.getCcList().isEmpty()) {
            for (String cc : mail.getCcList()) {
                message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(cc));
            }
        }
        if (!mail.getBccList().isEmpty()) {
            for (String bcc : mail.getBccList()) {
                message.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(bcc));
            }
        }

        message.setSubject(mail.getSubject());
        message.setText(mail.getText());

        return message;
    }

//    public static MimeMultipart encryptMessage(MimeBodyPart dataPart, X509Certificate cert)
//            throws CertificateParsingException, SMIMEException, OperatorCreationException {
//        SMIMECapabilityVector vector = new SMIMECapabilityVector();
//        vector.addCapability(SMIMECapability.aES256_CBC);
//        vector.addCapability(SMIMECapability.dES_EDE3_CBC);
//        vector.addCapability(SMIMECapability.rC2_CBC, 128);
//
//
//        ASN1EncodableVector signedAttrs = new ASN1EncodableVector();
//        signedAttrs.add(new SMIMECapabilitiesAttribute(vector));
//        signedAttrs.add(new SMIMEEncryptionKeyPreferenceAttribute(SMIMEUtil.createIssuerAndSerialNumberFor(cert)));
//
//        SMIMESignedGenerator gen = new SMIMESignedGenerator();
//        gen.addSigners(new JcaSimpleSignerInfoGeneratorBuilder().setProvider("BC").build("SHA1withRSA", signKP.getPrivate(), cert));
//
//        return gen.generate(dataPart);
//    }


    public static Boolean sendMail(MailUserEntity sender, MailEntity mail) {

        try {
            Transport.send(getMailMessage(getMailSession(sender),sender,mail));
            return true;
        } catch (MessagingException | UnsupportedEncodingException ex) {
            logger.error("Send fail.", ex);
        }
        return false;
    }

}
