//$(document).ready(function () {
////    setTimeout(openLogin, 2000);
//    setTimeout(reduseBlur, 2000);
//
//    $(".login-logo > img").mouseover(function () {
//
//        $(".login-formText").delay(100).fadeIn(10);
//        $(".login-logo").css("margin-left", "0vw");
//        $(".login-message").css("display", "none");
//        $(".login-footer-1").css("display", "none");
//        $(".login-footer-2").css("display", "none");
//    });
//

$(".but").mouseover(function () {
    $(this).children().eq(0).attr("class", "fa fa-unlock-alt fa-2x");
});
$(".but").mouseout(function () {
    $(this).children().eq(0).attr("class", "fa fa-lock fa-2x");
});


$(".but").on("click", function () {
    $("#target").submit();
});

$(document).keypress(function (e) {
    if (e.which == 13) {
        $("#target").submit();
    }
});
////
////    $(".login-logo > img").mouseover(function () {
////        $("html").css("background-image", "url(" + pageContext + "/resources/loginmain/images/login.jpg)");
////
////    });
//
//    function openLogin() {
//        $(".login-formText").delay(100).fadeIn();
//        $(".login-logo").css("margin-left", "0vw");
//    }
//    function reduseBlur() {
//        $("html").css("background-image", "url(" + pageContext + "/resources/loginmain/images/login.jpg)");
//    }
//    ;
//
//
//});