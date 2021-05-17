
var basepath = "/" + window.location.pathname.split("/")[1];

function uploadStudentXLSX(){      //上传XLSX文件

         $("#wait").html("Please wait for a moment...");

        // 点提交后首先清空文件名和waiting字段
        $("#filename").html("");

        var selectedFile = document.querySelector("#fileupload").files[0];
        
        var reader = new FileReader();
        // 读取上传文件为二进制
        reader.readAsBinaryString(selectedFile);
     
        reader.onload = function(event) {
          var data = event.target.result;
          var workbook = XLSX.read(data, {
            type: 'binary'
          });
          workbook.SheetNames.forEach(function(sheetName) {
            var XL_row_object = XLSX.utils.sheet_to_row_object_array(workbook.Sheets[sheetName]);
            var myJson = JSON.stringify(XL_row_object);
            alert(myJson);/////
            var totalNum = XL_row_object.length;



            $.ajax({
              type: "POST",
              dataType: "json",
              url: basepath+"/register/sendEmailToMultipleStudents",
              contentType: "application/json",
              data: myJson,
              success: function (result) {
                  // console.log("data is :" + JSON.stringify(result));
                  if (result.status == "success") {
                      
                    setTimeout("window.location.href='../../../status/StudentEmailStatus.html'", 5000);     //收到success后停留5秒钟后  跳到邮件发送status页面
                    // getEmailData(totalNum);

                  }
                  else {
                      // alert(result.status+" Please check again");
                  }
              }
            });

              setTimeout(function () { getEmailData(totalNum); },5*1000);    //5秒后第一次请求email success fail 数据


            if (XL_row_object.length > 0) {
              console.log(XL_row_object)
            }
          })
        };
        reader.onerror = function(event) {
          console.error("File could not be read! Code " + event.target.error.code);
        };
    
}




function getEmailData(totalNum){       //向后端请求email的发送数据
  $.ajax({
      url: basepath+"/register/emailCounts",
      type: "post",
      dataType: 'json',
      success: function (json) {
          //方法中传入的参数json为后台获取的数据:successNum,failNum

          var successNum = json.successNum;   
          var failNum = json.failNum;
          
          showProgressBar(successNum,failNum,totalNum);
          setTimeout(function () { getEmailData(totalNum); },500);   //每隔0.5秒向后端发一次请求


          // if((successNum+failNum) == totalNum){     //全部发送完毕
          //     showProgressBar(successNum,failNum,totalNum);
          //     setTimeout("window.location.href='../../../status/StudentEmailStatus.html'", 2000);     //停留2秒钟后  跳到邮件发送status页面
          
          // }else if((successNum+failNum) < totalNum){         //仍未发送完毕

          //     showProgressBar(successNum,failNum,totalNum);
          //     setTimeout(getEmailData(totalNum),2*1000);   //每隔2秒向后端发一次请求

          // }


      }
  })
}


function showProgressBar(successNum,failNum,totalNum){
        document.getElementById("progress-wrapper").style.visibility="visible";//显示

        
        var success = (successNum/totalNum)*100*4;
        var total = 100*4;
        var tipV = success- 30;

        var tip = document.querySelector(".percent-tip");        //百分比框
        var tip_val = document.getElementById("successNum");       //已发送成功数元素
        //input.value填写已发送成功邮件的个数
        document.styleSheets[0].addRule(".progress-wrapper .fill::before","width:"+success+"px");
        // tip.style.left = successNum*4 -30+"px";
        tip.style.left = tipV+"px";
        tip_val.innerHTML = successNum;


        var tip_val3 = document.getElementById("failNum");
        tip_val3.innerHTML = failNum;


        //var tip2 = document.querySelector(".percent-tip");
        var tip_val2 = document.getElementById("totalNum");     //总数元素
        document.styleSheets[0].addRule(".container .progress-wrapper div","width:"+total+"px");
        tip_val2.innerHTML = totalNum;
}