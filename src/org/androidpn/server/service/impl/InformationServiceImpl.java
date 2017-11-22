package org.androidpn.server.service.impl;

import java.util.List;

import org.androidpn.server.dao.InformationDao;
import org.androidpn.server.model.Information;
import org.androidpn.server.service.InformationService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class InformationServiceImpl implements  InformationService{
	
	 public InformationDao getInformationDao() {
		return informationDao;
	}

	public void setInformationDao(InformationDao informationDao) {
		this.informationDao = informationDao;
	}

	public Log getLog() {
		return log;
	}

	protected final Log log = LogFactory.getLog(getClass());
	 private InformationDao informationDao;
	    
	

	public void delete(Information information) {
		informationDao.delete(information);
		
	}

	public List<Information> find(String username) {
		// TODO Auto-generated method stub
		
		return informationDao.find(username);
	}

	public void save(Information information) {
		informationDao.save(information);
		
	}

}
