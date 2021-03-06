package com.nesty.boot.springboot.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class) //테스트를 진행할 때 JUnit에 내장된 실행자 외에 다른 실행자를 실행시킴 / 여기서는 SpringRunner 라는 스프링 실행자를 사용 / 즉, 스프링부트 테스트와 JUnit 사이의 연결자 역할
@WebMvcTest(controllers = HelloController.class) //여러 스프링테스트 어노테이션 중 Web(spring mvc)에 집중할 수 있는 어노테이션 / 선언할 경우 @Controller, @ControllerAdvice 등을 사용 가능 / @Service, @Compnent, @Repository 사용 불가
public class HelloControllerTest {

    @Autowired  //스프링이 관리하는 빈을 주입받음
    private MockMvc mvc; //웹 API를 테스트 할때 사용/ 스프링 MVC 테스트의 시작점 / 이클래스를 통해 HTTP GET, POST 등에 대한 API 테스트 가능

    @Test
    public void hello가_리턴된다() throws Exception{
        String hello = "hello";
        mvc.perform(get("/hello")) //MockMvc를 통해 /hello 주소로 HTTP GET 요청함 / 체이닝이 지원되어 아래와 같이 여러 검증 기능을 이어서 선언가능
                .andExpect(status().isOk()) //mvc.perform의 결과를 검증 / HTTP Header의 Status를 검증
                .andExpect(content().string(hello)); //mvc.perform의 결과를 검증 / 응답 본문의 내용을 검증 /
    }

    @Test
    public void helloDto가_리턴된다() throws Exception{
        String name = "nesty";
        int amount = 1000;

        mvc.perform(get("/hello/dto").param("name", name).param("amount", String.valueOf(amount)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.amount",is(amount)));
    }
}
