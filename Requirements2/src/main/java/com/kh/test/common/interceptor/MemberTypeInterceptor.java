package com.kh.test.common.interceptor;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import com.kh.test.service.MemberService;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class MemberTypeInterceptor implements HandlerInterceptor {

	@Autowired
	private MemberService service;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) 
			throws Exception {

		ServletContext application = request.getServletContext();

		if (application.getAttribute("memberTypeList") == null) {
			List<Map<String, Object>> memberTypeList 
				= service.selectMemberTypeList();

			application.setAttribute("memberTypeList", memberTypeList);
		}

		return HandlerInterceptor.super.preHandle(request, response, handler);
	}

}