    define([ 'backbone', 'model/user' ], function(Backbone, User) {

      var Users = Backbone.Collection.extend({
    	url: "api/user",
        // Reference to this collection's model.
        model : User
       });
      return Users;
    });

