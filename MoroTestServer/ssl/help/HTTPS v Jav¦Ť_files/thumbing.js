$(function() {
    $(".forum-show-reply-widget").click(function () { // Zobrazení odpovědi

        var replyId = $(this).attr('data-reply-id');
        var commentId = $(this).attr('data-comment-id');

        var $comment = $("#comment-" + commentId);

        if($comment.children('.reply-cite').length == 0)
        {
            var reply = $("#comment-" + replyId).html();
            var $blockquote = $(document.createElement('blockquote'));

            $blockquote
                .attr('class', 'reply-cite')
                .html(reply);

            $comment.prepend($blockquote);
        }
        else
        {
            $comment.children('.reply-cite').toggle();
        }
    });

    $(".comment-show-votes").click(function(e){
        e.preventDefault();
        var commentId = $(this).attr("data-id");
        var offset = $(this).offset();

        if ($("#popup-dialog[data-comment-id='"+commentId+"']").length)
        {
            closeDialog();
            $("#popup-dialog").removeAttr("data-comment-id", commentId);
        }
        else
        {
            showDialog(offset.top+25, offset.left, '<i class="fa fa-refresh fa-spin"></i> Načítám data ...', null);
            $("#popup-dialog").attr("data-comment-id", commentId);

            $.post('/api/Messaging-Comment/thumbs/' + commentId, {}, function(res){
                $("#popup-dialog").html(res);
            });
        }
    });

    $("[data-comment-expire]").each(function()
    {
        var self = $(this);
        var now = new Date();console.log(now);
        var expire = Date.parse($(this).attr('data-comment-expire'));console.log(expire);
        var timeout = expire - now;console.log(timeout);

        setTimeout(function() {
            self.fadeOut();
        }, timeout)
    });
});

function rateComment(element, value)
{
    var ratingUrl = "../api/Messaging-Comment/thumb/";
    var ratingCommentId = $(element).attr("data-id"); // Načte ID komentu

    if(ratingUrl.slice(-1) != "/") // Pokud URL nekončí lomítkem
    {ratingUrl = ratingUrl+"/";} // Přidá jej tam

    ratingUrl = ratingUrl+ratingCommentId+"/"+value; // Přidá ID komentu a hodnocení

    $.ajax({url: ratingUrl}).done(function(data){ // Ajaxovej request


        if (data == "1") // hodnocení uloženo úspěšně
        {
            var ratingSpan = $(element).parent().children(".totalvotes"); // Span s počtem hodnocení
            $(ratingSpan).css({color: "#3b94e0"});
            var ratingNumber = parseInt($(ratingSpan).text()); // Převedem na INT

            if(isNaN(ratingNumber)) // Pokud je prázdnej
            {
                var text = (value == 1)?"+1":"-1";
                $(ratingSpan).html("&nbsp;" + text); // Hodí tam +1/-1 a ukončí funkci
                return 0;
            }

            ratingNumber += value; // Zvýšíme o 1

            if(ratingNumber > 0) // Když je to větší než 0
            {ratingNumber = "+" + ratingNumber;} // Dá na začátek plusko

            ratingSpan.text(ratingNumber); // A hodí to do spanu
        }
        else if (data == "2")
        {
            window.location.href = '../prihlaseni';
        }
        else
        {
            alert(data);
        }

    });
}

$(function(){
    $(".message-content .vote-up, .wall-message-content .vote-up").click(function(e){ // Kliknutí na VOTE UP
        rateComment(this, 1);
        e.preventDefault();
    });

    $(".message-content .vote-down, .wall-message-content .vote-down").click(function(e){ // Kliknutí na VOTE DOWN
        rateComment(this, -1);
        e.preventDefault();
    });

});