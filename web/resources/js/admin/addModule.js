var basepath = "/" + window.location.pathname.split("/")[1];
function jumpToAdminHome(){
    window.location.href="../../html/admin/adminHome.html";
}

$(function(){

    var Request = new Object();
    Request = GetRequest();
    var moduleCode = Request["moduleCode"];    
    var moduleName = Request["moduleName"];    
    var teacherId = Request["teacherId"]; 
    
    if(typeof(moduleCode)=="undefined"){
        document.getElementById("moduleCode").value = "";
    }else if(typeof(moduleName)=="undefined"){
        document.getElementById("moduleName").value = "";
    }else if(typeof(teacherId)=="undefined"){
        document.getElementById("teacherID").value = "";
    }else{
        document.getElementById("moduleCode").value = moduleCode;
        document.getElementById("moduleName").value = moduleName;
        document.getElementById("teacherID").value = teacherId;

    }

    



    $("#register").on("click",function(){

        var moduleCode = $("#moduleCode").val();
        var moduleName = $("#moduleName").val();
        var teacherId = $("#teacherID").val();

        if(!moduleCode){
            $("#idmsg").html("Module code cannot be empty");
            $("#moduleCode").focus();
            setTimeout("$('#idmsg').html('')",2400);
            return false;
        }else if(!moduleName){
            $("#namemsg").html("Module name cannot be empty");
            $("#moduleName").focus();
            setTimeout("$('#namemsg').html('')",2400);
            return false;
        }else if(!teacherId){
            $("#emailmsg").html("Please click the input box to select a teacher");
            $("#teacherID").focus();
            setTimeout("$('#emailmsg').html('')",2400);
            return false;
        }
        

        var obj = { "code":moduleCode, "name":moduleName,"teacher_id":teacherId};
        var myJson = JSON.stringify(obj);
        
        // success();
        // // error();

        $.ajax({
            type: "POST",
            dataType: "json",
            url: basepath+"/teacher/registerModule",
            contentType: "application/json",
            data: myJson,
            success: function (result) {
                
                if (result.status == "success") {
                    success();
                    setTimeout("window.location.href='../../html/admin/adminModule.html'", 3200 )
                }
                else {
                    error();
                    setTimeout("window.location.href='../../html/admin/adminModule.html'",3200)
                    
                }
            }
        });


    })

})




function jumpToSelectTeacher(){
    var moduleCode = $("#moduleCode").val();
    var moduleName = $("#moduleName").val();
    if(!moduleCode){
        $("#idmsg").html("Module code cannot be empty");
        $("#moduleCode").focus();
        setTimeout("$('#idmsg').html('')",2400);
        return false;
    }else if(!moduleName){
        $("#namemsg").html("Module name cannot be empty");
        $("#moduleName").focus();
        setTimeout("$('#namemsg').html('')",2400);
        return false;
    }else{
        // var moduleJson = {"Code":moduleCode,"Name":moduleName};

        window.location.href='../../html/admin/selectTeacher.html?moduleCode='+moduleCode+'&moduleName='+moduleName;
    }

    
}

//URL传值解析函数
function GetRequest() {
    var url = decodeURI(location.search); //获取url中"?"符后的字串 
    var theRequest = new Object();
    if (url.indexOf("?") != -1) {
        var str = url.substr(1);
        strs = str.split("&");
        for (var i = 0; i < strs.length; i++) {
            theRequest[strs[i].split("=")[0]] = unescape(strs[i].split("=")[1]);
        }
    }
    return theRequest;
}


function backToAdminModule(){
    window.location.href='../../html/admin/adminModule.html';
}


 //操作成功调用 
function success(){
    hsycms.success('success','Registration Completed',function(){  console.log('操作成功关闭后');  },3200)
}

//操作失败调用
function error(){
    hsycms.error('error','Registration Failed',function(){  console.log('操作失败关闭后');  },3200)
}

