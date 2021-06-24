
var basepath = "/" + window.location.pathname.split("/")[1];

$(function (){
    
    var html="";

    $.ajax({
        url:basepath+"/register/student/allHistoryDates",
        type:"post",
        dataType:"json",
        success:function(datas){
            
            var hist = datas.history_dates;
            for(var i=0;i<hist.length;i++){
                var date = hist[i];
                html+="<span> <a href='stuRegisterHistory.html?msg="+date+ "  '> " + date + "</a> </span>"   +  "<br/>";
            }
            $("#timeline").html(html);
            
        }
    });
    // var datas={
    //     "history_dates":["20101:111:1111","2021/4/21111:10","201010111231"]
    // }

    // var hist = datas.history_dates;
    // for(var i=0;i<hist.length;i++){
    //     var date = hist[i];
    //     html+="<span> <a href='stuRegisterHistory.html?msg="+date+ "  '> " + date + "</a> </span>"   +  "<br/>";
    // }
    // $("#timeline").html(html);


    



   
    
});


function  backToStuMultiReg(){
    window.location.href = "../../html/admin/addMultipleStudent.html";

}

