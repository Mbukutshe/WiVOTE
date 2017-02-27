package com.example.mczondi.wivote;

import java.util.Date;

/**
 * Created by Jack on 5/18/2016.
 */
public class Message {
    private String mText;
    private String mSender;

    private String user;
    private String cell;
    private String password;

    private Date mDate;

    public String getmReceiver() {
        return mReceiver;
    }

    public void setmReceiver(String mReceiver) {
        this.mReceiver = mReceiver;
    }

    private String mReceiver;
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getCell() {
        return cell;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getmText() {
        return mText;
    }

    public void setmText(String mText) {
        this.mText = mText;
    }

    public String getmSender() {
        return mSender;
    }

    public void setmSender(String mSender) {
        this.mSender = mSender;
    }

    public Date getmDate() {
        return mDate;
    }

    public void setmDate(Date mDate) {
        this.mDate = mDate;
    }
}
