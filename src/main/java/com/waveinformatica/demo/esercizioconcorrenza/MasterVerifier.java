package com.waveinformatica.demo.esercizioconcorrenza;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

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
        AtomicBoolean masterCompleted = new AtomicBoolean(false);
        CountDownLatch countDownLatch = new CountDownLatch(s);
        for (int i=0; i<s;i++) {
            SlaveVerifier slave = ctx.getBean(SlaveVerifier.class, workingQueue, primeQueue, masterCompleted, countDownLatch);
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

        masterCompleted.set(true);
        try {
            countDownLatch.await();
            log.info("Tutti gli slave hanno finito");
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }

        log.info("La lista dei numeri primi contiene {} elementi", primeQueue.size());

        Map<String,List<Integer>> primeMap = new HashMap<>();
        while (true) {
            Integer prime = primeQueue.poll();
            if (prime == null) {
                break;
            }

            // prime contiene un numero primo
            String primeString = "" + prime;
            // Forme equivalenti per convertire un numero in stringa
            // String.format("%d", prime);
            // String.valueOf(prime);

            // 11, 1... <- "1"
            // 2 <- "2"
            // 3, 13, 23... <- "3"
            // ...

            String key = primeString.substring(primeString.length() - 1);
            List<Integer> list = primeMap.get(key);
            if (list == null) {
                // non abbiamo ancora memorizzato nella mappa alcun elemento con chiave key
                list = new LinkedList<>();
                primeMap.put(key, list);
            }

            // alternativa
//            list = primeMap.compute(key, (k, v) -> {
//                if (v == null)  {
//                    v = new LinkedList<>();
//                    primeMap.put(key, v);
//                }
//
//                return v;
//            });

            list.add(prime);
        }

        log.info("PRIME MAP: {}", primeMap);
        // itero le chiavi e ottengo i valori per chiave
        for (String key : primeMap.keySet()) {
            List<Integer> list = primeMap.get(key);
            log.info("KEY {} COUNT {} LIST {}", key, list.size(), list);
        }

        // itero i valori
        for (List<Integer> list : primeMap.values()) {
            log.info("COUNT {} LIST {}", list.size(), list);
        }

        // itero le entry
        for (Map.Entry<String,List<Integer>> entry : primeMap.entrySet()) {
            String key = entry.getKey();
            List<Integer> list = entry.getValue();
            log.info("KEY {} COUNT {} LIST {}", key, list.size(), list);
        }

        // lista delle dimensioni delle liste aggregate di numeri primi
        List<Integer> sizes = primeMap.keySet()
            .stream()
            .map(key -> primeMap.get(key))
            .map(list -> list.size())
            .collect(Collectors.toList());

        log.info("SIZES: {}", sizes);
    }
}
