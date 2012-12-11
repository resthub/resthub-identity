define(['underscore','backbone', 'hbs!../../../template/user/user-form', 'model/group','model/role','collection/groups','collection/roles','view/user/user-groups-view','view/user/user-roles-view','view/user/user-group-view','view/user/user-role-view','i18n!nls/labels','resthub-backbone-validation'], 
		function(_,Backbone, template,Group,Role,Groups,Roles,UserGroupsView,UserRolesView,UserGroupView,UserRoleView,labels) {

  var GroupFormView = Backbone.ResthubView.extend({
	root : '.user-list',
	strategy : 'append',
    template: template,
    tagName: 'form',
    labels : labels,
    events: {
      submit: 'save',
      'click .add-group-btn': 'addGroup',
      'click .add-role-btn': 'addRole',
      'click .cancel': 'cancel',
      'click .delete': 'delete'
    },
    initialize: function() {
      Backbone.Validation.bind(this);
      var groupRef =  new Array();
      var rolesRef =  new Array();
    },
    save: function() {
    	var roleList = new Array();
        this.$('.role-list').find('li').each(function(){
      	  if ( $(this).find('input').attr("checked") == "checked"){
      		  var nRole = new Role();
          	  nRole.set({
        	        name: $(this).find('input').val(),
        	        id : $(this).find('input').attr("name")
        	      });
          	  roleList.push(nRole);
      	  }
        });
    	var groupList = new Array();
        this.$('.group-list').find('li').each(function(){
      	  if ( $(this).find('input').attr("checked") == "checked"){
      		  var nGroup = new Group();
          	  nGroup.set({
        	        name: $(this).find('input').val(),
        	        id : $(this).find('input').attr("name")
        	      });
          	groupList.push(nGroup);
      	  }
        });
        this.model.set({
            lastName: this.$('.lastName-field').val(),
            firstName: this.$('.firstName-field').val(),
            login: this.$('.login-field').val(),
            email: this.$('.email-field').val(),
            password: this.$('.password-field').val(),
            roles : roleList,
            groups : groupList
          });
        this.model.save();
        return false;

    },
    render : function() {
    	this.$el.html(template({
            labels : labels,
            model : this.model.attributes
        }));
    	if (this.model.get("login") != null){
    	var roles = new Roles();
        roles.fetch({model:'user',parameter: this.model.get("login"),success : _.bind(function(){
        	var userRolesView = new UserRolesView({root: $(this.el).find(".user-roles-list"), strategy: 'prepend', collection: roles});
        	userRolesView.render();
        },this)});
    	var groups = new Groups();
    	groups.fetch({model:'user',parameter: this.model.get("login"), success : _.bind(function(){
    		var userGroupsView = new UserGroupsView({root: $(this.el).find(".user-groups-list"), strategy: 'prepend', collection: groups});
    		userGroupsView.render();
    	},this)});
    	
    	}
    	else{
    		var userRolesView = new UserRolesView({root: $(this.el).find(".user-roles-list"), strategy: 'prepend', collection: null});
    		userRolesView.render();
    		var userGroupsView = new UserGroupsView({root: $(this.el).find(".user-groups-list"), strategy: 'prepend', collection: null});
    		userGroupsView.render();
    	}
    	
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
    	var groupList = new Groups();
    	groupList.fetch({ data: { page: 'no'} ,success : _.bind(function(){
    		var groupsList =  new Array();
    		var i = 0;
    		groupRef = [];
    		groupList.models.forEach(function(group){
    			groupRef[i] = new Array();
    			groupsList.push(group.get("name"));
    			groupRef[i][0] = group.get("name");
    			groupRef[i][1] = group.get("id");
    			i++;
    		});
    		$(this.el).find(".group-name-list").autocomplete({
    			source : groupsList
    			
    		});
    	},this)});
		return this;
	},
	addRole : function(){
		var roleName = this.$('.role-name-list').val();
		var i;
		for (i=0; i < rolesRef.length;i++){
			if (rolesRef[i][0] == roleName ){
				var role = new Role();
				role.set({
					id : rolesRef[i][1],
			        name: rolesRef[i][0]
			      });
				var userRoleView = new UserRoleView({root: $(this.el).find(".user-roles-list").find('.role-list'), strategy: 'append', model: role});
				userRoleView.render();
				break;
			}
		}
		if (i>= rolesRef.length){
			console.log("Not existing role");
		}
		
	},
	addGroup : function(){
		var groupName = this.$('.group-name-list').val();
		var i;
		for (i=0; i < groupRef.length;i++){
			if (groupRef[i][0] == groupName ){
				var group = new Group();
				group.set({
					id : groupRef[i][1],
			        name: groupRef[i][0],
			        roles : {}
			      });
				var userGroupView = new UserGroupView({root: $(this.el).find(".user-groups-list").find('.group-list'), strategy: 'append', model: group});
				userGroupView.render();
				break;
			}
			if (i>= groupRef.length){
				console.log("Not existing group");
			}
		}
		
	},
    cancel: function() {
      this.model.trigger('change');
    },
    'delete': function() {
      this.model.destroy();
      return false;
    }
  });

  return GroupFormView;

});
