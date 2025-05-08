package com.taskmanagement.tool.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.taskmanagement.tool.controller.Usercontroller;
import com.taskmanagement.tool.dto.Userdetailsdto;
import com.taskmanagement.tool.model.User;
import com.taskmanagement.tool.model.Userdetails;
import com.taskmanagement.tool.repository.Jpauserrepository;
import com.taskmanagement.tool.repository.UserRepository;

@Service
public class Userservice {
	@Autowired
    private Jpauserrepository userRepository;
	@Autowired
	private UserRepository userRepo;
	private static final Logger logger = LoggerFactory.getLogger(Userservice.class);

	public Optional<Userdetails> findbyuserid(UUID userid) {
		logger.info("Just entered findbyuserid() method for userid:{} in userservice",userid);
		Optional<Userdetails> userdetailslist = userRepo.findById(userid);
		logger.info("Just leaving findbyuserid() method for userid:{} in userservice",userid);
        return userdetailslist;
        
    }
	
	public Optional<User> getbyuserid(UUID userid) {
		logger.info("Just entered getUserById() method for userid:{} in userservice",userid);
		Optional<User> userdetailslist = userRepository.findById(userid);
		logger.info("Just leaving getUserById() method for userid:{} in userservice",userid);
		return userdetailslist;
        
    }
	public void saveUser(User user) {
		logger.info("just entered saveUser() method");
         userRepository.save(user);
         logger.info("just leaving saveUser() method");
         
    }
	
	
	public void updateUser(User user) {
		logger.info("just entered updateUser() method");
	  userRepository.save(user);
	  logger.info("just leaving updateUser() method");
	}

	public Userdetails loadUserByUsername(String username) {
		Userdetails user = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
		return user;
	}



	public boolean deleteUserById(UUID userid) {
		if (userRepository.existsById(userid)) {
	        userRepository.deleteById(userid);
	        return true;
	    } else {
	        return false;
	    }

	

}
}

		
		

