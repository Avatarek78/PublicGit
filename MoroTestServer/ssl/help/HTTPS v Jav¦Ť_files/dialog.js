function showDialog(top, left, content, close)
{
    if (!$("#popup-dialog").length)
        $("body").append('<div id="popup-dialog"></div>');

    $("#popup-dialog")
        .html(content)
        .css({top: top, left: left})
        .fadeIn();

    $(close).click(closeDialog);
}

function closeDialog()
{
    $("#popup-dialog").fadeOut(function(){
        $("#popup-dialog").remove();
    });
}