package com.alphadevs.test.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A TestItem.
 */
@Entity
@Table(name = "test_item")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TestItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "item_unit")
    private Double itemUnit;

    @Column(name = "item_cost")
    private Double itemCost;

    @ManyToOne
    private Company relatedCompany;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public TestItem itemName(String itemName) {
        this.itemName = itemName;
        return this;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Double getItemUnit() {
        return itemUnit;
    }

    public TestItem itemUnit(Double itemUnit) {
        this.itemUnit = itemUnit;
        return this;
    }

    public void setItemUnit(Double itemUnit) {
        this.itemUnit = itemUnit;
    }

    public Double getItemCost() {
        return itemCost;
    }

    public TestItem itemCost(Double itemCost) {
        this.itemCost = itemCost;
        return this;
    }

    public void setItemCost(Double itemCost) {
        this.itemCost = itemCost;
    }

    public Company getRelatedCompany() {
        return relatedCompany;
    }

    public TestItem relatedCompany(Company company) {
        this.relatedCompany = company;
        return this;
    }

    public void setRelatedCompany(Company company) {
        this.relatedCompany = company;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TestItem testItem = (TestItem) o;
        if (testItem.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), testItem.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TestItem{" +
            "id=" + getId() +
            ", itemName='" + getItemName() + "'" +
            ", itemUnit='" + getItemUnit() + "'" +
            ", itemCost='" + getItemCost() + "'" +
            "}";
    }
}
