define([ 'backbone', 'hbs!../../template/about','i18n!nls/labels'
], function (Backbone, template,labels) {
    
    var AboutView = Backbone.ResthubView.extend({
    	template : template,
        labels : labels,

        initialize : function(options) {
            _.bindAll(this, 'render');

            // Appel à la fonction render définit par défaut par resthub
            // http://resthub.org/2/backbone-stack.html#view-instantiation
            this.render();
        }

    });
    return AboutView;
});
