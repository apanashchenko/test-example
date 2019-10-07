package com.hillel.page.objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by alpa on 2019-09-07
 */
public class CategoryPage extends BasePage {

    public CategoryPage(WebDriver driver) {
        super(driver);
    }

    public CategoryPage selectProductOnPageNumber(int itemNumber) {
        WebElement filterButton = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[@id='products-number']/a[@onclick='setNewPageSize("+itemNumber+")']")));
        filterButton.click();
        return this;
    }

    public String getTotalProducts() {
        WebElement totalProducts = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("#totalProducts")));
        return totalProducts.getText();
    }

    public int getProductItemsSize() {
        return driver.findElements(By.cssSelector("#products .product")).size();
    }

    public List<ProductItem> getProductItems() {
        List<WebElement> elements = driver.findElements(By.cssSelector("#products .product"));

//        List<ProductItem> productItemList = new ArrayList<>();
//        for (WebElement element: elements) {
//            productItemList.add(new ProductItem(element, driver));
//        }

        return elements.stream().map(element -> new ProductItem(element, driver)).collect(Collectors.toList());
    }

    public void openProductDetail(String name) {
        Optional<ProductItem> first = getProductItems().stream().filter(
                productItem -> name.equalsIgnoreCase(productItem.getTitle())).findFirst();
        if (first.isPresent()) {
            first.get().getViewDetailBtn().click();
        } else {
            //throw exception
        }
    }
}
