package com.codestorykh;

import com.codestorykh.model.User;
import com.codestorykh.repository.UserRepository;
import com.codestorykh.service.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringDataHibernate7Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringDataHibernate7Application.class, args);
	}

	@Autowired
	private UserService userService;
	@Autowired
	private UserRepository userRepository;

	@PostConstruct
	public void init() {
		User user = new User();
		user.setFirstName("Hello");
		user.setLastName("World");
		user.setEmail("hello@gmail.com");
		user.setPhoneNumber("089254766");
		user.setDepartment("IT");

		userRepository.saveAndFlush(user);


		User findUserByEmail = userService.findUserByEmail("hello@gail.com");
		System.out.println(findUserByEmail);

		var findUserByEmail2 = userService.getFullNameByEmail("hello@gmail.com");
		System.out.println("Full name: " + findUserByEmail2);

		System.out.println("Users: " + userService.getUsers());
	}
}
