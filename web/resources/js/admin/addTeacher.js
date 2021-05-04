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
            $("#idmsg").html("id cannot be null");
            $("#teacherId").focus();
            setTimeout("$('#idmsg').html('')",2400);
            return false;
        }else if(!(/(^[1-9]\d*$)/.test(id))){
            $("#notInt").html("id should only be a positive integer");
            $("#teacherId").focus();
            setTimeout("$('#notInt').html('')",2400);
            return false;
        }else if(!name){
            $("#namemsg").html("name cannot be null");
            $("#teacherName").focus();
            setTimeout("$('#namemsg').html('')",2400);
            return false;
        }else if(!email){
            $("#emailmsg").html("email cannot be null");
            $("#teacherEmail").focus();
            setTimeout("$('#emailmsg').html('')",2400);
            return false;
        }else if(!depCode){
            $("#depmsg").html("department code cannot be null");
            $("#depCode").focus();
            setTimeout("$('#depmsg').html('')",2400);
            return false;
        }else{
            $("#idmsg").html("");
            $("#namemsg").html("");
            $("#emailmsg").html("");
            $("#depmsg").html("");
        }

        var obj = { "id":id, "name":name,"email":email,"department_code":depCode};
        var myJson = JSON.stringify(obj);

        alert(myJson)

        $.ajax({
            type: "POST",
            dataType: "json",
            url: basepath+"/register/sendEmailToTeacher",
            contentType: "application/json",
            data: myJson,
            success: function (result) {
                //console.log("data is :" + JSON.stringify(result));
                if (result.status == "true") {
                    window.location.href="../../html/admin/adminHome.html";
                }
                else {
                    alert(result.status+" Please check again");
                }
            }
        });


    })

})