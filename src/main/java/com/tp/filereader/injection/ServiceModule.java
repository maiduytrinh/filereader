package com.tp.filereader.injection;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.tp.filereader.director.service.DirectoryWatchService;
import com.tp.filereader.director.service.impl.SimpleDirectoryWatchService;
import com.tp.filereader.service.CategoryService;
import com.tp.filereader.service.DataService;
import com.tp.filereader.service.DocumentService;
import com.tp.filereader.service.SearchService;
import com.tp.filereader.service.StatusService;
import com.tp.filereader.service.impl.CategoryServiceImpl;
import com.tp.filereader.service.impl.DataServiceImpl;
import com.tp.filereader.service.impl.DocumentServiceImpl;
import com.tp.filereader.service.impl.SearchServiceImpl;
import com.tp.filereader.service.impl.StatusServiceImpl;

import io.vertx.core.Vertx;

/**
 *
 */
public class ServiceModule implements Module {

	private Vertx vertx;
	public ServiceModule(Vertx vertx) {
		this.vertx = vertx;
	}

	@Override
	public void configure(Binder binder) {
		binder.bind(DataService.class).to(DataServiceImpl.class);
		binder.bind(CategoryService.class).to(CategoryServiceImpl.class);
		binder.bind(DocumentService.class).to(DocumentServiceImpl.class);
		binder.bind(SearchService.class).to(SearchServiceImpl.class);
		binder.bind(StatusService.class).to(StatusServiceImpl.class);
		binder.bind(DirectoryWatchService.class).to(SimpleDirectoryWatchService.class);
	}
}
