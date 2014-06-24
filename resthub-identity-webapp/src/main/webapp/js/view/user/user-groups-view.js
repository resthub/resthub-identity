define([ 'backbone', 'hbs!../../../template/user/user-groups', 'model/group', 'view/user/user-group-view', 'i18n!nls/labels'], function (Backbone, template, Group, UserGroupView, labels) {
    var UserGroupsView = Backbone.ResthubView.extend({
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
        add: function (group) {
            var userGroupView = new UserGroupView({
                root: this.$el.find('.group-list'),
                model: group
            });
            userGroupView.render();
        }
    });
    return UserGroupsView;
});