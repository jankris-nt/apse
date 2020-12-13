package com.apse.controller;


import org.springframework.web.bind.annotation.*;


import java.util.Map;

@RequestMapping("/index55")
public class AppController22 {



	@RequestMapping("/ttt")
    public @ResponseBody String hiThere(){
        return "hello worldffff! from apps";
    }
}
