function myFunctionBundleDropbtn(p) {
    window.alert("myFunctionBundleDropbtn");

    var btn =  document.getElementById('bundledropbtn');
    btn.innerHTML = document.getElementById(p).innerHTML;
}

function myFunctionSongDropbtn(p) {
    window.alert("myFunctionSongDropbtn");

    var btn =  document.getElementById('songdropbtn');
    btn.innerHTML = document.getElementById(p).innerHTML;
}

function getSongs(item)
{
    window.alert("getSongs");
    
    var url = '/songs/' + item.value;
    $("#resultsBlockSongs").load(url);
    var btn =  document.getElementById('bundledropbtn');

    var e = document.getElementById("dropdown-content-bundle");
    var value = e.options[e.selectedIndex].value;
    window.alert(value);
    window.alert(value.innerHTML);

    btn.innerHTML = value.innerHTML;
}
function getVerses(item)
{
    window.alert("getVerses");
    
    var url = '/verses/' + item.value;
    $("#resultsBlockVerses").load(url);
    var btn =  document.getElementById('songdropbtn');
    
    var e = document.getElementById("dropdown-content-song");
    var value = e.options[e.selectedIndex].value;
    window.alert(value);
    window.alert(value.innerHTML);

    btn.innerHTML = value.innerHTML;
}
