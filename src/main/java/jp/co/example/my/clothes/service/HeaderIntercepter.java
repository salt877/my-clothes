package jp.co.example.my.clothes.service;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import jp.co.example.my.clothes.domain.User;
import jp.co.example.my.clothes.domain.UserDetail;



/**
 * コントローラ呼び出し前にヘッダー表示処理を行うクラス.
 * 
 * @author rinashioda
 *
 */
public class HeaderIntercepter extends HandlerInterceptorAdapter{
	
	@Autowired
	private ShowUserNameService showUserNameService;

	
	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response,Object handler) throws Exception{
		
//		User loginUser = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		Integer userId = loginUser.getId();
//		
//		UserDetail userDetail = showUserNameService.showUserName(userId);
		//model.addAttribute("userDetail", userDetail);
		System.out.println("プレハンドルが呼ばれた");
		
		
		
		return true;
	}

}
