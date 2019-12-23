function myFunction(id) {

    var btn = document.getElementById(id);

    if (btn.value == "Open Curtain") {
        btn.value = "Close Curtain";
        btn.innerHTML = "Close Curtain";
    }
    else {
        btn.value = "Open Curtain";
        btn.innerHTML = "Open Curtain";
    }

}