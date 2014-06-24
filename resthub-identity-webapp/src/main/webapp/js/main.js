// Set the require.js configuration for your application.
require.config({

    shim: {
        'underscore': {
            exports: '_'
        },
        'underscore.string': {
            deps: [
                'underscore'
            ]
        },
        'handlebars': {
            exports: 'Handlebars'
        },
        'backbone-orig': {
            deps: [
                'underscore',
                'underscore.string',
                'jquery'
            ],
            exports: 'Backbone'
        },
        'backbone-queryparams': {
            deps: [
                'backbone-orig',
                'underscore'
            ]
        },
        'backbone-paginator': {
            deps: [
                'backbone-orig',
                'underscore',
                'jquery'
            ],
            exports: 'Backbone.Paginator'
        },
        'backbone-form': {
            deps: [
                'Backbone',
                'jquery-ui-editors'
            ]
        }, 'bootstrap': {
            deps: [
                'jquery'
            ]
        }
    },

    // Libraries
    paths: {
        jquery: 'lib/jquery',
        'jquery-ui-editors': 'lib/jquery-ui-editors',
        'jquery-ui': 'lib/jquery-ui',
        underscore: 'lib/underscore',
        'underscore.string': 'lib/underscore.string',
        'backbone-orig': 'lib/backbone',
        backbone: 'lib/resthub/backbone-resthub',
        'backbone-forms': 'lib/backbone-forms',
        localstorage: 'lib/localstorage',
        text: 'lib/text',
        i18n: 'lib/i18n',
        pubsub: 'lib/resthub/pubsub',
        'bootstrap': 'lib/bootstrap',
        'backbone-validation-orig': 'lib/backbone-validation',
        'resthub-backbone-validation': 'lib/resthub/backbone-validation.ext',
        handlebars: 'lib/handlebars',
        'resthub-handlebars': 'lib/resthub/handlebars-helpers',
        'backbone-queryparams': 'lib/backbone.queryparams',
        'backbone-paginator': 'lib/backbone.paginator',
        'backbone-relational': 'lib/backbone-relational',
        async: 'lib/async',
        keymaster: 'lib/keymaster',
        hbs: 'lib/resthub/require-handlebars',
        'backbone-localstorage': 'lib/backbone.localStorage',
        'p4-handlebars': 'p4-handlebars-helpers',
        console: 'lib/resthub/console'

    },
    locale: 'fr-fr'

});

//Load our app module and pass it to our definition function
require(['console', 'app']);