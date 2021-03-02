package springBootSimpleHTTPService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}




	/*@Bean
	public ApplicationRunner initializer(MarvelCharacterRepository repository) {
		return args -> repository.saveAll(Arrays.asList(
				MarvelCharacter.builder().heroName("Iron man").firstName("Tony").lastName("Stark").build(),
				MarvelCharacter.builder().heroName("Thor").firstName("Thor").lastName("Odinson").build(),
				MarvelCharacter.builder().heroName("Black widow").firstName("Natasha").lastName("Romanova").build(),
				MarvelCharacter.builder().heroName("Hawkeye").firstName("Clint").lastName("Barton").build(),
				MarvelCharacter.builder().heroName("Spider-man").firstName("Peter").lastName("Parker").build(),
				MarvelCharacter.builder().heroName("Captain America").firstName("Steve").lastName("Rogers").build(),
				MarvelCharacter.builder().heroName("Hulk").firstName("Bruce").lastName("Banner").build(),
				MarvelCharacter.builder().heroName("Ant-man").firstName("Scott").lastName("Lang").build()
		));
	}

	 */

}
