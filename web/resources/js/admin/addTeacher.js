var basepath = "/" + window.location.pathname.split("/")[1];
function jumpToAdminHome(){
    window.location.href="../../html/admin/adminHome.html";
}

$(function(){

    $("#register").on("click",function(){
        var id = $("#teacherId").val();
        var name = $("#teacherName").val();
        var email = $("#teacherEmail").val();
        var depCode = $("#depCode").val();

        if(!id){
            $("#idmsg").html("Id cannot be empty");
            $("#teacherId").focus();
            setTimeout("$('#idmsg').html('')",2400);
            return false;
        }else if(!(/(^[1-9]\d*$)/.test(id))){
            $("#notInt").html("Id should only be a positive integer");
            $("#teacherId").focus();
            setTimeout("$('#notInt').html('')",2400);
            return false;
        }else if(!name){
            $("#namemsg").html("Name cannot be empty");
            $("#teacherName").focus();
            setTimeout("$('#namemsg').html('')",2400);
            return false;
        }else if(!email){
            $("#emailmsg").html("Email cannot be empty");
            $("#teacherEmail").focus();
            setTimeout("$('#emailmsg').html('')",2400);
            return false;
        }else if(!depCode){
            $("#depmsg").html("Please choose a department");
            $("#depCode").focus();
            setTimeout("$('#depmsg').html('')",2400);
            return false;
        }else{
            $("#idmsg").html("");
            $("#namemsg").html("");
            $("#emailmsg").html("");
            $("#depmsg").html("");
        }

        var obj = { "id":id, "name":name, "email":email,"department_code":depCode};
        var myJson = JSON.stringify(obj);

        // alert(myJson)
        // success();
        // error();

        $.ajax({
            type: "POST",
            dataType: "json",
            url: basepath+"/register/sendEmailToTeacher",
            contentType: "application/json",
            data: myJson,
            success: function (result) {
                //console.log("data is :" + JSON.stringify(result));
                if (result.status == "true") {
                    success();
                    setTimeout("window.location.href='../../html/admin/addTeacher.html'", 3200 )
                }
                else {
                    error();
                    setTimeout("window.location.href='../../html/admin/addTeacher.html'",3200)
                    
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
function error(){
    hsycms.error('error','Registration Failed',function(){  console.log('操作失败关闭后');  },3200)
}