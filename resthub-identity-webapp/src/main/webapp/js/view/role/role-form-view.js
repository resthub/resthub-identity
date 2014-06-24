define(['backbone', 'model/role', 'hbs!../../../template/role/role-form', 'i18n!nls/labels', 'resthub-backbone-validation'], function (Backbone, Role, template, labels) {

    var RoleFormView = Backbone.ResthubView.extend({
        root: '.role-list',
        strategy: 'append',
        template: template,
        tagName: 'form',
        events: {
            submit: 'save',
            'click .cancel': 'cancel',
            'click .delete': 'delete'

        },
        initialize: function () {
            Backbone.Validation.bind(this);
        },
        save: function () {
            this.model.set({
                name: this.$('.name-field').val()
            });
            this.model.save();
            return false;
        },

        render: function () {
            this.$el.html(template({
                labels: labels,
                model: this.model.attributes
            }));
            return this;
        },
        cancel: function () {
            this.model.trigger('change');
        },
        'delete': function () {
            this.model.destroy();
            return false;
        }
    });

    return RoleFormView;

});
