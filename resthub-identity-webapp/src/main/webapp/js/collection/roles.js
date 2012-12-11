define([ 'backbone', 'model/role' ], function(
		Backbone, Role) {
	
	
	var Roles = Backbone.Collection.extend({
		url : "api/role",
		// Reference to this collection's model.
		model : Role,
		methodUrl:  function(model,input){
			if(model == "group"){
		    		return "api/group/name/"+input+"/roles";
		    	}
			if(model == "user"){
	    		return "api/user/name/"+input+"/roles";
	    	}
		    return false;
		},
		sync : function(method, model, options) {
			if (options.model != null,options.parameter != null)
			    if (model.methodUrl && model.methodUrl(options.model.toLowerCase(),options.parameter.toLowerCase())) {
			      	options = options || {};
			      	options.url = model.methodUrl(options.model.toLowerCase(),options.parameter.toLowerCase());
			    }
		   Backbone.sync(method, model, options);
		}
	});
	return Roles;
});
