package com.onlyflights.source.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.onlyflights.source.models.AccessLevel;
import com.onlyflights.source.models.Customer;
import com.onlyflights.source.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.onlyflights.source.repositories.AccessLevelRepo;

@Service
public class CustomerDetailService implements UserDetailsService {
	
	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private AccessLevelRepo accessLevelRepo;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public Customer findUserByEmail(String email) {
	    return customerRepository.findByEmail(email);
	}
	
	public void createUser(Customer customer) {
	    if(!customer.getEmail().contains("@")){
	        throw new IllegalArgumentException();
        }
        customer.setPassword(bCryptPasswordEncoder.encode(customer.getPassword()));
        customer.setEnabled(true);
        AccessLevel userAccessLevel = accessLevelRepo.findByAccessLevel("ADMIN");
        customer.setRoles(new HashSet<>(Arrays.asList(userAccessLevel)));
        customerRepository.save(customer);
    }
	
	@Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Customer customer = customerRepository.findByEmail(email);
        if(customer != null) {
            List<GrantedAuthority> authorities = userAuthority(customer.getRoles());
            return userForAuth(customer, authorities);
        } else {
            throw new UsernameNotFoundException("user not present");
        }
    }

    private UserDetails userForAuth(Customer customer, List<GrantedAuthority> authorities) {
        return new org.springframework.security.core.userdetails.User(customer.getEmail(), customer.getPassword(), authorities);
    }

    private List<GrantedAuthority> userAuthority(Set<AccessLevel> userAccessLevels) {
        Set<GrantedAuthority> roles = new HashSet<>();
        userAccessLevels.forEach((accessLevel) -> {
            roles.add(new SimpleGrantedAuthority(accessLevel.getAccessLevel()));
        });

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>(roles);
        return grantedAuthorities;
    }



}
