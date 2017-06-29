package com.example.service;

import com.example.model.Member;

import java.util.Date;
import java.util.List;

public interface DashboardService {

    public long getMemberCount();

    public long getGroupCount();

    public long getRemainingMessages();

    public long getTotalSubscriptions();

    List<Member> getUpcomingBirthDays(int noOfDays);

    List<Member> getUpcomingAnniversaries(int noOfDays);

    List<Member> findMemberByAnniversaryDate(Date date);
}
