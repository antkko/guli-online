package com.macro.aclservice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.macro.aclservice.entity.Permission;
import com.macro.aclservice.entity.RolePermission;
import com.macro.aclservice.entity.User;
import com.macro.aclservice.helper.MemuHelper;
import com.macro.aclservice.helper.PermissionHelper;
import com.macro.aclservice.mapper.PermissionMapper;
import com.macro.aclservice.service.PermissionService;
import com.macro.aclservice.service.RolePermissionService;
import com.macro.aclservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 权限 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-01-12
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Autowired
    private RolePermissionService rolePermissionService;

    @Autowired
    private UserService userService;

    // 获取全部菜单
    @Override
    public List<Permission> queryAllMenu() {

        final QueryWrapper<Permission> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        final List<Permission> permissionList = baseMapper.selectList(wrapper);

        final List<Permission> result = bulid(permissionList);

        return result;
    }

    // 根据角色获取菜单
    @Override
    public List<Permission> selectAllMenu(final String roleId) {
        final List<Permission> allPermissionList = baseMapper.selectList(new QueryWrapper<Permission>().orderByAsc("CAST(id AS SIGNED)"));

        // 根据角色id获取角色权限
        final List<RolePermission> rolePermissionList = rolePermissionService.list(new QueryWrapper<RolePermission>().eq("role_id", roleId));
        // 转换给角色id与角色权限对应Map对象
//        List<String> permissionIdList = rolePermissionList.stream().map(e -> e.getPermissionId()).collect(Collectors.toList());
//        allPermissionList.forEach(permission -> {
//            if(permissionIdList.contains(permission.getId())) {
//                permission.setSelect(true);
//            } else {
//                permission.setSelect(false);
//            }
//        });
        for (int i = 0; i < allPermissionList.size(); i++) {
            final Permission permission = allPermissionList.get(i);
            for (int m = 0; m < rolePermissionList.size(); m++) {
                final RolePermission rolePermission = rolePermissionList.get(m);
                if (rolePermission.getPermissionId().equals(permission.getId())) {
                    permission.setSelect(true);
                }
            }
        }

        return bulid(allPermissionList);
    }

    // 给角色分配权限
    @Override
    public void saveRolePermissionRealtionShip(final String roleId, final String[] permissionIds) {

        rolePermissionService.remove(new QueryWrapper<RolePermission>().eq("role_id", roleId));


        final List<RolePermission> rolePermissionList = new ArrayList<>();
        for (final String permissionId : permissionIds) {
            if (StringUtils.isEmpty(permissionId)) {
                continue;
            }

            final RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(roleId);
            rolePermission.setPermissionId(permissionId);
            rolePermissionList.add(rolePermission);
        }
        rolePermissionService.saveBatch(rolePermissionList);
    }

    // 递归删除菜单
    @Override
    public void removeChildById(final String id) {
        final List<String> idList = new ArrayList<>();
        this.selectChildListById(id, idList);

        idList.add(id);
        baseMapper.deleteBatchIds(idList);
    }

    // 根据用户id获取用户菜单
    @Override
    public List<String> selectPermissionValueByUserId(final String id) {

        List<String> selectPermissionValueList = null;
        if (this.isSysAdmin(id)) {
            // 如果是系统管理员，获取所有权限
            selectPermissionValueList = baseMapper.selectAllPermissionValue();
        } else {
            selectPermissionValueList = baseMapper.selectPermissionValueByUserId(id);
        }
        return selectPermissionValueList;
    }

    @Override
    public List<JSONObject> selectPermissionByUserId(final String userId) {
        List<Permission> selectPermissionList = null;
        if (this.isSysAdmin(userId)) {
            // 如果是超级管理员，获取所有菜单
            selectPermissionList = baseMapper.selectList(null);
        } else {
            selectPermissionList = baseMapper.selectPermissionByUserId(userId);
        }

        final List<Permission> permissionList = PermissionHelper.bulid(selectPermissionList);
        return MemuHelper.bulid(permissionList);
    }

    /**
     * 判断用户是否系统管理员
     *
     * @param userId
     * @return
     */
    private boolean isSysAdmin(final String userId) {
        final User user = userService.getById(userId);

        if (null != user && "admin".equals(user.getUsername())) {
            return true;
        }
        return false;
    }

    /**
     * 递归获取子节点
     *
     * @param id     菜单id
     * @param idList 菜单id集合
     */
    private void selectChildListById(final String id, final List<String> idList) {
        final List<Permission> childList = baseMapper.selectList(new QueryWrapper<Permission>().eq("pid", id).select("id"));
        childList.stream().forEach(item -> {
            idList.add(item.getId());
            this.selectChildListById(item.getId(), idList);
        });
    }

    /**
     * 使用递归方法建菜单
     *
     * @param treeNodes 菜单列表
     * @return 菜单列表
     */
    private static List<Permission> bulid(final List<Permission> treeNodes) {
        final List<Permission> trees = new ArrayList<>();
        for (final Permission treeNode : treeNodes) {
            if ("0".equals(treeNode.getPid())) {
                treeNode.setLevel(1);
                trees.add(findChildren(treeNode, treeNodes));
            }
        }
        return trees;
    }

    /**
     * 递归查找子节点
     *
     * @param treeNodes 当前节点
     * @return 当前节点的子节点
     */
    private static Permission findChildren(final Permission treeNode, final List<Permission> treeNodes) {
        treeNode.setChildren(new ArrayList<Permission>());

        for (final Permission it : treeNodes) {
            if (treeNode.getId().equals(it.getPid())) {
                final int level = treeNode.getLevel() + 1;
                it.setLevel(level);
                if (treeNode.getChildren() == null) {
                    treeNode.setChildren(new ArrayList<>());
                }
                treeNode.getChildren().add(findChildren(it, treeNodes));
            }
        }
        return treeNode;
    }


    //==============================递归查询所有菜单================================================
    // 获取全部菜单
    @Override
    public List<Permission> queryAllMenuGuli() {
        // 1 查询菜单表所有数据
        final QueryWrapper<Permission> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        final List<Permission> permissionList = baseMapper.selectList(wrapper);
        // 2 把查询所有菜单list集合按照要求进行封装
        return bulidPermission(permissionList);
    }

    // 把返回所有菜单list集合进行封装的方法
    public static List<Permission> bulidPermission(final List<Permission> permissionList) {

        // 创建list集合，用于数据最终封装
        final List<Permission> finalNode = new ArrayList<>();
        // 把所有菜单list集合遍历，得到顶层菜单 pid=0菜单，设置level是1
        for (final Permission permissionNode : permissionList) {
            // 得到顶层菜单 pid=0菜单
            if ("0".equals(permissionNode.getPid())) {
                // 设置顶层菜单的level是1
                permissionNode.setLevel(1);
                // 根据顶层菜单，向里面进行查询子菜单，封装到finalNode里面
                finalNode.add(selectChildren(permissionNode, permissionList));
            }
        }
        return finalNode;
    }

    private static Permission selectChildren(final Permission permissionNode, final List<Permission> permissionList) {
        // 1 因为向一层菜单里面放二层菜单，二层里面还要放三层，把对象初始化
        permissionNode.setChildren(new ArrayList<Permission>());

        // 2 遍历所有菜单list集合，进行判断比较，比较id和pid值是否相同
        for (final Permission it : permissionList) {
            // 判断 id和pid值是否相同
            if (permissionNode.getId().equals(it.getPid())) {
                // 把父菜单的level值+1
                final int level = permissionNode.getLevel() + 1;
                it.setLevel(level);
                // 如果children为空，进行初始化操作
                if (permissionNode.getChildren() == null) {
                    permissionNode.setChildren(new ArrayList<Permission>());
                }
                // 把查询出来的子菜单放到父菜单里面
                permissionNode.getChildren().add(selectChildren(it, permissionList));
            }
        }
        return permissionNode;
    }

    //============递归删除菜单==================================
    @Override
    public void removeChildByIdGuli(final String id) {
        // 1 创建list集合，用于封装所有删除菜单id值
        final List<String> idList = new ArrayList<>();
        // 2 向idList集合设置删除菜单id
        this.selectPermissionChildById(id, idList);
        // 把当前id封装到list里面
        idList.add(id);
        baseMapper.deleteBatchIds(idList);
    }

    // 2 根据当前菜单id，查询菜单里面子菜单id，封装到list集合
    private void selectPermissionChildById(final String id, final List<String> idList) {
        // 查询菜单里面子菜单id
        final QueryWrapper<Permission> wrapper = new QueryWrapper<>();
        wrapper.eq("pid", id);
        wrapper.select("id");
        final List<Permission> childIdList = baseMapper.selectList(wrapper);
        // 把childIdList里面菜单id值获取出来，封装idList里面，做递归查询
        childIdList.stream().forEach(item -> {
            // 封装idList里面
            idList.add(item.getId());
            // 递归查询
            this.selectPermissionChildById(item.getId(), idList);
        });
    }

    //=========================给角色分配菜单=======================
    @Override
    public void saveRolePermissionRealtionShipGuli(final String roleId, final String[] permissionIds) {
        // roleId角色id
        // permissionId菜单id 数组形式
        // 1 创建list集合，用于封装添加数据
        final List<RolePermission> rolePermissionList = new ArrayList<>();
        // 遍历所有菜单数组
        for (final String perId : permissionIds) {
            // RolePermission对象
            final RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(roleId);
            rolePermission.setPermissionId(perId);
            // 封装到list集合
            rolePermissionList.add(rolePermission);
        }
        // 添加到角色菜单关系表
        rolePermissionService.saveBatch(rolePermissionList);
    }
}
