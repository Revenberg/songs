function retrieveSongs(p) {
    window.alert("retrieveSongs");
    document.getElementById("resultsBlockVerses").innerHTML = "new content1"

    var bbtn =  document.getElementById('bundleid');    
    bbtn.value = p;

    var url = '/songs/' + p;
    $("#resultsBlockSongs").load(url);

    var btn =  document.getElementById('bundledropbtn');
    btn.innerHTML = document.getElementById("bundle_" + p).innerHTML;
}

function retrieveVerses(p) { 
    window.alert("retrieveVerses");   
    document.getElementById("resultsBlockVerses").innerHTML = "new content1"

    var sbtn =  document.getElementById('songid');
    sbtn.value = p;

    var url = '/verses/' + p;
    $("#resultsBlockVerses").load(url);

    var btn =  document.getElementById('songdropbtn');
    btn.innerHTML = document.getElementById("song_" + p).innerHTML;
}