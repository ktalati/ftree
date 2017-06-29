package com.example.repository;

import com.example.model.Member;
import org.aspectj.weaver.ast.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository("memberRepository")
public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByFirstName(String firstName);

    @Query("SELECT m FROM Member m WHERE m.firstName LIKE :name% or m.lastName LIKE :name%")
    List<Member> findMemberByFirstNameOrLastName(@Param("name") String name);

    List<Member> findByGroups_id(Long id);

    @Query(value = "select * from member m where day(m.birthDate) = day(:current_date) and month(m.birthDate) = month(:current_date)", nativeQuery = true)
    List<Member> findMemberByBirthDate(@Param("current_date") Date date);

    @Query(value = "select * from member m where MOD(DAYOFYEAR(m.birthdate) - DAYOFYEAR(CURRENT_DATE()) + 366, 366) BETWEEN 0 and :noOfDays ORDER by DAYOFYEAR(m.birthdate) ASC", nativeQuery = true)
    List<Member> getUpcomingBirthDays(@Param("noOfDays") int days);

    @Query(value = "select * from member m where MOD(DAYOFYEAR(m.anniversary_date) - DAYOFYEAR(CURRENT_DATE()) + 366, 366) BETWEEN 0 and :noOfDays ORDER by DAYOFYEAR(m.anniversary_date) ASC", nativeQuery = true)
    List<Member> getUpcomingAnniversaries(@Param("noOfDays") int days);

    @Query(value = "select * from member m where day(m.anniversary_date) = day(:current_date) and month(m.anniversary_date) = month(:current_date)", nativeQuery = true)
    List<Member> findMemberByAnniversaryDate(@Param("current_date") Date date);

}
