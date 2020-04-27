/**
 * @作者 WeLong
 * @博客 $ https://towelong.cn
 * @开源项目 $ https://github.com/ToWeLong
 * @创建时间 2020/4/19 16:01
 */
package io.github.towelong.blog.api.v1;


import io.github.towelong.blog.annotation.AdminRequired;
import io.github.towelong.blog.exception.http.Success;
import io.github.towelong.blog.model.Group;
import io.github.towelong.blog.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/group")
public class GroupController {

    @Autowired
    private GroupService groupService;

//    提供给CMS创建用户的接口
    @GetMapping("/all")
    @AdminRequired
    public List<Group> findAll(){
        return groupService.getAllGroup();
    }

    @GetMapping("/{gid}")
    @AdminRequired
    public Group findOne(@PathVariable Long gid){
        return groupService.getOneGroup(gid);
    }

    // TODO 增删改查分组

    // 提供给CMS增删改查分组
    @GetMapping("/alls")
    @AdminRequired
    public List<Group> findAllUnlimited(){
        return groupService.getAll();
    }

    @PostMapping("/add")
    @AdminRequired
    public void insertGroup(@RequestBody Group group){
        groupService.insertGroup(group);
        throw new Success(0);
    }

    @PutMapping("/edit")
    @AdminRequired
    public void editGroup(@RequestBody Group group){
        groupService.updateGroup(group);
        throw new Success(0);
    }

    @DeleteMapping("/delete")
    @AdminRequired
    public void delete(@RequestParam(value = "id")Long id){
        groupService.deleteGroup(id);
        throw new Success(0);
    }
}
