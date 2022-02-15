package com.charan.anthenasquare.Entites;

import java.util.ArrayList;
import java.util.List;

public class Student {

    private String name;
    private String collegeName;
    private String department;
    private String year;
    private List<String> badges = new ArrayList<>();
    private List<String> skills = new ArrayList<>();
    private List<String> awards = new ArrayList<>();
    private List<Intern> internships = new ArrayList<>();

    public Student(String name, String collegeName, String department, String year, List<String> badges, List<String> skills, List<String> awards, List<Intern> internships) {
        this.name = name;
        this.collegeName = collegeName;
        this.department = department;
        this.year = year;
        this.badges = badges;
        this.skills = skills;
        this.awards = awards;
        this.internships = internships;
    }

    public Student(){}


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public List<String> getBadges() {
        return badges;
    }

    public void setBadges(List<String> badges) {
        this.badges = badges;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public List<String> getAwards() {
        return awards;
    }

    public void setAwards(List<String> awards) {
        this.awards = awards;
    }

    public List<Intern> getInternships() {
        return internships;
    }

    public void setInternships(List<Intern> internships) {
        this.internships = internships;
    }
}
