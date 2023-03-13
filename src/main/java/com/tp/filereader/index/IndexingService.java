package com.tp.filereader.index;

import com.tp.filereader.entity.Documents;

public class IndexingService {

	public static void initKeyword() {
		final AbstractIndex<Documents, SearchResult> keywordIndex = new KeywordIndex();
		keywordIndex.init();
	}

}
