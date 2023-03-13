package com.tp.filereader.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "hashtag")
public class Hashtag {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "hashtag")
	private String hashtag;

	@Column(name = "name")
	private String name;

	@Column(name = "country")
	private String country;

	@Column(name = "url")
	private String url;

	@Column(name = "description")
	private String description;

	@Column(name = "isPublic")
	private String isPublic;

	@Column(name = "createdDate")
	private Long createdDate;

	public Integer getId() {
		return id;
	}

	public Hashtag setId(Integer id) {
		this.id = id;
		return this;
	}

	public String getHashtag() {
		return hashtag;
	}

	public Hashtag setHashtag(String hashtag) {
		this.hashtag = hashtag;
		return this;
	}

	public String getName() {
		return name;
	}

	public Hashtag setName(String name) {
		this.name = name;
		return this;
	}

	public String getCountry() {
		return country;
	}

	public Hashtag setCountry(String country) {
		this.country = country;
		return this;
	}

	public String getUrl() {
		return url;
	}

	public Hashtag setUrl(String url) {
		this.url = url;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIsPublic() {
		return isPublic;
	}

	public void setIsPublic(String isPublic) {
		this.isPublic = isPublic;
	}

	public Long getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Long createdDate) {
		this.createdDate = createdDate;
	}

	@Override
	public String toString() {
		return "Hashtag [id=" + id + ", hashtag=" + hashtag + ", name=" + name + ", country=" + country + ", url=" + url
				+ ", description=" + description + ", isPublic=" + isPublic + ", createdDate=" + createdDate + "]";
	}

}
