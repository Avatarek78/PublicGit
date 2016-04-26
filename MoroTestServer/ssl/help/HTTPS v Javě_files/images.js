function select_image(id, ind)
{
	$("#image").fadeOut(200, function() {	
		$(this).html('<img src="api/Articles-Article/image/' + id + '/' + ind + '" alt="" />');
		$(this).fadeIn();
	});
}

$(function(){
	$('.javascript-gallery').prepend('<div class="javascript-gallery-image"></div>');
    $('.javascript-gallery img').wrapAll('<div class="javascript-gallery-thumbs"></div>');

    $('.javascript-gallery-image').append('<img alt="Obrázek v galerii" /><div class="javascript-gallery-description"></div>');

    galleryChangeImage($('.javascript-gallery-thumbs img:first-child'));

    function galleryChangeImage(image, fade)
    {
        if ($('.javascript-gallery').length == 0 || $(image).length == 0)
            return;

        var fade = fade ? true : false;
        var $image = $(image);
        var src = $image.attr('data-original') ? $image.attr('data-original') : $image.attr('src');

        $('.javascript-gallery-thumbs img').removeClass('image-active');
        $image.addClass('image-active');

        if (fade)
        {
            $('.javascript-gallery-image img, .javascript-gallery-description').fadeOut(function(){
                $('.javascript-gallery-image img').attr('src', src);
                $('.javascript-gallery-description').text($image.attr('title'));

                $('.javascript-gallery-image img, .javascript-gallery-description').fadeIn();
            });
        }
        else
        {
            $('.javascript-gallery-image img').attr('src', src);
            $('.javascript-gallery-description').text($image.attr('title'));
        }
    }

    $('.javascript-gallery-thumbs img').click(function(){
        galleryChangeImage($(this), true);
    });
});