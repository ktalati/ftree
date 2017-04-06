package com.example.controller;

import com.example.model.Group;
import com.example.model.Member;
import com.example.model.Phone;
import com.example.service.GroupService;
import com.example.service.MemberService;
import com.example.util.PhoneEnum;
import com.example.util.SMSSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/sms")
public class SMSController {

    @Autowired
    private GroupService groupService;

    @Autowired
    private MemberService memberService;

    @Resource
    private SMSSender smsSender;

    @RequestMapping(value = "/sendSMS", method = RequestMethod.POST)
    public ResponseEntity<String> addMemberToGroup(@RequestParam(name = "smsInput") String message, @RequestParam(name = "groupId") Long groupId) {
        ModelAndView modelAndView = new ModelAndView();

        if(groupId != null && !StringUtils.isEmpty(message)){
            try{
                Group group = groupService.findById(groupId);
                if(group != null && group.getMembers()!= null && group.getMembers().size() > 0){

                    List<String> numbers = new ArrayList<String>();

                    for(Member member : group.getMembers()){
                        if(member.getPhones()!= null && member.getPhones().size() > 0){
                            for(Phone phone :member.getPhones()){
                                if(phone.getType() == PhoneEnum.MOBILE.getId()){
                                    numbers.add(phone.getNumber());
                                }
                            }
                        }
                    }

                    new Thread(new Runnable(){
                        @Override
                        public void run() {
                            smsSender.send(message, numbers);
                        }
                    }).start();

                    return new ResponseEntity("Message has been sent !!!", HttpStatus.OK);
                }else {
                    return new ResponseEntity("Group doesn't contains any Member !!!", HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }catch (Exception e){
                return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity("Group & Message must not be empty !!!", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}