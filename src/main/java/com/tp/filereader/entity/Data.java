package com.tp.filereader.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.tp.filereader.rdbms.api.BaseEntity;
import com.tp.filereader.utils.AppUtils;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@Entity
@Table(name = "Data")
@DataObject(generateConverter = true)
public class Data extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "idData")
    private Integer id;

	@Column(name = "nameData")
    private String name;
	
	@Column(name = "idJob")
    private Integer idJob;
	
	public Data() {
    }

    public Data(JsonObject json) {
        DataConverter.fromJson(json, this);
    }

    public static Data valueOf(String json) {
        return AppUtils.fromJson(json, Data.class);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        DataConverter.toJson(this, json);
        return json;
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
    
    public Integer getIdJob() {
		return idJob;
	}

	public void setIdJob(Integer idJob) {
		this.idJob = idJob;
	}
}
