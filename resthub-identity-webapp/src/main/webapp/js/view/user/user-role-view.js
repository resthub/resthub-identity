define(['backbone', 'hbs!../../../template/user/user-role' ],
		function(Backbone, template) {

			var UserRoleView = Backbone.ResthubView.extend({
				strategy : 'append',
				template : template,
				tagName : 'li',
				className : 'user-role-element element',
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
						UserRoleView.__super__.remove.apply(this);
					}.bind(this));
				}
			});
			return UserRoleView;
		});
