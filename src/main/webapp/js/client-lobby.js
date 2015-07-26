/**
 * Created by Kolia on 11.07.2015.
 */


$('#tariffs tbody > tr').click(function() {
    var row = $(this);
    if (row.hasClass("success"))
    {
        BootstrapDialog.show({
            message: "It's your current tariff!"
        });
    }
    else if (row.hasClass("no-money"))
    {
        BootstrapDialog.show({
            message: "You don't have enough money to change tariff!"
        });
    }
    else
    {
        BootstrapDialog.confirm('You are going to change tariff and disable all your current options, are you sure?', function(result){
            if(result) {
                var tariffName = row.children('td.tariff-name').text();
                $.get('client_lobby',{action:"change_tariff", tariffName:tariffName},function(responseText) {
                    var elements = $(responseText);
                    var found = $('#content', elements);
                    $('#content').replaceWith(found);
                    //$('#current_contract').text(email);
                });
            }
        });
    }


});

$('#options tbody > tr').click(function() {
    var row = $(this);
    if (row.hasClass("danger"))
    {
        BootstrapDialog.show({
            message: "You can't add this option with your current configuration!"
        });
    }
    else if (row.hasClass("no-money"))
    {
        BootstrapDialog.show({
            message: "You don't have enough money to add this option!"
        });
    }
    else if (row.hasClass("success"))
    {
        BootstrapDialog.confirm('You are going to disable this option, are you sure?', function(result){
            if(result) {
                var optionName = row.children('td.option-name').text();
                $.get('client_lobby',{action:"disable_option", optionName:optionName},function(responseText) {
                    var elements = $(responseText);
                    var found = $('#content', elements);
                    $('#content').replaceWith(found);
                    //$('#current_contract').text(email);
                });
            }
        });
    }
    else
    {
        BootstrapDialog.confirm('You are going to add this option, are you sure?', function(result){
            if(result) {
                var optionName = row.children('td.option-name').text();
                $.get('client_lobby',{action:"add_option", optionName:optionName},function(responseText) {
                    var elements = $(responseText);
                    var found = $('#content', elements);
                    $('#content').replaceWith(found);
                });
            }
        });
    }
});



$('#blockButton').click(function() {
    var button = $(this);
    if ($(button).hasClass("btn-danger"))
    {
        $.get('client_lobby',{action:"block"},function(responseText) {
            var elements = $(responseText);
            var found1 = $('#options', elements);
            $('#options').replaceWith("");

            var found2 = $('#tariffs', elements);
            $('#tariffs').replaceWith("");

            var found3 = $('#contract_info', elements);
            $('#contract_info').replaceWith(found3);

            var found4 = $('#myScripts', elements);
            $('#myScripts').replaceWith(found4);
        });
    }
    else
    {
        $.get('client_lobby',{action:"unblock"},function(responseText) {
            var elements = $(responseText);
            var found = $('#content', elements);
            $('#content').replaceWith(found);
        });
    }
});


$('#apply').click(function() {
    $('#cart').modal('hide');
    $.get('client_lobby',{action:"apply_changes"},function(responseText) {
        var elements = $(responseText);
        var found = $('#content', elements);
        $('#content').replaceWith(found);

    });
});


$('#discard').click(function() {
    $('#cart').modal('hide');
    $.get('client_lobby',{action:"discard_changes"},function(responseText) {
        var elements = $(responseText);
        var found = $('#content', elements);
        $('#content').replaceWith(found);
    });
});


$('#logout').click(function() {
    $.get('client_lobby',{action:"sign_out"},function(responseText) {
        window.location = "login.jsp";
    });
});