package com.onlyflights.source.repositories;

import com.onlyflights.source.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
	
	Customer findByEmail(final String email);

}
