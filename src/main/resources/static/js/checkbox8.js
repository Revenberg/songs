
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

// Listen for click on toggle checkbox
$('#select-all').click(function(event) {   
    if(this.checked) {
        // Iterate each checkbox
        $(':checkbox').each(function() {
            this.checked = true;                        
        });
    } else {
        $(':checkbox').each(function() {
            this.checked = false;                       
        });
    }
});
