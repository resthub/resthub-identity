define(['backbone', 'hbs!../../../template/group/group-role' ],
		function(Backbone, template) {

			var GroupRoleView = Backbone.ResthubView.extend({
				strategy : 'append',
				template : template,
				tagName : 'li',
				className : 'group-role-element element',
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
				remove : function() {
					this.$el.fadeOut(function() {
						GroupRoleView.__super__.remove.apply(this);
					}.bind(this));
				}
			});
			return GroupRoleView;
		});
