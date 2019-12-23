function myFunction() {

    var btn = document.getElementById("myButton");

    if (btn.value == "Open Curtain") {
        btn.value = "Close Curtain";
        btn.innerHTML = "Close Curtain";
    }
    else {
        btn.value = "Open Curtain";
        btn.innerHTML = "Open Curtain";
    }

}