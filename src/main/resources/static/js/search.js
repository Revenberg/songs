function retrieveBundles() {
    window.alert("searchBundle");
    var url = '/bundles';
    
    window.alert($('#searchBundle'));
    if ($('#searchBundle').val() != '') {
        url = url + '/' + $('#searchBundle').val();
    }
    window.alert(url);
    $("#resultsBlock").load(url);
}