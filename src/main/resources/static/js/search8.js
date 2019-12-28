function retrieveSongs(p) {
    window.alert("retrieveSongs");
    window.alert(p);
    var bbtn =  document.getElementById('bundleid');
    window.alert(bbtn.innerHTML);
    bbtn.innerHTML = p;

    var url = '/songs/' + p;
    $("#resultsBlockSongs").load(url);

    var btn =  document.getElementById('bundledropbtn');
    btn.innerHTML = document.getElementById("bundle_" + p).innerHTML;
}

function retrieveVerses(p) {    
    var sbtn =  document.getElementById('songid');
    sbtn.innerHTML = p;

    var url = '/verses/' + p;
    $("#resultsBlockVerses").load(url);

    var btn =  document.getElementById('songdropbtn');
    btn.innerHTML = document.getElementById("song_" + p).innerHTML;
}