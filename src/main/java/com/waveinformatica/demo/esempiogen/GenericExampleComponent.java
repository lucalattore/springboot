package com.waveinformatica.demo.esempiogen;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Component
@Slf4j
public class GenericExampleComponent {

    @PostConstruct
    private void init() {
        GenExample<String> a = new GenExample<>();
        a.setName("Luca");
        a.setValue("Una stringa");

        GenExample<Integer> b = new GenExample<>();
        b.setName("Giuseppe");
        b.setValue(123);

        log.info("TESTGEN: a = {}", a);
        log.info("TESTGEN: b = {}", b);

        int x = f(Optional.of(123));
        String y = f(Optional.of("Luca"));
    }

    private <T> T f(Optional<T> x) {
        return x.orElse(null);
    }
}
