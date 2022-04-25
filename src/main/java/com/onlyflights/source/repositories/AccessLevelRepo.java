package com.onlyflights.source.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.onlyflights.source.models.AccessLevel;

public interface AccessLevelRepo extends JpaRepository<AccessLevel, Long> {

	AccessLevel findByAccessLevel(final String accessLevel);

}
