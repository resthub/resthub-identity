package org.resthub.identity.webapp.repository;

import org.resthub.identity.core.repository.AbstractGroupRepository;
import org.resthub.identity.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.io.Serializable;
import java.util.List;

public interface GroupRepository extends AbstractGroupRepository<Group, Long> {

}
