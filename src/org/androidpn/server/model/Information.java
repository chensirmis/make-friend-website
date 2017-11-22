package org.androidpn.server.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name="information")
public class Information {
	@Id
	 @GeneratedValue(strategy = GenerationType.AUTO)
	 private Long id;
	
	 @Column(name="apiKey",length=64)
	 private String apiKey;
	 
	 @Column(name="username",length=64)
	 private String username;
	 
	 @Column(name="title",length=64)
	 private String  title;
	 
	 @Column(name="message",length=200)
	 private String  message;
	 
	@Column(name="uri",length=64)
	private String uri;
	 
	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	 
	 public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}
		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

}
