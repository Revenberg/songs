function retrieveBundles(p) {
    var url = '/bundles/' + p;
    $("#resultsBlock").load(url);
    var btn =  document.getElementById('bundledropbtn');
    btn.innerHTML = document.getElementById(p).innerHTML;
    var drpdwn =  document.getElementById('dropdown-content');
    drpdwn.style.display = "none";
}

function retrieveBundles1() {
    if ($('#searchBundle').val() != '') {
        url = url + '/' + $('#searchBundle').val();
    }
    $("#resultsBlock").load(url);
}