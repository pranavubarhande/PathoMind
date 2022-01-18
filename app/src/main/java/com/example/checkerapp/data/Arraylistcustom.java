package com.example.checkerapp.data;

public class Arraylistcustom {
    private String testname;
    private String testinfo;
    private  String testidfirebs;

    public Arraylistcustom(String testname, String testinfo, String testidfirebs) {
        this.testname = testname;
        this.testinfo = testinfo;
        this.testidfirebs = testidfirebs;
    }
    public Arraylistcustom(String testname, String testinfo) {
        this.testname = testname;
        this.testinfo = testinfo;
    }
    public String getTestname() {
        return testname;
    }
    public void setTestname(String testname) {
        this.testname = testname;
    }
    public String getTestinfo() {
        return testinfo;
    }
    public void setTestinfo(String testinfo) {
        this.testinfo = testinfo;
    }
    public String getTestidfirebs(){return testidfirebs;}
}
