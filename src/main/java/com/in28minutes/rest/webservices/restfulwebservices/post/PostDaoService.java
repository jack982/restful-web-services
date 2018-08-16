package com.in28minutes.rest.webservices.restfulwebservices.post;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.in28minutes.rest.webservices.restfulwebservices.user.User;

@Component
public class PostDaoService {

	private static List<Post> posts = new ArrayList<Post>();

	private static int postCount = 3;
/*
	static {
		posts.add(new Post(1, "Post 1", new Date(), new User(1, "Adam", new Date())));
		posts.add(new Post(2, "Post 2", new Date(), new User(2, "Eve", new Date())));
		posts.add(new Post(3, "Post 3", new Date(), new User(1, "Adam", new Date())));
	}
*/
	public List<Post> findAll() {
		return posts;
	}

	public List<Post> findAllByUser(User user) {
		List<Post> postsByUser = new ArrayList<Post>();
		for (Post p : posts) {
			if (p.getUser().getId().equals(user.getId())) {
				postsByUser.add(p);
			}
		}
		return postsByUser;
	}

	public Post save(Post post) {
		if (post.getId() == null) {
			post.setId(++postCount);
		}
		posts.add(post);
		return post;
	}

	public Post findOne(int id) {
		for (Post p : posts) {
			if (p.getId().equals(id)) {
				return p;
			}
		}
		return null;
	}

	public Post findOneByIdAndUser(int id, User user) {
		for (Post p : posts) {
			if (p.getId().equals(id) && p.getUser().getId().equals( user.getId())) {
				return p;
			}
		}
		return null;
	}

}