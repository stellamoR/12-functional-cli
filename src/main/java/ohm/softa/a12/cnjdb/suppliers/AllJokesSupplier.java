package ohm.softa.a12.cnjdb.suppliers;

import ohm.softa.a12.cnjdb.CNJDBApi;
import ohm.softa.a12.cnjdb.CNJDBService;
import ohm.softa.a12.model.JokeDto;
import org.apache.commons.lang3.NotImplementedException;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

/**
 * Supplier implementation to retrieve all jokes of the ICNDB in a linear way
 * @author Peter Kurfer
 */

public final class AllJokesSupplier implements Supplier<JokeDto> {

    /* ICNDB API proxy to retrieve jokes */
    private final CNJDBApi icndbApi;
	private HashMap<String, JokeDto> prevJokes = new HashMap<>();

    public AllJokesSupplier() {
        icndbApi = new CNJDBService().getInstance();
    }

    public JokeDto get() {

		JokeDto currJoke = null;
		try {
			currJoke = icndbApi.getRandomJoke().get();
			while(prevJokes.containsKey(currJoke.getId())){
				currJoke = icndbApi.getRandomJoke().get();
			}
			prevJokes.put(currJoke.getId(), currJoke);
			return currJoke;
		} catch (InterruptedException | ExecutionException e) {
			throw new RuntimeException(e);
		}
	}

}
