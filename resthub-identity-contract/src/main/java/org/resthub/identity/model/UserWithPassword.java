package org.resthub.identity.model;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

}
