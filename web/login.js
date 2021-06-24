var basepath = "/" + window.location.pathname.split("/")[1];

$(function(){

    $("#loginbutton").on("click",function(){
        var identity = $("#identity").val();
        var id = $("#id").val();
        var pwd = $("#pwd").val();

        if(!id){
            $("#idmsg").html("id cannot be null");
            $("#id").focus();
            setTimeout("window.location.reload()",2400);
            return false;
        }else if(!(/(^[1-9]\d*$)/.test(id))){
            $("#idmsg").html("id should only be a positive integer");
            $("#id").focus();
            setTimeout("window.location.reload()",2400);
            return false;
        }else if(!pwd){
            $("#pwdmsg").html("password cannot be null");
            $("#pwd").focus();
            setTimeout("window.location.reload()",2400);
            return false;
        }else{
            $("#idmsg").html("");
            $("#pwdmsg").html("");
        }

        var obj = {"id":id, "pwd":pwd};
        var myJson = JSON.stringify(obj);


        $.ajax({
            type: "POST",
            dataType: "json",
            url: basepath+'/doLogin',
            contentType: "application/json",
            data: myJson,
            success: function (result) {
                console.log("data is :" + JSON.stringify(result));
                if (result.status == "Login Success") {
                    if(result.identity=="student"){
                        window.location.href = "resources/html/student/studentHomepage.html";
                    }
                    else if(result.identity=="teacher"){
                        window.location.href = "resources/html/teacher/teacherHomePage.html";
                    }
                    else if(result.identity=="admin"){
                        window.location.href = "resources/html/admin/adminHome.html";
                    }
                }
                else {
                    alert(result.status+" Please check again");
                }
            }
        });


    })

})
            