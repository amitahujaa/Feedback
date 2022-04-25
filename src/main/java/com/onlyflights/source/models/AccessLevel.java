package com.onlyflights.source.models;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "access_level")
public class AccessLevel {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String accessLevel;

	@ManyToMany(mappedBy = "accessLevels")
	private Set<Customer> customers;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAccessLevel() {
		return accessLevel;
	}

	public void setAccessLevel(String accessLevel) {
		this.accessLevel = accessLevel;
	}

	public Set<Customer> getUsers() {
		return customers;
	}

	public void setUsers(Set<Customer> customers) {
		this.customers = customers;
	}

}
