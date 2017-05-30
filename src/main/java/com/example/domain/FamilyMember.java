package com.example.domain;

import java.util.List;

public class FamilyMember {

    private String name;
    private String photoStr;
    private boolean selectedMember;
    private List<FamilyMember> children;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoStr() {
        return photoStr;
    }

    public void setPhotoStr(String photoStr) {
        this.photoStr = photoStr;
    }

    public boolean isSelectedMember() {
        return selectedMember;
    }

    public void setSelectedMember(boolean selectedMember) {
        this.selectedMember = selectedMember;
    }

    public List<FamilyMember> getChildren() {
        return children;
    }

    public void setChildren(List<FamilyMember> children) {
        this.children = children;
    }
}
