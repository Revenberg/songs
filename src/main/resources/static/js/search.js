function retrieveBundles() {
    window.alert("sometext");
    var url = '/bundles';
    
    window.alert($('#bundlename').val());
    if ($('#bundlename').val() != '') {
        url = url + '/' + $('#bundlename').val();
    }
    window.alert(url);
    $("#resultsBlock").load(url);
}