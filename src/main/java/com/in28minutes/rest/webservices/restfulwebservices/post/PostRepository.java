package com.in28minutes.rest.webservices.restfulwebservices.post;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.in28minutes.rest.webservices.restfulwebservices.user.User;

public interface PostRepository extends JpaRepository<Post, Integer> {

	
	public Optional<Post> findByIdAndUser(Integer postId, User user);
}
