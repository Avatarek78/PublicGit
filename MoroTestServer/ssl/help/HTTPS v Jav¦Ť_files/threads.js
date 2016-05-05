$(function() {
    $("#move-threads-toggle-checkboxes").click(function(e) {
        $('.move-thread-form').slideToggle();
        $('.move-thread-control').toggle();
        e.preventDefault();
    });
});

