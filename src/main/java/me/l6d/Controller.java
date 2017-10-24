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
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.ArrayList;

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
    private CheckBox is_show;

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
    private MailUserEntity mailUser;

    @FXML
    public void initialize() {
        Gson gson = new Gson();
        mailUser = gson.fromJson(
                new BufferedReader(
                        new InputStreamReader(
                                FileUnit.loadFileInputStream("config.json"))),
                MailUserEntity.class);
        logger.info(mailUser.toString());
        if(mailUser == null) {
            mailUser.init();
        }

    }

    @FXML
    public void loadMainTab(){
        ArrayList<String> combobox = new ArrayList<>();
        combobox.set(0, mailUser.getDisplayName() + "<" + mailUser.getMailAddress() + ">");
        sender_combo.setItems(FXCollections.observableArrayList(combobox));
    }

    @FXML
    public void loadSettingTab(){
        mail_addr.setText(mailUser.getMailAddress());
        dis_name.setText(mailUser.getDisplayName());
        smtp_addr.setText(mailUser.getStmpAddress());
        pwd.setText(mailUser.getPassword());
        usr.setText(mailUser.getUsername());
        isTls.setSelected(mailUser.getTls());

        is_show.setVisible(false);
    }

    @FXML
    public void saveConfig(ActionEvent event){
        mailUser.setMailAddress(mail_addr.getText().trim());
        mailUser.setDisplayName(dis_name.getText().trim());
        mailUser.setStmpAddress(smtp_addr.getText().trim());
        mailUser.setPassword(pwd.getPromptText());
        mailUser.setUsername(usr.getText());
        mailUser.setTls(isTls.isSelected());
    }

    @FXML
    public void refreshItem(ActionEvent event){
        ArrayList<String> items = new ArrayList<>();
        //refresh combo box items
        sender_combo.setItems(FXCollections.observableArrayList(items));
    }

    @FXML
    public void showPwd(ActionEvent event){
        //
    }

    @FXML
    public void sendMail(ActionEvent event){
        //
    }

}
