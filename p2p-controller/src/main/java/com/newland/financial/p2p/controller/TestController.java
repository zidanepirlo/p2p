package com.newland.financial.p2p.controller;


import com.newland.financial.p2p.domain.entity.TCmmCity;
import com.newland.financial.p2p.domain.entity.User;
import com.newland.financial.p2p.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Controller
@RequestMapping("/TestController")
public class TestController {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IUserService userService;

    public void getReqAndRes(HttpServletRequest request, HttpServletResponse response){

        request.getSession().setAttribute("ddd",new Object());
        request.getSession().getAttribute("ddd");
    }

    @RequestMapping(value={"test"}, method = RequestMethod.PUT)
    @ResponseBody
    public void test(String id,String name,HttpServletResponse response) throws IOException {
        logger.info("TestController test");
        PrintWriter writer = response.getWriter();
        String result = "this is test! id="+id+"  name="+name;
        writer.write(result);

    }

    @RequestMapping("/test1")
    public void test1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("TestController test1");
//        User user = userService.getUserById("1111");
        List<TCmmCity> list = userService.findCityByName("广西自治区");

        PrintWriter writer = response.getWriter();
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        writer.write(list.get(0).getProvNm()+"--------"+list.get(0).getCityNm());
    }

}
