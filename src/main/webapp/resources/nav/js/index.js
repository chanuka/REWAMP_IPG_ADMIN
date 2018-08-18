//$(document).ready(function () {
//    var idsec = $.cookie("selectedsec");
//    var idpge = $.cookie("selectedpage");
//    var idsecval = "#" + idsec;
//    var idpgeval = "#" + idsec;
//    $(idsecval).addClass('selected');
//    $(idpgeval).addClass('active');
//});
$(document).ready(function () {
    var links_1 = $('.sidebar-links > div > a');
    var links_2 = $('.sidebar-links > div > ul > li');

    var scroll;
    $(".sidebar-left-collapse").scroll(function () {
        scroll = $(".sidebar-left-collapse").scrollTop();
        window.localStorage.setItem('scroll',scroll);
    });


    links_1.on('click', function () {

        if ($(this).parent().hasClass('selected')) {
            if ($(this).next().children().hasClass('active')) {
                $(this).css({'background-color': '#252525', 'color': '#ffffff'});

                links_1.parents().removeClass('selected');
            } else {
                $(this).parent().removeClass('selected');
            }

        } else {
            for (var i = 0; i < links_1.parents().length; i++) {
                if (links_1.eq(i).parents().hasClass('selected')) {
                    var foo = false;
                    if (links_1.eq(i).next().children().hasClass('active')) {
                        foo = true;
                    }
                    if (!foo) {
                        links_1.eq(i).parents().removeClass('selected');
                    }

                }
            }
            $(this).parent().addClass('selected');
            $(".sidebar-left-collapse").scrollTop(scroll);
        }
    });

//    links_2.on('click', function () {
//        links_2.removeClass('active');
//        links_1.parents().removeClass('selected');
//        links_1.css({'background-color': '', 'color': ''});
//
//        $(this).parent().parent().addClass('selected');
//        $(this).addClass('active');
//        $(this).parent().prev().css('background-color', '#ffffff');
//    });

});
