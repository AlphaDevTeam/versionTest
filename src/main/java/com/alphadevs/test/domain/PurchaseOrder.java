package com.alphadevs.test.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A PurchaseOrder.
 */
@Entity
@Table(name = "purchase_order")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PurchaseOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "po_number")
    private String poNumber;

    @Column(name = "order_date")
    private LocalDate orderDate;

    @ManyToOne
    private Company company;

    @OneToMany(mappedBy = "purchaseOrder")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PurchaseOrderDetails> purchaseOrderDetails = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPoNumber() {
        return poNumber;
    }

    public PurchaseOrder poNumber(String poNumber) {
        this.poNumber = poNumber;
        return this;
    }

    public void setPoNumber(String poNumber) {
        this.poNumber = poNumber;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public PurchaseOrder orderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
        return this;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public Company getCompany() {
        return company;
    }

    public PurchaseOrder company(Company company) {
        this.company = company;
        return this;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Set<PurchaseOrderDetails> getPurchaseOrderDetails() {
        return purchaseOrderDetails;
    }

    public PurchaseOrder purchaseOrderDetails(Set<PurchaseOrderDetails> purchaseOrderDetails) {
        this.purchaseOrderDetails = purchaseOrderDetails;
        return this;
    }

    public PurchaseOrder addPurchaseOrderDetails(PurchaseOrderDetails purchaseOrderDetails) {
        this.purchaseOrderDetails.add(purchaseOrderDetails);
        purchaseOrderDetails.setPurchaseOrder(this);
        return this;
    }

    public PurchaseOrder removePurchaseOrderDetails(PurchaseOrderDetails purchaseOrderDetails) {
        this.purchaseOrderDetails.remove(purchaseOrderDetails);
        purchaseOrderDetails.setPurchaseOrder(null);
        return this;
    }

    public void setPurchaseOrderDetails(Set<PurchaseOrderDetails> purchaseOrderDetails) {
        this.purchaseOrderDetails = purchaseOrderDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PurchaseOrder purchaseOrder = (PurchaseOrder) o;
        if (purchaseOrder.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), purchaseOrder.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PurchaseOrder{" +
            "id=" + getId() +
            ", poNumber='" + getPoNumber() + "'" +
            ", orderDate='" + getOrderDate() + "'" +
            "}";
    }
}
