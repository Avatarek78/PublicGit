$(function(){

    $(document).on('click', '.message-hide-button', function() {

        var ButtonParent = $(this).parent(".flash-message");

        if(ButtonParent.data("closable") === true) {
            if (ButtonParent.data('id') != -1)
            {
                expiry = new Date();
                expiry.setTime(expiry.getTime()+(86400*1000*365));
                if (getCookie("closed_messages") == "")
                    document.cookie = "closed_messages=" + ButtonParent.data('id') + "; expires=" +
                        expiry.toGMTString();
                else
                    document.cookie = "closed_messages=" + getCookie("closed_messages") + "," +
                        ButtonParent.data('id') + "; expires=" + expiry.toGMTString();
            }

            ButtonParent.slideUp(function() { this.remove() });
        }

    });

    $(document).on('click', '.flash-message', function() {

        var Message = $(this);

        if(Message.data("closable") === true && Message.children(".message-icon").is(":hidden") === true && Message.children(".message-hide-button").is(":hidden") === true) {

            Message.slideUp(function() { this.remove() });
        }

    });

    function getCookie(cname) {
        var name = cname + "=";
        var ca = document.cookie.split(';');
        for(var i=0; i<ca.length; i++) {
            var c = ca[i];
            while (c.charAt(0)==' ') c = c.substring(1);
            if (c.indexOf(name) == 0) return c.substring(name.length, c.length);
        }
        return "";
    }
});