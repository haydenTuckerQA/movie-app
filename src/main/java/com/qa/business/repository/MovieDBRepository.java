package com.qa.business.repository;

import static javax.transaction.Transactional.TxType.REQUIRED;
import static javax.transaction.Transactional.TxType.SUPPORTS;

import java.util.Collection;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.apache.log4j.Logger;

import com.qa.persistence.domain.Movie;
import com.qa.util.JSONUtil;

@Transactional(SUPPORTS)
public class MovieDBRepository implements IMovieRepository {
	
	private static final Logger LOGGER = 
			Logger.getLogger(MovieDBRepository.class);
	
	@PersistenceContext(unitName ="primary")
	private EntityManager manager;
	
	@Inject
	private JSONUtil util;

	@Override
	public String getAllMovies() {
		LOGGER.info("MovieDBRepository getAllMovies");
		Query query = manager.createQuery("SELECT m FROM Movie m");
		Collection<Movie> movies = (Collection<Movie>) query.getResultList();
		return util.getJSONForObject(movies);
	}

	@Override
	public String getMovie(Long id) {
		LOGGER.info("MovieDBRepository getMovie");
		Movie movie = findMovie(id);
		if(movie != null) {
			return util.getJSONForObject(movie);
		} else {
			return "{\"message\":\"movie not found\"}";
		}
	}

	private Movie findMovie(Long id) {
		return manager.find(Movie.class, id);
	}

	@Transactional(REQUIRED)
	@Override
	public String createMovie(String movieAsJSON) {
		Movie movie = util.getObjectForJSON(movieAsJSON, Movie.class);
		manager.persist(movie);
		return "{\"message\":\"movie successfully created\"}";
	}

	@Override
	public String deleteMovie(Long id) {
		Movie movie = findMovie(id);
		if (movie != null) {
			manager.remove(movie);
			return "{\"message\":\"movie successfully deleted\"}";
		} else {
			return "{\"message\":\"movie does not exist\"}";
		}
	}
}