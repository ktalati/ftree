package com.example.service;

import com.example.model.Group;

import java.util.List;

public interface GroupService {

    void saveGroup(Group group);

    Group findGroupByName(String name);

    List<Group> findAllGroups();

    Group findById(Long id);

    void deleteGroup(Long id);
}
