package com.tp.filereader.service;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import com.tp.filereader.common.ResultContext;
import com.tp.filereader.entity.Documents;

public interface DocumentService {
	
	public ResultContext<List<Documents>> getListDocumentOfCategory(String category, Integer limit);
	
	public ResultContext<Documents> recordDownFile(int s);
	
	public ResultContext<Documents> recordViewFile(int s);
	
	public void start();
}
