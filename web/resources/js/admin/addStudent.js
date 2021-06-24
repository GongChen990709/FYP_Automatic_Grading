var basepath = "/" + window.location.pathname.split("/")[1];
function jumpToAdminHome(){
    window.location.href="../../html/admin/adminHome.html";
}

$(function(){
    $("#register").on("click",function(){
        var id = $("#studentId").val();
        var name = $("#studentName").val();
        var email = $("#studentEmail").val();
        var majorCode = $("#majorCode").val();


        // 
        if(!id){
            $("#idmsg").html("Id cannot be empty");
            $("#studentId").focus();
            setTimeout("$('#idmsg').html('')",2400);
            return false;
        }else if(!(/(^[1-9]\d*$)/.test(id))){
            $("#notInt").html("Id should only be a positive integer");
            $("#studentId").focus();
            setTimeout("$('#notInt').html('')",2400);
            return false;
        }else if(!name){
            $("#namemsg").html("Name cannot be empty");
            $("#studentName").focus();
            setTimeout("$('#namemsg').html('')",2400);
            return false;
        }else if(!email){
            $("#emailmsg").html("Email cannot be empty");
            $("#studentEmail").focus();
            setTimeout("$('#emailmsg').html('')",2400);
            return false;
        }else if(!majorCode){
            $("#majormsg").html("Please choose a major");
            $("#majorCode").focus();
            setTimeout("$('#majormsg').html('')",2400);
            return false;
        }else{
            $("#idmsg").html("");
            $("#namemsg").html("");
            $("#emailmsg").html("");
            $("#majormsg").html("");
        }

        var obj = { "ucd_id":id, "name":name,"email":email,"major_code":majorCode};
        var myJson = JSON.stringify(obj);

        // alert(myJson)

        // success();
        // // error();

        $.ajax({
            type: "POST",
            dataType: "json",
            url: basepath+"/register/sendEmailToStudent",
            contentType: "application/json",
            data: myJson,
            success: function (result) {
                //console.log("data is :" + JSON.stringify(result));
                if (result.status == "true") {
                    success();
                    setTimeout("window.location.href='../../html/admin/addStudent.html'", 3200 )
                }
                else {
                    if(result.status == "err1"){
                        error_1();
                        setTimeout("window.location.href='../../html/admin/addStudent.html'",6000)
                    }
                    if(result.status == "err2"){
                        error_2();
                        setTimeout("window.location.href='../../html/admin/addStudent.html'",6000)
                    }
                }
            }
        });
    })

})



 //操作成功调用 
function success(){
    hsycms.success('success','Registration Completed',function(){  console.log('操作成功关闭后');  },3200)
}

//操作失败调用
function error_1(){
    hsycms.error('error','Registration Failed: Duplicate Student ID',function(){  console.log('操作失败关闭后');  },6000)
}

function error_2(){
    hsycms.error('error','Registration Failed: Email Sending Error',function(){  console.log('操作失败关闭后');  },6000)
}