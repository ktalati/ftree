package com.example.service;

import com.example.domain.MemberAutoSuggest;
import com.example.model.Member;

import java.util.List;

public interface MemberService {

    void saveMember(Member member);

    Member findUserByName(String name);

    List<Member> findAllMembers();

    Member findById(Long id);

    void deleteMember(Long id);

    List<MemberAutoSuggest> getMemberAutosuggest(String name);

    List<Member> findMembersByGroupId(Long id);
}
