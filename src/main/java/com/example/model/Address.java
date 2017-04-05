package com.example.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

@Entity
@Table(name = "address")
public class Address {

    @Id
    @Column(name="employee_id", unique=true, nullable=false)
    @GeneratedValue(generator="gen")
    @GenericGenerator(name="gen", strategy="foreign", parameters=@Parameter(name = "property", value="member"))
    private Long id;

    @Column(name = "line1")
    @NotEmpty(message = "*Please provide Line 1 Address")
    private String line1;

    @Column(name = "line2")
    private String line2;

    @Column(name = "landmark")
    private String landmark;

    @Column(name = "city")
    @NotEmpty(message = "*Please provide City Name")
    private String city;

    @Column(name = "zip")
    @Length(min = 6, message = "*Zipcode must have at least 6 characters")
    @NotEmpty(message = "*Please provide Zipcode")
    private String zipcode;

    @Column(name = "state")
    @NotEmpty(message = "*Please provide State Name")
    private String state;

    @Column(name = "country")
    @NotEmpty(message = "*Please provide Country Name")
    private String country;

    @OneToOne
    @PrimaryKeyJoinColumn
    private Member member;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLine1() {
        return line1;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public String getLine2() {
        return line2;
    }

    public void setLine2(String line2) {
        this.line2 = line2;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}