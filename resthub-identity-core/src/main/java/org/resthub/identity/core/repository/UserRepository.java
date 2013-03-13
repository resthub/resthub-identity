package org.resthub.identity.core.repository;

import org.resthub.identity.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends GenericUserRepository<User> {

    @Override @Query("SELECT DISTINCT u FROM User u JOIN u.groups g WHERE g.name = :groupName")
    List<User> getUsersFromGroup(@Param("groupName") String groupName);

}
