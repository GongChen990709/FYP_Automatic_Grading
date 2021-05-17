$(function () {
    var course=GetCourse();
    console.log("returned course name is"+course);
    var user_name=GetUserName();
    //console.log("(course):"+course_name);

    var html1 = "<span> <a href='studentAssignment.html?"+ course+ "&"+user_name+" '> " +
                "Assignment" +
                "</a> </span>";

    var html2 = "<span> <a href='studentGrade.html?"+ course+"&"+ user_name +" '> " + "Grade" +
                "</a> </span>";


    $("#assignment").html(html1);
    $("#grade").html(html2);


    $("#course_name").html(course);
    $("#user_name").html(user_name);
});