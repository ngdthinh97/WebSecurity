package com.spring.security.auth.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class SupplyAsync {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        List<Integer> lstInt = new ArrayList<>();
        lstInt.add(1);
        lstInt.add(2);
        lstInt.add(3);
        lstInt.add(4);
        lstInt.add(5);

        List<String> lstInt2 = lstInt
                .stream()
                .map(i -> "loda-" + i)
                .map(String::toUpperCase)
                .collect(Collectors.toList());

        System.out.println(lstInt);
        System.out.println(lstInt2);
    }
}
