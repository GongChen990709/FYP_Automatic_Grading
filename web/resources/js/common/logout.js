
var basepath = "/" + window.location.pathname.split("/")[1];
function logout(){

    var obj={"status":"logout"};
    var myJson = JSON.stringify(obj);
    $.ajax({
        type: "POST",
        dataType: "json",
        url: basepath+"/doLogOut",
        contentType: "application/json",
        data: myJson,
        success: function (result) {
            
            if (result.status == "true") {
                
                window.location.href = "../../../login.html";
            }
            else {
                alert(result.status+"logout error");
            }
        }
    });
}