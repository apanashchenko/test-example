package com.hillel;

import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by alpa on 10/10/19
 */
public class CollectionExample {

    @Test
    public void test() {
        List<String> stringList = new ArrayList<>();
        stringList.add("sdfsdf");
        stringList.add("sfsdf");
        stringList.add("sdsdf");

        List<String> stringList2 = new ArrayList<>();
        stringList2.add("sfsdf");
        stringList2.add("sdsdf");

        stringList.addAll(stringList2);

        System.out.println(stringList.size());

        Map<String, String> map = new HashMap<>();
        map.put("adsf", "sdf");
    }
}
