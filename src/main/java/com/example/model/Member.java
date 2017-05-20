package com.example.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "first_name")
    @NotEmpty(message = "*Please provide First name")
    private String firstName;

    @Column(name = "middle_name")
    @NotEmpty(message = "*Please provide Middle Name")
    private String middleName;

    @Column(name = "last_name")
    @NotEmpty(message = "*Please provide Last name")
    private String lastName;

    @Column(name = "gender")
    private int gender;

    @Temporal(TemporalType.DATE)
    @Column(name = "birthdate")
    @DateTimeFormat(pattern = "MM/dd/yyyy")
    private Date birthDate;

    @Column(name = "email")
    @Email(message = "*Please provide a valid Email")
    @NotEmpty(message = "*Please provide an email")
    private String email;

    @OneToOne
    @JoinColumn(name = "father_id")
    private Member fatherName;

    @OneToOne
    @JoinColumn(name = "mother_id")
    private Member motherName;

    @OneToMany(mappedBy = "member", cascade=CascadeType.ALL)
    private List<Phone> phones = new ArrayList<>();

    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name = "member_group", joinColumns = @JoinColumn(name = "member_id"), inverseJoinColumns = @JoinColumn(name = "community_id"))
    private List<Group> groups;

    @OneToOne(mappedBy="member", cascade=CascadeType.ALL)
    @Valid
    private Address address;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_on")
    private Date createdOn;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_on")
    private Date updatedOn;

    @Column(name = "father_name")
    private String fatherValue;

    @Column(name = "mother_name")
    private String motherValue;

    @Column(name = "avatar")
    @Lob
    private byte[] avatar;

    @Transient
    private String fatherMemberValue;

    @Transient
    private String motherMemberValue;

    public void addAddress(Address address){
        address.setMember(this);
        this.setAddress(address);
    }

    public void addPhones(List<Phone> phones) {
        for (Phone phone :
                phones) {
            phone.setMember(this);
        }
        this.setPhones(phones);
    }

    public void addGroups(List<Group> groups){
        this.setGroups(groups);
    }


    // Getter Setter Methods

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Member getFatherName() {
        return fatherName;
    }

    public void setFatherName(Member fatherName) {
        this.fatherName = fatherName;
    }

    public Member getMotherName() {
        return motherName;
    }

    public void setMotherName(Member motherName) {
        this.motherName = motherName;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public String getFatherMemberValue() {
        return fatherMemberValue;
    }

    public void setFatherMemberValue(String fatherMemberValue) {
        this.fatherMemberValue = fatherMemberValue;
    }

    public String getMotherMemberValue() {
        return motherMemberValue;
    }

    public void setMotherMemberValue(String motherMemberValue) {
        this.motherMemberValue = motherMemberValue;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getFatherValue() {
        return fatherValue;
    }

    public void setFatherValue(String fatherValue) {
        this.fatherValue = fatherValue;
    }

    public String getMotherValue() {
        return motherValue;
    }

    public void setMotherValue(String motherValue) {
        this.motherValue = motherValue;
    }

	public byte[] getAvatar() {
		return avatar;
	}

	public void setAvatar(byte[] avatar) {
		this.avatar = avatar;
	}
}