jQuery().ready(function(){    // accordion    var cookieName = 'stickyAccordion';    // simple accordion    var accordion = $("#basic");    var cookie = $.cookies.get( cookieName );    var active;    if (cookie !== null) {        active = accordion.find(".menu_nadpis:eq(" + cookie + ")");    } else {        active = 0    }    jQuery('#basic').accordion({        autoheight: false,        header: '.menu_nadpis',        active: active,    }).bind("change.ui-accordion", function(event, ui) {        $.cookies.set( cookieName, $(this).find('.menu_nadpis').index(ui.newHeader[0]) );    });    // tabs    $( ".tabs" ).tabs({ fx: { opacity: 'toggle', duration:'fast'} });    // Potvrzení kupování článků    $('input#purchase').click(function(event) {        var result = confirm("Opravdu si přeješ koupit tento článek za své body?");        if (!result)            event.preventDefault();    });    // sortable    $( "#sortable" ).sortable();    $( "#sortable" ).disableSelection();    // fuj fuj    var left = $("#container").offset().left - 162;    $(".clever_ad").css("position", "fixed");    $(".clever_ad").css("left", left);    $(".clever_ad").css("top", 350);});// TODO: Umí zatím jen {var}function f(text, tokens) {    for (token in tokens)    {        text = text.replace('{var}', tokens[token]);    }    return text;}