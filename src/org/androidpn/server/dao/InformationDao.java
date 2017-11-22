package org.androidpn.server.dao;

import java.util.List;

import org.androidpn.server.model.Information;



public interface InformationDao {
	
	public   void  save(Information  information);
	public  List<Information> find(String username);
	public  void  delete(Information  information);
	
}
