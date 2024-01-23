package com.macro.aclservice.service.impl;

import com.macro.aclservice.entity.User;
import com.macro.aclservice.service.PermissionService;
import com.macro.aclservice.service.UserService;
import com.macro.serurity.entity.SecurityUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * <p>
 * 自定义userDetailsService - 认证用户详情
 * </p>
 *
 * @author qy
 * @since 2019-11-08
 */
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Autowired
    private PermissionService permissionService;

    /***
     * 根据账号获取用户信息
     * @param username: 用户名
     * @return: org.springframework.security.core.userdetails.UserDetails
     */
    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        // 从数据库中取出用户信息
        final User user = userService.selectByUsername(username);

        // 判断用户是否存在
        if (null == user) {
            // throw new UsernameNotFoundException("用户名不存在！");
        }
        // 返回UserDetails实现类
        final com.macro.serurity.entity.User curUser = new com.macro.serurity.entity.User();
        assert user != null;
        BeanUtils.copyProperties(user, curUser);

        final List<String> authorities = permissionService.selectPermissionValueByUserId(user.getId());
        final SecurityUser securityUser = new SecurityUser(curUser);
        securityUser.setPermissionValueList(authorities);
        return securityUser;
    }

}
