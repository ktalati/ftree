package com.example.service;

import com.example.model.Member;
import com.example.repository.GroupRepository;
import com.example.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("dashboardService")
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Override
    public long getMemberCount() {
        return memberRepository.count();
    }

    @Override
    public long getGroupCount() {
        return groupRepository.count();
    }

    @Override
    public long getRemainingMessages() {
        return 0;
    }

    @Override
    public long getTotalSubscriptions() {
        return 0;
    }

    @Override
    public List<Member> getUpcomingBirthDays() {
        return memberRepository.getUpcomingBirthDays();
    }

    @Override
    public List<Member> findMemberByAnniversaryDate(Date date) {
        return memberRepository.findMemberByAnniversaryDate(date);
    }
}