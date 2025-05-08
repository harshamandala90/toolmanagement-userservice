package com.taskmanagement.tool.model;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_registration")
public class User  {
	    @Id
	    @GeneratedValue
        @Column(name = "user_id",nullable = false, unique = true)
	    private UUID userid;
        
	    @Column(name = "email",nullable = false, unique = true)
	    private String email;
	    
    

		@Column(name = "first_name",nullable = false, unique = true)
	    private String firstName;
        
        @Column(name = "last_name",nullable = false, unique = true)
	    private String lastName;
        
        @Column(name = "date_created",nullable = true, unique = true)
   	    private LocalDateTime  dateCreated;
        
        @Column(name = "date_modified",nullable = true, unique = true)
   	    private LocalDateTime  dateModified;
        	
        @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
        private Userdetails userDetails;
      
        
        
		public Userdetails getUserDetails() {
			return userDetails;
		}

		public void setUserDetails(Userdetails userDetails) {
			this.userDetails = userDetails;
		}

		public UUID getUserid() {
			return userid;
		}

		public void setUserid(UUID userid) {
			this.userid = userid;
		}

		public String getFirstName() {
			return firstName;
		}

		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		public String getLastName() {
			return lastName;
		}

		public void setLastName(String lastName) {
			this.lastName = lastName;
		}

		public LocalDateTime getDateCreated() {
			return dateCreated;
		}

		public void setDateCreated(LocalDateTime dateCreated) {
			this.dateCreated = dateCreated;
		}

		public LocalDateTime getDatemodified() {
			return dateModified;
		}

		public void setDatemodified(LocalDateTime dateModified) {
			this.dateModified = dateModified;
		}
	    public String getEmail() {
				return email;
			}

			public void setEmail(String email) {
				this.email = email;
			}
		    
		  
		}
		
		
		
		



