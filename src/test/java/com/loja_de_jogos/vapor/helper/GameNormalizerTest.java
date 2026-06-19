package com.loja_de_jogos.vapor.helper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class GameNormalizerTest {
  @Test
  void shouldGenerateNormalizeId() {
    String result = GameNormalizer.generateNormalizedId("Persóna 5!?", "Átlús");

    assertThat(result).isEqualTo("persona-5-atlus");
  }
}
