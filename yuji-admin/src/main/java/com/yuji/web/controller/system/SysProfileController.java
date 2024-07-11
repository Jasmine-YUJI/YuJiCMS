package com.yuji.web.controller.system;

import com.yuji.common.constant.Constants;
import com.yuji.common.constant.UserConstants;
import com.yuji.common.core.domain.entity.SysMenu;
import com.yuji.common.utils.ip.IP2RegionUtils;
import com.yuji.system.domain.vo.DashboardUserVO;
import com.yuji.system.domain.vo.ShortcutVO;
import com.yuji.system.preference.ShortcutUserPreference;
import com.yuji.system.service.ISysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.yuji.common.annotation.Log;
import com.yuji.common.config.YuJiConfig;
import com.yuji.common.core.controller.BaseController;
import com.yuji.common.core.domain.AjaxResult;
import com.yuji.common.core.domain.entity.SysUser;
import com.yuji.common.core.domain.model.LoginUser;
import com.yuji.common.enums.BusinessType;
import com.yuji.common.utils.SecurityUtils;
import com.yuji.common.utils.StringUtils;
import com.yuji.common.utils.file.FileUploadUtils;
import com.yuji.common.utils.file.MimeTypeUtils;
import com.yuji.framework.web.service.TokenService;
import com.yuji.system.service.ISysUserService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

/**
 * 个人信息 业务处理
 * 
 * @author Liguoqiang
 */
@RestController
@RequestMapping("/system/user/profile")
public class SysProfileController extends BaseController
{
    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysMenuService menuService;

    @Autowired
    private TokenService tokenService;

    /**
     * 个人信息
     */
    @GetMapping
    public AjaxResult profile()
    {
        LoginUser loginUser = getLoginUser();
        SysUser user = loginUser.getUser();
        AjaxResult ajax = AjaxResult.success(user);
        ajax.put("roleGroup", userService.selectUserRoleGroup(loginUser.getUsername()));
        ajax.put("postGroup", userService.selectUserPostGroup(loginUser.getUsername()));
        return ajax;
    }

    /**
     * 修改用户
     */
    @Log(title = "个人信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult updateProfile(@RequestBody SysUser user)
    {
        LoginUser loginUser = getLoginUser();
        SysUser currentUser = loginUser.getUser();
        currentUser.setNickName(user.getNickName());
        currentUser.setEmail(user.getEmail());
        currentUser.setPhonenumber(user.getPhonenumber());
        currentUser.setSex(user.getSex());
        if (StringUtils.isNotEmpty(user.getPhonenumber()) && !userService.checkPhoneUnique(currentUser))
        {
            return error("修改用户'" + loginUser.getUsername() + "'失败，手机号码已存在");
        }
        if (StringUtils.isNotEmpty(user.getEmail()) && !userService.checkEmailUnique(currentUser))
        {
            return error("修改用户'" + loginUser.getUsername() + "'失败，邮箱账号已存在");
        }
        if (userService.updateUserProfile(currentUser) > 0)
        {
            // 更新缓存用户信息
            tokenService.setLoginUser(loginUser);
            return success();
        }
        return error("修改个人信息异常，请联系管理员");
    }

    /**
     * 重置密码
     */
    @Log(title = "个人信息", businessType = BusinessType.UPDATE)
    @PutMapping("/updatePwd")
    public AjaxResult updatePwd(String oldPassword, String newPassword)
    {
        LoginUser loginUser = getLoginUser();
        String userName = loginUser.getUsername();
        String password = loginUser.getPassword();
        if (!SecurityUtils.matchesPassword(oldPassword, password))
        {
            return error("修改密码失败，旧密码错误");
        }
        if (SecurityUtils.matchesPassword(newPassword, password))
        {
            return error("新密码不能与旧密码相同");
        }
        newPassword = SecurityUtils.encryptPassword(newPassword);
        if (userService.resetUserPwd(userName, newPassword) > 0)
        {
            // 更新缓存用户密码
            loginUser.getUser().setPassword(newPassword);
            tokenService.setLoginUser(loginUser);
            return success();
        }
        return error("修改密码异常，请联系管理员");
    }

    /**
     * 头像上传
     */
    @Log(title = "用户头像", businessType = BusinessType.UPDATE)
    @PostMapping("/avatar")
    public AjaxResult avatar(@RequestParam("avatarfile") MultipartFile file) throws Exception
    {
        if (!file.isEmpty())
        {
            LoginUser loginUser = getLoginUser();
            String avatar = FileUploadUtils.upload(YuJiConfig.getAvatarPath(), file, MimeTypeUtils.IMAGE_EXTENSION);
            if (userService.updateUserAvatar(loginUser.getUsername(), avatar))
            {
                AjaxResult ajax = AjaxResult.success();
                ajax.put("imgUrl", avatar);
                // 更新缓存用户头像
                loginUser.getUser().setAvatar(avatar);
                tokenService.setLoginUser(loginUser);
                return ajax;
            }
        }
        return error("上传图片异常，请联系管理员");
    }

    /**
     * 首页用户信息
     */
    @GetMapping("/homeInfo")
    public AjaxResult getHomeInfo() {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        SysUser user =  loginUser.getUser();
        DashboardUserVO vo = new DashboardUserVO();
        vo.setUserName(user.getUserName());
        vo.setNickName(user.getNickName());
        vo.setLastLoginTime(user.getLoginDate());
        vo.setLastLoginIp(user.getLoginIp());
        vo.setLastLoginAddr(IP2RegionUtils.ip2Region(user.getLoginIp()));
        if (StringUtils.isNotEmpty(user.getAvatar())) {
            vo.setAvatar(user.getAvatar());
        }
        vo.setDeptName(user.getDept().getDeptName());
        return success(vo);
    }

    @GetMapping("/shortcuts")
    public AjaxResult getHomeShortcuts() {
        SysUser user = userService.selectUserById(SecurityUtils.getUserId());
        List<Long> menuIds = ShortcutUserPreference.getValue(user.getPreferences());
        List<String> menuTypes = new ArrayList<>();
        menuTypes.add(UserConstants.TYPE_MENU);
        SysMenu menu = new SysMenu();
        menu.getParams().put("menuTypes",menuTypes);
        menu.getParams().put("menuIds", menuIds);
        startPage(8);
        List<SysMenu> menus = menuService.selectMenuList(menu,SecurityUtils.getUserId());
        Set<String> menuPerms = SecurityUtils.getLoginUser().getPermissions();
        if (!menuPerms.contains(Constants.ALL_PERMISSION)) {
            menus = menus.stream().filter(m -> {
                return StringUtils.isEmpty(m.getPerms()) || menuPerms.contains(m.getPerms());
            }).toList();
        }
        List<ShortcutVO> result = menus.stream()
                .sorted(Comparator.comparingInt(m -> menuIds.indexOf(m.getMenuId())))
                .map(m -> new ShortcutVO(m.getMenuName(), m.getIcon(), StringUtils.capitalize(m.getPath()))).toList();
        return success(result);
    }
}
