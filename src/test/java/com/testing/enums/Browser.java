package com.testing.enums;

public enum Browser {
    chrome("chromedriver.exe"),
    firefox("geckodriver.exe"),
    edge("msedgedriver.exe");

    public String label;

    private Browser (String label){
        this.label = label;
    }
}
