package com.juaracoding;

public class App {

    public static void main(String[] args) {
        
        String path = "target\\extent-report\\screenshots\\Login_failure_with_incorrect_credentials_20260413212212.png";
        String modifiedPath = path.replace("target\\extent-report\\", "");
        System.out.println(modifiedPath);

    }
}
