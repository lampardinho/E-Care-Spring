/**
 * Created by Kolia on 06.07.2015.
 */

$( ".contracts" ).click(function(e) {
    var phoneNumber = $(e.target).text();
    $.get('client_lobby',{action:"select_contract", phoneNumber:phoneNumber},function(responseText) {
        var elements = $(responseText);
        var block = $('#blockButton', elements);
        if ($(block).hasClass("btn-danger"))
        {
            var found = $('#content', elements);
            $('#content').replaceWith(found);
        }
        else
        {
            var found1 = $('#options', elements);
            $('#options').replaceWith("");

            var found2 = $('#tariffs', elements);
            $('#tariffs').replaceWith("");

            var found3 = $('#contract_info', elements);
            $('#contract_info').replaceWith(found3);

            var found4 = $('#myScripts', elements);
            $('#myScripts').replaceWith(found4);
        }

        $('#current_contract').text(phoneNumber);
    });
});

