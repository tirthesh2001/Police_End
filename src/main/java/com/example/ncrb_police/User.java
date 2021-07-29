package com.example.ncrb_police;

public class User {

    String f_name,l_name,email,number,area;

    public User(){};

    public User(String f_name,String l_name,String email,String number, String area){
        this.f_name=f_name;
        this.l_name=l_name;
        this.email=email;
        this.number=number;
        this.area=area;
    }

    public String getF_name() {
        return f_name;
    }

    public void setF_name(String f_name) {
        this.f_name = f_name;
    }

    public String getL_name() {
        return l_name;
    }

    public void setL_name(String l_name) {
        this.l_name = l_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
