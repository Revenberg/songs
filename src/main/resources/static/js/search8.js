function retrieveSongs(p) {
    window.alert("retrieveSongs");

    var url = '/songs/' + p;
    $("#resultsBlockSongs").load(url);

    var btn =  document.getElementById('bundledropbtn');
    window.alert(p);
    btn.innerHTML = document.getElementById("bundle_" + p).innerHTML;
}

function retrieveVerses(p) {
    window.alert("retrieveVerses");

    var url = '/verses/' + p;
    $("#resultsBlockVerses").load(url);

    window.alert(p);

    var btn =  document.getElementById('songdropbtn');
    btn.innerHTML = document.getElementById("song" + p).innerHTML;
}

