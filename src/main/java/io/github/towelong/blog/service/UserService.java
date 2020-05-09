/**
 * @作者 WeLong
 * @博客 $ https://towelong.cn
 * @开源项目 $ https://github.com/ToWeLong
 * @创建时间 2020/4/17 19:00
 */
package io.github.towelong.blog.service;

import com.amdelamar.jhash.exception.InvalidHashException;
import com.auth0.jwt.interfaces.Claim;
import io.github.towelong.blog.BO.GroupBO;
import io.github.towelong.blog.BO.UserBO;
import io.github.towelong.blog.VO.UserAdminInfoVO;
import io.github.towelong.blog.exception.http.ExistException;
import io.github.towelong.blog.exception.http.ForbiddenException;
import io.github.towelong.blog.exception.http.NotFoundException;
import io.github.towelong.blog.model.Group;
import io.github.towelong.blog.model.User;
import io.github.towelong.blog.model.UserGroup;
import io.github.towelong.blog.repository.GroupRepository;
import io.github.towelong.blog.repository.UserGroupRepository;
import io.github.towelong.blog.repository.UserRepository;
import io.github.towelong.blog.utils.JWTUtils;
import io.github.towelong.blog.utils.RequestHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.*;

@Service
public class UserService {
    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserGroupRepository userGroupRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTUtils jwtUtils;

    public Map<String, Object> getUserGroup(Long uid) {
        UserGroup userGroup = userGroupRepository.findByUserId(uid);

        UserBO userBO = new UserBO();
        GroupBO groupBO = new GroupBO();

        Optional<User> userOptional = userRepository.findById(uid);
        userOptional.ifPresent(user -> BeanUtils.copyProperties(user, userBO));

        Optional<Group> groupOptional = groupRepository.findById(userGroup.getGroupId());
        groupOptional.ifPresent(group -> BeanUtils.copyProperties(group, groupBO));

        Map<String, Object> userGroupMap = new HashMap<>();
        userGroupMap.put("user_info", userBO);
        userGroupMap.put("group", groupBO);
        return userGroupMap;
    }


    public void registerUser(UserBO user) {
        User exit = userRepository.findOneByAccount(user.getAccount());
        if (exit != null) {
            throw new ExistException(20001);
        }
        User user1 = new User();
        String account = user.getAccount();
        String password = user1.setPasswordEncrypt(user.getPassword());
        String nickname = user.getNickname();
        userRepository.nativeSave(nickname, account, password);
        Long uid = userRepository.findByAccount(account).getId();
        if (userGroupRepository.findByUserId(uid) != null) {
            throw new ExistException(20004);
        }
        userGroupRepository.nativeSave(uid, user.getGroup_id());
    }

    public void editUser(UserBO user) {
        User user1 = new User();
        Long id = user.getId();
        if (!userRepository.findById(id).isPresent()) {
            throw new ExistException(20002);
        }
        String account = user.getAccount();
        String nickname = user.getNickname();
        // 将修改后的user写入数据库
        // 密码不为空的情况,写入新的密码
        if (!user.getPassword().isEmpty()) {
            String password1 = user1.setPasswordEncrypt(user.getPassword());
            userRepository.editUserPassword(password1, id);
            userRepository.editUser(nickname, account, id);
        }
        // 传过来的密码为空，则不修改
        userRepository.editUser(nickname, account, id);
        // 如果用户所在分组改变的话，去user_group中更新用户的group_id
        Long gid = user.getGroup_id();
        userGroupRepository.editUserGroup(gid, id);
    }

    public String createLoginToken(User user) throws InvalidHashException, UnsupportedEncodingException {
        User exit = userRepository.findByAccount(user.getAccount());
        if (exit == null) {
            throw new NotFoundException(20002);
        }
        if (exit.getDeleteTime() != null) {
            throw new NotFoundException(20002);
        }
        boolean isTrue = user.encrypt(user.getPassword(), exit.getPassword());
        if (isTrue) {
            UserGroup userGroup = userGroupRepository.findByUserId(exit.getId());
            Optional<Group> group = groupRepository.findById(userGroup.getGroupId());
            Long scope = group.get().getScope();
            return jwtUtils.CreateAccessToken(exit.getId(), scope);
        }
        throw new ForbiddenException(20003);
    }

    public String createRefreshToken(String access_token) throws UnsupportedEncodingException {
        return jwtUtils.CreateRefreshToken(jwtUtils.getInTokenUid(access_token));
    }

    public String refreshToAccess(String refresh_token, String access_token) throws UnsupportedEncodingException {
//        1. 先判断access_token是否合法,但是此时的access_token已过期
//        2. 从access_token中取出uid 和 scope,利用refresh_token换取新的access_token
//        3. 重新生成 access_token
        Long uid = jwtUtils.verifyRefreshTokenAndReturnUid(refresh_token);
        Long scope = jwtUtils.getInTokenScope(access_token);
        return jwtUtils.CreateAccessToken(uid, scope);
    }


    public Page<User> getAllUser(Integer pageNum, Integer size) {
        Pageable pageable = PageRequest.of(
                pageNum,
                size,
                Sort.by("createTime").descending());
        return userRepository.findAll(pageable);
    }

    public void deleteUser(Long uid){
        userRepository.deleteUser(new Date(),uid);
        userGroupRepository.deleteUserGroup(new Date(),uid);
    }

    public void changePassword(UserBO user) throws InvalidHashException {
        User user1 = new User();
        Optional<User> exit = userRepository.findById(RequestHelper.getUid());
        exit.orElseThrow(()-> new NotFoundException(20002));
        String cryptPassword = exit.get().getPassword();
        boolean isTrue = user1.encrypt(user.getOldPassword(), cryptPassword);
        if(isTrue){
            String password1 = user1.setPasswordEncrypt(user.getPassword());
            userRepository.editUserPassword(password1, RequestHelper.getUid());
        }
        else {
            throw new ForbiddenException(20003);
        }
    }
}
