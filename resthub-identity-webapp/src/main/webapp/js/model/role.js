define(['underscore', 'backbone' ], function( _, Backbone) {

	var Role = Backbone.Model.extend({
		validation : {
			id : {
				required : false
			},
			name : {
				required : true
			}
		}

	});
	return Role;
});
