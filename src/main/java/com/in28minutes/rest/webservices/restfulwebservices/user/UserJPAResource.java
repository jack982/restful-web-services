package com.in28minutes.rest.webservices.restfulwebservices.user;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.in28minutes.rest.webservices.restfulwebservices.post.Post;
import com.in28minutes.rest.webservices.restfulwebservices.post.PostNotFoundException;
import com.in28minutes.rest.webservices.restfulwebservices.post.PostRepository;

@RestController
public class UserJPAResource {
	
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	
	@GetMapping( path="/jpa/users")
	public List<User> retrieveAll() {
		return userRepository.findAll();
	}
	
	@GetMapping(path="/jpa/users/{id}")
	public Resource<User> retrieveUser(@PathVariable Integer id) {
		Optional<User> user = userRepository.findById( id );
		if (!user.isPresent()) 
			throw new UserNotFoundException("id-" + id);
		
		// HATEOAS
				
		// "all-users", SERVER_PATH + "/users"
		// .retrieveAll()		
		
		Resource<User> resource = new Resource<User>(user.get());
		
		ControllerLinkBuilder linkTo =
				linkTo(methodOn(this.getClass()).retrieveAll());
		
		resource.add(linkTo.withRel("all-users"));
		
		return resource;
	}
	
	@DeleteMapping(path="/jpa/users/{id}")
	public void deleteUser(@PathVariable Integer id) {
		userRepository.deleteById( id );
	}
	

	@PostMapping( path="/jpa/users")
	public ResponseEntity<User> save(@Valid @RequestBody User user ) {
		User savedUser = userRepository.save(user);
		
		URI location = ServletUriComponentsBuilder
							.fromCurrentRequest()
							.path("/{id}")
							.buildAndExpand( savedUser.getId()).toUri();
		
		return ResponseEntity.created( location).build();
	}

	
	// POSTS
	
	@GetMapping( path="/jpa/users/{id}/posts")
	public List<Post> findAllByUser(@PathVariable Integer id) {
		Optional<User> user = userRepository.findById( id );
		
		if ( !user.isPresent() ) {
			throw new UserNotFoundException("id - " + id);
		}
		
		
		List<Post> posts = user.get().getPosts();
		return posts;
	}
	
	
	@GetMapping( path="/jpa/users/{id}/posts/{postId}" )
	public Post findOneByUserAndId(@PathVariable Integer id, @PathVariable Integer postId) {
		Optional<User> userOpt = userRepository.findById( id );
		
		if ( !userOpt.isPresent() ) {
			throw new UserNotFoundException("id-"+id);
		}
		User user = userOpt.get();
		Optional<Post> postOpt = postRepository.findByIdAndUser(postId, user);
		
		if ( !postOpt.isPresent()) {
			throw new PostNotFoundException("postId-" + postId);
		}
		return postOpt.get();
	}
	
	@PostMapping( path="/jpa/users/{id}/posts")
	public ResponseEntity<Post> save(@PathVariable Integer id, @RequestBody Post post ) {
		Optional<User> userOptional = userRepository.findById(id);
		
		if ( !userOptional.isPresent() ) {
			throw new UserNotFoundException("id-"+ id);
		}
		
		User user = userOptional.get();
		
		post.setUser( user );
		
		
		Post savedPost = postRepository.save(post);
		
		URI location = ServletUriComponentsBuilder
							.fromCurrentRequest()
							.path("/{postId}")
							.buildAndExpand( savedPost.getId()).toUri();
		
		return ResponseEntity.created( location).build();
	}
	

	
}
