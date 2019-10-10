package com.hillel;

import groovy.lang.IntRange;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by alpa on 10/10/19
 */
public class CollectionLambada {

    private static String staticField = "one";

    @Test
    public void testSingleThread() {
        List<String> stringList = new ArrayList<>();
        stringList.add("one");
        stringList.add("two");
        stringList.add("three");

//        for (String s: stringList) {
//            System.out.println(Thread.currentThread().getName() + ": " + s);
//        }

        ForkJoinPool forkJoinPool = new ForkJoinPool(1);
//        Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//                stringList.parallelStream()
//                        .forEach(value -> System.out.println(Thread.currentThread().getName() + ": " + value));
//            }
//        };
//        forkJoinPool.submit(runnable);
        forkJoinPool.submit(() -> stringList.parallelStream()
                .forEach(value -> System.out.println(Thread.currentThread().getName() + ": " + value)));
    }


    @Test
    public void staticTest() {
        IntStream.range(0, 50)
                .parallel().forEach(value -> {
            System.out.println(Thread.currentThread().getName() + " first: " + value + " - " + staticField);
            staticField = null;
        });

        IntStream.range(0, 50).parallel().forEach(value -> {
            staticField = "two";
            System.out.println(Thread.currentThread().getName() + " second: " + value + " - " + staticField);
        });
    }

    @Test
    public void twoList() {
        List<String> stringList = new ArrayList<>();
        stringList.add("one");
        stringList.add("two");
        stringList.add("three");

        List<String> stringList2 = new ArrayList<>();
        stringList2.add("one1");
        stringList2.add("two2");
        stringList2.add("three");

        stringList.forEach(list1 ->
                stringList2.forEach(list2 -> {
                    if (list1.equalsIgnoreCase(list2)) {
                        System.out.println(list2);
                    }
                }));
    }

    @Test
    public void filterList() {
        List<String> stringList = new ArrayList<>();
        stringList.add("one1");
        stringList.add("two");
        stringList.add("three");
        stringList.add("for1");

        List<String> collect = stringList.stream().filter(value -> value.contains("1")).collect(Collectors.toList());
        System.out.println(collect);

    }

    @Test
    public void filterList1() {
        List<String> stringList = new ArrayList<>();
        stringList.add("one1");
        stringList.add("two");
        stringList.add("three");
        stringList.add("for1");

        String item = stringList.stream()
                .filter(value -> value.contains("1"))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Element not found"));
//                .orElseGet(v ->"");

        System.out.println(item);

    }
}
