// Ochrana před double-postem, musí být jako poslední spouštěný JS na stránce který se týká fancyformu
var submitElement = null;

$('form.fancyform input[type="submit"]').click(function() {
    submitElement = $(this);
});

$('form.fancyform').submit(function(e) {
    if (!$(this).hasClass("bypass-doublepost") && submitElement)
    {
        $(this).find('input[type=submit]').prop('disabled', true);
        // Vložení náhrady za odesílací tlačítko, protože hodnota se z disabled inputu neodešle
        $('<input>').attr('type','hidden').attr('name', submitElement.attr('name')).val(submitElement.val()).appendTo(this);
    }
});