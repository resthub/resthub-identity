package org.resthub.identity.core.controller;

import org.resthub.identity.model.Group;
import org.resthub.identity.model.Permission;
import org.resthub.identity.model.Role;
import org.resthub.identity.model.User;
import org.resthub.web.controller.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Bastien on 24/06/14.
 */
public interface GroupController<T extends Group, I extends Serializable> extends RestController<T, I> {
    @RequestMapping(method = RequestMethod.GET, value = "name/{name}")
    @ResponseBody
    T getGroupByName(@PathVariable("name") String name);

    @RequestMapping(method = RequestMethod.GET, value = "name/{name}/groups")
    @ResponseBody
    List<T> getGroupsFromGroups(@PathVariable("name") String name);

    @RequestMapping(method = RequestMethod.PUT, value = "name/{name}/groups/{group}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void addGroupToGroup(@PathVariable("name") String name, @PathVariable("group") String group);

    @RequestMapping(method = RequestMethod.DELETE, value = "name/{name}/groups/{groups}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void removeGroupsForGroup(@PathVariable("name") String name, @PathVariable("groups") String groupName);

    @RequestMapping(method = RequestMethod.GET, value = "/name/{name}/permissions")
    @ResponseBody
    List<Permission> getPermisionsFromGroup(@PathVariable("name") String name);

    @RequestMapping(method = RequestMethod.PUT, value = "name/{name}/permissions/{permission}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void addPermissionsToUser(@PathVariable("name") String login, @PathVariable("permission") Permission permission);

    @RequestMapping(method = RequestMethod.DELETE, value = "name/{name}/permissions/{permission}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deletePermissionsFromUser(@PathVariable("name") String name, @PathVariable("permission") Permission permission);

    @RequestMapping(method = RequestMethod.GET, value = "name/{name}/users")
    @ResponseBody
    List<User> getUsersFromGroup(@PathVariable("name") String name);

    @RequestMapping(method = RequestMethod.GET, value = "name/{name}/roles")
    @ResponseBody
    List<Role> getRolesFromGroup(@PathVariable("name") String name);

    @Secured({"IM_GROUP_ADMIN"})
    @RequestMapping(method = RequestMethod.PUT, value = "name/{name}/roles/{role}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void addRoleToGroup(@PathVariable("name") String name, @PathVariable("role") String role);

    @Secured({"IM_GROUP_ADMIN"})
    @RequestMapping(method = RequestMethod.DELETE, value = "name/{name}/roles/{role}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void removeRoleFromGroup(@PathVariable("name") String name, @PathVariable("role") String role);

    @Secured({"IM_GROUP_ADMIN"})
    @RequestMapping(method = RequestMethod.DELETE, value = "name/{name}/roles")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void removeAllRoleFromGroup(@PathVariable("name") String name);
}
