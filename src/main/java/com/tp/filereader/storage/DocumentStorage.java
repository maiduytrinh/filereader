package com.tp.filereader.storage;

import java.util.List;

import com.tp.filereader.common.ResultContext;
import com.tp.filereader.entity.Documents;
import com.tp.filereader.rdbms.api.GenericJPA;

public interface DocumentStorage extends GenericJPA<Documents, Integer>{
	public ResultContext<List<Documents>> getListDocumentOfCategory(String category, Integer limit);
	
}
