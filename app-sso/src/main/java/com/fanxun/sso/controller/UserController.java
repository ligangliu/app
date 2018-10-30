package com.fanxun.sso.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fanxun.common.pojo.FanXunResult;
import com.fanxun.common.utils.ExceptionUtil;
import com.fanxun.common.utils.ParsePostParamsUtil;
import com.fanxun.pojo.TbUser;
import com.fanxun.sso.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@Controller
@CrossOrigin
@RequestMapping("/api/v1")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/getAllUsers",method = RequestMethod.GET)
	@ResponseBody
	public FanXunResult getAllUsersInfo(@RequestParam(required = false,
			defaultValue = "1",value = "page")Integer page, @RequestParam(
			required = false,defaultValue = "10",value = "row")Integer row){
		System.out.println("================getAllUsersInfo======================");
		return userService.getAllUserInfo(page,row);
	}

	/**
	 * 校验数据，校验用户手机号或者用户名是否已存在
	 * @param request
	 * @param param
	 * @param type
	 * @return
	 */
	@RequestMapping("/checkData")
	@ResponseBody
	public FanXunResult checkUserInfo(HttpServletRequest request, String param, Integer type) {
		System.out.println("===========checkUserInfo============");
		if ("POST".equalsIgnoreCase(request.getMethod())){
			String enctype = request.getContentType();
			if (enctype!=null && enctype.contains("application/json")){
				Map<String,String> allParams = null;
				try {
					allParams = ParsePostParamsUtil.getJsonPostParams(request);
				}catch (IOException e){
					return FanXunResult.build(3000,"参数转换异常");
				}
				param = allParams.get("param");
				type = Integer.parseInt(allParams.get("type"));
			}
		}
		FanXunResult result = null;
		//参数有效性校验
		if (param == null || StringUtils.isBlank(param)) {
			return FanXunResult.build(3000, "校验内容不能为空");
		}
		if (type == null) {
			return FanXunResult.build(3000, "校验类型不能为空");
		}
		if (type !=1 && type != 2) {
			return FanXunResult.build(3000, "校验类型只能为1或2");
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
	@RequestMapping(value="/sendCode",method = RequestMethod.POST)
	@ResponseBody
	public FanXunResult sendMessage(HttpServletRequest request, String phone){
		System.out.println("================sendMessage===============");
		if ("POST".equalsIgnoreCase(request.getMethod())){
			String enctype = request.getContentType();
			if (enctype != null && enctype.contains("application/json")){
				Map<String,String> allParams = null;
				try {
					allParams = ParsePostParamsUtil.getJsonPostParams(request);
				}catch (IOException e){
					return FanXunResult.build(3000,"参数转换异常");
				}
				phone = allParams.get("phone");
			}
		}
		if (phone == null || StringUtils.isBlank(phone)){
			return FanXunResult.build(3000,"手机号不能为空");
		}
		//System.out.println(phone);
		String send_verifyCode = userService.sendMessage(phone);
		if (send_verifyCode != null){
			HashMap map_verifyCode = new HashMap();
			map_verifyCode.put("send_verifyCode",DigestUtils.md5DigestAsHex(send_verifyCode.getBytes()));
			return FanXunResult.build(1000,"验证码发送成功",map_verifyCode);
		}
		return FanXunResult.build(3000,"验证码发送失败");
	}
	/**
	 * 创建用户
	 * @return
	 */
	@RequestMapping(value="/register",method = RequestMethod.POST)
	@ResponseBody
	public FanXunResult createUser(HttpServletRequest request,TbUser user, String verifyCode, String send_verifyCode) {
		System.out.println("================createUser===============");
		if ("POST".equalsIgnoreCase(request.getMethod())){
			String enctype = request.getContentType();
			if (enctype != null && enctype.contains("application/json")){
				Map<String,String> allParams = null;
				try {
					allParams = ParsePostParamsUtil.getJsonPostParams(request);
				}catch (IOException e){
					return FanXunResult.build(3000,"参数转换异常");
				}
				user.setPhone(allParams.get("phone"));
				user.setUsername(allParams.get("username"));
				user.setPassword(allParams.get("password"));
				verifyCode = allParams.get("verifyCode");
				send_verifyCode = allParams.get("send_verifyCode");
			}
		}
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
		if (verifyCode == null || verifyCode.equals("")){
			return FanXunResult.build(3000,"验证码不能为空");
		}
		if (send_verifyCode == null || send_verifyCode.equals("")){
			return FanXunResult.build(3000,"核验验证码不能为空");
		}
		try {
			FanXunResult result = userService.createUser(user,verifyCode,send_verifyCode);
			return result;
		} catch (Exception e) {
			return FanXunResult.build(3000, ExceptionUtil.getStackTrace(e));
		}
	}

	/**
	 * 忘记密码
	 * @param request
	 * @param phone
	 * @param password
	 * @param verifyCode
	 * @param send_verifyCode
	 * @return
	 */
	@RequestMapping(value="/forgetPassword",method = RequestMethod.POST)
	@ResponseBody
	public FanXunResult forgetPassword(HttpServletRequest request,String phone,String password,String verifyCode,String send_verifyCode){
		System.out.println("==================forgetPassword==============");
		if ("POST".equalsIgnoreCase(request.getMethod())){
			String enctype = request.getContentType();
			if (enctype != null && enctype.contains("application/json")){
				Map<String,String> allParams = null;
				try {
					allParams = ParsePostParamsUtil.getJsonPostParams(request);
				}catch (IOException e){
					return FanXunResult.build(3000,"参数转换异常");
				}
				phone = allParams.get("phone");
				password = allParams.get("password");
				verifyCode = allParams.get("verifyCode");
				send_verifyCode = allParams.get("send_verifyCode");
			}
		}
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
	@RequestMapping(value="/login",method = RequestMethod.POST)
	@ResponseBody
	public FanXunResult userLogin(HttpServletRequest request,String phone,String password) {
		System.out.println("================userLogin===============");
		if ("POST".equalsIgnoreCase(request.getMethod())){
			String enctype = request.getContentType();
			if (enctype != null && enctype.contains("application/json")){
				Map<String,String> allParams = null;
				try {
					allParams = ParsePostParamsUtil.getJsonPostParams(request);
				}catch (IOException e){
					return FanXunResult.build(3000,"参数转换异常");
				}
				phone = allParams.get("phone");
				password = allParams.get("password");
			}
		}
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
	public FanXunResult getUserByToken(@PathVariable String token) {
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
	public FanXunResult userLogout(@PathVariable String token) {
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

	/**
	 * 刷新页面
	 * @param token
	 * @return
	 */
	@RequestMapping("/refresh/{token}")
	@ResponseBody
	public FanXunResult refresh(@PathVariable String token){
		System.out.println("================refresh===============");
		if (token == null || token.equals("")){
			return FanXunResult.build(3000,"token信息不能为空");
		}
		FanXunResult result = null;
		try {
			result = userService.refreshByToken(token);
		} catch (Exception e) {
			e.printStackTrace();
			result = FanXunResult.build(3000, ExceptionUtil.getStackTrace(e));
		}
		return result;
	}


}
