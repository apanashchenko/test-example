package com.hillel.page.objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Created by alpa on 2019-09-07
 */
public class ProductItem extends BasePage {

    private WebElement parent;

    public ProductItem(WebElement parent, WebDriver driver) {
        super(driver);
        this.parent = parent;
    }

    public String getTitle() {
        return parent.findElement(By.cssSelector("h3>a")).getText();
    }

    public String getPrice() {
        return parent.findElement(By.cssSelector(".price")).getText();
    }

    public WebElement getViewDetailBtn() {
        return parent.findElement(By.cssSelector(".buttons .btn-default"));
    }

    public WebElement getAddToCartBtn() {
        return parent.findElement(By.cssSelector(".buttons .btn-primary"));
    }

}
