package com.spring.boot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.spring.boot.dto.User;
import com.spring.boot.repository.UserRepository;

@RestController    // This means that this class is a Controller
@RequestMapping(path="/users") // This means URL's start with /demo (after Application path)
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	
	@PostMapping()
	public @ResponseBody String addNewUser (@RequestBody User user) {
		System.out.println("UserController.addNewUser():::::::::::::"+user);
		userRepository.save(user);
		return "Saved";
	}
	
	@GetMapping()
	public @ResponseBody Iterable<User> getAllUsers() {
		return userRepository.findAll();
	}
	
	@PutMapping()
	public @ResponseBody String update(@RequestBody User user) {
		if(userRepository.exists(user.getId())) {
			userRepository.save(user);
			return "updated";
		} else {
			return "No such user";
		}
	}
	
	@PatchMapping()
	public ResponseEntity<?> patchUpdate(@RequestBody User user) {
		System.out.println("UserController.patchUpdate():::::"+user);
		if(userRepository.exists(user.getId())) {
			User u = userRepository.findOne(user.getId());
			userRepository.save(user);
			return ResponseEntity.ok("updated");
		} else {
			return ResponseEntity.ok("No such User");
		}
	}
	
	@DeleteMapping()
	public @ResponseBody String delete(@RequestParam Long id) {
		try {
			userRepository.delete(id);
			return "User deleted";
		} catch(Exception e) {
			return ""+e.getMessage();
		}
		
	}
	
	public void patch(User user) {
		User u = userRepository.findOne(user.getId());
		if(user.getName() != null) {
			u.setName(user.getName());
		} else if(user.getEmail() != null) {
			u.setEmail(user.getEmail());
		}
		userRepository.save(u);
	}
}
