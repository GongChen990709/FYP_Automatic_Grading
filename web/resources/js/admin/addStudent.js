var basepath = "/" + window.location.pathname.split("/")[1];
function jumpToAdminHome(){
    window.location.href="../../html/admin/adminHome.html";
}

$(function(){
    $("#register").on("click",function(){
        var id = $("#studentId").val();
        var name = $("#studentName").val();
        var pwd = $("#studentPwd").val();
        var email = $("#studentEmail").val();
        var majorCode = $("#majorCode").val();


        // 
        if(!id){
            $("#idmsg").html("id cannot be null");
            $("#studentId").focus();
            setTimeout("$('#idmsg').html('')",2400);
            return false;
        }else if(!(/(^[1-9]\d*$)/.test(id))){
            $("#notInt").html("id should only be a positive integer");
            $("#studentId").focus();
            setTimeout("$('#notInt').html('')",2400);
            return false;
        }else if(!name){
            $("#namemsg").html("name cannot be null");
            $("#studentName").focus();
            setTimeout("$('#namemsg').html('')",2400);
            return false;
        }
        else if(!email){
            $("#emailmsg").html("email cannot be null");
            $("#studentEmail").focus();
            setTimeout("$('#emailmsg').html('')",2400);
            return false;
        }else if(!majorCode){
            $("#majormsg").html("major code cannot be null");
            $("#majorCode").focus();
            setTimeout("$('#majormsg').html('')",2400);
            return false;
        }else{
            $("#idmsg").html("");
            $("#namemsg").html("");
            $("#pwdmsg").html("");
            $("#emailmsg").html("");
            $("#majormsg").html("");
        }

        var obj = { "ucd_id":id, "name":name,"email":email,"major_code":majorCode};
        var myJson = JSON.stringify(obj);

        alert(myJson)

        $.ajax({
            type: "POST",
            dataType: "json",
            url: basepath+"/register/sendEmailToStudent",
            contentType: "application/json",
            data: myJson,
            success: function (result) {
                //console.log("data is :" + JSON.stringify(result));
                if (result.status == "true") {
                    window.location.href = "../../html/admin/adminHome.html";
                }
                else {
                    alert(result.status+" Please check again");
                }
            }
        });


    })

})