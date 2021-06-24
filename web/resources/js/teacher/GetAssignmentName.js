
console.log(window.location.href);
var str=window.location.href;


function GetAssignmentName(){


    var list=str.split('?');
    var assignment_name_list = list[1].split("%20");
    console.log(assignment_name_list);

    var assignment_name_0=assignment_name_list[0];
    var assignment_name=assignment_name_0;
    for(var i=1;i<assignment_name_list.length;i++){
        assignment_name = assignment_name+" "+assignment_name_list[i];
    }
    console.log(assignment_name);
    return assignment_name;
}

/*function GetAssignmentID(){
    var assIDList=str.split("Ass_ID:");
    console.log(assIDList);
    console.log(assIDList[1]);
    return assIDList[1];
}*/
