package com.hillel.page.objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Created by alpa on 2019-09-19
 */
public class ShippingForm extends AddressForm {

    public ShippingForm(WebDriver driver) {
        super(driver, driver.findElement(By.xpath("ShipopingLocator")));
    }
}
