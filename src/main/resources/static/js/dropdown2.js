function getNewVal(item)
{
    var url = '/bundles/' + item.value;
    $("#resultsBlock").load(url);
    var btn =  document.getElementById('bundledropbtn');
    btn.innerHTML = document.getElementById(p).innerHTML;
}

