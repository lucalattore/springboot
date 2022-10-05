package com.waveinformatica.demo.esercizioconcorrenza;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
@Scope("prototype")
@Slf4j
public class SlaveVerifier implements Runnable {

    @Autowired
    private NumberVerifier verifier;

    private final BlockingQueue<Integer> workingQueue;
    private final BlockingQueue<Integer> primeQueue;
    private final AtomicBoolean masterCompleted;

    private final CountDownLatch countDownLatch;

    public SlaveVerifier(
        BlockingQueue<Integer> workingQueue,
        BlockingQueue<Integer> primeQueue,
        AtomicBoolean masterCompleted,
        CountDownLatch countDownLatch)
    {
        this.workingQueue = workingQueue;
        this.primeQueue = primeQueue;
        this.masterCompleted = masterCompleted;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            log.info("Estraggo un numero dalla coda");
            try {
                Integer n = workingQueue.poll(10, TimeUnit.SECONDS);
                if (n == null) {
                    boolean done = masterCompleted.get();
                    if (done) {
                        log.info("Coda vuota. Non arriveranno più numeri");
                        break;
                    } else {
                        log.info("Coda vuota. Nulla da fare. Aspetto ancora.");
                    }
                } else {
                    boolean prime = verifier.isPrime(n);
                    log.info("The number {} is {}prime", n, prime ? "" : "NOT ");
                    if (prime) {
                        primeQueue.offer(n);
                    }
                    Thread.sleep(10000);
                }
            } catch (InterruptedException e) {
                log.error(e.getMessage());
            }
        } // end while
        log.info("Terminated");
        countDownLatch.countDown();
    }
}
