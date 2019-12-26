function retrieveBundles() {
    var url = '/bundles';
    
    if ($('#searchBundle').val() != '') {
        url = url + '/' + $('#searchBundle').val();
    }
    $("#resultsBlock").load(url);
}