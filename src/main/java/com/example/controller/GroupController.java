package com.example.controller;

import com.example.domain.GroupMember;
import com.example.model.Group;
import com.example.model.Member;
import com.example.service.GroupService;
import com.example.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/group")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @Autowired
    private MemberService memberService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class,
                new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S"), true));
    }

    @RequestMapping(value = "/listGroup", method = RequestMethod.GET)
    public ModelAndView listGroups() {
        ModelAndView modelAndView = new ModelAndView();
        List<Group> groups = groupService.findAllGroups();
        modelAndView.addObject("groups", groups);
        modelAndView.setViewName("group/listGroup");
        modelAndView.addObject("menu", "listGroup");
        return modelAndView;
    }

    @RequestMapping(value = "/createGroup", method = RequestMethod.GET)
    public ModelAndView registration() {
        ModelAndView modelAndView = new ModelAndView();
        Group group = new Group();
        modelAndView.addObject("group", group);
        modelAndView.setViewName("group/manageGroup");
        modelAndView.addObject("menu", "createGroup");
        return modelAndView;
    }

    @RequestMapping(value = "/createGroup", method = RequestMethod.POST)
    public ModelAndView createNewGroup(@Valid Group group, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        if (group.getId() == null || group.getId() < 0) {
            Group groupExists = groupService.findGroupByName(group.getName());
            if (groupExists != null) {
                bindingResult
                        .rejectValue("name", "error.group",
                                "There is already a Group with this name");
            }
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("group/manageGroup");
            modelAndView.addObject("menu", "createGroup");
        } else {
            if (group.getId() == null || group.getId() < 0) {
                group.setCreatedOn(new Date());
            } else {
                group.setUpdatedOn(new Date());
            }
            groupService.saveGroup(group);
            if (group.getId() != null && group.getId() > 0) {
                modelAndView.addObject("successMessage", "Group has been updated successfullly");
            } else {
                modelAndView.addObject("successMessage", "Group has been created successfullly");
            }
            List<Group> groups = groupService.findAllGroups();
            modelAndView = new ModelAndView();
            modelAndView.addObject("groups", groups);
            modelAndView.setViewName("group/listGroup");
            modelAndView.addObject("menu", "listGroup");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/addToGroup", method = RequestMethod.POST)
    public ResponseEntity<String> addMemberToGroup(@Valid GroupMember groupMember, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();

        try{

        Group group = groupService.findById(Long.parseLong(groupMember.getGroupId()));

        Member memberToBeAdded = memberService.findById(Long.parseLong(groupMember.getMemberValue()));

        if(memberToBeAdded != null){

            if(group.getMembers().contains(memberToBeAdded)){
                return new ResponseEntity("Member already exist !!", HttpStatus.INTERNAL_SERVER_ERROR);
            }

            group.getMembers().add(memberToBeAdded);
            memberToBeAdded.getGroups().add(group);
            //memberService.saveMember(memberToBeAdded);
            groupService.saveGroup(group);
            return new ResponseEntity("Data Saved Successfully", HttpStatus.OK);
        }else {
            return new ResponseEntity("Member Not Found !!!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }




    }

    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public ModelAndView showGroup(@ModelAttribute("id") Long id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        if (StringUtils.isEmpty(id) || id < 0) {
            throw new Exception("Group Not Found");
        }
        Group group = groupService.findById(id.longValue());
        if (group == null) {
            throw new Exception("Group Not Found");
        }

        List<Member> members = memberService.findMembersByGroupId(group.getId());
        modelAndView.addObject("members", members);

        modelAndView = new ModelAndView();
        modelAndView.addObject("group", group);
        modelAndView.addObject("members", members);
        modelAndView.setViewName("group/groupDetail");
        modelAndView.addObject("menu", "listGroup");
        return modelAndView;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView editGroup(@ModelAttribute("id") Long id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        if (StringUtils.isEmpty(id) || id < 0) {
            throw new Exception("Group Not Found");
        }
        Group group = groupService.findById(id.longValue());
        if (group == null) {
            throw new Exception("Group Not Found");
        }
        modelAndView = new ModelAndView();
        modelAndView.addObject("group", group);
        modelAndView.setViewName("group/manageGroup");
        modelAndView.addObject("menu", "createGroup");
        return modelAndView;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ModelAndView deleteGroup(@ModelAttribute("id") Long id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        if (StringUtils.isEmpty(id) || id < 0) {
            throw new Exception("Group Not Found");
        }
        Group group = groupService.findById(id);
        if (group == null) {
            throw new Exception("Group Not Found");
        }
        groupService.deleteGroup(id.longValue());
        List<Group> groups = groupService.findAllGroups();
        modelAndView = new ModelAndView();
        modelAndView.addObject("groups", groups);
        modelAndView.setViewName("group/listGroup");
        modelAndView.addObject("menu", "listGroup");
        return modelAndView;
    }
}