function myFunctionBundleDropbtn(p) {
    var btn =  document.getElementById('bundledropbtn');
    btn.innerHTML = document.getElementById(p).innerHTML;
}

function myFunctionSongDropbtn(p) {
    var btn =  document.getElementById('songdropbtn');
    btn.innerHTML = document.getElementById(p).innerHTML;
}

function getSongs(item)
{
    var url = '/songs/' + item.value;
    $("#resultsBlockSongs").load(url);
    var btn =  document.getElementById('bundledropbtn');
    btn.innerHTML = document.getElementById("bundle_" + item).innerHTML;
}
function getVerses(item)
{
    var url = '/verses/' + item.value;
    $("#resultsBlockVerses").load(url);
    var btn =  document.getElementById('songdropbtn');
    btn.innerHTML = document.getElementById("song_" + item).innerHTML;
}