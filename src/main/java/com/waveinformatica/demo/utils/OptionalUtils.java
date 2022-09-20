package com.waveinformatica.demo.utils;

import java.util.Optional;

public class OptionalUtils {

    public static <T> T value(Optional<T> x) {
        return x == null || !x.isPresent() ? null : x.get();
    }

}
