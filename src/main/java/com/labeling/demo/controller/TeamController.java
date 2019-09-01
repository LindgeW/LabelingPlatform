package com.labeling.demo.controller;

import com.labeling.demo.entity.Task;
import com.labeling.demo.entity.Team;
import com.labeling.demo.entity.User;
import com.labeling.demo.service.TaskService;
import com.labeling.demo.service.TeamService;
import com.labeling.demo.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
public class TeamController {
    private static final String root = "bg/";
    private UserService userService;
    private TaskService taskService;
    private TeamService teamService;

    @Autowired
    public TeamController(UserService userService, TaskService taskService, TeamService teamService) {
        this.userService = userService;
        this.taskService = taskService;
        this.teamService = teamService;
    }

    @RequestMapping("/build")
    @RequiresRoles("admin")
    public String teamPage(Model model){
        List<Task> tasks = taskService.findAll();
        List<User> users = userService.findUserWithoutTeam();

        User curUser = (User) SecurityUtils.getSubject().getPrincipal();
        List<String> teamNames = teamService.findByExpertName(curUser.getUsername());

        model.addAttribute("tasks", tasks);
        model.addAttribute("users", users);
        model.addAttribute("builtTeams", teamNames);

        return root+"build_team";
    }

    @RequestMapping("/browse")
    @RequiresRoles("admin")
    public String browseTeam(Model model){
        List<Team> teamLst = teamService.findAll();

        model.addAttribute("teams", teamLst);
        return root+"browse_team";
    }

    @PostMapping("/build_team")
    @RequiresRoles("admin")
    public String buildTeam(@RequestParam("task") String taskName,
                            @RequestParam("teamName") String teamName,
                            @RequestParam("members") String members,
                            Model model){
        /*
            同一个人不能进多个团队，一个团队不能标注多个任务！

            插入（新建）：
                团队不存在
            修改（添加成员）：
                团队存在，同一个人创建，且标注任务相同
            异常（团队重名）：
                1、团队存在，不是同一个人创建
                2、团队存在，同一个人创建，标注任务不同
         */

        User curUser = (User) SecurityUtils.getSubject().getPrincipal();
        String userName = curUser.getUsername();
        String[] memberLst = StringUtils.split(members, ";");

        // 保存团队信息
        Team team = teamService.findByName(teamName);
        boolean isBuilt = true;
        if (team == null) {
            boolean isOk = teamService.save(new Team(teamName, taskName, members, userName, false));
            if(isOk){
                // 修改用户状态
                for (String memberName: memberLst) {
                    User user = new User();
                    user.setUsername(memberName);
                    user.setTeamName(teamName);
                    userService.updateUser(user);
                }
            }
        } else if (!userName.equals(team.getExpertname())){
            isBuilt = false;
        } else if(!taskName.equals(team.getTaskName())){
            isBuilt = false;
        } else{
            // 添加成员
            String[] oldMembers = StringUtils.split(team.getMembers(), ";");
            Set<String> memberSet = new HashSet<>(Arrays.asList(oldMembers));
            boolean isNew = false;
            for(String memberName: memberLst){
                if (!memberSet.contains(memberName)){
                    User user = new User();
                    user.setUsername(memberName);
                    user.setTeamName(teamName);
                    userService.updateUser(user);

                    isNew = true;
                    memberSet.add(memberName);
                }
            }
            if (isNew) {  //有新成员加入
                Team updateTeam = new Team();
                updateTeam.setTeamId(team.getTeamId());
                updateTeam.setMembers(StringUtils.join(memberSet, ";"));
                teamService.updateTeam(updateTeam);
            }
        }

        model.addAttribute("isBuilt", isBuilt);
        return "forward:/build";
    }

}
