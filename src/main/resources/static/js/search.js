function retrieveSongs(p) {
    var url = '/bundles/' + p;
    $("#resultsBlock").load(url);
    var btn =  document.getElementById('bundledropbtn');
    btn.innerHTML = document.getElementById(p).innerHTML;
}

