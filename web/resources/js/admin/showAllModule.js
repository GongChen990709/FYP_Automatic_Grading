var basepath = "/" + window.location.pathname.split("/")[1];

var dataArr = new Array();

$(function (){
    

    $.ajax({
        url:basepath+"/teacher/associatedModule",
        type:"post",
        dataType:"json",
        success:function(datas){
            dataArr = datas;

            var tbody=document.querySelector("tbody");
            for(var i=0;i<datas.length;i++){
                var tr = document.createElement("tr");
                tbody.appendChild(tr);
                
                

                //第一个
                var td = document.createElement("td");  //创建单元格:module_code
                tr.appendChild(td);
                var moduleCode = datas[i].code;
                td.innerHTML= "<a href='../../html/admin/StudentOfModule.html?moduleName="+moduleCode+"'>"+moduleCode+"</a>";


                var td = document.createElement("td");  //创建单元格:module_name
                tr.appendChild(td);
                td.innerHTML=datas[i].name;
            
                var td = document.createElement("td");  //创建单元格:teacher_id
                tr.appendChild(td);
                td.innerHTML=datas[i].id;
            
                var td = document.createElement("td");  //创建单元格:teacher_name
                tr.appendChild(td);
                td.innerHTML=datas[i].teacher_name;
                
                var td = document.createElement("td");  //创建单元格:teacher_email
                tr.appendChild(td);
                td.innerHTML=datas[i].email;
            
            
                var td = document.createElement("td");  //创建单元格
                tr.appendChild(td);
                td.innerHTML='<button>remove</button>';
            }
            $("table").on("click","button",function(){
                var moduleVal = $(this).parent().siblings().first().html();
                var ModuleCode = moduleVal.match(/">(\S*)</)[1];
                confirm(ModuleCode);

                
            })
        }
    });


    
});






function searchModuleByCode(){


    var moduleCode = document.getElementById("moduleCodeSearch").value;
    for(var i=0;i<dataArr.length;i++){
        if(moduleCode==dataArr[i].code){
            
            document.getElementById("showInfoAllCourse").style.display = "none";
            document.getElementById("showCourseInfoByID").style.display = "block";

            var tbody1 = document.getElementById("tbody1");
            var tr = document.createElement("tr");
            tbody1.appendChild(tr);
            
            var td = document.createElement("td");  //创建单元格:module_code
            tr.appendChild(td);
            var moduleCode = dataArr[i].code;
            td.innerHTML= "<a href='../../html/admin/StudentOfModule.html?moduleName="+moduleCode+"'>"+moduleCode+"</a>";


            var td = document.createElement("td");  //创建单元格:module_name
            tr.appendChild(td);
            td.innerHTML=dataArr[i].name;
        
            var td = document.createElement("td");  //创建单元格:teacher_id
            tr.appendChild(td);
            td.innerHTML=dataArr[i].id;
        
            var td = document.createElement("td");  //创建单元格:teacher_name
            tr.appendChild(td);
            td.innerHTML=dataArr[i].teacher_name;
            
            var td = document.createElement("td");  //创建单元格:teacher_email
            tr.appendChild(td);
            td.innerHTML=dataArr[i].email;
        
        
            var td = document.createElement("td");  //创建单元格
            tr.appendChild(td);
            td.innerHTML='<button>remove</button>';
        


            document.getElementById("searchByModuleCode").style.display = "none";
            document.getElementById("searchByTeacherId").style.display = "none";
            document.getElementById("backToAdminHome").style.display = "none";
            document.getElementById("backToShowAll").style.display = "block";
        }

     

    }
}

function searchModuleByTeaId(){
    var teacherId = document.getElementById("teacherIdSearch").value;
    for(var i=0;i<dataArr.length;i++){
        if(teacherId==dataArr[i].id){
            
            document.getElementById("showInfoAllCourse").style.display = "none";
            document.getElementById("showCourseInfoByID").style.display = "block";


            var tbody1 = document.getElementById("tbody1");
            var tr = document.createElement("tr");
            tbody1.appendChild(tr);
            
            var td = document.createElement("td");  //创建单元格:module_code
            tr.appendChild(td);
            var moduleCode = dataArr[i].code;
            td.innerHTML= "<a href='../../html/admin/StudentOfModule.html?moduleName="+moduleCode+"' >"+moduleCode+"</a>";


            var td = document.createElement("td");  //创建单元格:module_name
            tr.appendChild(td);
            td.innerHTML=dataArr[i].name;
        
            var td = document.createElement("td");  //创建单元格:teacher_id
            tr.appendChild(td);
            td.innerHTML=dataArr[i].id;
        
            var td = document.createElement("td");  //创建单元格:teacher_name
            tr.appendChild(td);
            td.innerHTML=dataArr[i].teacher_name;
            
            var td = document.createElement("td");  //创建单元格:teacher_email
            tr.appendChild(td);
            td.innerHTML=dataArr[i].email;
            

        
        
            var td = document.createElement("td");  //创建单元格
            tr.appendChild(td);
            td.innerHTML='<button>remove</button>';


            document.getElementById("searchByModuleCode").style.display = "none";
            document.getElementById("searchByTeacherId").style.display = "none";
            document.getElementById("backToAdminHome").style.display = "none";

            document.getElementById("backToShowAll").style.display = "block";
        }

        
    }
}


function backToShowAll(){
        
     window.location.href="../../html/admin/adminModule.html";
}

function jumpToCourseRegister(){
    window.location.href="../../html/admin/addModule.html";
}


function backToAdminHome(){
    window.location.href="../../html/admin/adminHome.html";
}


// /////////////////////////点击删除后弹出确认是否删除？//////////////////////////////////////////////

//询问弹窗
function confirm(moduleCode){
    hsycms.confirm('confirm','Are you sure to remove this module?',
       function(res){         //确认删除

            var obj = {"module_code":moduleCode};
            var myJson = JSON.stringify(obj);
        
            $.ajax({
                type: "POST",
                dataType: "json",
                url: basepath+"/teacher/deleteModule",
                contentType: "application/json",
                data: myJson,
                success: function (result) {
                    
                    if (result.status == "success") {    //删除成功
                        success();
                        setTimeout('window.location.href="../../html/admin/adminModule.html"',2200);
                    }
                    else {       //删除失败
                        error();
                        setTimeout('window.location.href="../../html/admin/adminModule.html"',2200);                        
                    }
                }
            });
      
         
       },
      function(res){      //取消删除
          window.location.href="../../html/admin/adminModule.html";
       },
    )
 }

 //删除成功
 function success(){
   hsycms.success('success','Remove Successfully',function(){  console.log('操作成功关闭后');  },1800)
 }

 //删除失败
 function error(){
  hsycms.error('error','Remove Failed',function(){  console.log('操作失败关闭后');  },1800)
 }