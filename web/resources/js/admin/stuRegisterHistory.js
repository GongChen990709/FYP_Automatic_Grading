
var date;
var basepath = "/" + window.location.pathname.split("/")[1];

$(function(){
    //URL传值
    var Request = new Object();
    Request = GetRequest();
    var val= Request["msg"]; 
    date = val;

    document.getElementById("registerDate").innerHTML = "Registration Date: "+ date;

    var obj = { "history_date":date};
    var myJson = JSON.stringify(obj);

    getStudentSuccessRegister(myJson);
    getStudentFailRegister(myJson);
    
});







function getStudentSuccessRegister(myJson){

    // var datas =[{
    //     "id":"17202202",
    //     "name":"tom",
    //     "email":"123qqqcom",
    //     "major_code":"iot",
    //     "status":"internet"
    // },
    // {
    //     "id":"17202202",
    //     "name":"tom",
    //     "email":"123qqqcom",
    //     "major_code":"iot",
    //     "status":"internet"
    // }];

    // var tbody = document.getElementById("successBody");
    //         for(var i=0;i<datas.length;i++){
    //             var tr = document.createElement("tr");
    //             tbody.appendChild(tr);
                
    //             //第一个
    //             var td = document.createElement("td");  //创建单元格
    //             tr.appendChild(td);
    //             td.innerHTML=datas[i].id;

    //             var td = document.createElement("td");  //创建单元格
    //             tr.appendChild(td);
    //             td.innerHTML=datas[i].name;

    //             var td = document.createElement("td");  //创建单元格
    //             tr.appendChild(td);
    //             td.innerHTML=datas[i].email;

    //             var td = document.createElement("td");  //创建单元格
    //             tr.appendChild(td);
    //             td.innerHTML=datas[i].major_code;

    //             var td = document.createElement("td");  //创建单元格
    //             tr.appendChild(td);
    //             td.innerHTML=datas[i].status;

    //         }
    $.ajax({
        url:basepath+"/register/history/successStudents",
        type:"post",
        dataType:"json",
        contentType: "application/json",
        data: myJson,
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


function getStudentFailRegister(myJson){
    $.ajax({
        url:basepath+"/register/history/failedStudents",
        type:"post",
        dataType:"json",
        contentType: "application/json",
        data: myJson,
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


function BackToShowRegisterDate(){
    window.location.href = "../../html/admin/showRegisterDate.html";
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
