package com.testing.enums;

public enum Browser {
    chrome("chromedriver.exe"),
    firefox("geckodriver.exe"),
    ie("");

    public String label;

    private Browser (String label){
        this.label = label;
    }
}
