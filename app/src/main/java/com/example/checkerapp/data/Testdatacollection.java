package com.example.checkerapp.data;

public class Testdatacollection {
    String testname;

    public void setTestname(String testname) {
        this.testname = testname;
    }

    public void setTestdescription(String testdescription) {
        this.testdescription = testdescription;
    }

    String testdescription;

    public String getTestname() {
        return testname;
    }

    public String getTestdescription() {
        return testdescription;
    }



    public Testdatacollection() {
    }

    public Testdatacollection(String testname, String testdescription) {
        this.testname = testname;
        this.testdescription = testdescription;
    }


}
