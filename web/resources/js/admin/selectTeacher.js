var basepath = "/" + window.location.pathname.split("/")[1];
var module_Code;
var module_Name;

var dataArr = new Array();

$(function(){
    //URL传值
    var Request = new Object();
    Request = GetRequest();
    var moduleCode = Request["moduleCode"];    
    var moduleName = Request["moduleName"];    
    
    module_Code = moduleCode;
    module_Name = moduleName;
  
    $.ajax({
        url:basepath+"/teacher/allTeacherInfo",
        type:"post",
        dataType:"json",
        success:function(datas){

            alert(JSON.stringify(datas));
            dataArr = datas;

            var tbody=document.querySelector("tbody");
            for(var i=0;i<dataArr.length;i++){
                var tr = document.createElement("tr");
                tbody.appendChild(tr);
//         /////////////////////////////////////////////////////////////////////////////////////////////
                var td = document.createElement("td"); //创建单元格:select
                tr.appendChild(td);
                // td.innerHTML = "<input class='checkBox' type='checkbox' name='name' >";
                td.innerHTML = "<input class='checkBox' type='radio' name='name' >";



// //////////////////////////////////////////////////////////////////////////////////////////////////
                //第一个
                var td = document.createElement("td");  //创建单元格:teacher_id
                tr.appendChild(td);
                td.innerHTML=dataArr[i].id;

                var td = document.createElement("td");  //创建单元格:teacher_name
                tr.appendChild(td);
                td.innerHTML=dataArr[i].name;

                var td = document.createElement("td");  //创建单元格:teacher_email
                tr.appendChild(td);
                td.innerHTML=dataArr[i].email;

                var td = document.createElement("td");  //创建单元格:department_code
                tr.appendChild(td);
                td.innerHTML=dataArr[i].department_code;

                var td = document.createElement("td");  //创建单元格:department_name
                tr.appendChild(td);
                td.innerHTML=dataArr[i].department_name;


            }




            $('#addTeacherForModule').on('click', function() {
                var str = []
                var checkBox = document.querySelectorAll('.checkBox')
                for (var i = 0; i < checkBox.length; i++) {
                    if (checkBox[i].checked) {
                        var trNum = i + 1

                        str.push($('tbody tr:nth-child(' + trNum +') td:nth-child(2)').html())
                    }
                }


                var teacherId = str[0];
                // alert(typeof(teacherId))
                if(!teacherId){
                    callAlert();
                }else{
                    window.location.href='../../html/admin/addModule.html?moduleCode='+module_Code+'&moduleName='+module_Name+'&teacherId='+teacherId;

                }





            })
        }
    });
  

});





function searchTeacherById(){


    var teacherId = document.getElementById("teacherSearchById").value;
    for(var i=0;i<dataArr.length;i++){
        if(teacherId==dataArr[i].id){
            

            document.querySelector("tbody").innerHTML="";
            var tbody = document.querySelector("tbody");
            var tr = document.createElement("tr");
            tbody.appendChild(tr);


            var td = document.createElement("td"); //创建单元格:select
            tr.appendChild(td);
            td.innerHTML = "<input class='checkBox' type='radio' name='name'>";
            
            var td = document.createElement("td");  //创建单元格:teacher_id
            tr.appendChild(td);
            td.innerHTML=dataArr[i].id;
        
            var td = document.createElement("td");  //创建单元格:teacher_name
            tr.appendChild(td);
            td.innerHTML=dataArr[i].name;
        
            var td = document.createElement("td");  //创建单元格:teacher_email
            tr.appendChild(td);
            td.innerHTML=dataArr[i].email;
        
            var td = document.createElement("td");  //创建单元格:department_code
            tr.appendChild(td);
            td.innerHTML=dataArr[i].department_code;

            var td = document.createElement("td");  //创建单元格:department_name
            tr.appendChild(td);
            td.innerHTML=dataArr[i].department_name;
        

            document.getElementById("searchByModuleCode").style.display = "none";
            document.getElementById("backToPreviousPage").style.display = "none";
            document.getElementById("backToShowAll").style.display = "block";
        }

     

    }


   
}


function backToShowAll(){
    window.location.href="../../html/admin/selectTeacher.html?moduleCode="+module_Code+"&moduleName="+module_Name; 
  
}


function backToPreviousPage(){
    window.location.href="../../html/admin/addModule.html";

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

