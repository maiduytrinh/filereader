package com.tp.filereader.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tp.filereader.utils.AppUtils;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@Entity
@Table(name="categories")
@DataObject(generateConverter = true)
public class Category implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;

	@Column(name="name")
	private String name;

	@Column(name="thumb")
	private String thumb;

	@Column(name="country")
	private String country;

	@Column(name="isPublic")
	@JsonIgnore
	private String isPublic;

	@Column(name="rootHashtag")
	private String rootHashtag;
	
	@Column(name="createdDate")
	@JsonIgnore
	private Long createdDate;

	@Column(name="documentCount")
	@JsonIgnore
	private Integer documentCount;

	@Column(name="catOrder")
	@JsonIgnore
	private Integer catOrder;
	
	public Category() {
	}
	
	public Category(JsonObject json) {
		CategoryConverter.fromJson(json, this);
	}
	
	public static Category valueOf(String json) {
		return AppUtils.fromJson(json, Category.class);
	}
	
	public JsonObject toJson() {
		JsonObject json = new JsonObject();
		CategoryConverter.toJson(this, json);
		return json;
	}

	public Integer getId() {
		return id;
	}

	public Category setId(Integer id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public Category setName(String name) {
		this.name = name;
		return this;
	}

	public String getThumb() {
		return thumb;
	}

	public Category setThumb(String thumb) {
		this.thumb = thumb;
		return this;
	}

	public String getCountry() {
		return country;
	}

	public Category setCountry(String country) {
		this.country = country;
		return this;
	}

	public String getIsPublic() {
		return isPublic;
	}

	public Category setIsPublic(String isPublic) {
		this.isPublic = isPublic;
		return this;
	}

	public String getRootHashtag() {
		return rootHashtag;
	}
	

	public Category setRootHashtag(String rootHashtag) {
		this.rootHashtag = rootHashtag;
		return this;
	}
	
	public Long getCreatedDate() {
		return createdDate;
	}

	public Category setCreatedDate(Long createdDate) {
		this.createdDate = createdDate;
		return this;
	}

	public Integer getDocumentCount() {
		return documentCount;
	}

	public Category setDocumentCount(Integer documentCount) {
		this.documentCount = documentCount;
		return this;
	}

	public Integer getCatOrder() {
		return catOrder;
	}

	public Category setCatOrder(Integer catOrder) {
		this.catOrder = catOrder;
		return this;
	}

	@Override
	public String toString() {
		return "Category [id=" + id + ", name=" + name + ", thumb=" + thumb + ", country=" + country
				+ ", documentCount=" + documentCount + ", catOrder=" + catOrder + "]";
	}
	
	

}
