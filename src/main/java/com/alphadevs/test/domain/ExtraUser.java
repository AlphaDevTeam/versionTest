package com.alphadevs.test.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ExtraUser.
 */
@Entity
@Table(name = "extra_user")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ExtraUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "extra_info")
    private String extraInfo;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExtraInfo() {
        return extraInfo;
    }

    public ExtraUser extraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
        return this;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }

    public User getUser() {
        return user;
    }

    public ExtraUser user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ExtraUser extraUser = (ExtraUser) o;
        if (extraUser.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), extraUser.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ExtraUser{" +
            "id=" + getId() +
            ", extraInfo='" + getExtraInfo() + "'" +
            "}";
    }
}
