package com.e.speedogistic.ui.pre_book;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.Serializable;

public class Pre_bookViewModel implements Serializable {
    String bookid,
    comname,
    typeof,
    load,
    vehnum,
    licnum,
    rate,
    drivername,
    logphone,
    logemail,
    logaddress,
    pickaddress,
    destination,
            currentdate,
    pickdate,
    picktime,
    username,
    useremail,
    usernum,
    useraddress,
    bookingstatus,
    estimate,
    totalcost,
    message;


    public Pre_bookViewModel() {
    }

    public Pre_bookViewModel(String bookid, String comname, String typeof, String load, String vehnum, String licnum, String rate, String drivername, String logphone, String logemail, String logaddress, String pickaddress, String destination, String currentdate, String pickdate, String picktime, String username, String useremail, String usernum, String useraddress, String bookingstatus, String estimate, String totalcost, String message) {
        this.bookid = bookid;
        this.comname = comname;
        this.typeof = typeof;
        this.load = load;
        this.vehnum = vehnum;
        this.licnum = licnum;
        this.rate = rate;
        this.drivername = drivername;
        this.logphone = logphone;
        this.logemail = logemail;
        this.logaddress = logaddress;
        this.pickaddress = pickaddress;
        this.destination = destination;
        this.currentdate = currentdate;
        this.pickdate = pickdate;
        this.picktime = picktime;
        this.username = username;
        this.useremail = useremail;
        this.usernum = usernum;
        this.useraddress = useraddress;
        this.bookingstatus = bookingstatus;
        this.estimate = estimate;
        this.totalcost = totalcost;
        this.message = message;
    }

    public String getBookid() {
        return bookid;
    }

    public void setBookid(String bookid) {
        this.bookid = bookid;
    }

    public String getComname() {
        return comname;
    }

    public void setComname(String comname) {
        this.comname = comname;
    }

    public String getTypeof() {
        return typeof;
    }

    public void setTypeof(String typeof) {
        this.typeof = typeof;
    }

    public String getLoad() {
        return load;
    }

    public void setLoad(String load) {
        this.load = load;
    }

    public String getVehnum() {
        return vehnum;
    }

    public void setVehnum(String vehnum) {
        this.vehnum = vehnum;
    }

    public String getLicnum() {
        return licnum;
    }

    public void setLicnum(String licnum) {
        this.licnum = licnum;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getDrivername() {
        return drivername;
    }

    public void setDrivername(String drivername) {
        this.drivername = drivername;
    }

    public String getLogphone() {
        return logphone;
    }

    public void setLogphone(String logphone) {
        this.logphone = logphone;
    }

    public String getLogemail() {
        return logemail;
    }

    public void setLogemail(String logemail) {
        this.logemail = logemail;
    }

    public String getLogaddress() {
        return logaddress;
    }

    public void setLogaddress(String logaddress) {
        this.logaddress = logaddress;
    }

    public String getPickaddress() {
        return pickaddress;
    }

    public void setPickaddress(String pickaddress) {
        this.pickaddress = pickaddress;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getCurrentdate() {
        return currentdate;
    }

    public void setCurrentdate(String currentdate) {
        this.currentdate = currentdate;
    }

    public String getPickdate() {
        return pickdate;
    }

    public void setPickdate(String pickdate) {
        this.pickdate = pickdate;
    }

    public String getPicktime() {
        return picktime;
    }

    public void setPicktime(String picktime) {
        this.picktime = picktime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public String getUsernum() {
        return usernum;
    }

    public void setUsernum(String usernum) {
        this.usernum = usernum;
    }

    public String getUseraddress() {
        return useraddress;
    }

    public void setUseraddress(String useraddress) {
        this.useraddress = useraddress;
    }

    public String getBookingstatus() {
        return bookingstatus;
    }

    public void setBookingstatus(String bookingstatus) {
        this.bookingstatus = bookingstatus;
    }

    public String getEstimate() {
        return estimate;
    }

    public void setEstimate(String estimate) {
        this.estimate = estimate;
    }

    public String getTotalcost() {
        return totalcost;
    }

    public void setTotalcost(String totalcost) {
        this.totalcost = totalcost;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}