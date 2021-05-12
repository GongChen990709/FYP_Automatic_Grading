
console.log(window.location.href);

var str=window.location.href;


var list=str.split('?');
console.log(list);



var list1=list[1].split('Username:');

var list2=list[1].split('-');
console.log("courseID:"+list2[0]);

console.log(list2);
console.log("aaa:"+list1);
console.log("list1[0]:"+list1[0]);
console.log("list1[1]:"+list1[1]);
var course_name_list= list1[0].split("%20");
console.log("courses name list:"+course_name_list);

var user_name_list= list1[1].split("%20");
console.log("user name list:" + user_name_list);

function GetCourseName() {


                 /*var str=window.location.href;
                 var list=str.split('?');
                 console.log(list);

                 var list1=list[1].split('Name:');
                 console.log("aaa:"+list1);
                 console.log("list1[0]:"+list1[0])
                 var course_name_list= list1[0].split("%20")
                 console.log("courses name list:"+course_name_list)
*/

                 var course_name0=course_name_list[0];
                 var course_name=course_name0;
                 for(var i=1;i<course_name_list.length;i++){
                     course_name = course_name+" "+course_name_list[i];
                 }
                 console.log("course name and date:"+course_name);
                 return course_name;
                 /*$("#course_name").html(course_name);*/
                 /* var html='';
                  html = "<span> <a href='teacher_assignment.html?"+ course_name+ " '> "+
                         "</a> </span>";*/
}

function GetUserName(){

    var user_name0=user_name_list[0];
    var user_name=user_name0;
    for(var i=1;i<user_name_list.length;i++){
        user_name = user_name+" "+ user_name_list[i];
    }
    console.log("user_name:"+user_name);
    return user_name;
}

function GetCourseID() {

    return list2[0];

}
