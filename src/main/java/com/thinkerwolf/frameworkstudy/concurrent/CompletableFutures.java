package com.thinkerwolf.frameworkstudy.concurrent;

import com.thinkerwolf.frameworkstudy.common.Util;

import java.util.concurrent.CompletableFuture;

public class CompletableFutures {

    public static void main(String[] args) {
        CompletableFuture f1 = CompletableFuture.supplyAsync(() -> priceTB());
        CompletableFuture f2 = CompletableFuture.supplyAsync(() -> priceJD());
        CompletableFuture f3 = CompletableFuture.supplyAsync(() -> pricePDD());
        CompletableFuture result = CompletableFuture.allOf(f1, f2, f3);
        result.join();
        result.thenAccept(o -> {
            try {
                System.out.println(result.get());
            } catch (Exception ignored) {}
        });
    }

    public static double priceTB() {
        delay();
        return 1.9;
    }

    public static double priceJD() {
        delay();
        return 2.3;
    }

    public static double pricePDD() {
        delay();
        return 1.7;
    }

    private static void delay() {
        try {
            Thread.sleep(Util.nextInt(200, 1000));
        } catch (InterruptedException ignored) {}
    }

}
