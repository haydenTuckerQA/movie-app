package com.qa.business.service;

import javax.inject.Inject;

import com.qa.business.repository.IMovieRepository;

public class MovieService implements IMovieService {

	@Inject
	private IMovieRepository repo;
	
	@Override
	public String getAllMovies() {
		return repo.getAllMovies();
	}

	@Override
	public String getMovie(Long id) {
		return repo.getMovie(id);
	}

	@Override
	public String createMovie(String movieAsJSON) {
		return repo.createMovie(movieAsJSON);
	}

}
