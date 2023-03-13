package com.tp.filereader.rdbms.api;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import java.util.Date;

@MappedSuperclass
public abstract class BaseEntity {
	
	@Column(name = "Created_Date", insertable = true, updatable = false)
	private Long createdDate;

	public Long getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Long createdDate) {
		this.createdDate = createdDate;
	}

	@PrePersist
	private void onCreate() {
		this.setCreatedDate((new Date()).getTime());
	}

}