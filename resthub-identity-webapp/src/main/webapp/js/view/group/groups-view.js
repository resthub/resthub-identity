define([ 'backbone', 'hbs!../../../template/group/groups',
		'model/group', 'view/group/group-view', 'i18n!nls/labels'], function(Backbone,template, Group, GroupView,labels) {
	var GroupsView = Backbone.ResthubView.extend({
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
		add : function(group) {
			var groupView = new GroupView({
				model : group
			});
			groupView.render();
		},
		create : function() {
			var group = new Group();
			// Silent cause we do not want to render but to be in edit mode
			this.collection.add(group, {
				silent : true
			});
			var groupView = new GroupView({
				model : group
			});
			groupView.edit();
		}
	});
	return GroupsView;
});
