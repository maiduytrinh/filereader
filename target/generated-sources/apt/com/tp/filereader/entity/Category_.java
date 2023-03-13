package com.tp.filereader.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Category.class)
public abstract class Category_ {

	public static volatile SingularAttribute<Category, String> country;
	public static volatile SingularAttribute<Category, Integer> documentCount;
	public static volatile SingularAttribute<Category, Long> createdDate;
	public static volatile SingularAttribute<Category, String> thumb;
	public static volatile SingularAttribute<Category, String> name;
	public static volatile SingularAttribute<Category, String> isPublic;
	public static volatile SingularAttribute<Category, Integer> catOrder;
	public static volatile SingularAttribute<Category, String> rootHashtag;
	public static volatile SingularAttribute<Category, Integer> id;

	public static final String COUNTRY = "country";
	public static final String DOCUMENT_COUNT = "documentCount";
	public static final String CREATED_DATE = "createdDate";
	public static final String THUMB = "thumb";
	public static final String NAME = "name";
	public static final String IS_PUBLIC = "isPublic";
	public static final String CAT_ORDER = "catOrder";
	public static final String ROOT_HASHTAG = "rootHashtag";
	public static final String ID = "id";

}

