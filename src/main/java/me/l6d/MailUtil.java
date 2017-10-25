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

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class MailUtil {
    private static Logger logger = LogManager.getLogger("MailUtil");

    public static Boolean sendMail(MailUserEntity sender, MailEntity mail){
        Properties props = new Properties();

        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", sender.getTls());
        props.put("mail.smtp.host", sender.getStmpAddress());
        props.put("mail.smtp.port", sender.getPort().toString());

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(sender.getUsername(), sender.getPassword());
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(sender.getMailAddress(), sender.getDisplayName()));

            if(!mail.getToList().isEmpty()) {
                for (String to : mail.getToList()) {
                    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
                }
            }
            if(!mail.getCcList().isEmpty()) {
                for (String cc: mail.getCcList()) {
                    message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(cc));
                }
            }
            if(!mail.getBccList().isEmpty()) {
                for (String bcc: mail.getBccList()) {
                    message.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(bcc));
                }
            }

            message.setSubject(mail.getSubject());
            message.setText(mail.getText());

            Transport.send(message);
            return true;
        } catch (MessagingException e) {
            logger.error("Send fail.", e);
        } catch (UnsupportedEncodingException e) {
            logger.error(e);
        }
        return false;
    }
}
