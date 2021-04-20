
function abc(){//transfer csv to json array

    var files = $("#getfile")[0].files;
    var reader = new FileReader();
    reader.readAsText( files[0],"gbk" );            //以文本格式读取
    reader.onload = function(evt){
        var data = evt.target.result;        //读到的数据
        console.log(data);
        splitdate = data.split(/\s+/) ;
        console.log(splitdate.length)

        var arr1=new Array();
        var arr2=new Array();
        var arr3=new Array();
        var arr4=new Array();
        var arr5=new Array();
        
        var nameArr=new Array();
        var idArr=new Array();
        var pwdArr=new Array();
        var emailArr=new Array();
        var majorArr=new Array();

        for(var i=0;i<splitdate.length;i++){
            var data =splitdate[i].split(",");
            arr1.push(data[0])
            arr2.push(data[1])
            arr3.push(data[2])
            arr4.push(data[3])
            arr5.push(data[4])
        }

        for(var i=1;i<arr1.length-1;i++){
            nameArr.push(arr1[i])
        }
        for(var i=1;i<arr2.length-1;i++){
            idArr.push(arr2[i])
        }
        for(var i=1;i<arr1.length-1;i++){
            pwdArr.push(arr3[i])
        }
        for(var i=1;i<arr1.length-1;i++){
            emailArr.push(arr4[i])
        }
        for(var i=1;i<arr1.length-1;i++){
            majorArr.push(arr5[i])
        }

        var userList = [];
        for(var i=0;i<nameArr.length;i++){
            var json = {};
            json.name = nameArr[i];  
            json.id = idArr[i]; 
            json.pwd = pwdArr[i];
            json.email = emailArr[i];
            json.majorCode = majorArr[i];
            console.log(json);
            userList.push(json);

        }
        console.log(userList);
        var myJson = JSON.stringify(userList);
        alert(myJson)
    }
}
