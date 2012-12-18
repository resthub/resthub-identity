define(['underscore', 'backbone','model/role','model/group','backbone-relational' ], function( _, Backbone,Role,Group) {

	var GroupM = Backbone.RelationalModel.extend({
		validation : {
			id : {
				required : false
			},
			name : {
				required : true
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
	return GroupM;
});
