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

import java.util.ArrayList;

public class MailEntity {
    private ArrayList<String> toList;
    private ArrayList<String> ccList;
    private ArrayList<String> bccList;
    private String subject;
    private String text;

    public MailEntity() {
        toList = new ArrayList<>();
        ccList = new ArrayList<>();
        bccList = new ArrayList<>();
        subject = "";
        text = "";
    }

    public ArrayList<String> getToList() {
        return toList;
    }

    public void setToList(ArrayList<String> toList) {
        this.toList.addAll(toList);
    }

    public ArrayList<String> getCcList() {
        return ccList;
    }

    public void setCcList(ArrayList<String> ccList) {
        this.ccList.addAll(ccList);
    }

    public ArrayList<String> getBccList() {
        return bccList;
    }

    public void setBccList(ArrayList<String> bccList) {
        this.bccList.addAll(bccList);
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
