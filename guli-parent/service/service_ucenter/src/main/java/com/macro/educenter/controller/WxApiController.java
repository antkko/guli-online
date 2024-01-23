package com.macro.educenter.controller;

import com.google.gson.Gson;
import com.macro.commonutils.JwtUtils;
import com.macro.educenter.entity.UcenterMember;
import com.macro.educenter.service.UcenterMemberService;
import com.macro.educenter.utils.ConstantWxUtils;
import com.macro.educenter.utils.HttpClientUtils;
import com.macro.servicebase.exception.GuliException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * @author macro
 * @description 微信登录
 * @date 2024/1/2 19:16
 * @github https://github.com/bugstackss
 */
@Controller
@CrossOrigin
@RequestMapping("/api/ucenter/wx")
public class WxApiController {

    @Resource
    private UcenterMemberService memberService;

    /**
     * 生成微信扫描二维码
     *
     * @return 二维码地址
     */
    @GetMapping("login")
    public String getWxCode() {

        // 固定地址,后面拼接参数
        final String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";

        // 对redirect_url进行编码
        String redirectUrl = ConstantWxUtils.WX_OPEN_REDIRECT_URL;
        try {
            redirectUrl = URLEncoder.encode(redirectUrl, "utf-8");
        } catch (final UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        // 固定参数
        final String url = String.format(
                baseUrl,
                ConstantWxUtils.WX_OPEN_APP_ID,
                redirectUrl,
                "atguigu"
        );

        // 请求微信地址
        return "redirect:http://localhost:3000";
    }

    /**
     * 获取扫描人信息，添加数据
     *
     * @return
     */
    @GetMapping("callback")
    public String callback(final String code, final String state) {
        try {
            // 1.获取code值，临时票据，类似于验证码


            // 2.拿着code请求微信固定地址，得到两个值access_token和openid
            final String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                    "?appid=%s" +
                    "&secret=%s" +
                    "&code=%s" +
                    "&grant_type=authorization_code";

            // 拼接三个参数：id 秘钥 和 code值
            final String accessTokenUrl = String.format(baseAccessTokenUrl,
                    ConstantWxUtils.WX_OPEN_APP_ID,
                    ConstantWxUtils.WX_OPEN_APP_SECRET,
                    code);

            // 请求这个拼接好的地址，得到返回两个值access_token和openid
            // 使用httpclient发送请求，得到返回结果
            final String accessToken = HttpClientUtils.get(accessTokenUrl);
            // 从accessToken字符串获取出来两个值access_token和openid
            // 把accessToken字符串转换map集合，根据map里面key获取对应值
            final Gson gson = new Gson();
            final HashMap accessTokenMap = gson.fromJson(accessToken, HashMap.class);
            final String access_token = (String) accessTokenMap.get("access_token");
            final String openid = (String) accessTokenMap.get("openid");


            // 4.扫描人信息添加数据库里面
            // 判断数据库表里面是否存在相同微信信息，根据openid判断
            UcenterMember member = memberService.getOpenIdMember(openid);
            if (member == null) {

                // 3.拿着access_token和openid，再去请求微信提供的固定地址，获取扫描人信息
                // 3.1.拼接地址
                final String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                        "?access_token=%s" +
                        "&openid=%s";
                // 3.2.拼接两个参数
                final String userInfoUrl = String.format(baseUserInfoUrl, access_token, openid);
                // 3.3.发送请求
                final String userInfo = HttpClientUtils.get(userInfoUrl);
                // 3.4.获取返回userInfo字符串扫描人信息
                final HashMap userInfoMap = gson.fromJson(userInfo, HashMap.class);
                final String nickname = (String) userInfoMap.get("nickname");
                final String headimgurl = (String) userInfoMap.get("headimgurl");

                // 4.1.添加信息到数据库
                member = new UcenterMember();
                member.setOpenid(openid);
                member.setNickname(nickname);
                member.setAvatar(headimgurl);
                memberService.save(member);
            }
            // 使用jwt根据member对象生成token字符串
            final String jwtToken = JwtUtils.getJwtToken(member.getId(), member.getNickname());
            // 最后返回首页面,通过路径传递token字符串
            return "redirect:http://localhost:3000?token=" + jwtToken;

        } catch (final Exception e) {
            throw new GuliException(20001, "登录失败");
        }
    }
}
