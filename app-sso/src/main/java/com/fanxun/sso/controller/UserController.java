package com.fanxun.sso.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fanxun.common.pojo.FanXunResult;
import com.fanxun.common.utils.ExceptionUtil;
import com.fanxun.pojo.TbUser;
import com.fanxun.sso.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;


@Controller
@RequestMapping("/api/v1")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping("/check")
	@ResponseBody
	public Object checkUserInfo(String param, Integer type) {
		System.out.println("===========checkUserInfo============");
		FanXunResult result = null;
		//参数有效性校验
		if (StringUtils.isBlank(param)) {
			result = FanXunResult.build(3000, "校验内容不能为空");
		}
		if (type == null) {
			result = FanXunResult.build(3000, "校验类型不能为空");
		}
		if (type !=1 && type != 2) {
			result = FanXunResult.build(3000, "校验类型只能为1或2");
		}
		//校验出错
		if (null != result) {
			return result;
		}
		try {
			//调用服务
			result = userService.checkData(param, type);
		} catch (Exception e) {
			result = FanXunResult.build(3000, ExceptionUtil.getStackTrace(e));
		}

		return result;
	}

	/**
	 * 发送验证码
	 * @param phone
	 * @return
	 */
	@RequestMapping(value="/sendcode")
	@ResponseBody
	public FanXunResult sendMessage(String phone){
		System.out.println("================sendMessage===============");
		if (phone == null){
			return FanXunResult.build(3000,"手机号不能为空");
		}
		System.out.println(phone);
		String send_verifyCode = userService.sendMessage(phone);
		if (send_verifyCode != null){
			HashMap map_verifyCode = new HashMap();
			map_verifyCode.put("send_verifyCode",send_verifyCode);
			return FanXunResult.build(1000,"验证码发送成功",map_verifyCode);
		}
		return FanXunResult.build(3000,"验证码发送失败");
	}
	/**
	 * 创建用户
	 * @return
	 */
	@RequestMapping(value="/register",method=RequestMethod.POST)
	@ResponseBody
	public FanXunResult createUser(TbUser user, String verifyCode, String send_verifyCode) {
		System.out.println("================createUser===============");
		//System.out.println(user.getUsername());
		if (user.getPhone() == null || user.getPhone().equals("")){
			return FanXunResult.build(3000, "手机号不能为空");
		}
		if (user.getPassword() == null || user.getPassword().equals("")){
			return FanXunResult.build(3000,"密码不能为空");
		}
		if (user.getUsername() == null || user.getUsername().equals("")){
			return FanXunResult.build(3000,"用户名不能为空");
		}
		try {
			FanXunResult result = userService.createUser(user,verifyCode,send_verifyCode);
			return result;
		} catch (Exception e) {
			return FanXunResult.build(3000, ExceptionUtil.getStackTrace(e));
		}
	}

	@RequestMapping(value="/forgetPassword",method=RequestMethod.POST)
	@ResponseBody
	public FanXunResult forgetPassword(String phone,String password,String verifyCode,String send_verifyCode){
		System.out.println("==================forgetPassword==============");
		if (phone == null || phone.equals("")){
			return FanXunResult.build(3000,"手机号不能为空");
		}
		if (password == null || password.equals("")){
			return FanXunResult.build(3000,"密码不能为空");
		}
		try {
			FanXunResult result = userService.forgetPassword(phone,password,verifyCode,send_verifyCode);
			return result;
		} catch (Exception e) {
			return FanXunResult.build(3000, ExceptionUtil.getStackTrace(e));
		}
	}

	/**
	 * 用户登录
	 * @return
	 */
	@RequestMapping(value="/login")
	@ResponseBody
	public FanXunResult userLogin(String phone,String password) {
		System.out.println("================userLogin===============");
		if (phone == null || phone.equals("")){
			return FanXunResult.build(3000,"手机号不能为空");
		}
		if (password == null || password.equals("")){
			return FanXunResult.build(3000,"密码不能为空");
		}
		try {
			FanXunResult result = userService.userLogin(phone, password);
			return result;
		} catch (Exception e) {
			return FanXunResult.build(3000, ExceptionUtil.getStackTrace(e));
		}
	}
	
	/**
	 * 根据token返回用户信息
	 * @return
	 */
	@RequestMapping("/token/{token}")
	@ResponseBody
	public Object getUserByToken(@PathVariable String token) {
		System.out.println("================getUserByToken===============");
		if (token == null || token.equals("")){
			return FanXunResult.build(3000,"token信息不能为空");
		}
		FanXunResult result = null;
		try {
			result = userService.getUserByToken(token);
		} catch (Exception e) {
			e.printStackTrace();
			result = FanXunResult.build(3000, ExceptionUtil.getStackTrace(e));
			System.out.println(result);
		}

		return result;
	}
	
	/**
	 * 安全退出
	 * @param token
	 * @return
	 */
	@RequestMapping("/logout/{token}")
	@ResponseBody
	public Object userLogout(@PathVariable String token) {
		System.out.println("================userLogout===============");
		if (token == null || token.equals("")){
			return FanXunResult.build(3000,"token信息不能为空");
		}
		FanXunResult result = null;
		try {
			result = userService.userLogout(token);
		} catch (Exception e) {
			e.printStackTrace();
			result = FanXunResult.build(3000, ExceptionUtil.getStackTrace(e));
		}
		return result;
	}
}
