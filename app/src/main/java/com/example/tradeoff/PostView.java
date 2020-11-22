package com.example.tradeoff;

import android.widget.ImageView;

public class PostView {

    String username ;
    String region;
    String phone;
    String email;
    String give;
    String take;
    String textFree;
ImageView img;
    public PostView(){

    }
    public PostView(String username,String region,String phone,String email,String give,String take,String textFree){
this.username=username;
this.region=region;
this.phone=phone;
this.email=email;
this.give=give;
this.take=take;
this.textFree=textFree;
    }
    public String getEmail() {
        return email;
    }

    public String getGive() {
        return give;
    }

    public String getTake() {
        return take;
    }

    public String getPhone() {
        return phone;
    }

    public String getRegion() {
        return region;
    }

    public String getUsername() {
        return username;
    }

    public String getTextFree() {
        return textFree;
    }

    public void setTake(String take) {
        this.take = take;
    }

    public void setGive(String give) {
        this.give = give;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setTextFree(String textFree) {
        this.textFree = textFree;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
