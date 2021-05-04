var basepath = "/" + window.location.pathname.split("/")[1];
function uploadStudentCSV(){//transfer csv to json array
    var files = $("#getfile")[0].files;
    var reader = new FileReader();
    reader.readAsText( files[0],"gbk" );            //以文本格式读取
    reader.onload = function(evt){
        var data = evt.target.result;        //读到的数据
        console.log(data);
        splitdate = data.split(/\s+/);
        console.log(splitdate.length);
        var userList = [];
        for(var i=1;i<splitdate.length;i++){
            var json = {};
            var data =splitdate[i].split(",");
            console.log(data);
            json.ucd_id = data[1];
            json.name = data[0];
            json.pwd = data[2];
            json.email = data[3];
            json.major_code = data[4];
            console.log(json);
            userList.push(json);
        }
        console.log(userList);
        var myJson = JSON.stringify(userList);
        alert(myJson)
        $.ajax({
            type: "POST",
            dataType: "json",
            url: basepath+"/register/batchStudents",
            contentType: "application/json",
            data: myJson,
            success: function (result) {
                console.log("data is :" + JSON.stringify(result));
                if (result.status == "true") {
                    alert("upload csv file success")
                    window.location.href = "adminHome.html";
                }
                else {
                    alert(result.status+" Please check again");
                }
            }
        });
    }
}


function uploadTeacherCSV(){//transfer csv to json array
    var files = $("#getfile")[0].files;
    var reader = new FileReader();
    reader.readAsText( files[0],"gbk" );            //以文本格式读取
    reader.onload = function(evt){
        var data = evt.target.result;        //读到的数据
        console.log(data);
        splitdate = data.split(/\s+/);
        console.log(splitdate.length);
        var userList = [];
        for(var i=1;i<splitdate.length;i++){
            var json = {};
            var data =splitdate[i].split(",");
            console.log(data);
            json.id = data[1];
            json.name = data[0];
            json.pwd = data[2];
            json.email = data[3];
            json.department_code = data[4];
            console.log(json);
            userList.push(json);
        }
        console.log(userList);
        var myJson = JSON.stringify(userList);
        alert(myJson)
        $.ajax({
            type: "POST",
            dataType: "json",
            url: basepath+"/register/batchTeachers",
            contentType: "application/json",
            data: myJson,
            success: function (result) {
                console.log("data is :" + JSON.stringify(result));
                if (result.status == "true") {
                    alert("upload csv file success")
                    window.location.href = "adminHome.html";
                }
                else {
                    alert(result.status+" Please check again");
                }
            }
        });
    }
}

