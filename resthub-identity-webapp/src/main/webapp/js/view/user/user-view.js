define([ 'backbone', 'view/user/user-form-view', 'hbs!../../../template/user/user' ],
    function (Backbone, UserFormView, template) {

        var UserView = Backbone.ResthubView.extend({
            root: '.user-list',
            strategy: 'append',
            template: template,
            tagName: 'li',
            className: 'user-element element',
            events: {
                'dblclick .edit-user': 'edit'
            },
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
            edit: function () {
                this.$el.fadeOut(function () {
                    var userFormView = new UserFormView({
                        root: this.$el,
                        model: this.model
                    });
                    userFormView.render();
                    this.$el.fadeIn();
                }.bind(this));
            },
            remove: function () {
                this.$el.fadeOut(function () {
                    UserView.__super__.remove.apply(this);
                }.bind(this));
            },
            toggleDetails: function () {
                this.$('p').slideToggle();
            }
        });
        return UserView;
    });
