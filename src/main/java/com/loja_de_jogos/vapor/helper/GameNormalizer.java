package com.loja_de_jogos.vapor.helper;

import java.text.Normalizer;

public class GameNormalizer {

    public static String generateNormalizedId(String name, String publisher){
        return normalize(name) + "-" + normalize(publisher);
    }

    private static String normalize(String value){
        if (value == null){
            return "";
        }

        String normalized = Normalizer.normalize(value, Normalizer.Form.NFD);

        normalized = normalized
                .replaceAll("\\p{M}", "")
                .toLowerCase()
                .replaceAll("[^a-z0-9\\s]", "")
                .trim()
                .replaceAll("\\s+", "-");
        return normalized;
    }
}
