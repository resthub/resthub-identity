define([ 'backbone', 'hbs!../../../template/user/user-group' ],
    function (Backbone, template) {

        var UserGroupView = Backbone.ResthubView.extend({
            root: '.group-list',
            strategy: 'append',
            template: template,
            tagName: 'li',
            className: 'user-group-element element',
            initialize: function () {
                this.$el.hide();
                this.model.on('sync', this.render, this);
                this.model.on('change', this.render, this);
                this.model.on('destroy', this.remove, this);
            },
            render: function () {
                this.$el.fadeOut(function () {
                    this.$el.html(template({
                        model: this.model.attributes
                    }));
                    this.$el.fadeIn();
                }.bind(this));
                return this;
            },
            remove: function () {
                this.$el.fadeOut(function () {
                    UserGroupView.__super__.remove.apply(this);
                }.bind(this));
            }
        });
        return UserGroupView;
    });
