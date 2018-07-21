package com.in28minutes.rest.webservices.restfulwebservices.post;

import java.util.Date;

import com.in28minutes.rest.webservices.restfulwebservices.user.User;

public class Post {

	private Integer id;
	private String title;
	private Date timestamp;
	private User user;
	
	private Post() {}
	
	

	public Post(Integer id, String title, Date timestamp, User user) {
		super();
		this.id = id;
		this.title = title;
		this.timestamp = timestamp;
		this.user = user;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Post [id=" + id + ", title=" + title + ", timestamp=" + timestamp + ", user=" + user + "]";
	}
	
	
	
	
}
