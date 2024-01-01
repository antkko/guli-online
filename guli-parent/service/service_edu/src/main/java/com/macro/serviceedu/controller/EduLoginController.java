package com.macro.serviceedu.controller;

import com.macro.commonutils.R;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * @auther macro
 * @description 用户登录Controller
 * @date 2023/11/26 17:25
 */
@RestController
@CrossOrigin
@RequestMapping("eduservice/user")
public class EduLoginController {
    
    @PostMapping("login")
    public R login() {
        return R.ok().data("token", "admin");
    }

    @GetMapping("info")
    public R info() {
        final HashMap<String, Object> userMap = new HashMap<>();
        userMap.put("roles", "[admin]");
        userMap.put("name", "admin");
        userMap.put("avatar", "https://guli-edu-services.oss-cn-shanghai.aliyuncs.com/macro.jpeg");
        return R.ok().data(userMap);
    }
}
