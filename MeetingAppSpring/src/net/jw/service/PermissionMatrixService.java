package net.jw.service;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import net.jw.meeting.dao.PermissionMatrixDAO;
import net.jw.meeting.model.PermissionMatrix;
import net.jw.meeting.model.QueryObject;
import net.jw.meeting.model.Response;
import net.jw.system.SystemMessage;

public class PermissionMatrixService {

	private static ApplicationContext context;
	private static PermissionMatrixDAO dao;

	static {
		context = new ClassPathXmlApplicationContext("net/jw/bean/bean.xml");
		dao = (PermissionMatrixDAO) context.getBean("permissionMatrixDAO");
	}

	public static List<PermissionMatrix> fetch(QueryObject q) {
		List<PermissionMatrix> list = dao.fetch(q);

		return list;
	}

	public static Response insert(PermissionMatrix obj) {
		Response res = new Response();
		try {
			dao.insert(obj);

			res.setSuccess(true);
			res.setMessage(SystemMessage.MSG_SUCCESSFULLY_SAVED);
		} catch (Exception e) {
			res.setSuccess(false);
			res.setMessage(SystemMessage.MSG_ERROR_SAVING);
			SystemMessage.pringMessage(e.getMessage().toString());
		}

		return res;
	}

	public static Response delete(PermissionMatrix obj) {
		Response res = new Response();
		try {
			dao.delete(obj);

			res.setSuccess(true);
			res.setMessage(SystemMessage.MSG_SUCCESSFULLY_SAVED);
			res.setSuccess(true);
		} catch (Exception e) {
			res.setSuccess(false);
			res.setMessage(SystemMessage.MSG_ERROR_SAVING);
			SystemMessage.pringMessage(e.getMessage().toString());
		}

		return res;
	}

}
