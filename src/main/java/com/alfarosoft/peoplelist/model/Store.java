package com.alfarosoft.peoplelist.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "Store")
public class Store {

    @JsonProperty("storeId")
    @Id
    private String id = null;

    @JsonProperty("storeName")
    @Column(name = "STORE_NAME")
    private String storeName = null;

    @JsonProperty("storeAddress")
    @Column(name = "STORE_ADDRESS")
    private String address = null;

    public Store(String id, String storeName, String addresss) {
        this.id = id;
        this.storeName = storeName;
        this.address = address;
    }

    public Store() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Store store = (Store) o;
        return Objects.equals(id, store.id) &&
                Objects.equals(storeName, store.storeName) &&
                Objects.equals(address, store.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, storeName, address);
    }

    @Override
    public String toString() {
        return "Store{" +
                "id='" + id + '\'' +
                ", storeName='" + storeName + '\'' +
                ", address=" + address +
                '}';
    }
}
