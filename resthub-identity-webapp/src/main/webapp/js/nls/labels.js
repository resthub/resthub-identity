define({
    // root is mandatory.
    'root': {
        'menu': {
        	'home': 'Home',
            'roles': {
                'roles': 'Roles',
                'affect-group-roles': 'Affect role to group',
                'affect-user-roles': 'Affect role to user'
            },
            'users': 'Users',
            'groups': 'Groups',
            'console': 'Console',
            'about': 'About'
    },
    'home': {
    	'title': 'Users, groups and roles management',
        'description': 'This application allow to manage user, group and their roles and permissions.'
    },
        'about': {
            'content': 'This application allow to manage user, group and their roles and permissions.'
    },
    	'general': {
    		'delete': 'Delete',
    		'save': 'Save',
    		'cancel': 'Cancel',
    },
        'users': {
            'title': 'Users management',
            'new': 'New user ',
            'firstname': 'First name',
            'lastname': 'Last name',
            'email': 'Email',
            'login': 'Login',
            'password': 'Password',
            'permissions': 'Permissions',
            'groups': {
                'title': 'User groups',
                'name': 'Group name',
                'add': 'Add group to user'
            },
            'roles': {
            	'title': 'User roles',
                'name': 'Role name',
                'add': 'Add role to user'
            }
        },
        'groups': {
            'title': 'Groups management',
            'new': 'New group ',
            'name': 'Group name',
            'permissions': 'Permissions',
            'groups': {
                'title': 'Subgroups',
                'name': 'Group name',
                'add': 'Add group to group'
            },
            'roles': {
            	'title': 'Group roles',
                'name': 'Role name',
                'add': 'Add role to group'
            }
        },
        'roles': {
        	'affect-group': {
        		'roles': {
                    'name': 'Role name',
                    'add': 'Add role to group'
                },
                'title' : 'Group roles management'
            },
            'affect-user': {
        		'roles': {
                    'name': 'Role name',
                    'add': 'Add role to user'
                },
            },
            'title' : 'Role management',
            'new' :'New role',
            'name' : 'Role name'
    }
    }
});