package com.tp.filereader.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Data.class)
public abstract class Data_ extends com.tp.filereader.rdbms.api.BaseEntity_ {

	public static volatile SingularAttribute<Data, Integer> idJob;
	public static volatile SingularAttribute<Data, String> name;
	public static volatile SingularAttribute<Data, Integer> id;

	public static final String ID_JOB = "idJob";
	public static final String NAME = "name";
	public static final String ID = "id";

}

