package org.androidpn.server.dao.hibernate;

import java.util.List;

import org.androidpn.server.dao.InformationDao;
import org.androidpn.server.model.Information;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;


public class InformationDaoHibernate  extends HibernateDaoSupport implements InformationDao{
	
	public void delete(Information information) {
			getHibernateTemplate().delete(information);
	}
	
	@SuppressWarnings("unchecked")
	public List<Information> find(String username) {
		
		return getHibernateTemplate().find("form  information where username=?", username);
	}

	public void save(Information information) {
		getHibernateTemplate().saveOrUpdate(information);
		getHibernateTemplate().flush();
		
	}
}
