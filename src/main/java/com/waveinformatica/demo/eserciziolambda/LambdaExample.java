package com.waveinformatica.demo.eserciziolambda;

import com.waveinformatica.demo.esercizioconcorrenza.NumberVerifier;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Optional;
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

        test3(10, x -> {
            int y;
            if (x == null) {
                y = 0;
            } else {
                y = x;
            }

            log.info("TEST3A X: {}, Y: {}", x, y);
        });

        test3(10, x -> {
            int y = Optional.ofNullable(x).orElse(0);
            log.info("TEST3B X: {}, Y: {}", x, y);
        });

        test4(10, x -> {
            int y = Optional.ofNullable(x)
                .map(s -> s.length())
                .map(z -> z * 2)
                .orElse(0);
            log.info("TEST4 X: {}, Y: {}", x, y);
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

    private void test3(int n, Consumer<Integer> f) {
        for (int i=1; i<n; i++) {
            boolean prime = numberVerifier.isPrime(i);
            f.accept(prime ? i : null);
        }
    }

    private void test4(int n, Consumer<String> f) {
        for (int i=1; i<n; i++) {
            boolean prime = numberVerifier.isPrime(i);
            f.accept(prime ? StringUtils.repeat('X', i) : null);
        }
    }

}
