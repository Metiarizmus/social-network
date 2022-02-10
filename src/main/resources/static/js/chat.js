const  l = document.getElementsByClassName("friend");

document.getElementById("filter").addEventListener('click', function () {
    for (let i = 0; i < l.length; i++) {
        l[i].style.display="block";
    }
})

document.onclick = function (e) {
    if(event.target.className != 'friend' && event.target.className !='filter' ) {
        for (let i = 0; i < l.length; i++) {
            l[i].style.display="none";
        }

    }
}

$("#filter").keyup(function() {

    var filter = $(this).val(),
        count = 0;

    $('#listMyFriend div').each(function() {

        // If the list item does not contain the text phrase fade it out
        if ($(this).text().search(new RegExp(filter, "i")) < 0) {
            $(this).hide();  // MY CHANGE

            // Show the list item if the phrase matches and increase the count by 1
        } else {
            $(this).show(); // MY CHANGE
            count++;
        }

    });

});

