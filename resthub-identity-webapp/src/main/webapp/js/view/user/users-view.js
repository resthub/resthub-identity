define([ 'backbone', 'hbs!../../../template/user/users',
    'model/user', 'view/user/user-view', 'i18n!nls/labels'], function (Backbone, template, User, UserView, labels) {
    var UsersView = Backbone.ResthubView.extend({
        root: '#main',
        template: template,
        events: {
            'click #create': 'create'
        },
        initialize: function () {
            this.collection.on('reset', this.render, this);
            this.collection.on('add', this.add, this);
        },
        render: function () {
            this.$el.html(template({
                labels: labels
            }));
            this.collection.forEach(this.add, this);
            return this;
        },
        add: function (user) {
            var userView = new UserView({
                model: user
            });
            userView.render();
        },
        create: function () {
            var user = new User();
            // Silent cause we do not want to render but to be in edit mode
            this.collection.add(user, {
                silent: true
            });
            var userView = new UserView({
                model: user
            });
            userView.edit();
        }
    });
    return UsersView;
});
