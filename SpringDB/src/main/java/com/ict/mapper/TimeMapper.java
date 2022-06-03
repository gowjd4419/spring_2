package com.ict.mapper;

import org.apache.ibatis.annotations.Select;

public interface TimeMapper {
	
	@Select("SELECT SYSDATE FROM dual")
	public String getTime();
	
	public String getTime2();

}
