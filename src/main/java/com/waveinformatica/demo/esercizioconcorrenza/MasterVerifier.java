package com.waveinformatica.demo.esercizioconcorrenza;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class MasterVerifier {

    @Autowired
    private ApplicationContext ctx;

    private final List<Thread> slaveThreads;
    private final BlockingQueue<Integer> workingQueue;
    private final BlockingQueue<Integer> primeQueue;

    public MasterVerifier() {
        slaveThreads = new ArrayList<>();
        workingQueue = new ArrayBlockingQueue<>(100);
        primeQueue = new LinkedBlockingQueue<>();
    }

    @PostConstruct
    private void init() {
        int s = 10;
        log.info("Inizializzo {} slave", s);
        for (int i=0; i<s;i++) {
            SlaveVerifier slave = ctx.getBean(SlaveVerifier.class, workingQueue, primeQueue);
            Thread t = new Thread(slave);
            t.start();
            slaveThreads.add(t);
        }

        int m  = 100;
        log.debug("Inizio a verificare i primi {} numeri", m);

        for (int i=1; i<m; i++) {
            try {
                log.info("Inserisco {} in coda", i);
                while (!workingQueue.offer(i, 10, TimeUnit.SECONDS)) {
                    log.info("Coda piena. Aspetto che gli slave lavorino per inserire il {}.", i);
                }
            } catch (InterruptedException e) {
                log.error(e.getMessage());
            }
        }

        log.info("La lista dei numeri primi contiene {} elementi", primeQueue.size());
    }
}
