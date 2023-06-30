package ohm.softa.a12.icndb;

import ohm.softa.a12.cnjdb.JokeGenerator;
import ohm.softa.a12.model.JokeDto;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

class JokesGeneratorTests {

    private JokeGenerator jokeGenerator = new JokeGenerator();

    @Test
    void testRandomStream() {
		Stream<JokeDto> randStream = jokeGenerator.randomJokesStream();
		int count = 0;
		randStream
			.limit(200)
			.filter(joke -> {return joke.getCategories().contains("money");})
			.forEach(joke -> System.out.println(joke.getValue()));

        /* timeout to ensure that stream does not loop forever */
        /* TODO implement a test for the random joke stream */
    }


    //@Test
    void testJokesStream() {
		var ids = new HashSet<>();
		AtomicBoolean collision = new AtomicBoolean(false);
		List<JokeDto> collList = new ArrayList<>();

		Stream<JokeDto> str = jokeGenerator.allJokesStream();
		str
			.limit(350)
			.forEach((joke) -> {
				if(ids.contains(joke.getId())){
					collList.add(joke);
					collision.set(true);
				}});

		for(JokeDto j : collList){
			System.out.println("Collision von Joke " + j.getId() + " mit der Pointe: \n " + j.getValue());
		}

		assertTrue(!collision.get(), String.format("es gab keine Kollisionen, alles gut"));

        /* TODO implement a test for the linear jokes generator */
    }

}
