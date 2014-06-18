define([ 'backbone',  'hbs!../../template/login','i18n!nls/labels'
], function (Backbone, template,labels) {
    var LoginView = Backbone.ResthubView.extend({

    	template : template,
        labels : labels,

        initialize : function(options) {
            _.bindAll(this, 'render');

            // Appel à la fonction render définit par défaut par resthub
            // http://resthub.org/2/backbone-stack.html#view-instantiation
            this.render();
        }

    });
    return LoginView;
});
