package com.fai.DigitalSignature.model;

public class Organization {
    String Commonname;
    String Organizationunit;
    String Organization;
    String Locality;
    String State;
    String Country;
    String Email;


    public Organization() {

    }

    public Organization(String commonname, String organizationunit, String organization, String locality, String state, String country, String email) {
        Commonname = commonname;
        Organizationunit = organizationunit;
        Organization = organization;
        Locality = locality;
        State = state;
        Country = country;
        Email = email;
    }

    public String getCommonname() {
        return Commonname;
    }

    public void setCommonname(String commonname) {
        Commonname = commonname;
    }

    public String getOrganizationunit() {
        return Organizationunit;
    }

    public void setOrganizationunit(String organizationunit) {
        Organizationunit = organizationunit;
    }

    public String getOrganization() {
        return Organization;
    }

    public void setOrganization(String organization) {
        Organization = organization;
    }

    public String getLocality() {
        return Locality;
    }

    public void setLocality(String locality) {
        Locality = locality;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    @Override
    public String toString() {
        return "Organization{" +
                "Commonname='" + Commonname + '\'' +
                ", Organizationunit='" + Organizationunit + '\'' +
                ", Organization='" + Organization + '\'' +
                ", Locality='" + Locality + '\'' +
                ", State='" + State + '\'' +
                ", Country='" + Country + '\'' +
                ", Email='" + Email + '\'' +
                '}';
    }
}
