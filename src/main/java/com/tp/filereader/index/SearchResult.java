package com.tp.filereader.index;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tp.filereader.logger.TPLogger;

public class SearchResult {

	private static final Logger LOG = TPLogger.getLogger(SearchResult.class);
	
	private int id;
	private String name;
	private String keyword;
	private String country;
	private String categories;
	private String url;
	private String owner;
	private String fileSize;
	private String fileType;
	private String hashtags;
	private String subDescription;
	private String description;
	private Integer pageNumber;
	private Integer downcount;
	private Integer viewcount;
	@JsonIgnore
	private String isPublic;

	public SearchResult(String id, String name, String keyword, String country, String categories, String url,
			String owner, String fileSize, String fileType, String hashtags, String subDescription, String description,
			String pageNumber, String downcount, String viewcount, String isPublic) {
		super();
		try {
			this.id = StringUtils.isNumeric(id) ? Integer.valueOf(id) : 999999;
			this.name = name;
			this.keyword = keyword;
			this.country = country;
			this.categories = categories;
			this.url = url;
			this.owner = owner;
			this.fileSize = fileSize;
			this.fileType = fileType;
			this.hashtags = hashtags;
			this.subDescription = subDescription;
			this.description = description;
			this.pageNumber = StringUtils.isNumeric(pageNumber) ? Integer.valueOf(pageNumber) : 0;
			this.downcount = StringUtils.isNumeric(downcount) ? Integer.valueOf(downcount) : 0;
			this.viewcount = StringUtils.isNumeric(viewcount) ? Integer.valueOf(viewcount) : 0;
			this.isPublic = isPublic;
		} catch (Exception e) {
			LOG.info("Error convert id {} , countDown {} , viewcount {}", id, downcount, viewcount);
		}
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCategories() {
		return categories;
	}

	public void setCategories(String categories) {
		this.categories = categories;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getHashtags() {
		return hashtags;
	}

	public void setHashtags(String hashtags) {
		this.hashtags = hashtags;
	}

	public String getSubDescription() {
		return subDescription;
	}

	public void setSubDescription(String subDescription) {
		this.subDescription = subDescription;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public Integer getDowncount() {
		return downcount;
	}

	public void setDowncount(Integer downcount) {
		this.downcount = downcount;
	}

	public Integer getViewcount() {
		return viewcount;
	}

	public void setViewcount(Integer viewcount) {
		this.viewcount = viewcount;
	}
	
	public String getIsPublic() {
		return isPublic;
	}

	public void setIsPublic(String isPublic) {
		this.isPublic = isPublic;
	}
	
	public boolean checkIsPublic() {
		try {
			if(Integer.valueOf(this.isPublic) > 0) {
				return true;
			}
			return false;
		} catch (Exception e) {
			LOG.error("check is public exception");
		}
		return false;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof SearchResult) {
			return (((SearchResult)obj).getId()) == id || ((SearchResult)obj).getUrl().equals(this.getUrl());
		}
		return super.equals(obj);
	}

	@Override
	public String toString() {
		return "SearchResult [id=" + id + ", name=" + name + ", keyword=" + keyword + ", country=" + country
				+ ", categories=" + categories + ", url=" + url + ", isPublic=" + isPublic + "]";
	}
	
}
