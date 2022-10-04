package com.fkocak.springrestdemo.helloworld;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

///@Controller
@RestController
public class HelloWorldResource {
    // /hello-world => "Hello World"

    @RequestMapping("/hello-world")
    //@ResponseBody //We dont need it because @RestController already is a combination of @Controller and @ResponseBody
    public String helloWorld(){
        return "Hello World";
    }

    @RequestMapping("/hello-world-bean")
    public HelloWorldBean helloWorldBean(){
        return new HelloWorldBean("Hello World");
    }

    @RequestMapping("/hello-world-path-param/{name}")
    public HelloWorldBean helloWorldPathParam(@PathVariable String name){
        return new HelloWorldBean("Hello World, " + name);
    }

    @RequestMapping("/hello-world-path-param/{name}/message/{message}")
    public HelloWorldBean helloWorldMultiplePathParam(
            @PathVariable String name,
            @PathVariable String message
    ){
        return new HelloWorldBean("Hello World, " + name + "," + message);
    }
}
