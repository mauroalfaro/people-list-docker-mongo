package com.alfarosoft.peoplelist.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "Customer")
public class Customer {

    @JsonProperty("customerId")
    @Id
    private String id = null;

    @JsonProperty("customerName")
    @Column(name = "CUSTOMER_NAME")
    private String name = null;

    @JsonProperty("customerSurname")
    @Column(name = "CUSTOMER_SURNAME")
    private String surname= null;

    @JsonProperty("loyaltyId")
    @Column(name = "LOYALTY_ID")
    private String loyaltyId = null;

    @JsonProperty("customerAddress")
    @Column(name = "CUSTOMER_ADDRESS")
    private String address = null;

    @JsonProperty("customerPhone")
    @Column(name = "CUSTOMER_PHONE")
    private String phone = null;

    @JsonProperty("customerEmail")
    @Column(name = "CUSTOMER_EMAIL")
    private String email = null;

    public Customer() {
    }

    public Customer(String id, String name, String surname, String loyaltyId, String address, String phone, String email) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.loyaltyId = loyaltyId;
        this.address = address;
        this.phone = phone;
        this.email = email;
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

    public String getLoyaltyId() {
        return loyaltyId;
    }

    public void setLoyaltyId(String loyaltyId) {
        this.loyaltyId = loyaltyId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id) &&
                Objects.equals(name, customer.name) &&
                Objects.equals(surname, customer.surname) &&
                Objects.equals(loyaltyId, customer.loyaltyId) &&
                Objects.equals(address, customer.address) &&
                Objects.equals(phone, customer.phone) &&
                Objects.equals(email, customer.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, loyaltyId, address, phone, email);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", loyaltyId='" + loyaltyId + '\'' +
                ", address=" + address +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
