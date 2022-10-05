package com.waveinformatica.demo.esercizioconcorrenza;

import org.springframework.stereotype.Component;

@Component
public class NumberVerifier {

    public boolean isPrime(int n) {
        for (int i=n-1; i>1; i--) {
            if (n % i == 0) {
                return false;
            }
        }

        return true;
    }

}
