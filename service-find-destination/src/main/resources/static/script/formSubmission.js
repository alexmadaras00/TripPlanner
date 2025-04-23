if (!localStorage.getItem('formSubmitted')) {
    document.addEventListener("DOMContentLoaded", function () {
        setTimeout(function () {
            var form = document.getElementById("myForm");
            var xhr = new XMLHttpRequest();
            xhr.open(form.method, form.action);
            xhr.onreadystatechange = function() {
                if (xhr.readyState === 4 && xhr.status === 200) {
                    localStorage.setItem('formSubmitted', 'true');
                }
            };
            xhr.send(new FormData(form));
        }, 5000);  // Delay of 5 seconds
    });
}