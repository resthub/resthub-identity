define([ 'backbone', 'hbs!../../../template/role/roles',
		'model/role', 'view/role/role-view', 'i18n!nls/labels'], function(Backbone,  template, Role, RoleView,labels) {
	var RolesView = Backbone.ResthubView.extend({
		root : '#main',
		template : template,
		events : {
			'click #create' : 'create'
		},
		initialize : function() {
			this.collection.on('reset', this.render, this);
			this.collection.on('add', this.add, this);
		},
		render : function() {
			this.$el.html(template({
	            labels : labels
	        }));
			this.collection.forEach(this.add, this);
			return this;
		},
		add : function(role) {
			var roleView = new RoleView({
				model : role
			});
			roleView.render();
		},
		create : function() {
			var role = new Role();
			// Silent cause we do not want to render but to be in edit mode
			this.collection.add(role, {
				silent : true
			});
			var roleView = new RoleView({
				model : role
			});
			roleView.edit();
		}
	});
	return RolesView;
});
