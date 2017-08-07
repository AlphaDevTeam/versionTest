package com.alphadevs.test.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A PurchaseOrderDetails.
 */
@Entity
@Table(name = "purchase_order_details")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PurchaseOrderDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "qty")
    private Double qty;

    @Column(name = "ref_1")
    private String ref1;

    @ManyToOne
    private Company company;

    @ManyToOne
    private PurchaseOrder purchaseOrder;

    @ManyToOne
    private TestItem testItem;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getQty() {
        return qty;
    }

    public PurchaseOrderDetails qty(Double qty) {
        this.qty = qty;
        return this;
    }

    public void setQty(Double qty) {
        this.qty = qty;
    }

    public String getRef1() {
        return ref1;
    }

    public PurchaseOrderDetails ref1(String ref1) {
        this.ref1 = ref1;
        return this;
    }

    public void setRef1(String ref1) {
        this.ref1 = ref1;
    }

    public Company getCompany() {
        return company;
    }

    public PurchaseOrderDetails company(Company company) {
        this.company = company;
        return this;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public PurchaseOrder getPurchaseOrder() {
        return purchaseOrder;
    }

    public PurchaseOrderDetails purchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
        return this;
    }

    public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

    public TestItem getTestItem() {
        return testItem;
    }

    public PurchaseOrderDetails testItem(TestItem testItem) {
        this.testItem = testItem;
        return this;
    }

    public void setTestItem(TestItem testItem) {
        this.testItem = testItem;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PurchaseOrderDetails purchaseOrderDetails = (PurchaseOrderDetails) o;
        if (purchaseOrderDetails.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), purchaseOrderDetails.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PurchaseOrderDetails{" +
            "id=" + getId() +
            ", qty='" + getQty() + "'" +
            ", ref1='" + getRef1() + "'" +
            "}";
    }
}
