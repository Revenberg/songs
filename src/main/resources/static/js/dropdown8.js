function getSongs(item)
{
    window.alert("getSongs");
    document.getElementById("resultsBlockVerses").innerHTML = "new content1"

    var btn =  document.getElementById('bundleid');
    btn.value = item.value;

    var url = '/songs/' + item.value;
    $("#resultsBlockSongs").load(url);
 
}
function getVerses(item)
{
    window.alert("getVerses");
    document.getElementById("resultsBlockVerses").innerHTML = "new content1"

    var btn =  document.getElementById('songid');
    btn.value = item.value;

    var url = '/verses/' + item.value;
    $("#resultsBlockVerses").load(url); 
}
