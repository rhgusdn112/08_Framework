package com.kh.test.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.test.mapper.MemberMapper;

@Service
public class MemberServiceImpl implements MemberService {	

	@Autowired
	private MemberMapper mapper;
	
	@Override
	public List<Map<String, Object>> selectMemberTypeList() {
		return mapper.selectMemberTypeList();
	}
}
