package com.spring.security.auth.common;

import java.util.concurrent.*;

public class ThreadMethod {

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        System.out.println("Run a task specified by a Runnable Object asynchronously.");
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            System.out.println("It is runnig in a separate thread than the main thread.");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            System.out.println("Completed");
        });

        System.out.println("It is also running... ");
        // Block and wait for the future to complete
        future.get();
        System.out.println("Done!!!");
    }

    public static String computeSomething() {
        try {
            System.out.println("Computing ... ");
            Thread.sleep(3000);
            System.out.println("Compute completed ... ");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Future's Result";
    }
}
