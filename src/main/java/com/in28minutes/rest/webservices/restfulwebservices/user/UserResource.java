package com.in28minutes.rest.webservices.restfulwebservices.user;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.in28minutes.rest.webservices.restfulwebservices.post.Post;
import com.in28minutes.rest.webservices.restfulwebservices.post.PostDaoService;
import com.in28minutes.rest.webservices.restfulwebservices.post.PostNotFoundException;

@RestController
public class UserResource {
	
	@Autowired
	private UserDaoService userDaoService;
	
	@Autowired
	private PostDaoService postDaoService;
	

	@GetMapping( path="/users")
	public List<User> retrieveAll() {
		return userDaoService.findAll();
	}
	
	@GetMapping(path="/users/{id}")
	public Resource<User> retrieveUser(@PathVariable Integer id) {
		User user = userDaoService.findOne( id );
		if (user == null) 
			throw new UserNotFoundException("id-" + id);
		
		// HATEOAS
				
		// "all-users", SERVER_PATH + "/users"
		// .retrieveAll()		
		
		Resource<User> resource = new Resource<User>(user);
		
		ControllerLinkBuilder linkTo =
				linkTo(methodOn(this.getClass()).retrieveAll());
		
		resource.add(linkTo.withRel("all-users"));
		
		return resource;
	}
	
	@DeleteMapping(path="/users/{id}")
	public void deleteUser(@PathVariable Integer id) {
		User user = userDaoService.deleteById( id );
		if (user == null) 
			throw new UserNotFoundException("id-" + id);
		
	}
	

	@PostMapping( path="/users")
	public ResponseEntity<User> save(@Valid @RequestBody User user ) {
		User savedUser = userDaoService.save(user);
		
		URI location = ServletUriComponentsBuilder
							.fromCurrentRequest()
							.path("/{id}")
							.buildAndExpand( savedUser.getId()).toUri();
		
		return ResponseEntity.created( location).build();
	}

	
	// POSTS
	/*
	@GetMapping( path="/users/{id}/posts")
	public List<Post> findAllByUser(@PathVariable Integer id) {
		//User user = userDaoService.findOne(id);
		User user = retrieveUser(id);
		return postDaoService.findAllByUser(user);
	}
	
	@GetMapping( path="/users/{id}/posts/{postId}" )
	public Post findOneByUserAndId(@PathVariable Integer id, @PathVariable Integer postId) {
		User user = retrieveUser( id );
		Post post = postDaoService.findOneByIdAndUser(postId, user);
		if ( post == null ) {
			throw new PostNotFoundException("postId-" + postId);
		}
		return post;
	}
	
	@PostMapping( path="/users/{id}/posts")
	public ResponseEntity<Post> save(@PathVariable Integer id, @RequestBody Post post ) {
		User user = userDaoService.findOne(id);
		
		post.setUser( user );
		Post savedPost = postDaoService.save(post);
		
		URI location = ServletUriComponentsBuilder
							.fromCurrentRequest()
							.path("/{postId}")
							.buildAndExpand( savedPost.getId()).toUri();
		
		return ResponseEntity.created( location).build();
	}
	*/

	
}
