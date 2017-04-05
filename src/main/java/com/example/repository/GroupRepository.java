package com.example.repository;

import com.example.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("groupRepository")
public interface GroupRepository extends JpaRepository<Group, Long> {

    public Group findByName(String name);

}
