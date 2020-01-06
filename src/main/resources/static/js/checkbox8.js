
function checkbox(item)
{
    window.alert(item.value);
    window.alert(item.id);
    window.alert(item.checked);
    if (item.checked) {
        var btn =  document.getElementById('versesvalue');
        btn.value = btn.value + "+" + item.value;
    } else {
        var btn =  document.getElementById('versesvalue');
        btn.value = btn.value + "-" + item.value;
    }
}