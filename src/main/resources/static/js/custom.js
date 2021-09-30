/* Add here all your JS customizations */

function setCover(){
    const imageSource = document.getElementById("cover").files[0];
    var image;
    const op = document.getElementById('op');
    if( op.value === "update"){
        image = document.getElementById('pic1');
    }else {
        image = document.getElementById('pic2');
    }
    image.src = URL.createObjectURL(imageSource);
    if(!VerifyUploadSizeIsOK("cover")){
        document.getElementById("cover").value="";
    }
}
function VerifyUploadSizeIsOK(UploadFieldID){
    var MaxSizeInBytes = 2097152;
    var fld = document.getElementById(UploadFieldID);
    if( fld.files && fld.files.length == 1 && fld.files[0].size > MaxSizeInBytes ){
        document.getElementById("imgSizeError").classList.remove("d-none");
        document.getElementById("imgSizeError").classList.add("d-inline-block");
        return false;
    }
    document.getElementById("imgSizeError").classList.add("d-none");
    document.getElementById("imgSizeError").classList.remove("d-inline-block");
    return true;
}

/* Start of validation Code*/

const forms = $("form");
//maps each form in forms
$.map(forms, (form) =>{

    $(form).submit( (event) => { validateForm(event, form); });

    $.map(getChildrenBySelector(form, ".submit"), (b) => $(b).click((event) => {triggerSubmit(form);} ));

    const inputs = getChildrenBySelector(form, "input");
    $.map(inputs, (input) =>{
        keyupEnterSubmitForm(input, form);
    });
});

function getChildrenBySelector(container, selector){
    return container.querySelectorAll(selector);
}

//au press sur la touche enter
function keyupEnterSubmitForm(input, form){
    input.addEventListener("keyup", (event) =>{
        if ( event.which == 13 ) {
            event.preventDefault();

            $(form).trigger("submit");// submit();
        }
    });

}

function validateForm(event, form){

    //the code logic to validate each field of a form before submitting
    const inputs = getChildrenBySelector(form, "input");
    var isValid = true;
    var isPwsValid = false;
    $.map(inputs, (input) => {
        input.focus();
        input.blur();
        isValid = isValid && validateField(input);
    });
    if(form.className.includes("signup")){
        confirmPassword();
        if(isValid === false || isPwsValid === false){
            resetVal();
            event.preventDefault();
        }
    }else {
        if(!isValid){
            resetVal();
            event.preventDefault();
        }else {
            form.trigger("submit");
        }
    }

    function confirmPassword() {
        const pwds = form.getElementsByClassName("pwd");
        const samePwds = ( ((pwds[0].value != "") && (pwds[1].value != "") ) && (pwds[0].value == pwds[1].value) );
        isPwsValid = samePwds;

        if(!samePwds) {
            //password fields has different values,
            //so display a error message
            $.map(pwds, (pw) => {
                setInvalidFieldStyle(pw);
            });
            /* const alertMsg = document.getElementById("different-pwd-error");
             alertMsg.className = alertMsg.className.replace("invisible", "visible")*/
        }
    }


    function resetVal(){
        isValid = true;
        isPwsValid = false;
    }

}



//validation for one field
function validateField(field) {

    if(field.type == "email"){
        return validateEmail(field);
    }

    if (field.hasAttribute("required")) {
        if (field.value === "" || field.selectedIndex === 0) {
            setInvalidFieldStyle(field);
            return false;
        }
        else {
            resetFieldStyle(field);
        }
    }

    return true;
}

function validateEmail(field)
{
    /* const mailFormat = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/;
     if(field.value.match(mailFormat))*/
    if(/^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/.test(String(field.value).toLocaleLowerCase()))
    {
        resetFieldStyle(field);
        return true;
    }
    else
    {
        setInvalidFieldStyle(field);
        field.focus();
        return false;
    }
}

function triggerSubmit(form) {
    $(form).trigger("submit");
}

function setInvalidFieldStyle(field) {
    $(field).css("borderBottom", "2px solid red");
}
function resetFieldStyle(field) {
    $(field).css("borderBottom", "0.994318px solid rgb(241, 241, 241)");
}

/* End of validation Code*/