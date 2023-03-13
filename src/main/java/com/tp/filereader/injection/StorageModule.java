package com.tp.filereader.injection;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.tp.filereader.storage.*;
import com.tp.filereader.storage.impl.*;

public class StorageModule implements Module {

	@Override
	public void configure(Binder binder) {
		binder.bind(DataStorage.class).to(DataJPAImpl.class);
		binder.bind(CategoryStorage.class).to(CategoryJPAImpl.class);
		binder.bind(DocumentStorage.class).to(DocumentJPAImpl.class);
	}
}