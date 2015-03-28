/**
 * Created by lubo08 on 26.2.2015.
 */
define(['jquery','bootstrap'], function($){

    var initialize = function() {

        $(function()	{
            $('#wysihtml5-textarea').wysihtml5({"html":true});
        });

    };


    return {
        initialize: initialize
    };
});