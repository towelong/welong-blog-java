/**
 * @作者 WeLong
 * @博客 $ https://towelong.cn
 * @开源项目 $ https://github.com/ToWeLong
 * @创建时间 2020/4/17 18:59
 */
package io.github.towelong.blog.api.v1;

import com.amdelamar.jhash.exception.InvalidHashException;
import io.github.towelong.blog.BO.PageCounter;
import io.github.towelong.blog.BO.UserBO;
import io.github.towelong.blog.VO.PagingDozer;
import io.github.towelong.blog.VO.UserAdminInfoVO;
import io.github.towelong.blog.annotation.AdminRequired;
import io.github.towelong.blog.annotation.LoginRequired;
import io.github.towelong.blog.exception.http.Success;
import io.github.towelong.blog.model.User;
import io.github.towelong.blog.service.UserService;
import io.github.towelong.blog.utils.CommonUtil;
import io.github.towelong.blog.utils.RequestHelper;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/info")
    @LoginRequired
    public Map<String,Object> getUserGroup(){
        return userService.getUserGroup(RequestHelper.getUid());
    }

    @GetMapping("/info/{uid}")
    @AdminRequired
    public Map<String,Object> getUserGroupByUid(@PathVariable Long uid){
        return userService.getUserGroup(uid);
    }

    @PostMapping("/register")
    public void register(@RequestBody UserBO user){
        userService.registerUser(user);
        throw new Success(0);
    }

    @PostMapping("/login")
    public Map<String,String> getToken(@RequestBody User user) throws UnsupportedEncodingException, InvalidHashException {
        Map<String,String> map = new HashMap<>();
        String access_token = userService.createLoginToken(user);
        map.put("access_token",access_token);
        map.put("refresh_token",userService.createRefreshToken(access_token));
        return map;
    }

    @PostMapping("/refresh")
    public Map<String,String> getRefreshToken(@RequestBody Map<String,String> map) throws UnsupportedEncodingException {
        String access_token = map.get("access_token");
        String refresh_token = map.get("refresh_token");
        String refreshToken = userService.refreshToAccess(refresh_token,access_token);
        Map<String,String> refreshMap = new HashMap<>();
        refreshMap.put("new_access_token",refreshToken);
        return refreshMap;
    }


    @GetMapping("/all")
    @AdminRequired
    public PagingDozer<User,UserAdminInfoVO> getUserAll(
            @RequestParam(defaultValue = "0") Integer start,
            @RequestParam(defaultValue = "10") Integer count){
        PageCounter pageCounter = CommonUtil.convertToPageParameter(start,count);
        Page<User> userPage = userService.getAllUser(
                pageCounter.getPage(),
                pageCounter.getCount());
        return new PagingDozer<>(userPage, UserAdminInfoVO.class);
    }

    @PutMapping("/edit")
    @AdminRequired
    public void editUser(@RequestBody UserBO userBO){
        userService.editUser(userBO);
        throw new Success(0);
    }

    @DeleteMapping("/delete")
    public void delete(@RequestParam(value = "id") Long id){
        userService.deleteUser(id);
        throw new Success(0);
    }

    @PutMapping("/password")
    @LoginRequired
    public void changePassword(@RequestBody UserBO user) throws InvalidHashException {
        userService.changePassword(user);
        throw new Success(0);
    }
}
