package com.e.speedogistic.models;

import java.io.Serializable;

public class BookModel implements Serializable {

    String name,email,phone,address,comnames,type,load,resnum,licnum,ratekm;

    public BookModel(String name, String email, String phone, String address, String comnames, String type, String load, String resnum, String licnum, String ratekm) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.comnames = comnames;
        this.type = type;
        this.load = load;
        this.resnum = resnum;
        this.licnum = licnum;
        this.ratekm = ratekm;
    }

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getComnames() {
        return comnames;
    }

    public void setComnames(String comnames) {
        this.comnames = comnames;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLoad() {
        return load;
    }

    public void setLoad(String load) {
        this.load = load;
    }

    public String getResnum() {
        return resnum;
    }

    public void setResnum(String resnum) {
        this.resnum = resnum;
    }

    public String getLicnum() {
        return licnum;
    }

    public void setLicnum(String licnum) {
        this.licnum = licnum;
    }

    public String getRatekm() {
        return ratekm;
    }

    public void setRatekm(String ratekm) {
        this.ratekm = ratekm;
    }
}
