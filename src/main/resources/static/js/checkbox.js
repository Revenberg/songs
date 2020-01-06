$('#checkboxID').click(function () {
    if ($(this).attr('checked')) {
        window.alert('is checked');
    } else {
        window.alert('is not checked');
    }
})