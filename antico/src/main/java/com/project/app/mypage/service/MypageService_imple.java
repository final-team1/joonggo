package com.project.app.mypage.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project.app.mypage.model.MypageDAO;

@Service
public class MypageService_imple implements MypageService {

    @Autowired
    private MypageDAO mypagedao;
    
	// 탈퇴 테이블 탈퇴신청시 insert
	@Override
	public int delete_submit(Map<String, String> paraMap) {
		int n = mypagedao.delete_submit(paraMap);
		return n;
	}

	@Override
	public int pointcharge(Map<String, String> paraMap) {
		int n = mypagedao.pointcharge(paraMap); // 충전테이블 insert
		int m = 0;
		if(n == 1) {
			m = mypagedao.pointupdate(paraMap); // 회원의 포인트 update
		}
		int x = 0;
		if(m == 1) {
			x = mypagedao.point_history(paraMap); // 포인트가 충전되면 포인트내역 테이블에 insert
		}
		
		return n*m*x;
	}

	// 회원의 총 충전금액을 알아오기 위한 용도 (등급때매)
	@Override
	public int point_sum(String pk_member_no) {
		int n = mypagedao.point_sum(pk_member_no);
		String rank = "0";
	//	System.out.println("n확인"+n);

		if(n >= 1000000) { // 회원의 총 충전금액이 100만원이 넘을 경우
			rank = "1";
		} else if(n >= 2000000) { // 회원의 총 충전금액이 200만원이 넘을 경우
			rank = "2";
		}
		int m = mypagedao.role_update(pk_member_no, rank); // 총 충전금액이 일정금액 이상이면 등급을 올려준다
		
	//	System.out.println("m확인"+m);
		
		return n;
	}
}
