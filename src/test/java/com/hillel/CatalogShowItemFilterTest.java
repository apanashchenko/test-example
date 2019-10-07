package com.hillel;

import com.hillel.models.User;
import com.hillel.page.objects.CategoryPage;
import com.hillel.page.objects.HomePageObject;
import com.hillel.page.objects.ProductItem;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by alpa on 2019-09-07
 */
public class CatalogShowItemFilterTest {

    private WebDriver driver;
    private User existingUser;
    private HomePageObject homePage;
    private CategoryPage categoryPage;

    @BeforeClass
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        existingUser = new User();
        existingUser.setUserName("test");
        existingUser.setFirstName("bob");
        existingUser.setLastName("marley");
        existingUser.setEmail("bob@email.com");
        existingUser.setPassword("123456");

        driver = new ChromeDriver();
        driver.manage().timeouts().pageLoadTimeout(4000, TimeUnit.MILLISECONDS);
        driver.manage().timeouts().implicitlyWait(4000, TimeUnit.MILLISECONDS);

        homePage = new HomePageObject(driver);
        homePage.open();
        homePage.login(existingUser.getUserName(), existingUser.getPassword());
        driver.get("http://localhost/category.html");
        categoryPage = new CategoryPage(driver);
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }

    @Test(dataProvider = "productNumbers")
    public void showProductNumberFilterTest(int itemNumber) {
        categoryPage.selectProductOnPageNumber(itemNumber);

        assertThat(categoryPage.getTotalProducts()).contains(String.valueOf(itemNumber));
        assertThat(categoryPage.getProductItemsSize()).isEqualTo(itemNumber);

        List<ProductItem> productItems = categoryPage.getProductItems();
        assertThat(productItems).hasSize(itemNumber);

        productItems.forEach(productItem -> {
            assertThat(productItem)
                    .satisfies(item -> {
                assertThat(item.getTitle()).isNotEmpty();
                assertThat(item.getPrice()).isNotEmpty();
                assertThat(item.getAddToCartBtn().isDisplayed()).isTrue();
                assertThat(item.getAddToCartBtn().isEnabled()).isTrue();
                assertThat(item.getViewDetailBtn().isDisplayed()).isTrue();
                assertThat(item.getViewDetailBtn().isEnabled()).isTrue();
            });
//            assertThat(productItem.getTitle()).isNotEmpty();
//            assertThat(productItem.getPrice()).isNotEmpty();
//            assertThat(productItem.getAddToCartBtn().isDisplayed()).isTrue();
//            assertThat(productItem.getAddToCartBtn().isEnabled()).isTrue();
//
//            assertThat(productItem.getViewDetailBtn().isDisplayed()).isTrue();
//            assertThat(productItem.getViewDetailBtn().isEnabled()).isTrue();
        });

    }

    @Test
    public void openItemDetail() throws InterruptedException {
        categoryPage.openProductDetail("Holy");
        sleep(10000);
    }


    @DataProvider(name = "productNumbers")
    public Object[][] productNumbers() {
        return new Object[][] {
                { 3 },
                { 6 },
                { 9 }
        };
    }


}
