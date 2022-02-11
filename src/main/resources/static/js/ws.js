let stompClient
let idRecipient
let idSender = +$('#currentEmailUser').html();
let url = window.location.href;


let footer = document.getElementsByClassName("footer")[0].style.display="none"


function connectToChat(idTo, name) {

    document.getElementsByClassName("footer")[0].style.display="block"


    $('#nameRecipient').text("chat with " + name)
    console.log(name)

    let generalTopicId = +idTo+idSender

    console.log("connect to chat ", idTo)

    let socket = new SockJS('http://localhost:8080/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {

        console.log("connected: " + frame);

        stompClient.subscribe('/topic/messages/' + generalTopicId, function (response) {

            let data = JSON.parse(response.body);

            let mess = data['message']

            let messageTemplateHTML = "";


            if (data['fromLogin'] != idSender) {
                messageTemplateHTML = messageTemplateHTML + '<div id="child_message" class="d-flex justify-content-start mb-4">' +
                    '<div id="child_message" class="msg_cotainer">' + mess +
                    '</div>' +
                    '</div>';
            } else {
                messageTemplateHTML = messageTemplateHTML + '<div id="child_message" class="d-flex justify-content-end mb-4">' +
                    '<div id="child_message" class="msg_cotainer_send">' + mess +
                    '</div>' +
                    '</div>';
            }



            $('#contentChatId').append(messageTemplateHTML);

            updateScroll()
        });
    },{});


    idRecipient = idTo

    $('#contentChatId').html('')
    listMessages()

}

function updateScroll(){
    var objDiv = document.getElementById("contentChatId");
    objDiv.scrollTop = objDiv.scrollHeight;
}



// $('#contentChatId').scrollTop = $('#contentChatId').scrollHeight

document.getElementById("sendMess").addEventListener('keypress', function (e) {
    if (e.keyCode === 13) {

        let mess = $('#sendMess').val()

        let gen = +idRecipient + idSender

        stompClient.send("/app/chat/" + idRecipient, {}, JSON.stringify({
            fromLogin: idSender,
            message: mess,
            idTopic: gen
        }));

        document.getElementById("sendMess").value = ''
    }
})


function listMessages() {

    $.get(url + "/listmessage/" + idSender + "/" + idRecipient, function (response) {
        let messages = response;

        let messageTemplateHTML = ""

        for (let i = 0; i < messages.length; i++) {

            if (messages[i]['fromLogin'] != idSender) {
                messageTemplateHTML = messageTemplateHTML + '<div id="child_message" class="d-flex justify-content-start mb-4">' +
                    '<div id="child_message" class="msg_cotainer">' + messages[i]['message'] +
                    '</div>' +
                    '</div>';
            } else {
                messageTemplateHTML = messageTemplateHTML + '<div id="child_message" class="d-flex justify-content-end mb-4">' +
                    '<div id="child_message" class="msg_cotainer_send">' + messages[i]['message'] +
                    '</div>' +
                    '</div>';
            }

        }
        $('#contentChatId').append(messageTemplateHTML);
        updateScroll()
    });


}

