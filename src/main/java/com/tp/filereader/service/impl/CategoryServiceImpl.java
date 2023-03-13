package com.tp.filereader.service.impl;

import java.util.List;

import javax.inject.Inject;

import com.tp.filereader.common.ResultContext;
import com.tp.filereader.entity.Category;
import com.tp.filereader.rdbms.api.BaseService;
import com.tp.filereader.service.CategoryService;
import com.tp.filereader.storage.CategoryStorage;

public class CategoryServiceImpl extends BaseService<Category> implements CategoryService{

	@Inject
	private CategoryStorage categoryStorage;
	
	@Override
	public ResultContext<List<Category>> getListCategory(String country) {
		// TODO Auto-generated method stub
		return categoryStorage.getListCategory(country);
	} 

}
