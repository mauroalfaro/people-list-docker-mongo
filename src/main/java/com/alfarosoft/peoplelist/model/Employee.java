package com.alfarosoft.peoplelist.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "Employee")
public class Employee {

    @JsonProperty("employeeId")
    @Id
    private String id = null;

    @JsonProperty("employeeName")
    @Column(name = "EMPLOYEE_NAME")
    private String name = null;

    @JsonProperty("employeeSurname")
    @Column(name = "EMPLOYEE_SURNAME")
    private String surname = null;

    @JsonProperty("employeeAddress")
    @Column(name = "EMPLOYEE_ADDRESS")
    private String address = null;

    @JsonProperty("employeePhone")
    @Column(name = "EMPLOYEE_PHONE")
    private String phone = null;

    @JsonProperty("employeeEmail")
    @Column(name = "EMPLOYEE_EMAIL")
    private String email = null;

    @JsonProperty("dateHired")
    @Column(name = "DATE_HIRED")
    private String dateHired = null;

    @JsonProperty("isActiveEmployee")
    @Column(name = "ACTIVE_EMPLOYEE")
    private boolean isActiveEmployee = true;

    public Employee(String id, String name, String surname, String address, String phone, String email, String dateHired, boolean isActiveEmployee) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.dateHired = dateHired;
        this.isActiveEmployee = isActiveEmployee;
    }

    public Employee() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDateHired() {
        return dateHired;
    }

    public void setDateHired(String dateHired) {
        this.dateHired = dateHired;
    }

    public boolean isActiveEmployee() {
        return isActiveEmployee;
    }

    public void setActiveEmployee(boolean activeEmployee) {
        isActiveEmployee = activeEmployee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return isActiveEmployee == employee.isActiveEmployee &&
                Objects.equals(id, employee.id) &&
                Objects.equals(name, employee.name) &&
                Objects.equals(surname, employee.surname) &&
                Objects.equals(address, employee.address) &&
                Objects.equals(phone, employee.phone) &&
                Objects.equals(email, employee.email) &&
                Objects.equals(dateHired, employee.dateHired);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, address, phone, email, dateHired, isActiveEmployee);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", address=" + address +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", dateHired=" + dateHired +
                ", isActiveEmployee=" + isActiveEmployee +
                '}';
    }
}
