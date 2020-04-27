/**
 * @作者 WeLong
 * @博客 $ https://towelong.cn
 * @开源项目 $ https://github.com/ToWeLong
 * @创建时间 2020/4/22 20:19
 */
package io.github.towelong.blog.service;

import io.github.towelong.blog.model.Group;
import io.github.towelong.blog.model.User;
import io.github.towelong.blog.model.UserGroup;
import io.github.towelong.blog.repository.GroupRepository;
import io.github.towelong.blog.repository.UserGroupRepository;
import io.github.towelong.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserGroupRepository userGroupRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Group> getAllGroup(){
        List<Group> groupList = groupRepository.findAllByOrderByCreateTimeDesc();
        return groupList.stream().filter(group -> group.getDeleteTime() == null).collect(Collectors.toList());
    }

    public List<Group> getAll(){
        return groupRepository.findAllByOrderByCreateTimeDesc();
    }

    public Group getOneGroup(Long gid){
        return groupRepository.getOneById(gid);
    }

    public void insertGroup(Group group){
        groupRepository.insertGroup(group.getGroupName(),group.getScope());
    }

    public void updateGroup(Group group){
        groupRepository.updateGroup(group.getGroupName(),group.getScope(),group.getId());
    }

    @Transactional
    public void deleteGroup(Long gid){
        // 删除分组表里的记录
        groupRepository.deleteGroup(new Date(),gid);
        // 删除分组下的用户
        List<UserGroup> userGroupList = userGroupRepository.findAllByGroupId(gid);
        for(UserGroup userGroup:userGroupList){
            Long groupId = userGroup.getGroupId();
            Long uid = userGroup.getUserId();
            userRepository.deleteUser(new Date(),uid);
            userGroupRepository.deleteUserGroupByGroupId(new Date(),groupId);
        }
    }
}
