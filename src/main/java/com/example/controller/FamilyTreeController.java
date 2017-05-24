package com.example.controller;

import com.example.domain.FamilyMember;
import com.example.model.Member;
import com.example.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "/tree")
public class FamilyTreeController {

    @Autowired
    private MemberService memberService;

    @RequestMapping(value = "/generate", method = RequestMethod.GET)
    public ModelAndView viewMember(@ModelAttribute("id") Long id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        if (StringUtils.isEmpty(id) || id < 0) {
            throw new Exception("Member Not Found");
        }
        Member member = memberService.findById(id.longValue());
        if (member == null) {
            throw new Exception("Member Not Found");
        }
        modelAndView = new ModelAndView();

        FamilyMember familyMember = new FamilyMember();
        modelAndView.addObject("familyTree", generateTree(member, familyMember));
        modelAndView.setViewName("tree/familyTree");
        modelAndView.addObject("menu", "addMember");
        return modelAndView;
    }

    private FamilyMember generateTree(Member member, FamilyMember familyMember) {
        if (member != null) {
            familyMember.setName(member.getFirstName());
            familyMember.setPhoto(member.getAvatar());
            if (member.getChildren() != null) {
                for (Member child : member.getChildren()) {
                    familyMember.getChildren().add(generateTree(child, new FamilyMember()));
                }
            }
        }
        return familyMember;
    }
}