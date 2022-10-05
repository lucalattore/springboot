package com.waveinformatica.demo.esercizioconcorrenza;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

@Component
@Scope("prototype")
@Slf4j
public class SlaveVerifier implements Runnable {

    @Autowired
    private NumberVerifier verifier;

    private final BlockingQueue<Integer> workingQueue;
    private final BlockingQueue<Integer> primeQueue;

    public SlaveVerifier(BlockingQueue<Integer> workingQueue, BlockingQueue<Integer> primeQueue) {
        this.workingQueue = workingQueue;
        this.primeQueue = primeQueue;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            log.info("Estraggo un numero dalla coda");
            try {
                Integer n = workingQueue.poll(10, TimeUnit.SECONDS);
                if (n == null) {
                    log.info("Coda vuota. Nulla da fare. Aspetto ancora.");
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
        }
    }
}
