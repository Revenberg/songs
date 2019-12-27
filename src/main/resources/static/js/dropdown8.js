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

    window.alert("getSongs");
    var e = document.getElementById("dropdown-content-bundle");
    var value = e.options[e.selectedIndex].value;
    window.alert(value);
    window.alert(value.innerHTML);

    btn.innerHTML = value.innerHTML;
}
function getVerses(item)
{
    var url = '/verses/' + item.value;
    $("#resultsBlockVerses").load(url);
    var btn =  document.getElementById('songdropbtn');
    window.alert("getVerses");
    var e = document.getElementById("dropdown-content-song");
    var value = e.options[e.selectedIndex].value;
    window.alert(value);
    window.alert(value.innerHTML);

    btn.innerHTML = value.innerHTML;
}
