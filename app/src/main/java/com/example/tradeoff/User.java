package com.example.tradeoff;

 public class User {

     private String Fname;
     private String Lname;
     private String email;
     private String password;
     private String phone;
     private String activ;
     private String adress;


     public User() {
         this.Fname = "";
         this.Lname = "";
         this.email = "";
         this.password = "";
         this.phone = "";
         this.activ = "";
         this.adress = "";
     }

     public User(String _Fname, String Lname, String email, String password, String phone, String activ, String adress) {
         this.Fname = _Fname;
         this.Lname = Lname;
         this.email = email;
         this.password = password;
         this.phone = phone;
         this.activ = activ;
         this.adress = adress;
     }

     public String getEmail() {
         return email;
     }

     public String getFname() {
         return Fname;
     }

     public String getLname() {
         return Lname;
     }

     public String getActiv() {
         return activ;
     }

     public String getPassword() {
         return password;
     }

     public String getPhone() {
         return phone;
     }

     public void setActiv(String activ) {
         this.activ = activ;
     }

     public String getAdress() {
         return adress;
     }

     public void setAdress(String adress) {
         this.adress = adress;
     }

     public void setEmail(String email) {
         this.email = email;
     }

     public void setFname(String fname) {
         Fname = fname;
     }

     public void setLname(String lname) {
         Lname = lname;
     }

     public void setPassword(String password) {
         this.password = password;
     }

     public void setPhone(String phone) {
         this.phone = phone;
     }
 }
