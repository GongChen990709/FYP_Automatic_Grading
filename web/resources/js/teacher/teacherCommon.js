$(function () {
    var course=GetCourse();
    console.log("returned course name is"+course);
    var user_name=GetUserName();
    //console.log("(course):"+course_name);

    var html1 = "<span> <a href='teacherAddAssignment.html?"+ course+ "&"+user_name+" '> " +
            "Add a assignment" +
            "</a> </span>";

    var html2 = "<span> <a href='teacherCheckAssignment.html?"+ course+"&"+ user_name +" '> " + "Check Assignments" +
            "</a> </span>";

    var html3 = "<span> <a href='teacherCheckGrade.html?"+ course+"&"+ user_name +" '> " +
            "Grade" +
            "</a> </span>";

    $("#addAssignment").html(html1);
    $("#checkAssignment").html(html2);
    $("#checkGrade").html(html3);

    $("#course_name").html(course);
    $("#user_name").html(user_name);
});