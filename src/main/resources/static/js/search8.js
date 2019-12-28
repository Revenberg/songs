function retrieveSongs(p) {
    var bbtn =  document.getElementById('bundleid');
    bbtn.innerHTML = item.value;

    var url = '/songs/' + p;
    $("#resultsBlockSongs").load(url);

    var btn =  document.getElementById('bundledropbtn');
    btn.innerHTML = document.getElementById("bundle_" + p).innerHTML;
}

function retrieveVerses(p) {
    var sbtn =  document.getElementById('songid');
    sbtn.innerHTML = item.value;

    var url = '/verses/' + p;
    $("#resultsBlockVerses").load(url);

    var btn =  document.getElementById('songdropbtn');
    btn.innerHTML = document.getElementById("song_" + p).innerHTML;
}
