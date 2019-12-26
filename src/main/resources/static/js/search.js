function retrieveBundles() {
    var url = '/bundles';
    
    if ($('#bundlename').val() != '') {
        url = url + '/' + $('#bundlename').val();
    }
    
    $("#resultsBlock").load(url);
}