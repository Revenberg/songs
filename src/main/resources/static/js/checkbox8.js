
function checkbox(item)
{
    if (item.checked) {
        var btn =  document.getElementById('versesvalue');
        btn.value = btn.value + " " + item.id.replace("vers-", "");
    } else {
        var btn =  document.getElementById('versesvalue');
        btn.value = btn.value.replace( item.id.replace("vers-", ""), "");
    }
}