package com.example.tradeoff;

public class Post {
    private String Give;
    private String Take;
    private String freeText;
    private String mail;



    public Post() {
        this.mail="";
        this.freeText="";
        this.Give="";
        this.Take="";
    }




    public void setFreeText(String newFreeText) {
        freeText = newFreeText;
    }

    public void setGive(String give) {
        Give = give;
    }

    public void setTake(String take) {
        Take = take;
    }

    public String getGive() {
        return Give;
    }
    public String getTake() {
        return Take;
    }
    public String getfreeText() {
        return freeText;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Post(String GiveAsk, String TakeAsk, String moreInfoText, String _mail) {
        Give = GiveAsk;
        Take = TakeAsk;
        freeText = moreInfoText;
        mail=_mail;
    }

}