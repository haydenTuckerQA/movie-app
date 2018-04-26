package com.qa.business.repository;

import static javax.transaction.Transactional.TxType.REQUIRED;
import static javax.transaction.Transactional.TxType.SUPPORTS;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;
import javax.inject.Inject;
import javax.transaction.Transactional;

import com.qa.persistence.domain.Movie;
import com.qa.util.JSONUtil;

@ApplicationScoped
@Alternative
@Transactional(SUPPORTS)
public class MovieMapRepository implements IMovieRepository {
	
	private Map<Long, Movie> movies;
	
	private static Long uniqueId;
	
	@Inject
	JSONUtil util;
	
	public MovieMapRepository() {
		this.movies = new HashMap<Long, Movie>();
		this.movies.put(1L, new Movie("A", "B", "C"));
		uniqueId = 2L;
	}

	@Override
	public String getAllMovies() {
		return util.getJSONForObject(movies.values());
	}

	@Override
	public String getMovie(Long id) {
		Movie movie = movies.get(id);
		if(movie != null) {
			return util.getJSONForObject(movie);
		} else {
			return "{\"message\":\"movie not found\"}";
		}
	}

	@Transactional(REQUIRED)
	@Override
	public String createMovie(String movieAsJSON) {
		Movie movie = util.getObjectForJSON(movieAsJSON, Movie.class);
		movies.put(uniqueId, movie);
		uniqueId++;
		return "{\"message\":\"movie successfully created\"}";
	}

	@Override
	public String deleteMovie(Long id) {
		Movie movie = movies.get(id);
		if (movie != null) {
			movies.remove(movie);
			return "{\"message\":\"movie successfully deleted\"}";
		} else {
			return "{\"message\":\"movie does not exist\"}";
		}
	}
	
	@Override
	public String updateMovie(String movieAsJSON) {
		Movie updatedMovie = util.getObjectForJSON(movieAsJSON, Movie.class);
		Movie oldMovie = movies.get(updatedMovie.getId());
		if (oldMovie != null) {
			movies.put(updatedMovie.getId(), updatedMovie);
			return "{\"message\":\"movie successfully updated\"}";
		} else {
			return "{\"message\":\"movie does not exist\"}";
		}
	}
}
