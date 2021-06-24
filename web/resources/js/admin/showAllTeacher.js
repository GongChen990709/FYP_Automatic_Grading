var basepath = "/" + window.location.pathname.split("/")[1];

var dataArr = new Array();

$(function (){
    
    $.ajax({
        url:basepath+"/teacher/allTeachers",
        type:"post",
        dataType:"json",
        success:function(datas){
            dataArr = datas;

            var tbody=document.querySelector("tbody");
            for(var i=0;i<datas.length;i++){
                var tr = document.createElement("tr");
                tbody.appendChild(tr);
                
                //第一个
                var td = document.createElement("td");  //创建单元格:id
                tr.appendChild(td);
                td.innerHTML=datas[i].id;
            
                var td = document.createElement("td");  //创建单元格:name
                tr.appendChild(td);
                td.innerHTML=datas[i].name;
            
                var td = document.createElement("td");  //创建单元格:email
                tr.appendChild(td);
                td.innerHTML=datas[i].email;
            
                var td = document.createElement("td");  //创建单元格:department_code
                tr.appendChild(td);
                td.innerHTML=datas[i].department_code;
            
            
            
                var td = document.createElement("td");  //创建单元格
                tr.appendChild(td);
                td.innerHTML='<button>remove</button>';
            }
            $("table").on("click","button",function(){
                var idVal = $(this).parent().siblings().first().html();
                
                confirm(idVal);

            })
        }
    });
    // var datas=[{
    //     id:"17201111",
    //     name:"CARR",
    //     email:"ABCARR.QQCOM",
    //     department_code:"CS"
        
    // },{
    //     id:"17201222",
    //     name:"billy",
    //     email:"billyHongkong.cn",
    //     department_code:"EE"
        
    // },{
    //     id:"17201212",
    //     name:"niglas",
    //     email:"niglaucdie",
    //     department_code:"EE"
        
    // },{
    //     id:"1234567",
    //     name:"julifur",
    //     email:"eer.cn.QQCOM",
    //     department_code:"EE"
    // },{
    //     id:"172810123",
    //     name:"haro",
    //     email:"ABCARR.QQCOM",
    //     department_code:"EE"
    // },{
    //     id:"1231231",
    //     name:"ggg",
    //     email:"fgg.ccqq.QQCOM",
    //     department_code:"CS"
    // }];

    // dataArr = datas;

    //         var tbody=document.querySelector("tbody");
    //         for(var i=0;i<datas.length;i++){
    //             var tr = document.createElement("tr");
    //             tbody.appendChild(tr);
                
    //             //第一个
    //             var td = document.createElement("td");  //创建单元格:id
    //             tr.appendChild(td);
    //             td.innerHTML=datas[i].id;
            
    //             var td = document.createElement("td");  //创建单元格:name
    //             tr.appendChild(td);
    //             td.innerHTML=datas[i].name;
            
    //             var td = document.createElement("td");  //创建单元格:email
    //             tr.appendChild(td);
    //             td.innerHTML=datas[i].email;
            
    //             var td = document.createElement("td");  //创建单元格:department_code
    //             tr.appendChild(td);
    //             td.innerHTML=datas[i].department_code;
            
            
            
    //             var td = document.createElement("td");  //创建单元格
    //             tr.appendChild(td);
    //             td.innerHTML='<button>remove</button>';
    //         }
    //         $("table").on("click","button",function(){
    //             var idVal = $(this).parent().siblings().first().html();
                
    //             confirm(idVal);

    //         })
    
});










function searchStudentById(){


    var stuId = document.getElementById("stuIdSearch").value;
    for(var i=0;i<dataArr.length;i++){
        if(stuId==dataArr[i].id){
            
            document.getElementById("showInfoAllStu").style.display = "none";
            document.getElementById("showStudentInfoById").style.display = "block";

            // alert(JSON.stringify(dataArr[i]))
            // var tbody1=document.querySelector("tbody1");
            var tbody1 = document.getElementById("tbody1");
            var tr = document.createElement("tr");
            tbody1.appendChild(tr);
            
            //第一个
            var td = document.createElement("td");  //创建单元格
            tr.appendChild(td);
            td.innerHTML=dataArr[i].id;
        
            var td = document.createElement("td");  //创建单元格
            tr.appendChild(td);
            td.innerHTML=dataArr[i].name;
        
            var td = document.createElement("td");  //创建单元格
            tr.appendChild(td);
            td.innerHTML=dataArr[i].email;
        
            var td = document.createElement("td");  //创建单元格
            tr.appendChild(td);
            td.innerHTML=dataArr[i].department_code;
        
        
        
            var td = document.createElement("td");  //创建单元格
            tr.appendChild(td);
            td.innerHTML='<button>remove</button>';


            document.getElementById("searchById").style.display = "none";
            document.getElementById("searchByMajor").style.display = "none";
            document.getElementById("backToShowAll").style.display = "block";
        }

     

    }
}

function searchStudentByMajor(){
    var majorCode = document.getElementById("majorCode").value;
    for(var i=0;i<dataArr.length;i++){
        if(majorCode==dataArr[i].department_code){
            
            document.getElementById("showInfoAllStu").style.display = "none";
            document.getElementById("showStudentInfoById").style.display = "block";

            
            var tbody1 = document.getElementById("tbody1");
            var tr = document.createElement("tr");
            tbody1.appendChild(tr);
            
            //第一个
            var td = document.createElement("td");  //创建单元格
            tr.appendChild(td);
            td.innerHTML=dataArr[i].id;
        
            var td = document.createElement("td");  //创建单元格
            tr.appendChild(td);
            td.innerHTML=dataArr[i].name;
        
            var td = document.createElement("td");  //创建单元格
            tr.appendChild(td);
            td.innerHTML=dataArr[i].email;
        
            var td = document.createElement("td");  //创建单元格
            tr.appendChild(td);
            td.innerHTML=dataArr[i].department_code;

        
        
            var td = document.createElement("td");  //创建单元格
            tr.appendChild(td);
            td.innerHTML='<button>remove</button>';


            document.getElementById("searchById").style.display = "none";
            document.getElementById("searchByMajor").style.display = "none";
            document.getElementById("backToShowAll").style.display = "block";
        }

        
    }
}


function backToShowAll(){
        
        window.location.href="../../html/admin/adminTeacher.html";
}







// /////////////////////////点击删除后弹出确认是否删除？//////////////////////////////////////////////

//询问弹窗
function confirm(id){
    hsycms.confirm('confirm','Are you sure to delete this teacher?',
       function(res){         //确认删除

            var obj = {"teacher_id":id};
            var myJson = JSON.stringify(obj);
        
            $.ajax({
                type: "POST",
                dataType: "json",
                url: basepath+"",
                contentType: "application/json",
                data: myJson,
                success: function (result) {
                    
                    if (result.status == "true") {    //删除成功
                        success();
                        setTimeout('window.location.href="../../html/admin/adminTeacher.html"',2200);
                    }
                    else {       //删除失败
                        error();
                        setTimeout('window.location.href="../../html/admin/adminTeacher.html"',2200);                        
                    }
                }
            });
      
         
       },
      function(res){      //取消删除
          window.location.href="../../html/admin/adminTeacher.html";
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

