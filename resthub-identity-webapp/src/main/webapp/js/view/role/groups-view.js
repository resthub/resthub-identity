define([ 'backbone', 'hbs!../../../template/role/groups', 'view/role/group-view', 'i18n!nls/labels' ], function(Backbone, template, GroupView,labels) {
	var GroupsRoleView = Backbone.ResthubView.extend({
		root : '#main',
		template : template,
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
		add : function(group) {
			var groupView = new GroupView({
				model : group
			});
			groupView.render();
		},
	});
	return GroupsRoleView;
});
