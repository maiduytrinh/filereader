package com.tp.filereader.storage;

import java.util.List;

import com.tp.filereader.common.ResultContext;
import com.tp.filereader.entity.Category;
import com.tp.filereader.rdbms.api.GenericJPA;

public interface CategoryStorage extends GenericJPA<Category, Integer> {
	public ResultContext<List<Category>> getListCategory(String country);
}
