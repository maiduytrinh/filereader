package com.tp.filereader.entity;

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
@Table(name = "documents")
@DataObject(generateConverter = true)
public class Documents {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "name")
	private String name;

	@Column(name = "keyword")
	@JsonIgnore
	private String keyword;

	@Column(name = "country")
	private String country;

	@Column(name = "categories")
	private String categories;

	@Column(name = "url")
	private String url;

	@Column(name = "owner")
	private String owner;

	@Column(name = "fileSize")
	private String fileSize;

	@Column(name = "fileType")
	private String fileType;

	@Column(name = "hashtags")
	private String hashtags;

	@Column(name = "subDescription")
	private String subDescription;

	@Column(name = "description")
	private String description;

	@Column(name = "pageNumber")
	private Integer pageNumber;

	@Column(name = "downcount")
	private Integer downcount;

	@Column(name = "viewcount")
	private Integer viewcount;

	@Column(name = "copyRightPoint")
	@JsonIgnore
	private Integer copyRightPoint;

	@Column(name = "isPublic")
	@JsonIgnore
	private String isPublic;

	@Column(name = "trendOrder")
	@JsonIgnore
	private Integer trendOrder;

	@Column(name = "lastViewDate")
	@JsonIgnore
	private Long lastViewDate;

	@Column(name = "downDate")
	@JsonIgnore
	private Long downDate;

	@Column(name = "createdDate")
	@JsonIgnore
	private Long createdDate;
	
	
	public Documents() {
		super();
	}
	
	public Documents(JsonObject json) {
		DocumentsConverter.fromJson(json, this);
	}


	public static Documents valueOf(String json) {
		return AppUtils.fromJson(json, Documents.class);
	}
	
	public Integer getId() {
		return id;
	}

	public Documents setId(Integer id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public Documents setName(String name) {
		this.name = name;
		return this;
	}

	public String getKeyword() {
		return keyword;
	}

	public Documents setKeyword(String keyword) {
		this.keyword = keyword;
		return this;
	}

	public String getCountry() {
		return country;
	}

	public Documents setCountry(String country) {
		this.country = country;
		return this;
	}

	public String getCategories() {
		return categories;
	}

	public Documents setCategories(String categories) {
		this.categories = categories;
		return this;
	}

	public String getUrl() {
		return url;
	}

	public Documents setUrl(String url) {
		this.url = url;
		return this;
	}

	public String getOwner() {
		return owner;
	}

	public Documents setOwner(String owner) {
		this.owner = owner;
		return this;
	}

	public String getFileSize() {
		return fileSize;
	}

	public Documents setFileSize(String fileSize) {
		this.fileSize = fileSize;
		return this;
	}

	public String getFileType() {
		return fileType;
	}

	public Documents setFileType(String fileType) {
		this.fileType = fileType;
		return this;
	}

	public String getHashtags() {
		return hashtags;
	}

	public Documents setHashtags(String hashtags) {
		this.hashtags = hashtags;
		return this;
	}

	public String getSubDescription() {
		return subDescription;
	}

	public Documents setSubDescription(String subDescription) {
		this.subDescription = subDescription;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public Documents setDescription(String description) {
		this.description = description;
		return this;
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public Documents setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
		return this;
	}

	public Integer getDowncount() {
		return downcount;
	}

	public Documents setDowncount(Integer downcount) {
		this.downcount = downcount;
		return this;
	}

	public Integer getViewcount() {
		return viewcount;
	}

	public Documents setViewcount(Integer viewcount) {
		this.viewcount = viewcount;
		return this;
	}

	public Integer getCopyRightPoint() {
		return copyRightPoint;
	}

	public Documents setCopyRightPoint(Integer copyRightPoint) {
		this.copyRightPoint = copyRightPoint;
		return this;
	}

	public String getIsPublic() {
		return isPublic;
	}

	public Documents setIsPublic(String isPublic) {
		this.isPublic = isPublic;
		return this;
	}

	public Integer getTrendOrder() {
		return trendOrder;
	}

	public Documents setTrendOrder(Integer trendOrder) {
		this.trendOrder = trendOrder;
		return this;
	}

	public Long getLastViewDate() {
		return lastViewDate;
	}

	public Documents setLastViewDate(Long lastViewDate) {
		this.lastViewDate = lastViewDate;
		return this;
	}

	public Long getDownDate() {
		return downDate;
	}

	public Documents setDownDate(Long downDate) {
		this.downDate = downDate;
		return this;
	}

	public Long getCreatedDate() {
		return createdDate;
	}

	public Documents setCreatedDate(Long createdDate) {
		this.createdDate = createdDate;
		return this;
	}

	@Override
	public String toString() {
		return "Document [id=" + id + ", name=" + name + ", keyword=" + keyword + ", country=" + country
				+ ", categories=" + categories + ", url=" + url + ", owner=" + owner + ", fileType=" + fileType
				+ ", hashtags=" + hashtags + ", subDescription=" + subDescription + ", downcount=" + downcount
				+ ", viewcount=" + viewcount + "]";
	}

}
