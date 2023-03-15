package com.tp.filereader.service.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.*;

import javax.inject.Inject;

import com.tp.filereader.common.ResultContext;
import com.tp.filereader.entity.Documents;
import com.tp.filereader.rdbms.api.BaseService;
import com.tp.filereader.rest.common.ResponseBase;
import com.tp.filereader.service.DocumentService;
import com.tp.filereader.storage.DocumentStorage;
import com.tp.filereader.storage.impl.DocumentJPAImpl;

public class DocumentServiceImpl extends BaseService<Documents> implements DocumentService {
	private static ScheduledFuture<?> futureSaveActionHandle;
	private static List<Documents> documentsUpdate = new LinkedList<>();
	private static Boolean isSaveAll = false;
	private static int countProcess = 0;
	private static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

	@Inject
	private DocumentStorage documentStorage;

	@Override
	public ResultContext<List<Documents>> getListDocumentOfCategory(String category, Integer limit) {
		// TODO Auto-generated method stub
		return documentStorage.getListDocumentOfCategory(category, limit);
	}
	
	@Override
	public ResultContext<Documents> recordViewFile(int s) {
		// TODO Auto-generated method stub
		boolean isExist = false;
		for (Documents file : getDocumentsUpdate()) {
			if (s == file.getId()) {
				file.setViewcount(file.getViewcount() + 1);
				isExist = true;
			}
		}
		if (!isExist) {
			ResultContext<Documents> documentContext = documentStorage.find(s);
			if (documentContext.succeeded()) {
				Documents fileAdd = documentContext.result();
				fileAdd.setViewcount(fileAdd.getViewcount() + 1);
				getDocumentsUpdate().add(fileAdd);
				System.out.println("Document added: " + documentsUpdate.toString());
				if (documentsUpdate.size() >= 3) {
					upDateListRecord();
				}
				return ResultContext.succeededResult();
			} else {
				return ResultContext.notFoundResult("NOT FOUND");
			}
		}
		System.out.println(documentsUpdate.toString());
		return ResultContext.succeededResult();
	}

	@Override
	public ResultContext<Documents> recordDownFile(int s) {
		// TODO Auto-generated method stub
		try {
			boolean isExist = false;
			for (Documents file : getDocumentsUpdate()) {
				if (s == file.getId()) {
					file.setDowncount(file.getDowncount() + 1);
					System.out.println(documentsUpdate.toString());
					countProcess++;
					System.out.println("Số Process: " + countProcess);
					isExist = true;
				}
			}
			if (!isExist) {
				ResultContext<Documents> documentContext = documentStorage.find(s);
				if (documentContext.succeeded()) {
					Documents fileAdd = documentContext.result();
					fileAdd.setDowncount(fileAdd.getDowncount() + 1);
					getDocumentsUpdate().add(fileAdd);
					System.out.println(documentsUpdate.toString());
					countProcess++;
					System.out.println("Số Process: " + countProcess);
				} else {
					return ResultContext.notFoundResult("Not Found");
				}
			}
			if (countProcess >= 5 && !isSaveAll) {
				restart();
			}if (isSaveAll) {
				isSaveAll = false;
			}
			return ResultContext.succeededResult();
		} catch (Exception e) {
			return ResultContext.failedResult(e);
		}
	}

	public void upDateListRecord() {
		if(documentsUpdate != null && !documentsUpdate.isEmpty()) {
			List<Documents> documents = new ArrayList<>();
			documents.addAll(documentsUpdate);
			documentsUpdate.clear();
			boolean begunTx = beginTx();
			try {
				documentStorage.updateAll(documents);
				countProcess = 0;
				isSaveAll = true;
				LOG.info("Update ListRecord Success: ");
			} catch (Exception e) {
				LOG.error("Update ListRecord Error: " + e);
			} finally {
				endTx(begunTx);
			}
		}
	}
	
	public void start() {
		long delay = 10;
	    LOG.info("Start scheduler clear save with delay: " + delay + "(m) delay start: " + delay / 10 + "(m)");
	    futureSaveActionHandle = scheduler.scheduleWithFixedDelay(new Runnable() {
	      @Override
	      public void run() {
	        try {
	        	upDateListRecord();
	        	LOG.info("--------------------------Success save action--------------------");
	        } catch (Throwable e) {
	          LOG.info("Error save action: " + e);
	        }
	      }
	    }, delay / 10, delay, TimeUnit.SECONDS);
	}
	
	public void restart() {
		LOG.info("Restart scheduler task ");
		try {
			scheduler.shutdown();
		} catch (Exception e) {
			LOG.info("Shutdown exception : " + e);
		}
		scheduler = Executors.newScheduledThreadPool(1);
		start();
	}
	
	private List<Documents> getDocumentsUpdate() {
	    if (documentsUpdate == null) {
	      synchronized (DocumentServiceImpl.class) {
	    	  documentsUpdate = new LinkedList<Documents>();
	      }
	    }
	    return documentsUpdate;
	  }

}
