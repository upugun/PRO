package net.jw.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import net.jw.meeting.model.Users;
import net.jw.meeting.model.JWMail;
import net.jw.meeting.model.QueryObject;
import net.jw.meeting.model.Response;
import net.jw.service.MailService;
import net.jw.service.UsersService;
import net.jw.system.AppConstant;
import net.jw.system.MailTemplate;
import net.jw.system.SystemMessage;

@Controller
public class UsersController {

	@ResponseBody
	@RequestMapping(value = "/users/fetch", method = RequestMethod.POST)
	public List<Users> fetch(@RequestBody QueryObject q) {
		List<Users> list = UsersService.fetch(q);

		return list;
	}

	@ResponseBody
	@RequestMapping(value = "/users/reset", method = RequestMethod.POST)
	public Response reset(@RequestBody QueryObject q) {
		List<Users> list = UsersService.fetch(q);
		Response res = new Response();
		if (list.size() > 0) {
			Users user = list.get(0);
			String uuid = UUID.randomUUID().toString();
			String password=uuid.substring(30);
			
			UsersService.resetPassword(user.getUid(), password);
			res.setSuccess(true);
			res.setMessage(SystemMessage.PASSWORD_MAIL_SUCCESS);
			
			JWMail mail = new JWMail();
			mail.setTo(user.getEmail());
			mail.setFrom(AppConstant.SYSTEM_EMAIL);
			mail.setSubject(MailTemplate.MAIL_SUBJECT_PASSWORD_RESET );
			mail.setMessage(MailTemplate.userPasswordResetTemplate(user.getLogin().getUserName(), password));
			mail.setSenderId(AppConstant.SYS_ADMIN_ID);
			mail.setReadStatus(AppConstant.MAIL_READ_STATUS_UNREAD);
			MailService.sendMail(mail);

		} else {

			res.setSuccess(false);
			res.setMessage(SystemMessage.PASSWORD_MAIL_UNSUCCESS);
		}

		return res;
	}

	// @CrossOrigin(origins = "http://128.106.24.19:8080")
	@ResponseBody
	@RequestMapping(value = "/users/insert", method = RequestMethod.POST)
	public Response insert(@RequestBody Users obj) {
		Response res = UsersService.insert(obj);

		return res;
	}

	@ResponseBody
	@RequestMapping(value = "/users/update", method = RequestMethod.POST)
	public Response update(@RequestBody Users obj) {
		Response res = UsersService.update(obj);

		return res;
	}

	@ResponseBody
	@RequestMapping(value = "/meeting/register", method = RequestMethod.POST)
	public Response register(@RequestBody Users obj) {
		Response res = UsersService.insert(obj);

		if (res.isSuccess()) {

			JWMail mail = new JWMail();
			mail.setTo(obj.getEmail());
			mail.setFrom(AppConstant.SYSTEM_EMAIL);
			mail.setSubject(MailTemplate.MAIL_SUBJECT_USER_REGISTRATION);
			mail.setMessage(MailTemplate.userRegistrationTemplate(obj.getLogin().getUserName()));
			mail.setSenderId(AppConstant.SYS_ADMIN_ID);
			mail.setReadStatus(AppConstant.MAIL_READ_STATUS_UNREAD);
			mail.setCommon(obj.getCommon());
			MailService.sendMail(mail);
		}

		return res;
	}

	@ResponseBody
	@RequestMapping(value = "/users/activate", method = RequestMethod.POST)
	public Response activate(@RequestBody Users obj) {
		Response res = UsersService.activate(obj);

		if (res.isSuccess()) {
			JWMail mail = new JWMail();
			mail.setTo(obj.getEmail());
			mail.setFrom(AppConstant.SYSTEM_EMAIL);
			mail.setSubject(MailTemplate.MAIL_SUBJECT_USER_ACTIVATED);
			mail.setMessage(MailTemplate.userActivateTemplate(obj.getLogin().getUserName()));
			mail.setSenderId(AppConstant.SYS_ADMIN_ID);
			mail.setReadStatus(AppConstant.MAIL_READ_STATUS_UNREAD);
			mail.setCommon(obj.getCommon());
			MailService.sendMail(mail);
		}

		return res;
	}

	@ResponseBody
	@RequestMapping(value = "/users/inactivate", method = RequestMethod.POST)
	public Response inactivate(@RequestBody Users obj) {
		Response res = UsersService.inactivate(obj);

		return res;
	}

}
