package org.resthub.identity.core.controller;

import org.resthub.identity.core.service.RoleService;
import org.resthub.identity.model.Role;
import org.resthub.identity.model.User;
import org.resthub.web.controller.RestController;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Bastien on 24/06/14.
 */
public interface RoleController<T extends Role, I extends Serializable> extends RestController<T, I> {

    @Secured({"IM-ADMIN"})
    @RequestMapping(method = RequestMethod.GET, value = "{name}/users")
    @ResponseBody
    List<User> findAllUsersWithRole(@PathVariable("name") String name);
}
