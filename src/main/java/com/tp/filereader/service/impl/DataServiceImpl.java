package com.tp.filereader.service.impl;

import javax.inject.Inject;

import com.tp.filereader.entity.Data;
import com.tp.filereader.rdbms.api.BaseService;
import com.tp.filereader.service.DataService;
import com.tp.filereader.storage.DataStorage;

public class DataServiceImpl extends BaseService<Data> implements DataService {


	@Inject
	private DataStorage dataStorage;

}
