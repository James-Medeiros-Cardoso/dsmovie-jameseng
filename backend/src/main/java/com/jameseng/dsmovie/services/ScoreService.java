package com.jameseng.dsmovie.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jameseng.dsmovie.dto.MovieDTO;
import com.jameseng.dsmovie.dto.ScoreDTO;
import com.jameseng.dsmovie.entities.Movie;
import com.jameseng.dsmovie.entities.Score;
import com.jameseng.dsmovie.entities.User;
import com.jameseng.dsmovie.repositories.MovieRepository;
import com.jameseng.dsmovie.repositories.ScoreRepository;
import com.jameseng.dsmovie.repositories.UserRepository;

@Service
public class ScoreService {

	@Autowired
	private MovieRepository movieRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ScoreRepository scoreRepository;

	@Transactional
	public MovieDTO saveScore(ScoreDTO dto) {

		User user = userRepository.findByEmail(dto.getEmail());

		if (user == null) { // se o user não existe no banco, inserir este.
			user = new User();
			user.setEmail(dto.getEmail());
			user = userRepository.saveAndFlush(user); // saveAndFlush = terá o objeto atualizado
		}

		// findById retorna optional = colocar o .get() para pegar o objeto
		Movie movie = movieRepository.findById(dto.getMovieId()).get();

		// 3 - Salvar a avaliação do usuário para o tal filme
		Score score = new Score();
		score.setMovie(movie);
		score.setUser(user);
		score.setValue(dto.getScore());
		score = scoreRepository.saveAndFlush(score);

		// 4 - Recalcular a avaliação média do filme e salvar no banco de dados:
		double sum = 0;
		for (Score s : movie.getScores()) {
			sum = sum + s.getValue();
		}

		// média:
		double avg = sum / movie.getScores().size();

		// salvando:
		movie.setScore(avg);
		movie.setCount(movie.getScores().size());
		movie = movieRepository.save(movie);
		
		return new MovieDTO(movie);
	}

}
