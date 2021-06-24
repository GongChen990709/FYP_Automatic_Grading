$(function(){

    $("#confirm").on("click",function(){
        var oldPwd = $("#oldpwd").val();
        var newPwd = $("#newpwd").val();
        var confirmPwd = $("#confirmpwd").val();
        
        if(!oldPwd){
            $("#oldpwdmsg").html("This field is required. ");
            $("#oldpwd").focus();
            setTimeout("$('#oldpwdmsg').html('')",2400);
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
            // $("#errormsg").html("The two passwords are inconsistent");
            $("#errormsg").html("The password filled in the confirmation box is inconsistent with the new password");
            
            $("#confirmpwd").focus();
            setTimeout("$('#errormsg').html('')",3600);
            $("#confirmpwd").val('');
            return false;
        }
        else{
            $("#oldpwdmsg").html("");
            $("#newpwdmsg").html("");
            $("#confirmmsg").html("");
            
        }

        var obj = { "oldPWD":oldPwd, "newPWD":newPwd, "confirmPWD":confirmPwd};
        var myJson = JSON.stringify(obj);
        
        

        $.ajax({
            type: "POST",
            dataType: "json",
            url: "",
            contentType: "application/json",
            data: myJson,
            success: function (result) {
                
                if (result.status == "true") {
                    success();
                    setTimeout("window.location.href='../../html/common/userSetting.html'", 3200 )
                }
                else {
                    error();
                    setTimeout("window.location.href='../../html/common/userSetting.html'",3200)
                    
                }
            }
        });


    })

})


 //操作成功调用 
function success(){
    hsycms.success('success','Password changed successfully',function(){  console.log('操作成功关闭后');  },3200)
}

//操作失败调用
function error(){
    hsycms.error('error','Failed to modify password',function(){  console.log('操作失败关闭后');  },3200)
}