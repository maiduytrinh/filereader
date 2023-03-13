package com.tp.filereader.service;

import java.util.List;

import com.tp.filereader.common.ResultContext;
import com.tp.filereader.entity.Category;

public interface CategoryService {
	public ResultContext<List<Category>> getListCategory(String country);
}
