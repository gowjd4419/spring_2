package com.ict.mapper;

import com.ict.persistence.MemberVO;

public interface MemberMapper {
    public MemberVO read(String userid);
}
