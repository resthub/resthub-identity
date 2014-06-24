define([ 'backbone', 'hbs!../../../template/group/group-groups', 'model/group', 'view/group/group-group-view', 'i18n!nls/labels'], function (Backbone, template, Group, GroupGroupView, labels) {
    var GroupGroupsView = Backbone.ResthubView.extend({
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
            var groupGroupView = new GroupGroupView({
                root: this.$el.find('.group-list'),
                model: group
            });
            groupGroupView.render();
        }
    });
    return GroupGroupsView;
});