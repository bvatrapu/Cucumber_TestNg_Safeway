package com.Safeway.constants;

public enum dataKey {

    // URLS
    HomePage("HomePage.Url"),
    SignIn_UserName("SignIn.UserName"),
    SignIn_Password("SignIn.Password"),


       ;
    private String propName;

    private dataKey(String propName) {
        this.setpropVal(propName);
    }

    public String getpropVal() {
        return this.propName;
    }

    private void setpropVal(String name) {
        this.propName = name;
    }
}
