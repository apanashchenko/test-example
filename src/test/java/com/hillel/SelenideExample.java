package com.hillel;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.sun.tools.doclets.formats.html.ConfigurationImpl;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Selenide.*;

/**
 * Created by alpa on 10/10/19
 */
public class SelenideExample {

    @Test
    public void test() {
//        Configuration.browser = "firefox";
        open("https://selenide.org/");
        $(".quicklinks").shouldBe(Condition.visible).click();
        $x("//a[text()='Released Selenide 5.3.1']").shouldBe(Condition.exist);
    }
}
