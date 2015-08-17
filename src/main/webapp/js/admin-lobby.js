/**
 * Created by Kolia on 11.07.2015.
 */


$('#searchUser').click(function() {

    if ($('#userProfile').hasClass('in')) return;
    $('#userProfile').modal('hide');

    var phoneNumber = $('#searchPhoneNumber').val();

    $.get('admin_lobby/find_user',{phoneNumber:phoneNumber},function(responseText) {
        var elements = $(responseText);
        var found = $('#userProfile', elements);
        $('#userProfile').replaceWith(found);

        $('#userProfile').modal('show');

    });
});


$('#createUser').click(function()
{

    if (!$('#addUser').hasClass('in')) return;

    $('#addUser').modal('hide');


    var firstName = $('#user_firstName').val();
    var lastName = $('#user_lastName').val();
    var birthDate = $('#user_birthDate').val();
    var passportData = $('#user_passportData').val();
    var address = $('#user_address').val();
    var email = $('#user_email').val();
    var password = $('#user_password').val();
    var isAdmin = $('#user_isAdmin').val();

    $.get('admin_lobby/add_user',{
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




$('.unlockButton').click(function() {
    var email = "";
    if ($(this).parent().is("td")) {
        var row = $(this).parent().parent();
        email = row.children('td.user_email').text();
    }
    else
    {

        if (!$('#userProfile').hasClass('in')) return;

        $('#userProfile').modal('hide');


        email = $('#foundUserEmail').text();
    }
    $.get('admin_lobby/unlock_user',{email:email},function(responseText) {
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

        if (!$('#userProfile').hasClass('in')) return;

        $('#userProfile').modal('hide');


        email = $('#foundUserEmail').text();
    }
    $.get('admin_lobby/lock_user',{email:email},function(responseText) {
        var elements = $(responseText);
        var found = $('#content', elements);
        $('#content').replaceWith(found);
    });
});











$('#createContract').click(function()
{

    if (!$('#newContract').hasClass('in')) return;

    $('#newContract').modal('hide');


    var owner = $('#owner').val();
    var phoneNumber = $('#phoneNumber').val();
    var balance = $('#balance').val();
    var tariff = $('#tariff').val();

    $.get('admin_lobby/add_contract',{
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



var selectedPhoneNumber;

$('.editOptionsButton').click(function() {

    if ($('#editOptions').hasClass('in')) return;
    $('#editOptions').modal('hide');

    var row = $(this).parent().parent();
    selectedPhoneNumber = row.children('td.contract_phone').text();
    $.get('admin_lobby/contract_edit_options',{phoneNumber:selectedPhoneNumber},function(responseText) {
        var elements = $(responseText);
        var found = $('#editOptions', elements);
        $('#editOptions').replaceWith(found);

        var found1 = $('#myScripts', elements);
        $('#myScripts').replaceWith(found1);

        $('#editOptions').modal('show');

    });
});



$('#saveEditOptions').click(function() {

    if (!$('#editOptions').hasClass('in')) return;

    $('#editOptions').modal('hide');


    $.get('admin_lobby/save_sel_options',{phoneNumber: selectedPhoneNumber},function(responseText) {
        var elements = $(responseText);
        var found = $('#content', elements);
        $('#content').replaceWith(found);
        $("#clients").removeClass("active");
        $("#contracts").addClass("active");
    });
});

$('#closeEditOptions').click(function() {

    if (!$('#editOptions').hasClass('in')) return;

    $('#editOptions').modal('hide');

});



$('input[name="selectedOptions"]').change(function () {

    var optionName = $(this).val();
    $.get('admin_lobby/sel_option',{phoneNumber: selectedPhoneNumber, optionName:optionName},function(responseText) {
        var elements = $(responseText);
        var found = $('#avail_options_div', elements);
        $('#avail_options_div').replaceWith(found);

        var found1 = $('#myScripts', elements);
        $('#myScripts').replaceWith(found1);
    });
});



var changeTariffPhone;

$('.changeTariffButton').click(function() {


    var row = $(this).parent().parent();
    changeTariffPhone = row.children('td.contract_phone').text();
    //alert(changeTariffPhone)

    $('#changeTariff').modal('show');

});


$('#saveChangeTariff').click(function() {

    if (!$('#changeTariff').hasClass('in')) return;

    $('#changeTariff').modal('hide');


    var tariff = $("#avail_tariffs").val();

    $.get('admin_lobby/change_tariff',{phoneNumber:changeTariffPhone, tariff:tariff},function(responseText) {
        var elements = $(responseText);
        var found = $('#content', elements);
        $('#content').replaceWith(found);
        $("#clients").removeClass("active");
        $("#contracts").addClass("active");
    });
});


$('#closeChangeTariff').click(function() {

    if (!$('#changeTariff').hasClass('in')) return;

    $('#changeTariff').modal('hide');

});










$('#createTariff').click(function() {

    if (!$('#newTariff').hasClass('in')) return;

    $('#newTariff').modal('hide');


    var tariffName = $('#tariffName').val();
    var tariffPrice = $('#tariffPrice').val();

    var options = [];
    $('input[name="avail_options"]:checked').each(function () {
        options.push($(this).val());
    });

    $.get('admin_lobby/add_tariff',{
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

$('.editTariffButton').click(function() {
    var row = $(this).parent().parent();
    editTariffName = row.children('td.tariff_name').text();
    //alert(editTariffName)
});

$('#saveEditTariff').click(function() {

    if (!$('#editTariff').hasClass('in')) return;

    $('#editTariff').modal('hide');


    var options = [];
    $('input[name="edit_avail_options"]:checked').each(function () {
        options.push($(this).val());
    });

    $.get('admin_lobby/edit_tariff',{
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

    if (!$('#editTariff').hasClass('in')) return;

    $('#editTariff').modal('hide');


    $.get('admin_lobby/delete_tariff',{
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






var editOptionName;

$('.editLockedOptionsButton').click(function() {
    var row = $(this).parent().parent();
    editOptionName = row.children('td.option_name').text();

    $.get('admin_lobby/edit_locked_options',{
            optionName: editOptionName},
        function(responseText) {
            var elements = $(responseText);
            var found = $('#locked_options_div', elements);
            $('#locked_options_div').replaceWith(found);

            var found1 = $('#myScripts', elements);
            $('#myScripts').replaceWith(found1);

            $("#clients").removeClass("active");
            $("#options").addClass("active");

            $('#editLockedOptions').modal('show');
        }
    );
});


$('#saveEditLockedOptionButton').click(function() {
    $('#editLockedOptions').modal('hide');

    var options = [];
    $('input[name="edit_lock_options"]:checked').each(function () {
        options.push($(this).val());
    });

    $.get('admin_lobby/save_edit_locked_options',{
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




$('.editNeededOptionsButton').click(function() {
    var row = $(this).parent().parent();
    editOptionName = row.children('td.option_name').text();

    $.get('admin_lobby/edit_needed_options',{
            optionName: editOptionName},
        function(responseText) {
            var elements = $(responseText);
            var found = $('#needed_options_div', elements);
            $('#needed_options_div').replaceWith(found);

            var found1 = $('#myScripts', elements);
            $('#myScripts').replaceWith(found1);

            $("#clients").removeClass("active");
            $("#options").addClass("active");

            $('#editNeededOptions').modal('show');
        }
    );
});


$('#saveEditNeededOptionButton').click(function() {
    $('#editNeededOptions').modal('hide');

    var options = [];
    $('input[name="edit_need_options"]:checked').each(function () {
        options.push($(this).val());
    });

    $.get('admin_lobby/save_edit_needed_options',{
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
    location.href = 'j_spring_security_logout';
});