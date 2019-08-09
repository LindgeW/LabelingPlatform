package com.labeling.demo.controller;

import com.labeling.demo.entity.Task;
import com.labeling.demo.entity.Team;
import com.labeling.demo.entity.User;
import com.labeling.demo.service.TaskService;
import com.labeling.demo.service.TeamService;
import com.labeling.demo.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@Controller
public class TeamController {

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
        List<User> users = userService.findAll();

        model.addAttribute("tasks", tasks);
        model.addAttribute("users", users);

        return "team";
    }

    @PostMapping("/build_team")
    @RequiresRoles("admin")
    public String buildTeam(@RequestParam("task") String taskName,
                            @RequestParam("teamName") String teamName,
                            @RequestParam("members") String members, Model model){

        String[] memberLst = StringUtils.split(members, ";");
        // 修改用户状态
        for (String m: memberLst) {
            userService.updateUser(new User(m, teamName));
        }

        // 保存团队信息
        teamService.save(new Team(teamName, taskName, (short)memberLst.length));

        model.addAttribute("isBuilt", true);
        return "forward:/build";
    }

}
