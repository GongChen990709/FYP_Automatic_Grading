
var basepath = "/" + window.location.pathname.split("/")[1];


var moduleCode; 
var dataArr = new Array();
$(function(){
    //URL传值
    var Request = new Object();
    Request = GetRequest();
    var val= Request["moduleName"]; 
    moduleCode = val;
    
    document.getElementById("moduleCode").innerHTML = moduleCode;
    var obj = { "module_code":moduleCode};
    var myJson = JSON.stringify(obj);

    $.ajax({
        url:basepath+"/student/allActivatedStudentsNotUnderModule",
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

                var td = document.createElement("td"); //创建单元格:select
                tr.appendChild(td);
                td.innerHTML = "<input class='checkBox' type='checkbox' name='name'>";

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

            }
            
        }
    });
    
    



//     var tbody=document.querySelector("tbody");
//     for(var i=0;i<datas.length;i++){
//         var tr = document.createElement("tr");
//         tbody.appendChild(tr);
// //         /////////////////////////////////////////////////////////////////////////////////////////////
//         var td = document.createElement("td"); //创建单元格:select
//         tr.appendChild(td);
//         td.innerHTML = "<input class='checkBox' type='checkbox' name='name'>";
// // //////////////////////////////////////////////////////////////////////////////////////////////////
//         //第一个
//         var td = document.createElement("td");  //创建单元格:student_id
//         tr.appendChild(td);
//         td.innerHTML=datas[i].student_id;
    
//         var td = document.createElement("td");  //创建单元格:student_name
//         tr.appendChild(td);
//         td.innerHTML=datas[i].student_name;
    
//         var td = document.createElement("td");  //创建单元格:major_code
//         tr.appendChild(td);
//         td.innerHTML=datas[i].major_code;
    
//         var td = document.createElement("td");  //创建单元格:major_name
//         tr.appendChild(td);
//         td.innerHTML=datas[i].major_name;

//         var td = document.createElement("td");  //创建单元格:student_email
//         tr.appendChild(td);
//         td.innerHTML=datas[i].student_email;

    
//     }



    $('#addStudentForModule').on('click', function() {
        var str = []
        var checkBox = document.querySelectorAll('.checkBox')
        for (var i = 0; i < checkBox.length; i++) {
            if (checkBox[i].checked) {
                var trNum = i + 1

                str.push($('tbody tr:nth-child(' + trNum +') td:nth-child(2)').html())
            }
        }
        console.log(str);
        console.log(str.length)

        if(str.length==0){
            callAlert();
        }else{
            var moduleStu = {"module_code":moduleCode,"ucd_id":str};
            var myJson = JSON.stringify(moduleStu);
            // console.log(myJson)

            $.ajax({
                type: "POST",
                dataType: "json",
                url: basepath+"/student/batchRegisterToModule",
                contentType: "application/json",
                data: myJson,
                success: function (result) {
                    
                    if (result.status == "success") {
                        success();
                        setTimeout('window.location.href="../../html/admin/StudentOfModule.html?moduleName="+moduleCode', 3200 )
                    }
                    else {
                        error();
                        setTimeout('window.location.href="../../html/admin/StudentOfModule.html?moduleName="+moduleCode', 3200 )
                        
                    }
                }
            });

        }
        
    })
  

});

function searchStudentById(){
    var studentId = document.getElementById("studentSearchById").value;
        for(var i=0;i<dataArr.length;i++){
        if(studentId==dataArr[i].ucd_id){
            
            document.querySelector("tbody").innerHTML="";
            var tbody=document.querySelector("tbody");

            var tr = document.createElement("tr");
            tbody.appendChild(tr);


            var td = document.createElement("td"); //创建单元格:select
            tr.appendChild(td);
            td.innerHTML = "<input class='checkBox' type='checkbox' name='name'>";
            
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


            document.getElementById("searchByModuleCode").style.display = "none";
            document.getElementById("backToPreviousPage").style.display = "none";
            document.getElementById("backToShowAll").style.display = "block";
            
        }
    }
}


function backToShowAll(){
        
     window.location.href="../../html/admin/addStuForModule.html?moduleName="+moduleCode;
}


function backToPreviousPage(){
        
    window.location.href="../../html/admin/StudentOfModule.html?moduleName="+moduleCode;
}


function jumpToStudentCourseRegister(){
    window.location.href="../../html/admin/addStuForModule.html";
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



 //操作成功调用 
 function success(){
    hsycms.success('success','Registration Completed',function(){  console.log('操作成功关闭后');  },3200)
}

//操作失败调用
function error(){
    hsycms.error('error','Registration Failed',function(){  console.log('操作失败关闭后');  },3200)
}
