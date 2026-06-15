package com.loja_de_jogos.vapor;

import com.loja_de_jogos.vapor.repositories.GameRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.data.jpa.autoconfigure.DataJpaRepositoriesAutoConfiguration;
import org.springframework.boot.hibernate.autoconfigure.HibernateJpaAutoConfiguration;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import static org.mockito.Mockito.mock;

@SpringBootTest
@EnableAutoConfiguration(exclude = {
		DataSourceAutoConfiguration.class,
		HibernateJpaAutoConfiguration.class,
		DataJpaRepositoriesAutoConfiguration.class
})
@Import(VaporApplicationTests.TestRepositoryConfiguration.class)
class VaporApplicationTests {

	@Test
	void contextLoads() {
	}

	@TestConfiguration
	static class TestRepositoryConfiguration {

		@Bean
		GameRepository gameRepository() {
			return mock(GameRepository.class);
		}
	}

}
