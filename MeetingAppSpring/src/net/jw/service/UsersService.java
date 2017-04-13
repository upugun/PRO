package net.jw.service;

import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import net.jw.meeting.dao.UsersDAO;
import net.jw.meeting.model.Users;
import net.jw.system.SystemMessage;
import net.jw.meeting.model.QueryObject;
import net.jw.meeting.model.Response;

public class UsersService {

	private static ClassPathXmlApplicationContext context;
	private static UsersDAO dao;

	static {
		context = new ClassPathXmlApplicationContext("net/jw/bean/bean.xml");
		dao = (UsersDAO) context.getBean("usersDAO");
	}

	public static List<Users> fetch(QueryObject q) {
		List<Users> list = dao.fetch(q);

		return list;
	}

	public static Response insert(Users obj) {
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

	public static Response update(Users obj) {
		Response res = new Response();
		try {
			dao.update(obj);

			res.setSuccess(true);
			res.setMessage(SystemMessage.MSG_SUCCESSFULLY_SAVED);
		} catch (Exception e) {
			res.setSuccess(false);
			res.setMessage(SystemMessage.MSG_ERROR_SAVING);
			SystemMessage.pringMessage(e.getMessage().toString());
		}
		return res;
	}

	public static Response activate(Users obj) {
		Response res = new Response();
		try {
			dao.activate(obj);

			res.setSuccess(true);
			res.setMessage(SystemMessage.MSG_USER_ACTIVATED);
		} catch (Exception e) {
			res.setSuccess(false);
			res.setMessage(SystemMessage.MSG_ERROR_SAVING);
			SystemMessage.pringMessage(e.getMessage().toString());
		}
		return res;
	}
	
	public static Response resetPassword(int uid, String pass) {
		Response res = new Response();
		try {
			dao.resetPassword(uid, pass);

			res.setSuccess(true);
			res.setMessage(SystemMessage.MSG_SUCCESSFULLY_SAVED);
		} catch (Exception e) {
			res.setSuccess(false);
			res.setMessage(SystemMessage.MSG_ERROR_SAVING);
			SystemMessage.pringMessage(e.getMessage().toString());
		}
		return res;
	}

	public static Response inactivate(Users obj) {
		Response res = new Response();
		try {
			dao.inactivate(obj);
			res.setSuccess(true);
			res.setMessage(SystemMessage.MSG_USER_INACTIVATED);

		} catch (Exception e) {
			res.setSuccess(false);
			res.setMessage(SystemMessage.MSG_ERROR_SAVING);
			SystemMessage.pringMessage(e.getMessage().toString());
		}
		return res;
	}


}
