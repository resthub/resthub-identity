define(['underscore','backbone','view/group/group-groups-view','view/group/group-group-view', 'view/group/group-roles-view','view/group/group-role-view','collection/groups','collection/roles','model/group','model/role','hbs!../../../template/group/group-form', 'i18n!nls/labels', 'resthub-backbone-validation'], function(_,Backbone,GroupGroupsView,GroupGroupView, GroupRolesView,GroupRoleView,Groups,Roles,Group,Role,template,labels) {

  var GroupFormView = Backbone.ResthubView.extend({
	root : '.group-list',
	strategy : 'append',
	template: template,
    tagName: 'form',
    events: {
      submit: 'save',
      'click .cancel': 'cancel',
      'click .add-role-btn': 'addRole',
      'click .add-group-btn': 'addGroup',
      'click .delete': 'delete'
      
    },
    
    initialize: function() {
      Backbone.Validation.bind(this);
      var rolesRef =  new Array();
      var groupRef =  new Array();
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
    	  name: this.$('.name-field').val(),
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
    	if (this.model.get("name") != null){
    	
    	
    	var roles = new Roles();
    	roles.fetch({model:'group',parameter: this.model.get("name"),success : _.bind(function(){
    		var groupRolesView = new GroupRolesView({root: $(this.el).find(".group-roles-list"), strategy: 'html', collection: roles});
    		groupRolesView.render();
    	},this)});
    	var groups = new Groups();
    	groups.fetch({model:'group',parameter: this.model.get("name"), success : _.bind(function(){
    		var groupGroupsView = new GroupGroupsView({root: $(this.el).find(".group-groups-list"), strategy: 'html', collection: groups});
    		groupGroupsView.render();
    	},this)});
    	
    	}
    	else{
    		var groupRolesView = new GroupRolesView({root: $(this.el).find(".group-roles-list"), strategy: 'html', collection: null});
    		groupRolesView.render();
    		var groupGroupsView = new GroupGroupsView({root: $(this.el).find(".group-groups-list"), strategy: 'html', collection: null});
    		groupGroupsView.render();
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
				var groupRoleView = new GroupRoleView({root: $(this.el).find(".group-roles-list").find('.role-list'), strategy: 'append', model: role});
				groupRoleView.render();
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
				var groupGroupView = new GroupGroupView({root: $(this.el).find(".group-groups-list").find('.group-list'), strategy: 'append', model: group});
				groupGroupView.render();
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
