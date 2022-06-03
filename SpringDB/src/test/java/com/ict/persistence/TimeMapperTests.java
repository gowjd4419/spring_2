package com.ict.persistence;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ict.mapper.TimeMapper;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class TimeMapperTests {
	
	@Autowired
	private TimeMapper timeMapper;
	
	//@Test
	public void testGetTime() {
		log.info("현재 시간 조회중...");
		log.info(timeMapper.getTime());
	}
	@Test
	public void testGetTime2(){
		log.info("getTime2가 얻어온 시간");
		log.info(timeMapper.getTime2());
	}

}

