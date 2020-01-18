/*
 * Copyright 2017 author and authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.l6d;

import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;


public class Controller {

    // Default Tab
    @FXML
    private Tab mainTab;

    @FXML
    private ComboBox<String> sender_combo;

    @FXML
    private TextField to_addr;

    @FXML
    private TextField cc_addr;

    @FXML
    private TextField bcc_addr;

    @FXML
    private TextField subject_txt;

    @FXML
    private TextArea mail_text;

    @FXML
    private Button send_btn;

    // Setting Tab
    @FXML
    private Tab settingTab;

    @FXML
    private TextField mail_addr;

    @FXML
    private TextField dis_name;

    @FXML
    private TextField smtp_addr;

    @FXML
    private PasswordField pwd;

    @FXML
    private TextField port;

    @FXML
    private TextField usr;

    @FXML
    private CheckBox isTls;

    @FXML
    private Button save_btn;

    public Controller(){}
    private Logger logger = LogManager.getLogger(this.getClass().getName());
    private MailUserEntity mailUser = MailUserEntity.getInstance();
    private final static String DELIMITER = ";";

    @FXML
    public void initialize() {
        mailUser = new Gson().fromJson(FileUtil.loadFileReader("config.json"), MailUserEntity.class);
        if(mailUser == null) {
            mailUser = MailUserEntity.getInstance().init();
        }
        logger.info(mailUser.toString());
        loadMainTab();
    }

    @FXML
    public void loadMainTab(){
        if(!mailUser.isEmpty()) {
            sender_combo.setValue(mailUser.getDisplayName() + "<" + mailUser.getMailAddress() + ">");
        }
    }

    @FXML
    public void loadSettingTab(){
        mail_addr.setText(mailUser.getMailAddress());
        dis_name.setText(mailUser.getDisplayName());
        smtp_addr.setText(mailUser.getStmpAddress());
        port.setText(mailUser.getPort().toString());
        pwd.setText(mailUser.getPassword());
        usr.setText(mailUser.getUsername());
        isTls.setSelected(mailUser.getTls());
    }

    @FXML
    public void saveConfig(ActionEvent event){
        mailUser.setMailAddress(mail_addr.getText().trim());
        mailUser.setDisplayName(dis_name.getText().trim());
        mailUser.setStmpAddress(smtp_addr.getText().trim());
        mailUser.setPort(Integer.parseInt(port.getText()));
        mailUser.setPassword(pwd.getCharacters().toString());
        mailUser.setUsername(usr.getText());
        mailUser.setTls(isTls.isSelected());

        FileUtil.saveFileWrite("config.json", new Gson().toJson(mailUser));
    }

    @FXML
    public void refreshItem(ActionEvent event){
//        ArrayList<String> items = new ArrayList<>();
//        sender_combo.setItems(FXCollections.observableArrayList(items));
    }

    @FXML
    public void sendMail(ActionEvent event){
        disableAll();

        //new Thread(new sendMailTask()).start();
        Platform.runLater(new sendMailTask());

        enableAll();
    }

    private void disableAll() {
        settingTab.setDisable(true);
        to_addr.setDisable(true);
        cc_addr.setDisable(true);
        bcc_addr.setDisable(true);
        subject_txt.setDisable(true);
        mail_text.setDisable(true);
        send_btn.setDisable(true);
    }

    private void enableAll() {
        settingTab.setDisable(false);
        to_addr.setDisable(false);
        cc_addr.setDisable(false);
        bcc_addr.setDisable(false);
        subject_txt.setDisable(false);
        mail_text.setDisable(false);
        send_btn.setDisable(false);
    }

    class sendMailTask extends Task {
        @Override
        protected Boolean call() throws Exception {
            MailEntity mail = new MailEntity();

            String toAddrs = to_addr.getText();
            if(!toAddrs.isEmpty()) {
                ArrayList<String> toList = new ArrayList<>();
                if(toAddrs.contains(DELIMITER)) {
                    toList.addAll(Arrays.asList(toAddrs.split(DELIMITER)));
                } else {
                    toList.add(toAddrs);
                }
                mail.setToList(toList);
            }

            String ccAddrs = cc_addr.getText();
            if(!ccAddrs.isEmpty()) {
                ArrayList<String> ccList = new ArrayList<>();
                if(ccAddrs.contains(DELIMITER)) {
                    ccList.addAll(Arrays.asList(ccAddrs.split(DELIMITER)));
                } else {
                    ccList.add(ccAddrs);
                }
                mail.setCcList(ccList);
            }

            String bccAddrs = bcc_addr.getText();
            if(!bccAddrs.isEmpty()) {
                ArrayList<String> bccList = new ArrayList<>();
                if(bccAddrs.contains(DELIMITER)) {
                    bccList.addAll(Arrays.asList(bccAddrs.split(DELIMITER)));
                } else {
                    bccList.add(bccAddrs);
                }
                mail.setCcList(bccList);
            }

            mail.setSubject(subject_txt.getText());
            mail.setText(mail_text.getText());

            return MailUtil.sendMail(mailUser, mail);
        }
    }
}
