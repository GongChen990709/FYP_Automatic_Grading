

function afterClickOverloadOrNot(){
    var overloadDecision = document.getElementById("overloadOrSingle").value;
    if(overloadDecision=="pleaseSelect"){
        document.getElementById("overloadVersion").style.display = "none";
        document.getElementById("simpleVersion").style.display = "none";

    }
    else if(overloadDecision=="yes"){
        document.getElementById("simpleVersion").style.display = "none";
        document.getElementById("overloadVersion").style.display = "block";

    }else if(overloadDecision=="no"){
        document.getElementById("overloadVersion").style.display = "none";
        document.getElementById("simpleVersion").style.display = "block";
    }
}

// var overloadFunctionName;       //存起来overload的function name
// function showOverloadFuncArea(){     //确定方法名之后调用此方法， 首先存起来方法名，再展示出return，param的选择框
//     var functionName = document.getElementById("overloadfunctionName").value;
//     overloadFunctionName = functionName;
//     document.getElementById("overloadFuncSetting").style.display = "block";
// }

//点击continue之后，先保存return和paramArr到一个数组，再清空return和param区域
var overloadReturnTypeStorage = new Array();
var overloadParamArrStorage = new Array();
function overloadContinue(){      
    var returnType = document.getElementById("overloadreturnType").value;
    var returnTypeJson = getReturnType(returnType);      
    var paramArr = getOverloadParamArray();

    //将return存入数组中
    var returnJson = {"returnType":returnTypeJson};
    overloadReturnTypeStorage.push(returnJson);
    //将paramArr存入数组中
    var overLoadJson = {"parameters":paramArr};
    overloadParamArrStorage.push(overLoadJson);

    // alert(JSON.stringify(overloadReturnTypeStorage))
    // alert(JSON.stringify(overloadParamArrStorage))


    document.getElementById("overloadreturnType").value = "void";
    document.getElementById("overloadparaNum").value = "";
    document.getElementById("overloadparaArea").innerHTML = "";

    document.getElementById("overloadreturnType").focus();

    document.getElementById("setOverloadParamDat").style.display = "none";

    clearArray(temporaryStorageForReturnType);   //returnType List
    clearArray(temporaryStorageForParam);        //parameter List
    clearArray(temporaryStorageForReturnMAP);    //returnType MAP
    clearArray(temporaryStorageForParamMAP);      //parameter  MAP
    clearArray(temporaryStorageForReturnStack);    //returnType Stack
    clearArray(temporaryStorageForParamStack);    //parameter Stack
    clearArray(temporaryStorageForReturnArray);    //returnType array
    clearArray(temporaryStorageForParamArray);    //parameter array
    
}


function getOverloadParamArray(){    
    var paraArr = new Array();
    var paraNum = $("#overloadparaNum").val();
    for(var i=0;i<paraNum;i++){
        var element = $("#"+i).val();
        var dataJson = judgeDataType(element,i);
        paraArr.push(dataJson);
        
    }
    
    return paraArr;
}

function overloadparaNumButton(){
    var html="";
    var paraNum = $("#overloadparaNum").val();

    if(paraNum>=1){
        document.getElementById("setOverloadParamDat").style.display = "block";
    }

    for(var i=0;i<paraNum;i++){
        html+= "<select id='"+i+"' name='paraSelect' onchange='OverloadAfterClickParamSelectBox("+i+")'>"+
       // "<option value ='void'>void</option>"+  
        "<option value ='Double'>Double</option>"+     
        "<option value ='Byte'>Byte</option>"+         
        "<option value ='Float'>Float</option>"+       
        "<option value ='Boolean'>Boolean</option>"+       
        "<option value ='Character'>Character</option>"+
        "<option value ='Integer'>Integer</option>"+
        "<option value ='Short'>Short</option>"+
        "<option value ='Long'>Long</option>"+
        "<option value ='String'>String</option>"+
        "<option value ='double'>double</option>"+
        "<option value ='byte'>byte</option>"+
        "<option value ='float'>float</option>"+
        "<option value ='boolean'>boolean</option>"+
        "<option value ='char'>char</option>"+
        "<option value ='int'>int</option>"+
        "<option value ='short'>short</option>"+
        "<option value ='long'>long</option>"+

        "<option value ='listOfParam'>List</option>"+
        "<option value ='mapOfParam'>Map</option>"+
        "<option value ='stackOfParam'>Stack</option>"+
        "<option value='arrayOfParam'>Array</option></select>"
    }

    $("#overloadparaArea").html(html);

}

// var temporaryId;
function OverloadAfterClickParamSelectBox(i){
    temporaryId = i;
    var typeName = document.getElementById(i).value;
    if(typeName=="void" || typeName=="Double" || typeName=="Byte" || typeName=="Float" || typeName=="Boolean" || typeName=="Character" || typeName=="Integer" || typeName=="Short" || typeName=="Long" || typeName=="String" || typeName=="double" || typeName=="byte" || typeName=="float" || typeName=="boolean" || typeName=="char" || typeName=="int" || typeName=="short" || typeName=="long"){
        return;
    }else if(typeName=="listOfParam"){    
        showWinParam();
    }else if(typeName=="mapOfParam"){
        showWinParamMAP();
    }else if(typeName=="stackOfParam"){
        showWinParamStack();
    }else if(typeName=="arrayOfParam"){
        showWinParamArray();
    }
}


function OverloadAfterClickReturnSelectBox(){
    var typeName = document.getElementById("overloadreturnType").value;
    if(typeName=="void" || typeName=="Double" || typeName=="Byte" || typeName=="Float" || typeName=="Boolean" || typeName=="Character" || typeName=="Integer" || typeName=="Short" || typeName=="Long" || typeName=="String" || typeName=="double" || typeName=="byte" || typeName=="float" || typeName=="boolean" || typeName=="char" || typeName=="int" || typeName=="short" || typeName=="long"){
        return;
    }else if(typeName=="list"){    
        showWin();
    }else if(typeName=="map"){
        showWinMAP();
    }else if(typeName=="stack"){
        showWinStack();
    }else if(typeName=="array"){
        showWinArray();
    }
}

// function getOverloadReturn(){
//     return overloadReturnTypeStorage;
// }
// function getOverloadArr(){
//     return overloadParamArrStorage;
// }


// var overloadFunctionName;       //存起来overload的function name

function OverloadAddFunction(){
    var className = $("#className").val();
    var functionName = document.getElementById("overloadfunctionName").value;
    var newArr1 = new Array();
    var newArr2 = new Array();

    for(var x=0; x<overloadReturnTypeStorage.length;x++){
        var returnTypeArr = overloadReturnTypeStorage[x];
        var overLoadArr = overloadParamArrStorage[x];
        newArr1.push(returnTypeArr);
        newArr2.push(overLoadArr);
    }



    // overloadFunction = getSingleFuncComponent(overloadFunctionName,className,newArr1,newArr2);
    overloadFunction = getSingleFuncComponent(functionName,className,newArr1,newArr2);

    functionArray.push(overloadFunction);

    document.getElementById("overloadfunctionName").value = "";
    document.getElementById("overloadVersion").style.display = "none";

    document.getElementById("setOverloadParamDat").style.display = "none";
    document.getElementById("overloadOrSingle").value = "pleaseSelect";

    clearArray(overloadReturnTypeStorage);
    clearArray(overloadParamArrStorage);


    

}





































////////////////////////////////////////Already Completed Part////////////////////////////////////////////////////////


//1111111111111111
//just after click one item in ReturnType select box, call this function
function afterClickReturnSelectBox(){
    var typeName = document.getElementById("returnType").value;
    if(typeName=="void" || typeName=="Double" || typeName=="Byte" || typeName=="Float" || typeName=="Boolean" || typeName=="Character" || typeName=="Integer" || typeName=="Short" || typeName=="Long" || typeName=="String" || typeName=="double" || typeName=="byte" || typeName=="float" || typeName=="boolean" || typeName=="char" || typeName=="int" || typeName=="short" || typeName=="long"){
        return;
    }else if(typeName=="list"){    
        showWin();
    }else if(typeName=="map"){
        showWinMAP();
    }else if(typeName=="stack"){
        showWinStack();
    }else if(typeName=="array"){
        showWinArray();
    }
}


/////////////////////////////////////////////////////////////////////////////////////////
//点击help之后
function showWinHelp(){
    document.getElementById("popOutHelp").style.display = "block";
    document.getElementById("popOutBgHelp").style.display = "block";
}
//关闭弹出的表单
function closeWinHelp(){
    document.getElementById("popOutHelp").style.display = "none";
    document.getElementById("popOutBgHelp").style.display = "none";
      
}












///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//在returnType框选择了List之后，弹出表单
function showWin(){
    document.getElementById("popOut").style.display = "block";
    document.getElementById("popOutBg").style.display = "block";
}
//关闭弹出的表单
function closeWin(){
    disappearWin();
    returnTypeBackToVoid();    
}

//清空数组内容
function clearArray(array){
    array.length=0;
}


var temporaryStorageForReturnType = new Array();
//点击generics弹框的confirm后将填写的值保存到数组
function getValueFromWin(){
    var memberType = document.getElementById("memberType").value;
    var implementName = document.getElementById("implementNameType").value;
    
    var fullClassName;
    if(implementName=="linkedlist"){
        fullClassName = "java.util.LinkedList";
    }else if(implementName=="arraylist"){
        fullClassName = "java.util.ArrayList";
    }

    var memberArr = getMemberArray(memberType);
    
    var memberImpletFullClass = {"memberType":memberArr,"implementName":implementName,"fullClassName":fullClassName};
    temporaryStorageForReturnType.push(memberImpletFullClass);
    // alert(JSON.stringify(temporaryStorageForReturnType))
    disappearWin();
    document.getElementById("memberType").value = "Double";
    document.getElementById("implementNameType").value = "linkedlist";

}
function disappearWin(){
    document.getElementById("popOut").style.display = "none";
    document.getElementById("popOutBg").style.display = "none";
}
function returnTypeBackToVoid(){
    document.getElementById("returnType").value = "void";
}

//从List弹出框的member框拿值并转为  [ { "dataType":"basic", "typeName":"int" } ]
function getMemberArray(memberType){
    var memberArr = new Array();
    var memberJson = judgeDataType(memberType);       //返回{"dataType":"basic","typeName":"String"}  json
    memberArr.push(memberJson);    
    return memberArr;                 //返回  [ { "dataType":"basic", "typeName":"int" } ]
}


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////







//在returnType框选择了MAP之后，弹出表单
function showWinMAP(){
    document.getElementById("popOutMAP").style.display = "block";
    document.getElementById("popOutBgMAP").style.display = "block";
}
//关闭弹出的表单
function closeWinMAP(){
    disappearWinMAP();
    returnTypeBackToVoid();    
}
function disappearWinMAP(){
    document.getElementById("popOutMAP").style.display = "none";
    document.getElementById("popOutBgMAP").style.display = "none";
}

function getMemberArrMAP(memberKey,memberValue){    
    var memberArrMAP = new Array();
    var KeyJson = judgeDataType(memberKey);
    var ValueJson = judgeDataType(memberValue);
    memberArrMAP.push(KeyJson);
    memberArrMAP.push(ValueJson);
    return memberArrMAP;

}

var temporaryStorageForReturnMAP = new Array();
//点击generics弹框的confirm后将填写的值保存到数组
function getValueFromWinMAP(){
    var memberKey = document.getElementById("memberKey").value;
    var memberValue = document.getElementById("memberValue").value;
    var implementName = document.getElementById("implementNameTypeMAP").value;
    
    var fullClassName;
    if(implementName=="hashmap"){
        fullClassName = "java.util.HashMap";
    }

    var memberArr = getMemberArrMAP(memberKey,memberValue);
    
    var memberImpletFullClass = {"memberType":memberArr,"implementName":implementName,"fullClassName":fullClassName};
    temporaryStorageForReturnMAP.push(memberImpletFullClass);
    // alert(JSON.stringify(temporaryStorageForReturnMAP))
    disappearWinMAP();
    document.getElementById("memberKey").value = "Double";
    document.getElementById("memberValue").value = "Double";
    document.getElementById("implementNameTypeMAP").value = "hashmap";

}


// //////////////////////////////////////////////////////////////////////////////////////////////////////////////




//在returnType框选择了Stack之后，弹出表单
function showWinStack(){
    document.getElementById("popOutStack").style.display = "block";
    document.getElementById("popOutBgStack").style.display = "block";
}
//关闭弹出的表单
function closeWinStack(){
    disappearWinStack();
    returnTypeBackToVoid();    
}
function disappearWinStack(){
    document.getElementById("popOutStack").style.display = "none";
    document.getElementById("popOutBgStack").style.display = "none";
}

var temporaryStorageForReturnStack = new Array();
//点击generics弹框的confirm后将填写的值保存到数组
function getValueFromWinStack(){
    var memberType = document.getElementById("memberStack").value;
    var implementName = document.getElementById("implementNameTypeStack").value;
    
    var fullClassName;
    if(implementName=="stack"){
        fullClassName = "java.util.Stack";
    }

    var memberArr = getMemberArray(memberType);
    
    var memberImpletFullClass = {"memberType":memberArr,"implementName":implementName,"fullClassName":fullClassName};
    temporaryStorageForReturnStack.push(memberImpletFullClass);
    // alert(JSON.stringify(temporaryStorageForReturnStack))
    disappearWinStack();
    document.getElementById("memberStack").value = "Double";
    document.getElementById("implementNameTypeStack").value = "stack";

}


//////////////////////////////////////////////////////////////////////////////////////////////////////////





//在returnType框选择了Array之后，弹出表单
function showWinArray(){
    document.getElementById("popOutArray").style.display = "block";
    document.getElementById("popOutBgArray").style.display = "block";
}
//关闭弹出的表单
function closeWinArray(){
    disappearWinArray();
    returnTypeBackToVoid();    
}
function disappearWinArray(){
    document.getElementById("popOutArray").style.display = "none";
    document.getElementById("popOutBgArray").style.display = "none";
}

var temporaryStorageForReturnArray = new Array();
//点击generics弹框的confirm后将填写的值保存到数组
function getValueFromWinArray(){
    var memberType = document.getElementById("memberArray").value;
    var implementName = document.getElementById("implementNameTypeArray").value;
    
    // var fullClassName;
    // if(implementName=="array"){
    //     fullClassName = "java.util.Stack";
    // }

    var memberArr = getMemberArray(memberType);
    
    var memberImplet = {"memberType":memberArr,"implementName":implementName};
    temporaryStorageForReturnArray.push(memberImplet);
    // alert(JSON.stringify(temporaryStorageForReturnArray))
    disappearWinArray();
    document.getElementById("memberArray").value = "Double";
    document.getElementById("implementNameTypeArray").value = "array";

}






// 22222222222222222222222222222
//just after click parameter box

var temporaryId;
function afterClickParamSelectBox(i){
    temporaryId = i;
    var typeName = document.getElementById(i).value;
    if(typeName=="void" || typeName=="Double" || typeName=="Byte" || typeName=="Float" || typeName=="Boolean" || typeName=="Character" || typeName=="Integer" || typeName=="Short" || typeName=="Long" || typeName=="String" || typeName=="double" || typeName=="byte" || typeName=="float" || typeName=="boolean" || typeName=="char" || typeName=="int" || typeName=="short" || typeName=="long"){
        return;
    }else if(typeName=="listOfParam"){    
        showWinParam();
    }else if(typeName=="mapOfParam"){
        showWinParamMAP();
    }else if(typeName=="stackOfParam"){
        showWinParamStack();
    }else if(typeName=="arrayOfParam"){
        showWinParamArray();
    }
}

// /////////////////////////   Parameter List ///////////////////////////////////////////////////////
function showWinParam(){
    document.getElementById("popOutParam").style.display = "block";
    document.getElementById("popOutBgParam").style.display = "block";
}
//关闭弹出的表单
function closeWinParam(){
    disappearWinParam();
    returnTypeBackToDoubleParam(temporaryId);    
}
function disappearWinParam(){
    document.getElementById("popOutParam").style.display = "none";
    document.getElementById("popOutBgParam").style.display = "none";
}
function returnTypeBackToDoubleParam(i){
    document.getElementById(i).value = "Double";
}

var temporaryStorageForParam = new Array();
//点击generics弹框的confirm后将填写的值保存到数组
function getValueFromWinParam(){
    var memberType = document.getElementById("memberTypeParam").value;
    var implementName = document.getElementById("implementNameTypeParam").value;
    // alert(temporaryId)
    var fullClassName;
    if(implementName=="linkedlist"){
        fullClassName = "java.util.LinkedList";
    }else if(implementName=="arraylist"){
        fullClassName = "java.util.ArrayList";
    }

    var memberArr = getMemberArray(memberType);
    
    var memberImpletFullClass = {"Id":temporaryId,"memberType":memberArr,"implementName":implementName,"fullClassName":fullClassName};
    temporaryStorageForParam.push(memberImpletFullClass);
    // alert(JSON.stringify(temporaryStorageForParam))
    disappearWinParam();
    document.getElementById("memberTypeParam").value = "Double";
    document.getElementById("implementNameTypeParam").value = "linkedlist";

}




///////////////////////////// Parameter MAP  ///////////////////////////////////////////////////////

function showWinParamMAP(){
    document.getElementById("popOutParamMAP").style.display = "block";
    document.getElementById("popOutBgParamMAP").style.display = "block";
}
//关闭弹出的表单
function closeWinParamMAP(){
    disappearWinParamMAP();
    returnTypeBackToDoubleParam(temporaryId);    
}
function disappearWinParamMAP(){
    document.getElementById("popOutParamMAP").style.display = "none";
    document.getElementById("popOutBgParamMAP").style.display = "none";
}
// function returnTypeBackToDoubleParam(i){
//     document.getElementById(i).value = "Double";
// }

var temporaryStorageForParamMAP = new Array();
//点击generics弹框的confirm后将填写的值保存到数组
function getValueFromWinParamMAP(){

    
    var memberKey = document.getElementById("memberParamMAPKey").value;
    var memberValue = document.getElementById("memberParamMAPValue").value;
    var implementName = document.getElementById("implementNameTypeParamMAP").value;
    
    var fullClassName;
    if(implementName=="hashmap"){
        fullClassName = "java.util.HashMap";
    }

    var memberArr = getMemberArrMAP(memberKey,memberValue);
    
    var memberImpletFullClass = {"Id":temporaryId,"memberType":memberArr,"implementName":implementName,"fullClassName":fullClassName};
    temporaryStorageForParamMAP.push(memberImpletFullClass);
    // alert(JSON.stringify(temporaryStorageForParamMAP))
    disappearWinParamMAP();
    document.getElementById("memberParamMAPKey").value = "Double";
    document.getElementById("memberParamMAPValue").value = "Double";
    document.getElementById("implementNameTypeParamMAP").value = "hashmap";

}




//////////////////////////////////  Parameter Stack  /////////////////////////////////////////////////////////////////

function showWinParamStack(){
    document.getElementById("popOutParamStack").style.display = "block";
    document.getElementById("popOutBgParamStack").style.display = "block";
}
//关闭弹出的表单
function closeWinParamStack(){
    disappearWinParamStack();
    returnTypeBackToDoubleParam(temporaryId);    
}
function disappearWinParamStack(){
    document.getElementById("popOutParamStack").style.display = "none";
    document.getElementById("popOutBgParamStack").style.display = "none";
}
// function returnTypeBackToDoubleParam(i){
//     document.getElementById(i).value = "Double";
// }

var temporaryStorageForParamStack = new Array();
//点击generics弹框的confirm后将填写的值保存到数组
function getValueFromWinParamStack(){
    var memberType = document.getElementById("memberTypeParamStack").value;
    var implementName = document.getElementById("implementNameTypeParamStack").value;
    // alert(temporaryId)
    var fullClassName;
    if(implementName=="stack"){
        fullClassName = "java.util.Stack";
    }

    var memberArr = getMemberArray(memberType);
    
    var memberImpletFullClass = {"Id":temporaryId,"memberType":memberArr,"implementName":implementName,"fullClassName":fullClassName};
    temporaryStorageForParamStack.push(memberImpletFullClass);
    // alert(JSON.stringify(temporaryStorageForParam))
    disappearWinParamStack();
    document.getElementById("memberTypeParamStack").value = "Double";
    document.getElementById("implementNameTypeParamStack").value = "stack";

}





///////////////////////////////    Parameter Array  ///////////////////////////////////////////////////////

function showWinParamArray(){
    document.getElementById("popOutParamArray").style.display = "block";
    document.getElementById("popOutBgParamArray").style.display = "block";
}
//关闭弹出的表单
function closeWinParamArray(){
    disappearWinParamArray();
    returnTypeBackToDoubleParam(temporaryId);    
}
function disappearWinParamArray(){
    document.getElementById("popOutParamArray").style.display = "none";
    document.getElementById("popOutBgParamArray").style.display = "none";
}
// function returnTypeBackToDoubleParam(i){
//     document.getElementById(i).value = "Double";
// }

var temporaryStorageForParamArray = new Array();
//点击generics弹框的confirm后将填写的值保存到数组
function getValueFromWinParamArray(){
    var memberType = document.getElementById("memberTypeParamArray").value;
    var implementName = document.getElementById("implementNameTypeParamArray").value;
    // alert(temporaryId)
    // var fullClassName;
    // if(implementName=="stack"){
    //     fullClassName = "java.util.Stack";
    // }

    var memberArr = getMemberArray(memberType);
    
    var memberImplet = {"Id":temporaryId,"memberType":memberArr,"implementName":implementName};
    temporaryStorageForParamArray.push(memberImplet);
    // alert(JSON.stringify(temporaryStorageForParam))
    disappearWinParamArray();
    document.getElementById("memberTypeParamArray").value = "Double";
    document.getElementById("implementNameTypeParamArray").value = "array";

}








function paraNumButton(){
    var html="";
    var paraNum = $("#paraNum").val();
    if(paraNum>=1){
        document.getElementById("setParamDat").style.display = "block";
    }

    for(var i=0;i<paraNum;i++){
        html+= "<select id='"+i+"' name='paraSelect' onchange='afterClickParamSelectBox("+i+")'>"+
       // "<option value ='void'>void</option>"+  
        "<option value ='Double'>Double</option>"+     
        "<option value ='Byte'>Byte</option>"+         
        "<option value ='Float'>Float</option>"+       
        "<option value ='Boolean'>Boolean</option>"+       
        "<option value ='Character'>Character</option>"+
        "<option value ='Integer'>Integer</option>"+
        "<option value ='Short'>Short</option>"+
        "<option value ='Long'>Long</option>"+
        "<option value ='String'>String</option>"+
        "<option value ='double'>double</option>"+
        "<option value ='byte'>byte</option>"+
        "<option value ='float'>float</option>"+
        "<option value ='boolean'>boolean</option>"+
        "<option value ='char'>char</option>"+
        "<option value ='int'>int</option>"+
        "<option value ='short'>short</option>"+
        "<option value ='long'>long</option>"+

        "<option value ='listOfParam'>List</option>"+
        "<option value ='mapOfParam'>Map</option>"+
        "<option value ='stackOfParam'>Stack</option>"+
        "<option value='arrayOfParam'>Array</option></select>"
    }

    $("#paraArea").html(html);

}


// 返回 三角 或者 "null"
function getReturnType(typeName){       
    var returnType;
    if(typeName == "void"){
        returnType = "null";
    }
    else if(typeName=="Double" || typeName=="Byte" || typeName=="Float" || typeName=="Boolean" || typeName=="Character" || typeName=="Integer" || typeName=="Short" || typeName=="Long" || typeName=="String" || typeName=="double" || typeName=="byte" || typeName=="float" || typeName=="boolean" || typeName=="char" || typeName=="int" || typeName=="short" || typeName=="long"){
       
        returnType = judgeDataType(typeName);
    }else if(typeName=="list" || typeName=="map" || typeName=="stack"){
        returnType = judgeDataType(typeName);
    }else if(typeName=="array"){
        returnType = judgeDataType(typeName);
    }


    return returnType;
}





//返回{"dataType":"basic","typeName":"String"}  json
function judgeDataType(typeName,i){    
    var dataJson;
    //basic 判断  (parameters,returnType通用)
    if(typeName=="Double" || typeName=="Byte" || typeName=="Float" || typeName=="Boolean" || typeName=="Character" || typeName=="Integer" || typeName=="Short" || typeName=="Long" || typeName=="String" || typeName=="double" || typeName=="byte" || typeName=="float" || typeName=="boolean" || typeName=="char" || typeName=="int" || typeName=="short" || typeName=="long"){
        dataJson = {"dataType":"basic","typeName":typeName};
    }
    //专门用于returnType的list类型
    else if(typeName=="list"){
        
        // {"memberType":memberArr,"implementName":implementName,"fullClassName":fullClassName};
        var memberImpletFullClass = temporaryStorageForReturnType[0];
        var memberArr = memberImpletFullClass.memberType;
        var implementName = memberImpletFullClass.implementName;
        var fullClassName = memberImpletFullClass.fullClassName;
        dataJson = {
            "dataType":"generics",
            "typeName":"list",
            "members":memberArr,
            "implementName":implementName,                                //"linkedlist",  /arraylist
			"fullClassName":fullClassName                     //    /java.util.ArrayList
        };
    }

    //专门用于returnType的map类型
    else if(typeName=="map"){
// var memberImpletFullClass = {"memberType":memberArr,"implementName":implementName,"fullClassName":fullClassName};
        
        var memberImpletFullClassMAP = temporaryStorageForReturnMAP[0];
        var memberArrMAP = memberImpletFullClassMAP.memberType;
        var implementNameMAP = memberImpletFullClassMAP.implementName;
        var fullClassNameMAP = memberImpletFullClassMAP.fullClassName;
        dataJson = {
            "dataType":"generics",
            "typeName":"map",
            "members":memberArrMAP,
            "implementName":implementNameMAP,                    //  "hashmap"
			"fullClassName":fullClassNameMAP                     //    "java.util.HashMap"
        };
        
    }


    //专门用于returnType的stack类型
    else if(typeName=="stack"){
        // {"memberType":memberArr,"implementName":implementName,"fullClassName":fullClassName};
        var memberImpletFullClass = temporaryStorageForReturnStack[0];
        var memberArr = memberImpletFullClass.memberType;
        var implementName = memberImpletFullClass.implementName;
        var fullClassName = memberImpletFullClass.fullClassName;
        dataJson = {
            "dataType":"generics",
            "typeName":"stack",
            "members":memberArr,
            "implementName":implementName,                                //stack
			"fullClassName":fullClassName                     //    "java.util.Stack"
        };
    }


    //专门用于returnType的array类型
    else if(typeName=="array"){
        //     var memberImplet = {"memberType":memberArr,"implementName":implementName};

        var memberImplet = temporaryStorageForReturnArray[0];
        var memberArr = memberImplet.memberType;
        var implementName = memberImplet.implementName;
       
        dataJson = {
            "dataType":"array",
            "typeName":"array",
            "members":memberArr,
            "implementName":implementName,                                
        };
    }



    //用于parameter的list类型，typeName稍有改动
    else if(typeName=="listOfParam"){
        // var memberImpletFullClass = {"Id":temporaryId,"memberType":memberArr,"implementName":implementName,"fullClassName":fullClassName};
    // temporaryStorageForParam.push(memberImpletFullClass);
        var len = temporaryStorageForParam.length;
        for(var x=0;x<len;x++){
            if(temporaryStorageForParam[x].Id==i){
                var memberArr = temporaryStorageForParam[x].memberType;
                var implementName = temporaryStorageForParam[x].implementName;
                var fullClassName = temporaryStorageForParam[x].fullClassName; 

                dataJson = {
                    "dataType":"generics",
                    "typeName":"list",
                    "members":memberArr,
                    "implementName":implementName,                                //"linkedlist",  /arraylist
                    "fullClassName":fullClassName                     //    /java.util.ArrayList
                };

            }
        }

    }

    //用于parameter的map类型
    else if(typeName=="mapOfParam"){
        var len = temporaryStorageForParamMAP.length;
        for(var x=0;x<len;x++){
            if(temporaryStorageForParamMAP[x].Id==i){

//var memberImpletFullClass = {"Id":temporaryId,"memberType":memberArr,"implementName":implementName,"fullClassName":fullClassName};

                var memberArr = temporaryStorageForParamMAP[x].memberType;
                var implementName = temporaryStorageForParamMAP[x].implementName;
                var fullClassName = temporaryStorageForParamMAP[x].fullClassName; 

                dataJson = {
                    "dataType":"generics",
                    "typeName":"map",
                    "members":memberArr,
                    "implementName":implementName,                                //"linkedlist",  /arraylist
                    "fullClassName":fullClassName                     //    /java.util.ArrayList
                };

            }
        }
    }



    else if(typeName=="stackOfParam"){
        var len = temporaryStorageForParamStack.length;
        for(var x=0;x<len;x++){
            if(temporaryStorageForParamStack[x].Id==i){
                var memberArr = temporaryStorageForParamStack[x].memberType;
                var implementName = temporaryStorageForParamStack[x].implementName;
                var fullClassName = temporaryStorageForParamStack[x].fullClassName; 

                dataJson = {
                    "dataType":"generics",
                    "typeName":"stack",
                    "members":memberArr,
                    "implementName":implementName,                       
                    "fullClassName":fullClassName                     
                };

            }
        }
    }




    else if(typeName=="arrayOfParam"){
        var len = temporaryStorageForParamArray.length;
        for(var x=0;x<len;x++){
            if(temporaryStorageForParamArray[x].Id==i){
                var memberArr = temporaryStorageForParamArray[x].memberType;
                var implementName = temporaryStorageForParamArray[x].implementName;
               

                dataJson = {
                    "dataType":"array",
                    "typeName":"array",
                    "members":memberArr,
                    "implementName":implementName                   
                                       
                };

            }
        }
    }
    
    return dataJson;
}





//return the parameters json array
function getParamArray(){    
        var paraArr = new Array();
        var paraNum = $("#paraNum").val();
        for(var i=0;i<paraNum;i++){
            var element = $("#"+i).val();
            var dataJson = judgeDataType(element,i);
            paraArr.push(dataJson);
            
        }
        
        return paraArr;
}


function getSingleFuncComponent(functionName,className,returnType,paramArr){
    var singleFuncComponent;
    singleFuncComponent = {
            "functionName":functionName,
			"className":className,
			"returnTypes":returnType,
			"overLoad":paramArr
    };
    return singleFuncComponent;
}




//add a function button

var functionArray = new Array();

function addFunction(){
        
    var className = $("#className").val();          //get the className
    var functionName = $("#functionName").val();   //get the functionName
    var returnType = $("#returnType").val();      //get the returnType
    var returnTypeJson = getReturnType(returnType);
    var paramArr = getParamArray();                 //get the parameters array

    //new added//////////////////////////////////////////////////////////
    var returnTypesArr = new Array();
    var returnJson = {"returnType":returnTypeJson};
    returnTypesArr.push(returnJson);

    var overLoadArr = new Array();
    var overLoadJson = {"parameters":paramArr};
    overLoadArr.push(overLoadJson);

    var singleFunction;
    singleFunction = getSingleFuncComponent(functionName,className,returnTypesArr,overLoadArr);
    clearInputBox();

    functionArray.push(singleFunction);

    clearArray(temporaryStorageForReturnType);   //returnType List
    clearArray(temporaryStorageForParam);        //parameter List
    clearArray(temporaryStorageForReturnMAP);    //returnType MAP
    clearArray(temporaryStorageForParamMAP);      //parameter  MAP
    clearArray(temporaryStorageForReturnStack);    //returnType Stack
    clearArray(temporaryStorageForParamStack);    //parameter Stack
    clearArray(temporaryStorageForReturnArray);    //returnType array
    clearArray(temporaryStorageForParamArray);    //parameter array


    document.getElementById("simpleVersion").style.display = "none";
    
    document.getElementById("setParamDat").style.display = "none";

    
    document.getElementById("overloadOrSingle").value = "pleaseSelect";
    
}

//clear the input box
function clearInputBox(){
    
        document.getElementById("functionName").value = "";
        document.getElementById("returnType").value = "void";
        document.getElementById("paraNum").value = 0;
        document.getElementById("paraArea").innerHTML = "";
}


// return the json file: {"className":"","allFunctions":[]}
function getJsonFile(){

    var className = $("#className").val();
    var allFunctions = functionArray;
    var assignment_id = GetAssignmentID();

    var finalJson;
    finalJson = {"assignment_id":assignment_id ,"dataType":{
        "className":className,
        "allFunctions":allFunctions
    }};
    var finalJsonToString = JSON.stringify(finalJson);
    // alert(finalJsonToString);
    console.log(finalJsonToString)
    // return finalJsonToString;


    var basepath = "/" + window.location.pathname.split("/")[1];


    $.ajax({
        type: "POST",
        dataType: "json",
        url: basepath+"/teacher/UI/DataTypeUpload",
        contentType: "application/json",
        data: finalJsonToString,
        success: function (result) {
            //console.log("data is :" + JSON.stringify(result));
            if (result.status == "success") {
                success();
                var course=GetCourse();
                var user_name=GetUserName();
                var assignment_id=GetAssignmentID();
                var address = "teacherUploadFile.html?"+course+'&'+user_name+'&'+assignment_id;
                setTimeout(function () {
                    window.location.href= address;
                },3200)
                //setTimeout("window.location.href = address", 3200 )
            }
            else {
                error();
                var course=GetCourse();
                var user_name=GetUserName();
                var assignment_id=GetAssignmentID();
                var address = "teacherSetDataType.html?"+course+'&'+user_name+'&'+assignment_id;
                setTimeout(function () {
                    window.location.href= address;
                },3200)
            }
        }
    });


}






function backToPreviousPage(){

        var course=GetCourse();
        var user_name=GetUserName();
        var assignment_id=GetAssignmentID();
        window.location.href="teacherUploadFile.html?"+course+"&"+user_name+"&"+assignment_id;

}






 //操作成功调用 
 function success(){
    hsycms.success('success','Success',function(){  console.log('操作成功关闭后');  },3200)
}

//操作失败调用
function error(){
    hsycms.error('error','Fail: please fill in the form again',function(){  console.log('操作失败关闭后');  },3200)
}









