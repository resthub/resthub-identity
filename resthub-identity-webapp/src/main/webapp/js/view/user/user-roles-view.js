define([ 'backbone', 'hbs!../../../template/user/user-roles', 'model/role', 'view/user/user-role-view', 'i18n!nls/labels'], function (Backbone, template, Role, UserRoleView, labels) {
    var UserRolesView = Backbone.ResthubView.extend({
        strategy: 'prepend',
        template: template,
        initialize: function () {
        },
        render: function () {
            this.$el.html(template({
                labels: labels
            }));
            if (this.collection != null) {
                this.collection.forEach(this.add, this);
            }
            return this;
        },
        add: function (role) {
            var userRoleView = new UserRoleView({
                root: this.$el.find('.role-list'),
                model: role
            });
            userRoleView.render();
        }
    });
    return UserRolesView;
});