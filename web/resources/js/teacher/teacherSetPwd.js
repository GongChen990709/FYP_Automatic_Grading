$(function(){

    $("#confirm").on("click",function(){
        var teacherId = $("#teacherId").val();
        var newPwd = $("#newpwd").val();
        var confirmPwd = $("#confirmpwd").val();
        
        if(!teacherId){
            $("#idmsg").html("id cannot be null");
            $("#teacherId").focus();
            setTimeout("$('#idmsg').html('')",2400);
            return false;
        }else if(!(/(^[1-9]\d*$)/.test(teacherId))){
            $("#notInt").html("id should only be a positive integer");
            $("#teacherId").focus();
            setTimeout("$('#notInt').html('')",2400);
            return false;
        }else if(!newPwd){
            $("#newpwdmsg").html("This field is required. ");
            $("#newpwd").focus();
            setTimeout("$('#newpwdmsg').html('')",2400);
            return false;
        }else if(!confirmPwd){
            $("#confirmmsg").html("This field is required. ");
            $("#confirmpwd").focus();
            setTimeout("$('#confirmmsg').html('')",2400);
            return false;
        }else if(newPwd!=confirmPwd){
            $("#errormsg").html("The two passwords are inconsistent");
            $("#confirmpwd").focus();
            setTimeout("$('#errormsg').html('')",2400);
            $("#confirmpwd").val('');
            return false;
        }
        else{
            $("#idmsg").html("");
            $("#notInt").html("");
            $("#newpwdmsg").html("");
            $("#confirmmsg").html("");
            
        }

        var obj = { "teacherId":teacherId, "newPWD":newPwd, "confirmPWD":confirmPwd};
        var myJson = JSON.stringify(obj);
        
        alert(myJson)
        var basepath = "/" + window.location.pathname.split("/")[1];
        $.ajax({
            type: "POST",
            dataType: "json",
            url: basepath+"/register/setTeaInitialPwd",
            contentType: "application/json",
            data: myJson,
            success: function (result) {
                if (result.status == "success") {
                    window.location.href = "../../../status/activationSuccess.html";
                }
                else if(result.status == "failed"){
                    window.location.href = "../../../status/activationFail.html";
                }
            }
        });
    })

})