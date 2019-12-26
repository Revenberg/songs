function retrieveBundles(p) {
    var url = '/bundles/' +  document.getElementById(p).innerHTML;
    if ($('#bundledropbtn').val() != '') {
        url = url + '/' + $('#bundledropbtn').val();
    }

    if ($('#bundledropbtn1').val() != '') {
        url = url + '/' + $('#bundledropbtn1').val();
    }

    if ($('#searchBundle').val() != '') {
        url = url + '/' + $('#searchBundle').val();
    }
    window.alert(url);
    $("#resultsBlock").load(url);
}