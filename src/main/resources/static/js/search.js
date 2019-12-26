function retrieveBundles() {
    var url = '/bundles';
    
    if ($('#bundledropbtn').val() != '') {
        url = url + '/' + $('#bundledropbtn').val();
    }

    if ($('#searchBundle').val() != '') {
        url = url + '/' + $('#searchBundle').val();
    }
    $("#resultsBlock").load(url);
}