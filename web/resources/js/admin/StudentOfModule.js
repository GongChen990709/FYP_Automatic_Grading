var basepath = "/" + window.location.pathname.split("/")[1];

var module_code; 
var dataArr = new Array();
$(function(){
    //URL传值
    var Request = new Object();
    Request = GetRequest();
    var val= Request["moduleName"]; 
    module_code = val;

    document.getElementById("moduleCode").innerHTML = module_code;
    var obj = { "module_code":module_code};
    var myJson = JSON.stringify(obj);
    
    
    $.ajax({
        url:basepath+"/student/allActivatedStudentsUnderModule",
        type:"post",
        dataType:"json",
        contentType: "application/json",
        data: myJson,
        success:function(datas){
            dataArr = datas;

            var tbody=document.querySelector("tbody");
            for(var i=0;i<datas.length;i++){
                var tr = document.createElement("tr");
                tbody.appendChild(tr);

                //第一个
                var td = document.createElement("td");  //创建单元格:student_id
                tr.appendChild(td);
                td.innerHTML=datas[i].ucd_id;
            
                var td = document.createElement("td");  //创建单元格:student_name
                tr.appendChild(td);
                td.innerHTML=datas[i].name;

                var td = document.createElement("td");  //创建单元格:student_email
                tr.appendChild(td);
                td.innerHTML=datas[i].email;
            
                var td = document.createElement("td");  //创建单元格:major_code
                tr.appendChild(td);
                td.innerHTML=datas[i].code;
            
                var td = document.createElement("td");  //创建单元格:major_name
                tr.appendChild(td);
                td.innerHTML=datas[i].title;
            
            
                var td = document.createElement("td");  //创建单元格
                tr.appendChild(td);
                td.innerHTML='<button>remove</button>';
            }
            $("table").on("click","button",function(){
                var ucd_id = $(this).parent().siblings().first().html();
                alert(ucd_id);
                alert(module_code);
                confirm(ucd_id,module_code);

            })
        }
    });

});


function searchStudentById(){


    var studentId = document.getElementById("studentSearchById").value;
    for(var i=0;i<dataArr.length;i++){
        if(studentId==dataArr[i].ucd_id){
            
            document.getElementById("showStudentModuleArea").style.display = "none";
            document.getElementById("showSingleStudent").style.display = "block";

            var tbody1 = document.getElementById("tbody1");
            var tr = document.createElement("tr");
            tbody1.appendChild(tr);
            
            var td = document.createElement("td");  //创建单元格:student_id
            tr.appendChild(td);
            td.innerHTML=dataArr[i].ucd_id;
        
            var td = document.createElement("td");  //创建单元格:student_name
            tr.appendChild(td);
            td.innerHTML=dataArr[i].name;

            var td = document.createElement("td");  //创建单元格:student_email
            tr.appendChild(td);
            td.innerHTML=dataArr[i].email;
        
            var td = document.createElement("td");  //创建单元格:major_code
            tr.appendChild(td);
            td.innerHTML=dataArr[i].code;
        
            var td = document.createElement("td");  //创建单元格:major_name
            tr.appendChild(td);
            td.innerHTML=dataArr[i].title;

            
        
            var td = document.createElement("td");  //创建单元格
            tr.appendChild(td);
            td.innerHTML='<button>remove</button>';
        
        


            document.getElementById("searchByModuleCode").style.display = "none";
            document.getElementById("backToPreviousPage").style.display = "none";
            document.getElementById("backToShowAll").style.display = "block";
        }

     

    }
}



function backToShowAll(){
        
     window.location.href="../../html/admin/StudentOfModule.html?moduleName="+module_code;
}


function backToPreviousPage(){
        
    window.location.href="../../html/admin/adminModule.html";
}


function jumpToStudentCourseRegister(){
    window.location.href="../../html/admin/addStuForModule.html?moduleName="+module_code;
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





// /////////////////////////点击删除后弹出确认是否删除？//////////////////////////////////////////////

//询问弹窗
function confirm(ucd_id,module_code){

    hsycms.confirm('confirm','Are you sure to delete this student?',
       function(res){         //确认删除


            var obj = {"ucd_id":ucd_id,"module_code":module_code};
            var myJson = JSON.stringify(obj);
        
            $.ajax({
                type: "POST",
                dataType: "json",
                url: basepath+"/student/deregisterModule",
                contentType: "application/json",
                data: myJson,
                success: function (result) {
                    
                    if (result.status == "success") {    //删除成功
                        success();
                        setTimeout('window.location.href="../../html/admin/StudentOfModule.html?moduleName="+module_code',2200);
                    }
                    else {       //删除失败
                        error();
                        setTimeout('window.location.href="../../html/admin/StudentOfModule.html?moduleName="+module_code',2200);
                    }
                }
            });
      
         
       },
      function(res){      //取消删除
          window.location.href="../../html/admin/StudentOfModule.html?moduleName="+module_code;
       },
    )
 }

 //删除成功
 function success(){
   hsycms.success('success','Delete Successfully',function(){  console.log('操作成功关闭后');  },1800)
 }

 //删除失败
 function error(){
  hsycms.error('error','Delete Failed',function(){  console.log('操作失败关闭后');  },1800)
 }