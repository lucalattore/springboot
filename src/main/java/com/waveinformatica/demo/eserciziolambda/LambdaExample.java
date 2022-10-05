package com.waveinformatica.demo.eserciziolambda;

import com.waveinformatica.demo.esercizioconcorrenza.NumberVerifier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.function.Consumer;
import java.util.function.Function;

@Component
@Slf4j
public class LambdaExample {

    @Autowired
    NumberVerifier numberVerifier;

    @PostConstruct
    private void init() {
        test(100, x -> x * 3 + 1);
        test2(10, x -> {
            log.info("X: {}", x);
        });
    }

    private void test(int n, Function<Integer,Integer> f) {
        for (int i=1; i<n; i++) {
            boolean prime = numberVerifier.isPrime(i);
            if (prime) {
                int r = f.apply(i);
                log.info("I: {} -> {}", i, r);
            }
        }
    }

    private void test2(int n, Consumer<Integer> f) {
        for (int i=1; i<n; i++) {
            boolean prime = numberVerifier.isPrime(i);
            if (prime) {
                f.accept(i);
            }
        }
    }

}
