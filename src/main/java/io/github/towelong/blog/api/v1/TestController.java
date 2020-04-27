/**
 * @作者 WeLong
 * @博客 $ https://towelong.cn
 * @开源项目 $ https://github.com/ToWeLong
 * @创建时间 2020/4/15 17:38
 */
package io.github.towelong.blog.api.v1;

import io.github.towelong.blog.annotation.LoginRequired;
import io.github.towelong.blog.exception.http.NotFoundException;
import io.github.towelong.blog.model.Group;
import io.github.towelong.blog.model.UserGroup;
import io.github.towelong.blog.repository.ArticleUserRepository;
import io.github.towelong.blog.repository.GroupRepository;
import io.github.towelong.blog.repository.UserGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class TestController {

    @Autowired
    private ArticleUserRepository articleUserRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserGroupRepository userGroupRepository;


    @GetMapping("/user")
    @LoginRequired
    public Group art() {
        UserGroup userGroup = userGroupRepository.findByUserId((long) 1);
        Optional<Group> groupOptional = groupRepository.findById(userGroup.getGroupId());
        return groupOptional.orElseThrow(()-> new NotFoundException(10000));
    }
}
