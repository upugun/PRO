package net.jw.meeting.dao;

import java.util.List;


public interface IDAO<T,Q> {
	
	
	public List<T> fetch(Q q);
	
	public void insert(T obj);
	
	public void update(T obj);
	
	public void activate(T obj);
	
	public void inactivate(T obj);

}
