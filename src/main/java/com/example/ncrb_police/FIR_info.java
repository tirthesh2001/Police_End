package com.example.ncrb_police;

public class FIR_info {
    String name,email,phone,suspect,time,date,area,statement,evid;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSuspect() {
        return suspect;
    }

    public void setSuspect(String suspect) {
        this.suspect = suspect;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public String getEvid() {
        return evid;
    }

    public void setEvid(String evid) {
        this.evid = evid;
    }

    public FIR_info(String name, String email, String phone, String suspect, String time, String date, String area, String statement, String evid) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.suspect = suspect;
        this.time = time;
        this.date = date;
        this.area = area;
        this.statement = statement;
        this.evid = evid;
    }

    public FIR_info() {
    }
}
