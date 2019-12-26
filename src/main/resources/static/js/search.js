function retrieveBundles(p) {
    var url = '/bundles/' + p;
    $("#resultsBlock").load(url);
}

function retrieveBundles1() {
    if ($('#searchBundle').val() != '') {
        url = url + '/' + $('#searchBundle').val();
    }
    $("#resultsBlock").load(url);
}