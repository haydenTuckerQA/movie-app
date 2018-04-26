package com.qa.business.service;

public interface IMovieService {
	String getAllMovies();
	String getMovie(Long id);
	String createMovie(String movieAsJSON);
}
