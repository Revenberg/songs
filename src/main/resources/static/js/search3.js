function retrieveSongs(p) {
    var url = '/songs/' + p;
    $("#resultsBlockSongs").load(url);
    var btn =  document.getElementById('bundledropbtn');
    btn.innerHTML = document.getElementById(p).innerHTML;
}

function retrieveBundles1() {
    if ($('#searchBundle').val() != '') {
        url = url + '/' + $('#searchBundle').val();
    }
    $("#resultsBlockSongs").load(url);
}
