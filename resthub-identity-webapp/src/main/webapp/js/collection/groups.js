    define([ 'backbone', 'model/group' ], function(Backbone, Group) {

      var Groups = Backbone.Collection.extend({
    	url: "api/group",
        // Reference to this collection's model.
        model : Group,
        methodUrl:  function(model,input){
			if(model == "user"){
		    		return "api/user/name/"+input+"/groups";
		    	}
			if(model == "group"){
	    		return "api/group/name/"+input+"/groups";
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
      return Groups;
    });

