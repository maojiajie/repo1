package com.itheima.test;


import com.itheima.dao.ItemsDao;
import com.itheima.domain.Items;
import com.itheima.service.ItemsService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:**.xml"})
@WebAppConfiguration
public class ItemsTest {
	
	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext webContext;
	
	@Before
	public void setUpMockMvc(){
		mockMvc = MockMvcBuilders.webAppContextSetup(webContext).build();
	}
	
	@Test
	public void get() throws Exception {
		Items items = new Items();
		items.setId(1);
		items.setName("传智播客");
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = simpleDateFormat.parse("2018-03-13 09:29:30");
		items.setCreatetime(date);
		items.setDetail("带我走上人生巅峰");
		mockMvc.perform(MockMvcRequestBuilders.get("/items/showDetail/1")
				.accept(MediaType.APPLICATION_FORM_URLENCODED))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("item"));
				//.andExpect(MockMvcResultMatchers.model().attribute("item", Matchers.hasSize(1)))
				//.andExpect(MockMvcResultMatchers.model().attribute("item",Matchers.contains(Matchers.samePropertyValuesAs(items))));
	}
	
	@Test
	public void daoTest(){
		//得到spring容器
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		//从spring容器中得到所需dao接口的代理对象
		ItemsDao itemsDao = applicationContext.getBean(ItemsDao.class);
		//调用方法
		Items items = itemsDao.findById(1);
		System.out.println(items.getName());
	}
	
	@Test
	public void serviceTest(){
		//得到spring容器
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		//从spring容器中得到所需dao接口的代理对象
		ItemsService itemsService = applicationContext.getBean(ItemsService.class);
		//调用方法
		Items items = itemsService.findById(1);
		System.out.println(items.getName());
	}
}
