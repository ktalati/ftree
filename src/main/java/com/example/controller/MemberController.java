package com.example.controller;

import com.example.domain.MemberAutoSuggest;
import com.example.model.Member;
import com.example.model.Phone;
import com.example.service.GroupService;
import com.example.service.MemberService;
import com.example.util.PhoneEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private GroupService groupService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, "createdOn",
                new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S"), true));

        binder.registerCustomEditor(Date.class, "updatedOn",
                new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S"), true));
    }

    @RequestMapping(value = "/listMember", method = RequestMethod.GET)
    public ModelAndView listMembers() {
        ModelAndView modelAndView = new ModelAndView();
        List<Member> members = memberService.findAllMembers();
        modelAndView.addObject("members", members);
        modelAndView.setViewName("member/listMember");
        modelAndView.addObject("menu", "listMembers");
        return modelAndView;
    }

    @RequestMapping(value = "/addMember", method = RequestMethod.GET)
    public ModelAndView registration() {
        ModelAndView modelAndView = new ModelAndView();
        Member member = new Member();

        member.getPhones().add(new Phone(PhoneEnum.LANDLINE.getId()));
        member.getPhones().add(new Phone(PhoneEnum.MOBILE.getId()));

        modelAndView.addObject("member", member);
        modelAndView.setViewName("member/manageMember");
        modelAndView.addObject("menu", "addMember");
        modelAndView.addObject("groups", groupService.findAllGroups());
        return modelAndView;
    }

    @RequestMapping(value = "/addMember", method = RequestMethod.POST)
    public ModelAndView addNewMember(@Valid Member member, BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            member.setFatherMemberValue("");
            member.setMotherMemberValue("");
            if(bindingResult.getFieldError("fatherMemberValue") == null){
                bindingResult.rejectValue("fatherMemberValue","error.member","*Please provide Father Name");
            }
            if(bindingResult.getFieldError("motherMemberValue") == null){
                bindingResult.rejectValue("motherMemberValue","error.member","*Please provide Mother Name");
            }
            modelAndView.addObject("member", member);
            modelAndView.addObject("groups", groupService.findAllGroups());
            modelAndView.setViewName("member/manageMember");
            modelAndView.addObject("menu", "addMember");
        } else {
            if (member.getId() == null || member.getId() < 0) {
                member.setCreatedOn(new Date());
            } else {
                member.setUpdatedOn(new Date());
            }

            memberService.saveMember(member);
            if (member.getId() != null && member.getId() > 0) {
                modelAndView.addObject("successMessage", "Member has been updated successfullly");
            } else {
                modelAndView.addObject("successMessage", "Member has been created successfullly");
            }
            List<Member> members = memberService.findAllMembers();
            modelAndView = new ModelAndView();
            modelAndView.addObject("members", members);
            modelAndView.setViewName("member/listMember");
            modelAndView.addObject("menu", "listMember");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView editMember(@ModelAttribute("id") Long id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        if (StringUtils.isEmpty(id) || id < 0) {
            throw new Exception("Member Not Found");
        }
        Member member = memberService.findById(id.longValue());
        if (member == null) {
            throw new Exception("Member Not Found");
        }
        modelAndView = new ModelAndView();
        modelAndView.addObject("member", member);
        modelAndView.setViewName("member/manageMember");
        modelAndView.addObject("menu", "addMember");
        return modelAndView;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ModelAndView deleteMember(@ModelAttribute("id") Long id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        if (StringUtils.isEmpty(id) || id < 0) {
            throw new Exception("Member Not Found");
        }
        Member member = memberService.findById(id);
        if (member == null) {
            throw new Exception("Member Not Found");
        }
        memberService.deleteMember(id.longValue());
        List<Member> members = memberService.findAllMembers();
        modelAndView = new ModelAndView();
        modelAndView.addObject("members", members);
        modelAndView.setViewName("member/listMember");
        modelAndView.addObject("menu", "listMember");
        return modelAndView;
    }


    @RequestMapping(value = "/getMember", method = RequestMethod.GET)
    public ResponseEntity<List<MemberAutoSuggest>> getMemberAutosuggest(@RequestParam(value = "term") final String name) {
        List<MemberAutoSuggest> autosuggestions = memberService.getMemberAutosuggest(name);
        List<MemberAutoSuggest> memberAutoSuggestList = new ArrayList<>();
        if(autosuggestions == null){

            memberAutoSuggestList.add(new MemberAutoSuggest(new StringBuilder().append("Kishan1").append(" ").append("N").append(" ").append("Talati").toString(), "1"));
            memberAutoSuggestList.add(new MemberAutoSuggest(new StringBuilder().append("Kishan2").append(" ").append("N").append(" ").append("Talati").toString(), "2"));
            memberAutoSuggestList.add(new MemberAutoSuggest(new StringBuilder().append("Kishan3").append(" ").append("N").append(" ").append("Talati").toString(), "3"));
            memberAutoSuggestList.add(new MemberAutoSuggest(new StringBuilder().append("Kishan4").append(" ").append("N").append(" ").append("Talati").toString(), "4"));
            return new ResponseEntity<List<MemberAutoSuggest>>(memberAutoSuggestList, HttpStatus.OK);

        }
        return new ResponseEntity<List<MemberAutoSuggest>>(autosuggestions, HttpStatus.OK);
    }
}