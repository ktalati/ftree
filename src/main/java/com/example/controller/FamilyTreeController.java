package com.example.controller;

import com.example.domain.FamilyMember;
import com.example.model.Member;
import com.example.service.MemberService;
import com.example.util.FamilyTreeConstants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

@Controller
@RequestMapping(value = "/tree")
public class FamilyTreeController {

    public static final String MEMBER_SEPERATOR = "~";
    
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

        Object object = findRoot(member);
        FamilyMember familyMember = new FamilyMember();
        if(object instanceof Member){
            modelAndView.addObject("familyTree", generateTree((Member) object, member.getId() , familyMember));
        }else {
            String firstParent = object.toString().split("~")[0];
            Member firstParentMember = memberService.findById(Long.parseLong(firstParent));

            FamilyMember childTree = generateTree(firstParentMember, member.getId() ,familyMember);
            familyMember = new FamilyMember();
            familyMember.setName(object.toString().split(MEMBER_SEPERATOR)[1]);
            familyMember.setPhotoStr(encodeBytes(new byte[]{}));
            familyMember.setChildren(new ArrayList<FamilyMember>(){{
                add(childTree);
            }});

            modelAndView.addObject("familyTree", familyMember);
        }
        modelAndView.setViewName("tree/familyTree");
        modelAndView.addObject("menu", "addMember");
        return modelAndView;
    }

    private FamilyMember generateTree(Member member, long selectedMember, FamilyMember familyMember) {
        if (member != null) {
            if(member.getId() == selectedMember){
                familyMember.setSelectedMember(Boolean.TRUE);
            }
            familyMember.setName(member.getFirstName());
            familyMember.setPhotoStr(encodeBytes(member.getAvatar()));
            if (member.getChildren() != null) {
                for (Member child : member.getChildren()) {
                    if(familyMember.getChildren() == null){
                        familyMember.setChildren(new ArrayList<FamilyMember>());
                    }
                    familyMember.getChildren().add(generateTree(child, selectedMember, new FamilyMember()));
                }
            }
        }
        return familyMember;
    }

    private Object findRoot(Member member){
        if(member != null){
            if(member.getFatherName() != null){
                return findRoot(member.getFatherName());
            }else {
                if(member.getFatherValue() != null){
                    StringBuilder builder = new StringBuilder();
                    builder.append(member.getId());
                    builder.append("~");
                    builder.append(member.getFatherValue());
                    return builder.toString();
                }
            }
        }
        return null;
    }
    
    private String encodeBytes(final byte[] photo){
    	byte[] encoded=Base64.encode(photo);
    	if(null != encoded && encoded.length > 0){
    		return "data:image/jpg;base64,".concat(new String(encoded));
    	}else{
    		return FamilyTreeConstants.DEFAULT_AVATAR_PATH;
    	}
    }
}