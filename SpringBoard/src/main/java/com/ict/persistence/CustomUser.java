package com.ict.persistence;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;

@Getter
public class CustomUser extends User{

	
	private MemberVO member;
	
	public CustomUser(String username, String password, Collection<? extends GrantedAuthority> auth) {
		super(username, password, auth);
		
	}
	public CustomUser(MemberVO vo) {
		super(vo.getUserid(),
			  vo.getUserpw(),
				vo.getAuthList().stream().map(author->
				         new SimpleGrantedAuthority(author.getAuth())).collect(Collectors.toList()));
	}

}
