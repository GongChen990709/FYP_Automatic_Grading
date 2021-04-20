function jumpToStudentRegister(){
    window.location.href="../../html/admin/addStudent.html";
}

function jumpToMultipleStudent(){
    window.location.href="../../html/admin/addMultipleStudent.html";
}

function jumpToTeacherRegister(){
    window.location.href="../../html/admin/addTeacher.html";
}

function jumpToMultipleTeacher(){
    window.location.href="../../html/admin/addMultipleTeacher.html";
}


function jumpToLoginPage(){
    window.location.href="../../../login.html";
}

function jumpToAdminStudent(){
    window.location.href="../../html/admin/adminStudent.html";
}
function jumpToAdminTeacher(){
    window.location.href="../../html/admin/adminTeacher.html";
}



var flag=true;

function set() {
    var menu2=document.getElementById("setting");
    if(flag){
        menu2.style.display="inline-block";
        flag=false;
    }else{
        menu2.style.display="none";
        flag=true;
    }

}

function set1(){
    var menu2=document.getElementById("setting");
    menu2.style.display="none";
    flag=true;
}