//package jp.co.example.my.clothes.controller;
//
//import java.util.Locale;
//import java.util.UUID;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.MessageSource;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import jp.co.example.my.clothes.GenericResponse;
//import jp.co.example.my.clothes.IUserService;
//import jp.co.example.my.clothes.domain.User;
//import jp.co.example.my.clothes.service.ChangeUserPasswordService;
//
///**
// * web/controller/RegistrationRestController.javaの内容
// * 
// * @author rinashioda
// *
// */
//@RestController
//public class RegistrationRestController {
//
//	@Autowired
//	private ChangeUserPasswordService changeUserPasswordService;
//
//	@Autowired
//	private IUserService userService;
//
//	@Autowired
//	private JavaMailSender mailSender;
//
//	@Autowired
//	private MessageSource messages;
//
//	public RegistrationRestController() {
//		super();
//	}
//
//	@PostMapping("/user/resetPassword")
//	public GenericResponse resetPassword(final HttpServletRequest request, @RequestParam("email") String email) {
//
//		final User user = changeUserPasswordService.findByEmail(email);
//
//		if (user != null) {
//			final String token = UUID.randomUUID().toString();
//			userService.createPasswordResetTokenForUser(user, token);
//			mailSender.send(constructResetTokenEmail(getAppUrl(request), request.getLocale(), token, user));
//		}
//		return new GenericResponse(messages.getMessage("message.resetPasswordEmail", null, request.getLocale()));
//	}
//
//	private SimpleMailMessage constructResetTokenEmail(final String contextPath, final Locale locale,
//			final String token, final User user) {
//
//		final String url = contextPath + "/user/changePassword?token=" + token;
//		final String message = messages.getMessage("message.resetPassword", null, locale);
//		return constructEmail("Reset Password", message + "\r\n" + url, user);
//	}
//
//	private SimpleMailMessage constructEmail(String subject, String body, User user) {
//		SimpleMailMessage email = new SimpleMailMessage();
//		email.setSubject(subject);
//		email.setText(body);
//		email.setTo(user.getEmail());
//		email.setFrom("my.clothes0702@gmail.com");
//		return email;
//	}
//
//	private String getAppUrl(HttpServletRequest request) {
//		return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
//	}
//
//}
