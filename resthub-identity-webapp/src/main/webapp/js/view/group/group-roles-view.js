define([ 'backbone', 'hbs!../../../template/group/group-roles', 'model/role', 'view/group/group-role-view', 'i18n!nls/labels'], function (Backbone, template, Role, GroupRoleView, labels) {
    var RolesView = Backbone.ResthubView.extend({
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
            var groupRoleView = new GroupRoleView({
                root: this.$el.find('.role-list'),
                model: role
            });
            groupRoleView.render();
        }
    });
    return RolesView;
});