define([ 'backbone', 'backbone-queryparams' ], function (Backbone) {
    var AppRouter = Backbone.Router.extend({
        routes: {
            '': 'login',
            'home': 'home',
            'login': 'login',
            'users': 'users',
            'groups': 'groups',
            'roles': 'roles',
            'group-roles': 'groupRoles',
            'about': 'about'
        },

        home: function () {
            require([ 'view/home-view'], function (HomeView) {
                new HomeView({
                    root: $('#main')
                });
            });
        },
        login: function () {
            require([ 'view/login-view'], function (LoginView) {
                new LoginView({
                    root: $('#main')
                });
            });
        },
        users: function () {
            require([ 'model/user', 'view/user/users-view', 'collection/users' ], function (User, UsersView, Users) {
                window.User = User;
                window.users = new Users();

                var usersView = new UsersView({
                    collection: users
                });
                users.fetch({ data: { page: 'no'} });
                usersView.render();
            });
        },
        groups: function () {
            require([ 'model/group', 'view/group/groups-view', 'collection/groups' ], function (Profile, ProfilesView, Profiles) {
                window.Profile = Profile;
                window.profiles = new Profiles();

                var profilesView = new ProfilesView({
                    collection: profiles
                });
                profiles.fetch({ data: { page: 'no'} });
                profilesView.render();
            });
        },
        groupRoles: function () {
            require([ 'model/group', 'view/role/groups-view', 'collection/groups' ], function (Group, GroupsView, Groups) {
                window.group = Groups;
                window.groups = new Groups();

                var groupsView = new GroupsView({
                    collection: groups
                });
                groups.fetch({ data: { page: 'no'} });
                groupsView.render();
            });

        },
        roles: function () {
            require([ 'model/role', 'view/role/roles-view', 'collection/roles' ], function (Role, RolesView, Roles) {
                window.role = Role;
                window.roles = new Roles();

                var rolesView = new RolesView({
                    collection: roles
                });
                roles.fetch({ data: { page: 'no'} });
                rolesView.render();
            });
        },
        about: function () {
            require([ 'view/about-view'], function (AboutView) {
                new AboutView({
                    root: $('#main')
                });
            });
        }


    });

    return AppRouter;

});