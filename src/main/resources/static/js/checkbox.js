$(document).ready(function () {
    $("input[type='checkbox']").click(function (e) {
        if ($(this).is(':checked')) {
            window.alert("true");
        } else {
            window.alert("false");
        }
    });
})