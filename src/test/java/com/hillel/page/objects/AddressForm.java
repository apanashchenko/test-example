package com.hillel.page.objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Created by alpa on 2019-09-19
 */
public class AddressForm extends BasePage {

    private WebElement address;

    public AddressForm(WebDriver driver, WebElement address) {
        super(driver);
        this.address = address;
    }


    public String getCity() {
       return address.findElement(By.xpath("")).getText();
    }

}
