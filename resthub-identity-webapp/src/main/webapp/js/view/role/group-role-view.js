define([ 'backbone',  'hbs!../../../template/role/group-role', 'i18n!nls/labels' ],
		function(Backbone,template,labels) {

			var GroupRoleView = Backbone.ResthubView.extend({
				strategy : 'append',
				template : template,
				tagName : 'li',
				className : 'role-group-element element',
				events: {
				      'click .delete': 'delete'	      
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
				            labels : labels,
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
				},
			    'delete': function() {
			    	$.ajax({
						   url: '/api/group/name/'+this.options.group+'/roles/'+this.model.get("name"),
						   type: 'DELETE',
						   success: function(data) {
							   console.log('droits supprimes');
						   },
					   		error : function(){
					   			console.log('erreur pendant la suppression des droits');
					   		}
					   });
			      this.remove();
			      return false;
			    }
			});
			return GroupRoleView;
		});
