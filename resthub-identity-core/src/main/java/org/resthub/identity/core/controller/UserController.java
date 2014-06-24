package org.resthub.identity.core.controller;

import org.resthub.identity.core.service.UserService;
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
public interface UserController<T extends User, I extends Serializable> extends RestController<T, I> {
    @Secured({"IM_USER_ADMIN"})
    @RequestMapping(method = RequestMethod.DELETE, value = "name/{name}/roles")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void removeAllRoleFromUser(@PathVariable("login") String login);

    @Secured({"IM_USER_ADMIN"})
    @RequestMapping(method = RequestMethod.DELETE, value = "name/{name}/groups")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void removeAllGroupFromUser(@PathVariable("login") String login);

    @Secured({"IM_USER_ADMIN", "IM_USER_READ"})
    @RequestMapping(method = RequestMethod.GET, value = "login/{login}")
    @ResponseBody
    User getUserByLogin(@PathVariable("login") String login);

    @Secured({"IS_AUTHENTICATED_FULLY"})
    @RequestMapping(method = RequestMethod.GET, value = "me")
    @ResponseBody
    User currentUser();

    @Secured({"IS_AUTHENTICATED_FULLY"})
    @RequestMapping(method = RequestMethod.PUT, value = "me")
    @ResponseBody
    T updateMe(T user);

    @Secured({"IM_USER_ADMIN", "IM_USER_READ"})
    @RequestMapping(method = RequestMethod.GET, value = "name/{login}/groups")
    @ResponseBody
    List<Group> getGroupsFromUser(@PathVariable("login") String login);

    @Secured({"IM_USER_ADMIN"})
    @RequestMapping(method = RequestMethod.PUT, value = "name/{login}/groups/{group}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void addGroupToUser(@PathVariable("login") String login, @PathVariable("group") String group);

    @Secured({"IM_USER_ADMIN"})
    @RequestMapping(method = RequestMethod.DELETE, value = "name/{login}/groups/{groups}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void removeGroupsForUser(@PathVariable("login") String userLogin, @PathVariable("groups") String groupName);

    @Secured({"IM_USER_ADMIN", "IM_USER_READ"})
    @RequestMapping(method = RequestMethod.GET, value = "name/{login}/permissions")
    @ResponseBody
    List<Permission> getPermissionsFromUser(@PathVariable("login") String login);

    @Secured({"IM_USER_ADMIN", "IM_USER_READ"})
    @RequestMapping(method = RequestMethod.GET, value = "name/{login}/permissions/{application}")
    @ResponseBody
    List<Permission> getPermissionsFromUserAndApplication(@PathVariable("login") String login, @PathVariable("application") String application);

    @Secured({"IM_USER_ADMIN"})
    @RequestMapping(method = RequestMethod.PUT, value = "name/{login}/permissions/{permission}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void addPermissionsToUser(@PathVariable("login") String login, @PathVariable("permission") Permission permission);

    @Secured({"IM_USER_ADMIN"})
    @RequestMapping(method = RequestMethod.DELETE, value = "name/{login}/permissions/{permission}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deletePermissionsFromUser(@PathVariable("login") String login, @PathVariable("permission") Permission permission);

    @Secured({"IM_USER_ADMIN"})
    @RequestMapping(method = RequestMethod.PUT, value = "name/{login}/roles/{role}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void addRoleToUser(@PathVariable("login") String login, @PathVariable("role") String role);

    @Secured({"IM_USER_ADMIN"})
    @RequestMapping(method = RequestMethod.DELETE, value = "name/{login}/roles/{role}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void removeRoleFromUser(@PathVariable("login") String login, @PathVariable("role") String role);

    @Secured({"IM_USER_ADMIN", "IM_USER_READ"})
    @RequestMapping(method = RequestMethod.GET, value = "name/{login}/roles")
    @ResponseBody
    List<Role> getAllUserRoles(@PathVariable("login") String login);

    @RequestMapping(method = RequestMethod.POST, value = "checkuser")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void authenticateUser(@RequestParam("user") String username, @RequestParam("password") String password);
}
