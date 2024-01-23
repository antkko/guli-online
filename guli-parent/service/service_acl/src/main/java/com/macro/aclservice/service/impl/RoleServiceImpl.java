package com.macro.aclservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.macro.aclservice.entity.Role;
import com.macro.aclservice.entity.UserRole;
import com.macro.aclservice.mapper.RoleMapper;
import com.macro.aclservice.service.RoleService;
import com.macro.aclservice.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-01-12
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private UserRoleService userRoleService;


    // 根据用户获取角色数据
    @Override
    public Map<String, Object> findRoleByUserId(final String userId) {
        // 查询所有的角色
        final List<Role> allRolesList = baseMapper.selectList(null);

        // 根据用户id，查询用户拥有的角色id
        final List<UserRole> existUserRoleList = userRoleService.list(new QueryWrapper<UserRole>().eq("user_id", userId).select("role_id"));

        final List<String> existRoleList = existUserRoleList.stream().map(c -> c.getRoleId()).collect(Collectors.toList());

        // 对角色进行分类
        final List<Role> assignRoles = new ArrayList<Role>();
        for (final Role role : allRolesList) {
            // 已分配
            if (existRoleList.contains(role.getId())) {
                assignRoles.add(role);
            }
        }

        final Map<String, Object> roleMap = new HashMap<>();
        roleMap.put("assignRoles", assignRoles);
        roleMap.put("allRolesList", allRolesList);
        return roleMap;
    }

    // 根据用户分配角色
    @Override
    public void saveUserRoleRealtionShip(final String userId, final String[] roleIds) {
        userRoleService.remove(new QueryWrapper<UserRole>().eq("user_id", userId));

        final List<UserRole> userRoleList = new ArrayList<>();
        for (final String roleId : roleIds) {
            if (StringUtils.isEmpty(roleId)) {
                continue;
            }
            final UserRole userRole = new UserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(roleId);

            userRoleList.add(userRole);
        }
        userRoleService.saveBatch(userRoleList);
    }

    @Override
    public List<Role> selectRoleByUserId(final String id) {
        // 根据用户id拥有的角色id
        final List<UserRole> userRoleList = userRoleService.list(new QueryWrapper<UserRole>().eq("user_id", id).select("role_id"));
        final List<String> roleIdList = userRoleList.stream().map(item -> item.getRoleId()).collect(Collectors.toList());
        List<Role> roleList = new ArrayList<>();
        if (roleIdList.size() > 0) {
            roleList = baseMapper.selectBatchIds(roleIdList);
        }
        return roleList;
    }
}
