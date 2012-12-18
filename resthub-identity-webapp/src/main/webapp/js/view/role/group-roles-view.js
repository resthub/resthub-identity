define([ 'underscore', 'backbone',  'hbs!../../../template/role/group-roles',
		'model/role', 'view/role/group-role-view','collection/roles','i18n!nls/labels'], function(_,
		Backbone,template, Role, RoleGroupView,Roles,labels) {
	var GroupRolesView = Backbone.ResthubView.extend({
		template : template,
		events: {
		      'click .add-role-btn': 'addRole',
		      'click .cancel': 'toggle' 
		    },
		initialize : function() {
			this.collection.on('reset', this.render, this);
			this.collection.on('add', this.add, this);
			var rolesRef =  new Array();
		},
		render : function() {
			this.$el.html(template({
				labels : labels
	        }));
			this.collection.forEach(this.add, this);
			var roleList = new Roles();
			roleList.fetch({ data: { page: 'no'} ,success : _.bind(function(){
	    		var rolesList =  new Array();
	    		var i = 0;
	    		rolesRef = [];
	    		roleList.models.forEach(function(role){
	    			rolesRef[i] = new Array();
	    			rolesList.push(role.get("name"));
	    			rolesRef[i][0] = role.get("name");
	    			rolesRef[i][1] = role.get("id");
	    			i++;
	    		});
	    		$(this.el).find(".role-name-list").autocomplete({
	    			source : rolesList	    			
	    		});
	    	},this)});
			return this;
		},
		add : function(role) {
			var roleGroupView = new RoleGroupView({
				root : this.$el.find('.role-group-list'),
				model : role,
				group : this.options.group
			});
			roleGroupView.render();
		},
		addRole : function(){
			var roleName = this.$('.role-name-list').val();
			var i;
			for (i=0; i < rolesRef.length;i++){
				if (rolesRef[i][0] == roleName ){
							$.ajax({
								url: '/api/group/name/'+this.options.group+'/roles/'+roleName,
								   type: 'PUT',
								   success: _.bind(function(data) {
									   console.log('droits ajoutes');
									   this.collection.reset();
									   this.collection.fetch({model:'group',parameter: this.options.group,success : _.bind(function(){
										   this.render();
									   },this)});
									 							 
								   },this),
							   		error : function(){
							   			console.log('erreur pendant l\'ajout des droits');
							   		}
							   });
							   break;
				}
					
			}
			if (i>= rolesRef.length){
				console.log("Not existing role");
			}
			
			
			
		},
		toggle: function() {
			this.$el.toggle();
		}
	});
	return GroupRolesView;
});
