

if(window.location.href.includes('searchPeople')){
    const form = document.getElementById("formSearchPeoples");
    document.getElementById("keyword").value = getSavedValue("keyword");

    function saveValue(e){
        var id = e.id;  // get the sender's id to save it .
        var val = e.value; // get the value.
        localStorage.setItem(id, val);// Every time user writing something, the localStorage's value will override .
    }

    function getSavedValue  (v){
        if (!localStorage.getItem(v)) {
            return "";// You can change this to your defualt value.
        }
        return localStorage.getItem(v);
    }

    function searchPeopleF(e) {
        if(e.keyCode === 13) {
            form.submit();
        }
    }
}



