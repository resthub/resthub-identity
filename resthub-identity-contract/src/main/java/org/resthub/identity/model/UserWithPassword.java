package org.resthub.identity.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.Column;

public class UserWithPassword extends User {

    private static final long serialVersionUID = 2388104110338965708L;

    public UserWithPassword() {
        super();
    }

    public UserWithPassword(User u) {
        super(u);
    }

    @Override
    @Column(nullable = false)
    @JsonIgnore(value = false)
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public void setPassword(String password) {
        super.setPassword(password);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
