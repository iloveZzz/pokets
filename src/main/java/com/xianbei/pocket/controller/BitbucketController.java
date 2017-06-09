package com.xianbei.pocket.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zhudaoming on 2017/6/9.
 */
@Controller
@ResponseBody
public class BitbucketController {
    @RequestMapping("pocket_hook")
    public String pocket_hook(String key, @RequestBody String rq, BindingResult bindingResult) {
        System.out.println(key);
        return "buhao";
    }
}
