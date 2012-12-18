define([ 'underscore', 'backbone','view/role/group-roles-view', 'hbs!../../../template/role/group','collection/roles' ],
		function(_, Backbone,  GroupRoleView, template,Roles) {

			var GroupView = Backbone.ResthubView.extend({
				root : '.group-list',
				strategy : 'append',
				template : template,
				tagName : 'li',
				className : 'group-element element',
				events : {
					'dblclick .editGroup' : 'edit'
				},
				initialize : function() {
					this.$el.hide();
					this.model.on('sync', this.render, this);
					this.model.on('change', this.render, this);
					this.model.on('destroy', this.remove, this);
				},
				render : function() {
					this.$el.fadeOut(function() {
						this.$el.html(template({
				            model : this.model.attributes
				        }));
						this.$el.fadeIn();
					}.bind(this));
					return this;
				},
				edit : function() {
					this.$el.fadeOut(function() {
						var roles = new Roles();
				    	roles.fetch({model:'group',parameter: this.model.get("name"),success : _.bind(function(){
				    		var groupRoleView = new GroupRoleView({
								root : this.$el.find('.roles-group-list'),
								collection : roles,
								group : this.model.get("name")
							});
							groupRoleView.render();
							this.$el.fadeIn();
				    	},this)});
					}.bind(this));
				},
				remove : function() {
					this.$el.fadeOut(function() {
						GroupView.__super__.remove.apply(this);
					}.bind(this));
				}
			});
			return GroupView;
		});
