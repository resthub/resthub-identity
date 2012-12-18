define(['backbone','view/group/group-form-view', 'hbs!../../../template/group/group'],
		function(Backbone, GroupFormView, template) {

			var GroupView = Backbone.ResthubView.extend({
				root : '.group-list',
				strategy : 'append',
				template : template,
				tagName : 'li',
				className : 'group-element element',
				events : {
					'dblclick .edit-group'  : 'edit'
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
						var groupFormView = new GroupFormView({
							root : this.$el,
							model : this.model
						});
						groupFormView.render();
						this.$el.fadeIn();
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
