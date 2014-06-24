define([ 'backbone', 'view/role/role-form-view', 'hbs!../../../template/role/role' ],
    function (Backbone, RoleFormView, template) {

        var RoleView = Backbone.ResthubView.extend({
            root: '.role-list',
            strategy: 'append',
            template: template,
            tagName: 'li',
            className: 'role-element element',
            events: {
                'dblclick .edit-role': 'edit'
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
                    var roleFormView = new RoleFormView({
                        root: this.$el,
                        model: this.model
                    });
                    roleFormView.render();
                    this.$el.fadeIn();
                }.bind(this));
            },
            remove: function () {
                this.$el.fadeOut(function () {
                    RoleView.__super__.remove.apply(this);
                }.bind(this));
            }
        });
        return RoleView;
    });
