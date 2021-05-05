package com.edureka;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Main {

    public static void main(String[] args) {
        WebDriver browser;
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true);
        browser = new ChromeDriver(options);
        browser.get("http://127.0.0.1:8080/index.php");
        WebElement button = browser.findElement(By.id("about"));
        assert(button.isDisplayed());
        System.out.println("Button about is there: " + button.isDisplayed());
        button.click();
        assert(button.getText().equals("about"));
        browser.close();
    }
}
