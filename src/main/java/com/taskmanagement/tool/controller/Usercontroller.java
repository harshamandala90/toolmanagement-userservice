package com.taskmanagement.tool.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.taskmanagement.tool.dto.Userdetailsdto;
import com.taskmanagement.tool.model.User;
import com.taskmanagement.tool.model.Userdetails;
import com.taskmanagement.tool.repository.Jpauserrepository;
import com.taskmanagement.tool.service.CustomUserDetailsService;
import com.taskmanagement.tool.service.Userservice;
import com.taskmanagement.tool.util.Passwordutil;

@RestController
@RequestMapping("/api/v1/auth")
public class Usercontroller {

	@Autowired
    private Userservice userService;
	
	@Autowired
    private CustomUserDetailsService userDetailsService;

	@Autowired
    private BCryptPasswordEncoder passwordEncoder;
	
	private static final Logger logger = LoggerFactory.getLogger(Usercontroller.class);
	private final ObjectMapper objectMapper = new ObjectMapper();
	
	@PostMapping("/registeruser")
    @ResponseBody
     public ResponseEntity<ObjectNode> userregistration(@RequestBody Userdetailsdto userdetailsdto)
    {
		logger.info("Just entered userregistration() method");
		try {
			
		
		Userdetails userDetails = new Userdetails();
		User user =new User();
	
		user.setFirstName(userdetailsdto.getFirstName());
		user.setLastName(userdetailsdto.getLastName());
		user.setEmail(userdetailsdto.getemail());
		user.setDateCreated(LocalDateTime.now());
		userDetails.setUsername(userdetailsdto.getUsername());
		userDetails.setPassword(passwordEncoder.encode(userdetailsdto.getPassword()));
		userDetails.setRoles(userdetailsdto.getRoles());
		userDetails.setUser(user);
		user.setUserDetails(userDetails);
		userService.saveUser(user);
		
		
		 ObjectNode userNode = objectMapper.createObjectNode();
	        userNode.put("uuid",user.getUserid().toString());
	        userNode.put("firstname", user.getFirstName());
	        userNode.put("lastname", user.getLastName());
	        userNode.put("email", user.getEmail());
	     

	        ObjectNode responseNode = objectMapper.createObjectNode();
	        responseNode.put("message", "User created successfully.");
	        responseNode.set("user", userNode);

		logger.info("Just leaving userregistration() method from Usercontroller");
		return ResponseEntity.ok(responseNode);
	    } catch (Exception e) {
	    	
	    	logger.debug("Exception in userergistration() method" + e.getMessage());
	    	
	    	
	    	ObjectMapper mapper = new ObjectMapper();
	    	ObjectNode errorResponse = mapper.createObjectNode();
	    	errorResponse.put("error", "User registration failed");
	    	errorResponse.put("details", e.getMessage());

	    	return ResponseEntity
	    	        .status(HttpStatus.INTERNAL_SERVER_ERROR)
	    	        .body(errorResponse);
	    }
        
		
    }
	
		@GetMapping("/users/{userid}")
	    @ResponseBody
	    public ResponseEntity<?> getUserById(@PathVariable UUID userid) {
			try {
				
			logger.info("Just entered getUserById() method for userid:{}",userid);
			Optional<User> userdetailslist = userService.getbyuserid(userid);
	        if (userdetailslist.isPresent()) {
	            return ResponseEntity.ok(userdetailslist.get());
	        } else {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found with ID: " + userid);
	        }
	        
			}catch (Exception e) {
		    	logger.debug("Exception in getUserById() method" + e.getMessage());
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
		                             .body("User fetching failed: " + e.getMessage());
		    }
			
	        
	    }
	


	
	@PatchMapping("/users/{userid}")
    @ResponseBody
    public ResponseEntity<String> updateuserbyid(@RequestBody Userdetailsdto userdetailsdto,@PathVariable UUID userid)
    {
		logger.info("Just entered updateuserbyid() method for user:{}",userid);
		try {
			
			Optional<User> userdetailslist = userService.getbyuserid(userid);
		
		if (userdetailslist.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found with ID: " + userid);
        }
		
		User user = userdetailslist.get();
		
		Userdetails userdetails = 
		user.getUserDetails();
		
		
		user.setUserid(userid);
		
//		System.out.println(userdetails.get);
		
		
		user.setDatemodified(LocalDateTime.now());
		
		 if (userdetailsdto.getFirstName()!= null) {
			 user.setFirstName(userdetailsdto.getFirstName());	
	        }
		
		 
		 if (userdetailsdto.getLastName()!= null) {
			 user.setLastName(userdetailsdto.getLastName());	
	        }
		 
		 if (userdetailsdto.getemail()!= null) {
			 user.setEmail(userdetailsdto.getemail());
	        }
		 
		 
		 if (userdetailsdto.getRoles()!= null) {
			 userdetails.setRoles(userdetailsdto.getRoles());
	        }
		
		
		
		userdetails.setUser(user);
		
		user.setUserDetails(userdetails);
		userService.updateUser(user);
		
		logger.info("Just leaving updateuserbyid() method for user:{}",userid);
		return ResponseEntity.ok("User successfully updated");
		
		} catch (Exception e) {
			logger.debug("Exception in updateuserbyid() method" + e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating user: " + e.getMessage());
	    }
}
		
	
	

	
	

	
	
	
	@GetMapping("/users/username/{username}")
    @ResponseBody
    public ResponseEntity<?> getUserdetailsbyusername(@PathVariable String username) {
		try {
			
		logger.info("Just entered getUserById() method for userid:{}",username);
		Userdetails userDetails = userService.loadUserByUsername(username);
		Userdetailsdto userdetailsdto = new Userdetailsdto();
		userdetailsdto.setUsername(userDetails.getUsername());
		userdetailsdto.setPassword(userDetails.getPassword());
		userdetailsdto.setRoles(userDetails.getRoles());
		userdetailsdto.setId(userDetails.getId().toString());
		return ResponseEntity.ok(userdetailsdto);
		}catch (Exception e) {
	    	logger.debug("Exception in getUserById() method" + e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                             .body("User fetching failed: " + e.getMessage());
	    }
		
        
    }
	
	
//	@DeleteMapping("/users/{userid}")
//	public ResponseEntity<String> deleteTask(@PathVariable UUID userid) {
//	    boolean isDeleted = userService.deleteUserById(userid);
//	    
//	    if (isDeleted) {
//	        return ResponseEntity.ok("User successfully deleted");
//	      
//	    } else {
//	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
//	                             .body("user not found with ID to delete: " + userid);
//	    }
//	}
}


