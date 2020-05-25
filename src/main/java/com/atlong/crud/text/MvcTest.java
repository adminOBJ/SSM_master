package com.atlong.crud.text;

import com.atlong.crud.bean.Employee;
import com.github.pagehelper.PageInfo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

/**
 * @author : chen
 * @project:SSM-master
 * @date : 2020-05-23 19:31
 * @description:分页测试
 *使用spring测试工具提供的测试请求功能，测试curd请求的正确性
 **/

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration        //Web App配置
@ContextConfiguration(locations = {"classpath:applicationContext.xml","file:src/main/webapp/WEB-INF/dispatcherServlet-servlet.xml"})
//make  as  de
public class MvcTest {
    //创建虚拟mvc请求，获取到处理结果
    MockMvc mvc;

    @Autowired
    WebApplicationContext context;

    @Before
    public void  initmvc(){
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void testPage() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/empl").param("pn", "5")).andReturn();
        //请求成功  请求域中会有pageinfo  取出pageinfo进行验证
        MockHttpServletRequest request = result.getRequest();
        PageInfo pi = (PageInfo) request.getAttribute("PageInfo");

        System.out.println("当前页码:" + pi.getPageNum());
        System.out.println("总页码:" + pi.getPages());
        System.out.println("总记录数:" + pi.getTotal());
        System.out.println("在页面要显示的连续页码:");
        int [] nums = pi.getNavigatepageNums();
        for (int i : nums) {
            System.out.println(" "+i);
        }
        //获取员工数据
        List<Employee> list = pi.getList();
        for (Employee employee : list) {
            System.out.println("id:" + employee.getEmpId() + "name:" + employee.getEmpName());
        }

    }
}
