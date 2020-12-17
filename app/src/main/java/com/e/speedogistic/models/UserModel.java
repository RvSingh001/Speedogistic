package com.e.speedogistic.models;

import java.io.Serializable;



public class UserModel implements Serializable {
    private String firstName,lastName,email,password,phone,date,address,role,comname,typeOfVehicule,capacity,regnum,licenum,rate;

    public UserModel() {
    }

    public UserModel(String firstName, String lastName, String email, String password, String phone, String date, String address, String role, String comname, String typeOfVehicule, String capacity, String regnum , String licenum, String rate){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.date = date;
        this.address = address;
        this.role = role;
        this.comname = comname;
        this.typeOfVehicule = typeOfVehicule;
        this.capacity = capacity;
        this.regnum=regnum;
        this.licenum = licenum;
        this.rate = rate;
    }

    public String getRegnum() {
        return regnum;
    }

    public void setRegnum(String regnum) {
        this.regnum = regnum;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getComname() {
        return comname;
    }

    public void setComname(String comname) {
        this.comname = comname;
    }

    public String getTypeOfVehicule() {
        return typeOfVehicule;
    }

    public void setTypeOfVehicule(String typeOfVehicule) {
        this.typeOfVehicule = typeOfVehicule;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getLicenum() {
        return licenum;
    }

    public void setLicenum(String licenum) {
        this.licenum = licenum;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
}
