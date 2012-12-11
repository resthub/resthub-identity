define([ 'jquery', 'underscore', 'backbone','model/group','model/role','backbone-relational' ], function($, _, Backbone,Group,Role) {

	var User = Backbone.RelationalModel.extend({
		validation : {
			lastName : {
				required : true
			},
			firstName : {
				required : true
			},
			login : {
				required : true
			},
			password : {
				required : true
			},
			email : {
				required : true,
				pattern : "email"
			}
		},
		relations: [{
			type: Backbone.HasMany,
		    key: 'roles',
		    relatedModel: Role,
		    includeInJSON: true
		},
		{
		    type: Backbone.HasMany,
		    key: 'groups',
		    relatedModel: Group,
		    includeInJSON: true
		}]
	});
	return User;
});
