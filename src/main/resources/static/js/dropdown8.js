

function getSongs(item)
{
    var btn =  document.getElementById('bundleid');
    btn.value = item.value;

    var url = '/songs/' + item.value;
    $("#resultsBlockSongs").load(url);
 
}
function getVerses(item)
{
    var btn =  document.getElementById('songid');
    btn.value = item.value;

    var url = '/verses/' + item.value;
    $("#resultsBlockVerses").load(url); 
}
