package org.resthub.identity.webapp.repository;

import org.resthub.identity.core.repository.AbstractUserRepository;
import org.resthub.identity.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.io.Serializable;
import java.util.List;

public interface UserRepository extends AbstractUserRepository<User, Long> {

}
