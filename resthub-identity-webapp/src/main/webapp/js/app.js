define([ 'jquery', 'backbone', 'router/app-router', 'hbs!../../template/main', 'i18n!nls/labels', 'bootstrap', 'jquery-ui' ]
    , function ($, Backbone, AppRouter, mainTemplate, labels, BootStrap, Ui) {

        // Envoie les logs côté serveur
        console.level = 'debug';

        $("body").html(mainTemplate({
            labels: labels
        }));


        new AppRouter;
        Backbone.history.start();

    });