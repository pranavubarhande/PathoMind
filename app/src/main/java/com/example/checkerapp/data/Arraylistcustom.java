package com.example.checkerapp.data;

public class Arraylistcustom {
    private String testname;
    private String testdescription;
    private String testid;
    public Arraylistcustom(){

    }

    public Arraylistcustom(String testdescription,String testid, String testname) {
        this.testdescription = testdescription;
        this.testid = testid;
        this.testname = testname;

    }
    public String gettestname() {
        return testname;
    }
    public void settestname(String testname) {
        this.testname = testname;
    }
    public String gettestdescription() {
        return testdescription;
    }
    public void settestdescription(String testinfo) {
        this.testdescription = testinfo;
    }
    public String gettestid() {
        return testid;
    }
    public void settestid(String testid) {
        this.testid = testid;
    }
}
