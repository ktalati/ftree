package com.example.service;

import com.example.domain.MemberAutoSuggest;
import com.example.model.Member;
import com.example.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("memberService")
public class MemberServiceImpl implements MemberService{

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PhoneService phoneService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private GroupService groupService;


    @Override
    @Transactional(propagation= Propagation.REQUIRED)
    public void saveMember(Member member) {
        try{
            member.addPhones(member.getPhones());
            member.addAddress(member.getAddress());

            try{
                if(member.getFatherMemberValue() != null){
                    long fatherId = Long.parseLong(member.getFatherMemberValue());
                    member.setFatherName(findById(fatherId));
                }
            }catch (Exception e){
                // need to store data in different column
                member.setFatherValue(member.getFatherMemberValue());
            }

            try{
                if(member.getMotherMemberValue() != null){
                    long motherId = Long.parseLong(member.getMotherMemberValue());
                    member.setMotherName(findById(motherId));
                }
            }catch (Exception e){
                // need to store data in different column
                member.setMotherValue(member.getMotherMemberValue());
            }

            memberRepository.save(member);
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    @Override
    public Member findUserByName(String name) {
        return memberRepository.findByFirstName(name);
    }

    @Override
    public List<Member> findAllMembers() {
        return memberRepository.findAll();
    }

    @Override
    public Member findById(Long id) {

        Member member = memberRepository.findOne(id);

        if(member != null){
            if(member.getFatherName()!=null){
                member.setFatherMemberValue(String.valueOf(member.getFatherName().getId()));
                member.setFatherValue(member.getFatherName().getFirstName() + " " +member.getFatherName().getMiddleName() + " " +member.getFatherName().getLastName());
            }else{
                member.setFatherMemberValue(member.getFatherValue());
            }

            if(member.getMotherName()!=null){
                member.setMotherMemberValue(String.valueOf(member.getMotherName().getId()));
                member.setMotherValue(member.getMotherName().getFirstName() + " " +member.getMotherName().getMiddleName() + " " + member.getMotherName().getLastName());
            }else{
                member.setMotherMemberValue(member.getMotherValue());
            }
        }
        return member;
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED)
    public void deleteMember(Long id) {
        Member memberToBeDeleted = findById(id);
        memberRepository.delete(memberToBeDeleted);
    }

    @Override
    public List<MemberAutoSuggest> getMemberAutosuggest(String name) {
        List<Member> members = memberRepository.findMemberByFirstNameOrLastName(name);

        if (!CollectionUtils.isEmpty(members)) {
            List<MemberAutoSuggest> memberAutoSuggestList = new ArrayList<>();
            for (Member member : members) {
                memberAutoSuggestList.add(new MemberAutoSuggest(new StringBuilder().append(member.getFirstName()).append(" ").append(member.getMiddleName()).append(" ").append(member.getLastName()).toString(), member.getId().toString()));
            }
            return memberAutoSuggestList;
        }
        return null;
    }

    @Override
    public List<Member> findMembersByGroupId(Long id) {
        return memberRepository.findByGroups_id(id);
    }

}