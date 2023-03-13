package com.tp.filereader.service.impl;

import java.util.List;
import java.util.TimerTask;

import javax.inject.Inject;

import com.tp.filereader.common.ResultContext;
import com.tp.filereader.entity.Documents;
import com.tp.filereader.rdbms.api.BaseService;
import com.tp.filereader.service.DocumentService;
import com.tp.filereader.storage.DocumentStorage;
import com.tp.filereader.storage.impl.DocumentJPAImpl;

public class DocumentServiceImpl extends BaseService<Documents> implements DocumentService {

	@Inject
	private DocumentStorage documentStorage;

	@Override
	public ResultContext<List<Documents>> getListDocumentOfCategory(String category, Integer limit) {
		// TODO Auto-generated method stub
		return documentStorage.getListDocumentOfCategory(category, limit);
	}

	@Override
	public ResultContext<Documents> recordDownFile(int s) {
		// TODO Auto-generated method stub
		boolean isExist = false;
		for (Documents file : documentsUpdate) {
			if (s == file.getId()) {
				file.setDowncount(file.getDowncount() + 1);
				isExist = true;
			}
		}
		if (!isExist) {
			ResultContext<Documents> documentContext = documentStorage.find(s);
			if (documentContext.succeeded()) {
				Documents fileAdd = documentContext.result();
				fileAdd.setDowncount(fileAdd.getDowncount() + 1);
				documentsUpdate.add(fileAdd);
				System.out.println(documentsUpdate.toString());
				if (documentsUpdate.size() >= 3) {
					boolean begunTx = beginTx();
					try {
						documentStorage.updateAll(documentsUpdate);
						documentsUpdate.clear();
						return ResultContext.succeededResult();
					} catch (Exception e) {
						return ResultContext.failedResult(e);
					} finally {
						endTx(begunTx);
					}
				}
				return ResultContext.succeededResult();
			} else {
				return ResultContext.notFoundResult("NOT FOUND");
			}
		}
		System.out.println(documentsUpdate.toString());
		return ResultContext.succeededResult();
		

//		boolean begunTx = beginTx();
//		try {
//			Documents document = new Documents();
//			if (documentStorage.find(s).succeeded()) {
//				document = documentStorage.find(s).result();
//				document.setDowncount(document.getDowncount() + 1);
//				return documentStorage.update(document);
//			} else {
//				return ResultContext.notFoundResult("NOT FOUND");
//			}
//		} finally {
//			endTx(begunTx);
//		}
	}

	@Override
	public ResultContext<Documents> recordViewFile(int s) {
		// TODO Auto-generated method stub
		ResultContext<Documents> documentContext = documentStorage.find(s);
		if (documentContext.succeeded()) {
//			check tồn tại trong list
			boolean isExist = false;
			for (Documents file : documentsUpdate) {
				if (s == file.getId()) {
					file.setViewcount(file.getViewcount() + 1);
					isExist = true;
				}
			}
//			không tồn tại sẽ add list
			if (!isExist) {
				Documents fileAdd = documentContext.result();
				fileAdd.setViewcount(fileAdd.getViewcount() + 1);
				documentsUpdate.add(fileAdd);
				if (documentsUpdate.size() >= 3) {
					boolean begunTx = beginTx();
					try {
						documentStorage.updateAll(documentsUpdate);
						documentsUpdate.clear();
						return ResultContext.succeededResult();
					} catch (Exception e) {
						return ResultContext.failedResult(e);
					} finally {
						endTx(begunTx);
						
					}
				}
			}
			return ResultContext.succeededResult();
		} else {
			return ResultContext.notFoundResult("NOT FOUND");
		}

//		boolean begunTx = beginTx();
//		try {
//			Documents document = new Documents();
//			if (documentStorage.find(s).succeeded()) {
//				document = documentStorage.find(s).result();
//				document.setViewcount(document.getViewcount() + 1);
//				return documentStorage.update(document);
//			} else {
//				return ResultContext.notFoundResult("NOT FOUND");
//			}
//		} finally {
//			endTx(begunTx);
//		}
	}

	@Override
	public TimerTask autoRecordFile() {
		return new TimerTask() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if (documentsUpdate != null && !documentsUpdate.isEmpty()) {
					DocumentStorage documentStorage1 = new DocumentJPAImpl();
					boolean begunTx = beginTx();
					try {
						documentStorage1.updateAll(documentsUpdate);
						documentsUpdate.clear();
						LOG.info("Update ListRecord Success: ");
					} catch (Exception e) {
						LOG.error("Update ListRecord Error: " + e);
					} finally {
						endTx(begunTx);
						
					}
				}
			}
		};
	}

}
