/**
 * Created by Kolia on 11.07.2015.
 */



$('#searchUser').click(function() {
    var phoneNumber = $('#searchPhoneNumber').val();

    $.get('admin_lobby',{action:"find_user", phoneNumber:phoneNumber},function(responseText) {
        var elements = $(responseText);
        var found = $('#userProfile', elements);
        $('#userProfile').replaceWith(found);

        $('#userProfile').modal('show');
    });
});


$('#createUser').click(function()
{
    $('#addUser').modal('hide');

    var firstName = $('#user_firstName').val();
    var lastName = $('#user_lastName').val();
    var birthDate = $('#user_birthDate').val();
    var passportData = $('#user_passportData').val();
    var address = $('#user_address').val();
    var email = $('#user_email').val();
    var password = $('#user_password').val();
    var isAdmin = $('#user_isAdmin').val();

    $.get('admin_lobby',{action:"add_user",
        firstName:firstName,
        lastName:lastName,
        birthDate:birthDate,
        passportData:passportData,
        address:address,
        email:email,
        password:password,
        isAdmin:isAdmin},
        function(responseText) {
            var elements = $(responseText);
            var found = $('#content', elements);
            $('#content').replaceWith(found);
    });
});


$('.editOptionsButton').click(function() {
    var row = $(this).parent().parent();
    var tariffName = row.children('td.tariff-name').text();
    $.get('admin_lobby',{action:"get_avail_options", tariffName:tariffName},function(responseText) {
        var elements = $(responseText);
        var found = $('#editOptions', elements);
        $('#editOptions').replaceWith(found);

        var found1 = $('#myScripts', elements);
        $('#myScripts').replaceWith(found1);

        $('#editOptions').modal('show');
    });
});



$('#createContract').click(function()
{
    $('#newContract').modal('hide');

    var owner = $('#owner').val();
    var phoneNumber = $('#phoneNumber').val();
    var balance = $('#balance').val();
    var tariff = $('#tariff').val();

    $.get('admin_lobby',{action:"add_contract",
        owner:owner,
        phoneNumber:phoneNumber,
        balance:balance,
        tariff:tariff},
        function(responseText) {
            var elements = $(responseText);
            var found = $('#content', elements);
            $('#content').replaceWith(found);
            $("#clients").removeClass("active");
            $("#contracts").addClass("active");
        });
});


$('.unlockButton').click(function() {
    var email = "";
    if ($(this).parent().is("td")) {
        var row = $(this).parent().parent();
        email = row.children('td.user_email').text();
    }
    else
    {
        $('#userProfile').modal('hide');
        email = $('#foundUserEmail').text();
    }
    $.get('admin_lobby',{action:"unlock_user",email:email},function(responseText) {
        var elements = $(responseText);
        var found = $('#content', elements);
        $('#content').replaceWith(found);
    });
});


$('.lockButton').click(function() {
    var email = "";
    if ($(this).parent().is("td")) {
        var row = $(this).parent().parent();
        email = row.children('td.user_email').text();
    }
    else
    {
        $('#userProfile').modal('hide');
        email = $('#foundUserEmail').text();
    }
    $.get('admin_lobby',{action:"lock_user",email:email},function(responseText) {
        var elements = $(responseText);
        var found = $('#content', elements);
        $('#content').replaceWith(found);
    });
});


$('#saveEditOptions').click(function() {
    /*$('input[name="selectedOptions"]:checked').each(function() {
        alert(this.value);
    });*/
    var data = { action:"save_sel_options", options : []};
    $('input[name="selectedOptions"]:checked').each(function() {
        data['options'].push($(this).val());
    });
    alert(data)
    $.get('admin_lobby',data,function(responseText) {

    });
});



$('input[name="selectedOptions"]').change(function () {
    alert('changed');
});

var changeTariffPhone;

$('.changeTariffButton').click(function() {
    var row = $(this).parent().parent();
    changeTariffPhone = row.children('td.contract_phone').text();
    //alert(changeTariffPhone)
});


$('#saveChangeTariff').click(function() {
    $('#changeTariff').modal('hide');

    var tariff = $("#avail_tariffs").val();

    $.get('admin_lobby',{action:"change_tariff", phoneNumber:changeTariffPhone, tariff:tariff},function(responseText) {
        var elements = $(responseText);
        var found = $('#content', elements);
        $('#content').replaceWith(found);
        $("#clients").removeClass("active");
        $("#contracts").addClass("active");
    });
});










$('#createTariff').click(function() {
    $('#newTariff').modal('hide');

    var tariffName = $('#tariffName').val();
    var tariffPrice = $('#tariffPrice').val();

    var options = [];
    $('input[name="avail_options"]:checked').each(function () {
        options.push($(this).val());
    });

    $.get('admin_lobby',{action:"add_tariff",
            tariffName:tariffName,
            tariffPrice:tariffPrice,
            options:options},
        function(responseText) {
            var elements = $(responseText);
            var found = $('#content', elements);
            $('#content').replaceWith(found);
            $("#clients").removeClass("active");
            $("#tariffs").addClass("active");
        }
    );
});


var editTariffName;

$('#tariffs tbody > tr').click(function() {
    editTariffName = $(this).children('td.tariff_name').text();
    $('#editTariff').modal('show');
});

$('#saveEditTariff').click(function() {
    $('#editTariff').modal('hide');

    var options = [];
    $('input[name="edit_avail_options"]:checked').each(function () {
        options.push($(this).val());
    });

    $.get('admin_lobby',{action:"edit_tariff",
            tariffName: editTariffName,
            options:options},
        function(responseText) {
            var elements = $(responseText);
            var found = $('#content', elements);
            $('#content').replaceWith(found);
            $("#clients").removeClass("active");
            $("#tariffs").addClass("active");
        }
    );
});



$('#deleteTariff').click(function() {
    $('#editTariff').modal('hide');

    $.get('admin_lobby',{action:"delete_tariff",
            tariffName: editTariffName},
        function(responseText) {
            var elements = $(responseText);
            var found = $('#content', elements);
            $('#content').replaceWith(found);
            $("#clients").removeClass("active");
            $("#tariffs").addClass("active");
        }
    );
});






var editOptionName

$('#options tbody > tr').click(function() {
    editOptionName = $(this).children('td.option_name').text();
    $('#editOption').modal('show');
});


$('#editOptionButton').click(function() {
    $('#editOption').modal('hide');

    var options = [];
    $('input[name="edit_lock_options"]:checked').each(function () {
        options.push($(this).val());
    });

    $.get('admin_lobby',{action:"edit_option",
            optionName: editOptionName,
            options:options},
        function(responseText) {
            var elements = $(responseText);
            var found = $('#content', elements);
            $('#content').replaceWith(found);
            $("#clients").removeClass("active");
            $("#options").addClass("active");
        }
    );
});




$('#logout').click(function() {
    $.get('admin_lobby',{action:"sign_out"},function(responseText) {
        window.location = "login.jsp";
    });
});