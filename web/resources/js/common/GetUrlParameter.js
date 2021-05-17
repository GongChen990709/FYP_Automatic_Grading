
console.log(window.location.href);

var str=window.location.href;


var list=str.split('?');
console.log(list);


var parameter=list[1].split('&');

var course_list=parameter[0];
var user_name_list=parameter[1];



var module_code=list[1].split('-');

console.log("courseID:"+module_code[0]);

var course_split= course_list.split("%20");

var username_split= user_name_list.split("%20");


function GetCourse() {
    var course_0=course_split[0];
    var course=course_0;
    for(var i=1;i<course_split.length;i++){
        course = course+" "+course_split[i];
    }
    console.log("course name and date:"+course);
    return course;
}

function GetUserName(){

    var user_name_0=username_split[0];
    var user_name=user_name_0;
    for(var i=1;i<username_split.length;i++){
        user_name = user_name+" "+ username_split[i];
    }
    console.log("user_name:"+user_name);
    return user_name;
}

function GetCourseID() {

    return module_code[0];

}

function GetAssignmentID(){
    if(parameter[2]!=null){
        var assignment_id = parameter[2];
    }
    return assignment_id;
}
