package com.example.domain;

import java.util.ArrayList;
import java.util.List;

public class FamilyMember {

    private String name;
    private byte[] photo;
    private List<FamilyMember> children = new ArrayList<FamilyMember>();


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public List<FamilyMember> getChildren() {
        return children;
    }

    public void setChildren(List<FamilyMember> children) {
        this.children = children;
    }
}
