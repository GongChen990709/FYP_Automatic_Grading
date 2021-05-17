$(function (){
    getStudentSuccessEmail();
    getStudentFailEmail();
});
var basepath = "/" + window.location.pathname.split("/")[1];
function getStudentSuccessEmail(){
    $.ajax({
        url:basepath+"/register/successStudents",
        type:"post",
        dataType:"json",
        success:function(datas){
            var tbody = document.getElementById("successBody");
            for(var i=0;i<datas.length;i++){
                var tr = document.createElement("tr");
                tbody.appendChild(tr);

                //第一个
                var td = document.createElement("td");  //创建单元格
                tr.appendChild(td);
                td.innerHTML=datas[i].id;

                var td = document.createElement("td");  //创建单元格
                tr.appendChild(td);
                td.innerHTML=datas[i].name;

                var td = document.createElement("td");  //创建单元格
                tr.appendChild(td);
                td.innerHTML=datas[i].email;

                var td = document.createElement("td");  //创建单元格
                tr.appendChild(td);
                td.innerHTML=datas[i].major_code;

                var td = document.createElement("td");  //创建单元格
                tr.appendChild(td);
                td.innerHTML=datas[i].status;

            }

        }
    });
}


function getStudentFailEmail(){
    $.ajax({
        url:basepath+"/register/failedStudents",
        type:"post",
        dataType:"json",
        success:function(datas){
            var tbody = document.getElementById("failBody");
            for(var i=0;i<datas.length;i++){
                var tr = document.createElement("tr");
                tbody.appendChild(tr);

                //第一个
                var td = document.createElement("td");  //创建单元格
                tr.appendChild(td);
                td.innerHTML=datas[i].id;

                var td = document.createElement("td");  //创建单元格
                tr.appendChild(td);
                td.innerHTML=datas[i].name;

                var td = document.createElement("td");  //创建单元格
                tr.appendChild(td);
                td.innerHTML=datas[i].email;

                var td = document.createElement("td");  //创建单元格
                tr.appendChild(td);
                td.innerHTML=datas[i].major_code;

                var td = document.createElement("td");  //创建单元格
                tr.appendChild(td);
                td.innerHTML=datas[i].status;

            }
        }
    });
}




