package com.onlyflights.source.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.onlyflights.source.models.Feedback;

public interface FeedbackRepo extends JpaRepository<Feedback, Long> {
	
	Feedback findById(final int id);
	
	List<Feedback> findAll();
	
	Feedback saveAndFlush(Feedback feedback);
	
	
}


