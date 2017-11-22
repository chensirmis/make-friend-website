package org.androidpn.server.service;

import java.util.List;

import org.androidpn.server.model.Information;


public interface InformationService {
	
	public void save(Information  information);
	public List<Information> find(String username);
	public void  delete(Information  information);

}
