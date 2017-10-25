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

public class MailUserEntity {
    private static MailUserEntity ourInstance = new MailUserEntity();
    public static MailUserEntity getInstance() {
        return ourInstance;
    }

    private MailUserEntity() {
    }

    private String mailAddress;
    private String displayName;
    private String stmpAddress;
    private Integer port;
    private String password;
    private String username;
    private Boolean isTls;

    public MailUserEntity init(){
        mailAddress = "";
        displayName = "";
        stmpAddress = "";
        port = 0;
        password = "";
        username = "";
        isTls = false;
        return this;
    }

    @Override
    public String toString() {
        return "{" +
                " 'mailAddress':'" + mailAddress + '\'' +
                ", 'displayName':'" + displayName + '\'' +
                ", 'stmpAddress':'" + stmpAddress + '\'' +
                ", 'port':" + port +
                ", 'password':'" + password + '\'' +
                ", 'username':'" + username + '\'' +
                ", 'isTls':" + isTls +
                '}';
    }

    public Boolean isEmpty(){
        return !StringUtil.hasLength(mailAddress) && !StringUtil.hasLength(displayName) && !StringUtil.hasLength(stmpAddress)
                && StringUtil.isEmpty(port) && !StringUtil.hasLength(password) && !StringUtil.hasLength(username)
                && StringUtil.isEmpty(isTls);
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getStmpAddress() {
        return stmpAddress;
    }

    public void setStmpAddress(String stmpAddress) {
        this.stmpAddress = stmpAddress;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getTls() {
        return isTls;
    }

    public void setTls(Boolean tls) {
        isTls = tls;
    }
}
